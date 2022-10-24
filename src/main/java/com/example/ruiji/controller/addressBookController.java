package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ruiji.Common.BaseContext;
import com.example.ruiji.Common.R;
import com.example.ruiji.pojo.AddressBook;
import com.example.ruiji.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/addressBook")
@RestController
@Slf4j
public class addressBookController {

    @Autowired
    AddressBookService addressBookService;

    @PostMapping
    public R<String> save(@RequestBody AddressBook addressBook, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("user");
        BaseContext.setCurrentID((Long) request.getSession().getAttribute("user"));
        addressBook.setUserId(userId);
        addressBookService.save(addressBook);
        return R.success("添加地址成功");
    }

    @GetMapping("list")
    public R<List<AddressBook>> list(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("user");
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, userId);
        List<AddressBook> addressBooksList = new ArrayList<>();
        addressBooksList = addressBookService.list(queryWrapper);
        return R.success(addressBooksList);
    }

    @PutMapping("default")
    public R<String> SetDefaultAddress(@RequestBody AddressBook addressBook,HttpServletRequest request){
        BaseContext.setCurrentID((Long) request.getSession().getAttribute("user"));
        AddressBook addressBookInMysql=addressBookService.getById(addressBook.getId());
        addressBookInMysql.setIsDefault(new Integer(1));
        addressBookService.updateById(addressBookInMysql);
        return R.success("设置默认地址成功");
    }

    @GetMapping("default")
    public R<AddressBook> getDefaultAddress(HttpServletRequest request){
        Long userId = (Long) request.getSession().getAttribute("user");
        LambdaQueryWrapper<AddressBook> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,userId);
        queryWrapper.eq(AddressBook::getIsDefault,1);

        return R.success(addressBookService.getOne(queryWrapper));
    }
}

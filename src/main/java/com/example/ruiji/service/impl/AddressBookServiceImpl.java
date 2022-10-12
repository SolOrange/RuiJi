package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.pojo.AddressBook;
import com.example.ruiji.service.AddressBookService;
import com.example.ruiji.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author chengwang29
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-08-29 16:28:05
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}





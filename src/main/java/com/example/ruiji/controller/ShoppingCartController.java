package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ruiji.Common.R;
import com.example.ruiji.pojo.Dish;
import com.example.ruiji.pojo.ShoppingCart;
import com.example.ruiji.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart/")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService service;

    @PostMapping("add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request) {

        LambdaQueryWrapper<ShoppingCart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Long userId = (Long) request.getSession().getAttribute("user");

        cartLambdaQueryWrapper.eq(shoppingCart.getDishFlavor() != null,
                ShoppingCart::getDishFlavor, shoppingCart.getDishFlavor());
        cartLambdaQueryWrapper.eq(shoppingCart.getDishId() != null,
                ShoppingCart::getDishId, shoppingCart.getDishId());
        cartLambdaQueryWrapper.eq(shoppingCart.getUserId() != null,
                ShoppingCart::getUserId, userId);
        cartLambdaQueryWrapper.eq(shoppingCart.getSetmealId() != null,
                ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        ShoppingCart shoppingCartInMysql = service.getOne(cartLambdaQueryWrapper);
        if (shoppingCartInMysql == null) {
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(new Date());
            service.save(shoppingCart);
            return R.success(shoppingCart);
        } else {
            shoppingCartInMysql.setNumber(shoppingCartInMysql.getNumber() + 1);
            service.updateById(shoppingCartInMysql);
            return R.success(shoppingCartInMysql);
        }
    }

    @GetMapping("list")
    public R<List<ShoppingCart>> list(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("user");
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userId != null, ShoppingCart::getUserId, userId);
        shoppingCartList = service.list(queryWrapper);
        return R.success(shoppingCartList);
    }

    @DeleteMapping("clean")
    public R<String> delete(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("user");
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userId != null, ShoppingCart::getUserId, userId);
        service.remove(queryWrapper);
        return R.success("删除成功!");
    }

    @PostMapping("sub")
    public R<String> decreaseOne(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        Long userId = (Long) request.getSession().getAttribute("user");
        queryWrapper.eq(shoppingCart.getDishId() != null,
                ShoppingCart::getDishId, shoppingCart.getDishId());
        queryWrapper.eq(userId != null,
                ShoppingCart::getUserId, userId);
        queryWrapper.eq(shoppingCart.getSetmealId() != null,
                ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        queryWrapper.eq(shoppingCart.getDishFlavor() != null,
                ShoppingCart::getDishFlavor, shoppingCart.getDishFlavor());
        ShoppingCart shoppingCartInMysql = service.getOne(queryWrapper);
        Integer newNumber = shoppingCartInMysql.getNumber() - 1;
        if (newNumber > 0) {
            shoppingCartInMysql.setNumber(newNumber);
            service.updateById(shoppingCartInMysql);
        } else{
            service.remove(queryWrapper);
        }
        return R.success("减少成功！");
    }
}

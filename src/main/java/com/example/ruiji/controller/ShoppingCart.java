package com.example.ruiji.controller;

import com.example.ruiji.Common.R;
import com.example.ruiji.pojo.Dish;
import com.example.ruiji.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("shoppingCart")
public class ShoppingCart {

    @Autowired
    ShoppingCartService service;

    public R<List<Dish>> list(){

        return null;
    }
}

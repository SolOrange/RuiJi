package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.pojo.ShoppingCart;
import com.example.ruiji.service.ShoppingCartService;
import com.example.ruiji.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author chengwang29
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2022-08-29 16:36:52
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}





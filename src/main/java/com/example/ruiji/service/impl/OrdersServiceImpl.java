package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.pojo.Orders;
import com.example.ruiji.service.OrdersService;
import com.example.ruiji.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author chengwang29
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2022-08-29 16:36:34
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}





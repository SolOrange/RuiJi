package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.pojo.OrderDetail;
import com.example.ruiji.service.OrderDetailService;
import com.example.ruiji.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author chengwang29
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-08-29 16:29:06
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}





package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.Common.BaseContext;
import com.example.ruiji.Common.R;
import com.example.ruiji.pojo.OrderDetail;
import com.example.ruiji.pojo.Orders;
import com.example.ruiji.pojo.ShoppingCart;
import com.example.ruiji.service.*;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/order")
@RestController
@Slf4j
public class OrederController {

    @Autowired
    OrdersService ordersService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    UserService userService;

    @Autowired
    AddressBookService addressBookService;

    @Autowired
    OrderDetailService detailService;

    @Transactional
    @PostMapping("submit")
    public R<String> submit(@RequestBody Orders orders, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("user");
        BaseContext.setCurrentID((Long) request.getSession().getAttribute("user"));
        orders.setUserId(userId);
        orders.setAddress(addressBookService.getById(orders.getAddressBookId()).getDetail());
        orders.setConsignee(addressBookService.getById(orders.getAddressBookId()).getConsignee());
        orders.setPhone(addressBookService.getById(orders.getAddressBookId()).getPhone());
        Long orderNum = IdWorker.getId();
        orders.setNumber(orderNum.toString());
        orders.setOrderTime(new Date());
        orders.setCheckoutTime(new Date());
        orders.setAmount(new BigDecimal(0));
        ordersService.save(orders);
        BigDecimal totalAmount = new BigDecimal(0);
        //向订单明细表里添加订单的菜品
        LambdaQueryWrapper<ShoppingCart> shoppingCartQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartQueryWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(shoppingCartQueryWrapper);

        for (ShoppingCart item : shoppingCartList
        ) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setAmount(item.getAmount());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setImage(item.getImage());
            orderDetail.setName(item.getName());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setNumber(item.getNumber());
            orderDetail.setAmount(item.getAmount());
            LambdaQueryWrapper<Orders> orderQueryWrapper = new LambdaQueryWrapper<>();
            orderQueryWrapper.eq(Orders::getNumber, orderNum);
            Orders ordersInMysql = ordersService.getOne(orderQueryWrapper);
            orderDetail.setOrderId(ordersInMysql.getId());
            totalAmount = totalAmount.add(orderDetail.getAmount().multiply(new BigDecimal(orderDetail.getNumber())));
            ordersInMysql.setAmount(totalAmount);
            ordersService.updateById(ordersInMysql);
            detailService.save(orderDetail);

        }
        return R.success("下单成功！");
    }

    @GetMapping("userPage")
    public R<Page> userPage(int page, int pageSize, String name) {
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> ordersQueryWrapper=new LambdaQueryWrapper<>();
        ordersQueryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(pageInfo,ordersQueryWrapper);
        return R.success(pageInfo);
    }

}

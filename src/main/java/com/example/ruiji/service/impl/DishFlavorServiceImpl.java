package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.pojo.DishFlavor;
import com.example.ruiji.service.DishFlavorService;
import com.example.ruiji.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author chengwang29
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-08-29 16:28:53
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}





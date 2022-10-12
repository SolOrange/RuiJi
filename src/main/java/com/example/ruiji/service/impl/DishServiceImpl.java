package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.Common.R;
import com.example.ruiji.dto.DishDto;
import com.example.ruiji.pojo.Category;
import com.example.ruiji.pojo.Dish;
import com.example.ruiji.pojo.DishFlavor;
import com.example.ruiji.service.CategoryService;
import com.example.ruiji.service.DishFlavorService;
import com.example.ruiji.service.DishService;
import com.example.ruiji.mapper.DishMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chengwang29
 * @description 针对表【dish(菜品管理)】的数据库操作Service实现
 * @createDate 2022-08-29 16:28:48
 */
@Service
@Transactional
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
        implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;


    @Override
    public void saveWithFlavors(DishDto dishDto) {
        this.save(dishDto);
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        DishDto dishDto = new DishDto();
        Dish dish = new Dish();
        dish = this.getById(id);
        BeanUtils.copyProperties(dish, dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavorList = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavorList);

        return dishDto;
    }

    @Override
    public void updateWithFlavors(DishDto dishDto) {
        //修改dish
        this.updateById(dishDto);

        //删除dish对应的flavor
        LambdaQueryWrapper<DishFlavor> flavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        flavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(flavorLambdaQueryWrapper);
        //更新新的flavor
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);

    }


}





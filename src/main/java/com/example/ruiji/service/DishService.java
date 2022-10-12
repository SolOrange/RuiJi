package com.example.ruiji.service;

import com.example.ruiji.dto.DishDto;
import com.example.ruiji.pojo.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chengwang29
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-08-29 16:28:48
*/
public interface DishService extends IService<Dish> {

    void saveWithFlavors(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavors(DishDto dishDto);
}

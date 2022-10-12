package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.dto.SetmealDto;
import com.example.ruiji.pojo.DishFlavor;
import com.example.ruiji.pojo.Setmeal;
import com.example.ruiji.pojo.SetmealDish;
import com.example.ruiji.service.SetmealDishService;
import com.example.ruiji.service.SetmealService;
import com.example.ruiji.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chengwang29
 * @description 针对表【setmeal(套餐)】的数据库操作Service实现
 * @createDate 2022-08-29 16:36:41
 */
@Service
@Transactional
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
        implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setMealDishList = setmealDto.getSetmealDishes();
        setMealDishList = setMealDishList.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId().toString());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setMealDishList);
        return;

    }
}





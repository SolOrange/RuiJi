package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.Common.CustomerException;
import com.example.ruiji.pojo.Category;
import com.example.ruiji.pojo.Dish;
import com.example.ruiji.pojo.Setmeal;
import com.example.ruiji.service.CategoryService;
import com.example.ruiji.mapper.CategoryMapper;
import com.example.ruiji.service.DishService;
import com.example.ruiji.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chengwang29
 * @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
 * @createDate 2022-08-29 16:28:40
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;

    @Override
    public boolean remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();

        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);

        int dishCount =dishService.count(dishLambdaQueryWrapper);

        LambdaQueryWrapper<Setmeal> setMealLambdaQueryWrapper=new LambdaQueryWrapper<>();

        setMealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);

        int mealCount=setmealService.count(setMealLambdaQueryWrapper);

        if(dishCount>0){
            throw new CustomerException("菜品类别有菜品关联，无法删除！");
        } else if (mealCount>0) {
            throw new CustomerException("菜品类别有套餐关联，无法删除！");
        }
        removeById(id);
        return true;
    }
}





package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.Common.BaseContext;
import com.example.ruiji.Common.R;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ruiji.Utils.SMSUtils;
import com.example.ruiji.Utils.ValidateCodeUtils;
import com.example.ruiji.dto.DishDto;
import com.example.ruiji.dto.SetmealDto;
import com.example.ruiji.pojo.Dish;
import com.example.ruiji.pojo.Setmeal;
import com.example.ruiji.pojo.SetmealDish;
import com.example.ruiji.service.CategoryService;
import com.example.ruiji.service.SetmealDishService;
import com.example.ruiji.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody SetmealDto setmealDto) {
        BaseContext.setCurrentID((Long) request.getSession().getAttribute("employee"));
        setmealService.saveWithDish(setmealDto);
        return R.success("添加成功");
    }

    @GetMapping("page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> SetMealPage = new Page<>(page, pageSize);
        Page<SetmealDto> SetMealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name != null, Setmeal::getName, name);

        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(SetMealPage, queryWrapper);
        BeanUtils.copyProperties(SetMealPage, SetMealDtoPage, "records");

        List<Setmeal> records = SetMealPage.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            setmealDto.setCategoryName(categoryService.getById(item.getCategoryId()).getName());
            LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId, item.getId());
            setmealDto.setSetmealDishes(setmealDishService.list(setmealDishLambdaQueryWrapper));
            return setmealDto;
        }).collect(Collectors.toList());

        SetMealDtoPage.setRecords(list);
        return R.success(SetMealDtoPage);
    }

    @DeleteMapping
    public R<String> remove(Long ids) {

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(SetmealDish::getSetmealId,setmealService.getById(ids).getId());
        setmealDishService.remove(queryWrapper);
        setmealService.removeById(ids);
        return R.success("删除成功");

    }

}

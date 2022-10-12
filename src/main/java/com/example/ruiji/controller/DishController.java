package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.Common.BaseContext;
import com.example.ruiji.Common.R;
import com.example.ruiji.dto.DishDto;
import com.example.ruiji.dto.SetmealDto;
import com.example.ruiji.pojo.Category;
import com.example.ruiji.pojo.Dish;
import com.example.ruiji.service.CategoryService;
import com.example.ruiji.service.DishFlavorService;
import com.example.ruiji.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService flavorService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody DishDto dishDto) {
        BaseContext.setCurrentID((Long) request.getSession().getAttribute("employee"));
        dishService.saveWithFlavors(dishDto);
        return R.success("添加成功");
    }

    @GetMapping("page")
    public R<Page> page(int page, int pageSize, String name) {

        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name != null, Dish::getName, name);

        queryWrapper.orderByDesc(Dish::getUpdateTime);

        dishService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();

            Category category = categoryService.getById(categoryId);
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);

    }

    @GetMapping("{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody DishDto dishDto) {
        BaseContext.setCurrentID((Long) request.getSession().getAttribute("employee"));
        dishService.updateWithFlavors(dishDto);
        return R.success("修改成功");
    }

    @GetMapping("list")
    public R<List<Dish>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> dishList=dishService.list(queryWrapper);

        return R.success(dishList);
    }




}

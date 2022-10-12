package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.Common.BaseContext;
import com.example.ruiji.Common.R;
import com.example.ruiji.pojo.Category;
import com.example.ruiji.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    /**
     * 新增菜品以及套餐
     *
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Category category) {
        BaseContext.setCurrentID((Long) request.getSession().getAttribute("employee"));
        service.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 菜品分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("page")
    public R<Page> page(int page, int pageSize) {
        //分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        //排序条件
        wrapper.orderByAsc(Category::getSort);
        //进行分页查询
        service.page(pageInfo, wrapper);

        return R.success(pageInfo);

    }

    /**
     * 根据ID删除对应菜品
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> remove(Long ids) {
        if (ids == null)
            return R.error("ID为空");
        if (service.remove(ids)) {
            return R.success("删除成功！");
        } else {
            return R.error("删除失败！");
        }
    }

    /**
     * 更新分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Category category){

        if(category==null)
            return R.error("信息为空，更新失败!");
        BaseContext.setCurrentID((Long) request.getSession().getAttribute("employee"));
        service.updateById(category);
        return R.success("更新成功!");

    }

    /**
     * 条件查询菜品
     * @param category
     * @return
     */
    @GetMapping("list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();

        wrapper.eq(category.getType()!=null,Category::getType,category.getType());
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list=service.list(wrapper);
        return R.success(list);
    }


}

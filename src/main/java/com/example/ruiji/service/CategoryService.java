package com.example.ruiji.service;

import com.example.ruiji.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chengwang29
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-08-29 16:28:40
*/
public interface CategoryService extends IService<Category> {

    public boolean remove(Long id);
}

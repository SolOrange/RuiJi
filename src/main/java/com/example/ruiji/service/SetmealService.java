package com.example.ruiji.service;

import com.example.ruiji.dto.SetmealDto;
import com.example.ruiji.pojo.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chengwang29
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-08-29 16:36:41
*/
public interface SetmealService extends IService<Setmeal> {

    void saveWithDish(SetmealDto setmealDto);
}

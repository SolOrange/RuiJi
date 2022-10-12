package com.example.ruiji.dto;


import com.example.ruiji.pojo.Setmeal;
import com.example.ruiji.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}

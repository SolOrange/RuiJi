package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.pojo.Employee;
import com.example.ruiji.service.EmployeeService;
import com.example.ruiji.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author chengwang29
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-08-29 16:29:01
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

}





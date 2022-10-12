package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.pojo.User;
import com.example.ruiji.service.UserService;
import com.example.ruiji.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author chengwang29
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-08-29 16:36:58
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}





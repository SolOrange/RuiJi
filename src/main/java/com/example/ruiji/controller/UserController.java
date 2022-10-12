package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ruiji.Common.R;
import com.example.ruiji.Utils.SMSUtils;
import com.example.ruiji.Utils.ValidateCodeUtils;
import com.example.ruiji.pojo.User;
import com.example.ruiji.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserService service;

    private String Vercode;

    @PostMapping("sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        String code = ValidateCodeUtils.generateValidateCode4String(4);
        log.info(code);
        if (StringUtils.hasText(phone)) {
            //SMSUtils.sendMessage("阿里云短信测试", "SMS_154950909", phone, code);
            session.setAttribute(phone, code);
            //this.Vercode=code;
            return R.success("发送成功");
        }
        return R.error("发送失败");
    }

    @PostMapping("login")
    public R<String> login(@RequestBody Map map, HttpSession session, HttpServletRequest request) {
        log.info(map.toString());
        String phone = (String) map.get("phone");
        Object codeSession = session.getAttribute(map.get("phone").toString());

        if (codeSession == null || !codeSession.equals(map.get("code").toString())) {
            return R.error("验证码错误");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        User user = service.getOne(queryWrapper);
        if (user==null) {
            User newUser = new User();
            newUser.setPhone(phone);
            service.save(newUser);
            request.getSession().setAttribute("user",newUser.getId());
        }
        request.getSession().setAttribute("user",user.getId());
        return R.success("登陆成功");
    }


}

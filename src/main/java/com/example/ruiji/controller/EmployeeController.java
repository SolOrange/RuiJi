package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.Common.BaseContext;
import com.example.ruiji.Common.R;
import com.example.ruiji.pojo.Employee;
import com.example.ruiji.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    /**
     * 员工登陆
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        //将页面传来的密码进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        //查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        if (emp == null)
            return R.error("登陆失败！");
        if (!emp.getPassword().equals(password))
            return R.error("密码错误!");
        if (emp.getStatus() == 0)
            return R.error("用户已被禁用");
        request.getSession().setAttribute("employee", emp.getId());

        return R.success(emp);
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功！");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        BaseContext.setCurrentID((Long) request.getSession().getAttribute("employee"));
        //通过MD5加密设置初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));


        employeeService.save(employee);

        return R.success("添加成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("page")
    public R<Page> page(int page, int pageSize, String name) {
        //构造分页构造器
        Page pageInfo = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<Employee>();
        //添加过滤条件
        queryWrapper.like(StringUtils.hasLength(name), Employee::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 更新用户状态
     *
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {

/*        Long id = Thread.currentThread().getId();

        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<>();*/
        BaseContext.setCurrentID((Long) request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return R.success("更新成功");
    }

    /**
     * 根据id查找员工
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null)
            return R.success(employee);
        else
            return R.error("不存在");
    }
}

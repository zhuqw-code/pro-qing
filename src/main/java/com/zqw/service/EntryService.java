package com.zqw.service;

import cn.hutool.core.util.StrUtil;
import com.zqw.entity.Account;
import com.zqw.entity.Admin;
import com.zqw.entity.Employee;
import com.zqw.exception.CustomException;
import com.zqw.mapper.AdminMapper;
import com.zqw.mapper.EmployeeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;

@Service
public class EntryService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private AdminMapper adminMapper;
    /**
     * 查找数据库判断是否有该用户
     * !!! 注意这里必须要分开查询不同角色的数据库
     * @param account 多角色登录
     */
    @PostMapping("/login")
    public Account login(Account account) {
        Account act = null;
        // 先通过账号查询用户信息
        String username = account.getUsername();
        if ("MAN".equals(account.getRole())) {
            // 调用adminMapper的查询方法
            act = adminMapper.selectByUsername(username);

        } else if ("EMP".equals(account.getRole())) {
            act = employeeMapper.selectByUsername(username);
        }
        if (act == null){
            // 如果没有就抛出异常
            throw new CustomException("500", "用户名不存在!!!");
        }
        // 存在当前用户名的用户，判断密码是否正确
        if (!act.getPassword().equals(account.getPassword())){
            throw new CustomException("500", "您输入的密码有误!!!");
        }
        return act;
    }

    /**
     * 将新用户信息存储到数据库中
     * @param employee 前端输入的用户信息
     *                 只有用户/员工能注册，管理员只能是管理员添加
     */
    public void register(Employee employee) {
        // 是否要判断用户名被占用
        String username = employee.getUsername();
        Employee empl = employeeMapper.selectByUsername(username);
        if (empl != null){
            throw new CustomException("500", "用户名已被占用");
        }
        empl = employeeMapper.selectByNo(employee.getNo());
        // 判断工号是否被占用
        if (empl != null) {
            throw new CustomException("500", "工号已占用");
        }
        // 为一些属性设置默认值
        if (StrUtil.isBlank(employee.getPassword())){
            employee.setPassword("666");
        }
        if (StrUtil.isBlank(employee.getName())){
            employee.setName("海公牛！！！");
        }
        if (StrUtil.isBlank(employee.getRole())){
            employee.setRole("EMP");
        }
        // 否则能够添加该用户
        employeeMapper.add(employee);
    }

    /**
     * 更新密码
     * @param account 更新密码所需的id和password和newPassword
     */
    public void updatePassword(Account account) {
        Account act = null;
        String role = account.getRole();
        if ("EMP".equals(role)) {
            // 调用employeeMapper对employee数据库进行修改
            act = employeeMapper.selectById(account.getId());
        } else if ("MAN".equals(role)) {
            // 调用adminMapper对admin数据库进行修改
            act = adminMapper.selectById(account.getId());
        }
        if (act == null) {
            throw new CustomException("500", "无法找到相关账号信息, 请联系管理员！！！");
        } else {
            // 判断根据账号从数据库中查询到的数据的密码与前端输入的密码进行比较
            if (!act.getPassword().equals(account.getPassword())) {
                throw new CustomException("500", "密码错误请重新尝试！！！");
            }
        }
        // 将account持久化到数据库
        act.setPassword(account.getNewPassword());
        if ("EMP".equals(role)) {
            // 更改employee数据库
            Employee employee = new Employee();
            BeanUtils.copyProperties(act, employee);
            employeeMapper.modify(employee);
        } else if ("MAN".equals(role)) {
            // 更改admin数据库
            Admin admin = new Admin();
            BeanUtils.copyProperties(act, admin);
            adminMapper.modify(admin);
        }
    }
}

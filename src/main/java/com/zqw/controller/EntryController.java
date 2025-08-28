package com.zqw.controller;

import com.zqw.common.Result;
import com.zqw.entity.Account;
import com.zqw.entity.Employee;
import com.zqw.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class EntryController {

    @Autowired
    private EntryService entryService;

    /**
     * TODO: 这里有多个角色，不能只用Employee接收，故我们通过基类来实现多角色登录Account
     * @param account 使用基类管理多角色登录
     * @return
     */
    @PostMapping("/login")
    public Result Login(@RequestBody Account account){
        Account act = entryService.login(account);// 查询数据库是否能够查询到是该账号和密码的用户
        return Result.success(act);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Employee employee){
        // System.out.println(map);
        entryService.register(employee);
        return Result.success();
    }

    /**
     * 修改密码
     * @param account
     * @return
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody() Account account){
        entryService.updatePassword(account);
        return Result.success();
    }
}

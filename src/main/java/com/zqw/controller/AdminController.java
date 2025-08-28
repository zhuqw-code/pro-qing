package com.zqw.controller;

import com.github.pagehelper.PageInfo;
import com.zqw.common.Result;
import com.zqw.entity.Admin;
import com.zqw.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/all")
    public Result selectAll(){
        List<Admin> list = adminService.selectAll();
        return Result.success(list);
    }

    @GetMapping("/id/{id}/{username}")
    public Result selectById(@PathVariable Integer id, @PathVariable String username){    // 需要在url中指定参数名
        System.out.println("id = " + id + ",username = " + username);
        Admin admin = adminService.selectById(id);
        return Result.success(admin);
    }
    @GetMapping("/one")    // 前端query参数，传递的参数如果后端有就必须传（只能多传不能少传）
    public Result selectOne(@RequestParam Integer id, @RequestParam String username){      // 不需要指定参数名，只需要保证形参名和url中变量名相同即可
        System.out.println("id = " + id + ",username = " + username);
        Admin admin = adminService.selectById(id);
        return Result.success(admin);
    }

    @GetMapping("/selectList")                    // 能够将属性映射到employee属性上
    public Result selectList(Admin admin){     // get请求能够将query参数转化为对象中的属性
        List<Admin> list = adminService.selectList(admin);
        return Result.success(list);
    }

    // @GetMapping("/page")
    // public Result pageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
    //                         @RequestParam(defaultValue = "10") Integer pageSize){
    //     PageInfo<Employee> page = employeeService.pageQuery(pageNum, pageSize);
    //     return Result.success(page);
    // }

    @GetMapping("/page")
    public Result pageQuery(
                            Admin admin,      // 可将query参数中的归属于employee的属性赋值给该对象
                            @RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Admin> page = adminService.pageQuery(admin, pageNum, pageSize);
        return Result.success(page);
    }

    // Post请求实现新增数据
    @PostMapping("/add")
    public Result add(@RequestBody Admin admin){
        adminService.add(admin);
        return Result.success();
    }
    // Put实现更新
    @PutMapping("/modify")
    public Result modify(@RequestBody Admin admin){
        adminService.modify(admin);
        return Result.success();
    }
    // Delete删除单个信息
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        adminService.delete(id);
        return Result.success();
    }

    /**
     * 批量删除
     * @param list id数组
     * @return xxx
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Integer> list){
        adminService.removeBatch(list);
        return Result.success();
    }
}

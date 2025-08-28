package com.zqw.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zqw.entity.Admin;
import com.zqw.entity.Employee;
import com.zqw.mapper.AdminMapper;
import com.zqw.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public List<Admin> selectAll() {
        return adminMapper.selectAll();
    }

    public Admin selectById(Integer id) {
        return adminMapper.selectById(id);
    }

    public List<Admin> selectList(Admin admin) {
        System.out.println(admin);
        return null;
    }

    public PageInfo<Admin> pageQuery(Admin admin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);     // 告诉PageHelper要查询哪个页面
        // List<Employee> list = employeeMapper.selectAll();  // 总数据
        List<Admin> list = adminMapper.fuzzyQuery(admin);  // 总数据
        return PageInfo.of(list);     // 上面两者结合的关键【PageInfo将查到的数据封装为特定对象】
    }

    public void add(Admin admin) {
        adminMapper.add(admin);
    }

    /**
     * 更新数据
     * @param admin
     */
    public void modify(Admin admin) {
        adminMapper.modify(admin);
    }

    /**
     * 删除id=id的用户
     * @param id
     */
    public void delete(Integer id) {
        adminMapper.delete(id);
    }

    /**
     * 批量删除
     * @param list
     */
    public void removeBatch(List<Integer> list) {
        adminMapper.removeBatch(list);
    }
}

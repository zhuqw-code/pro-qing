package com.zqw.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zqw.entity.Employee;
import com.zqw.exception.CustomException;
import com.zqw.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> selectAll() {
        return employeeMapper.selectAll();
    }

    public Employee selectById(Integer id) {
        return employeeMapper.selectById(id);
    }

    public List<Employee> selectList(Employee employee) {
        System.out.println(employee);
        return null;
    }

    public PageInfo<Employee> pageQuery(Employee employee, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);     // 告诉PageHelper要查询哪个页面
        // List<Employee> list = employeeMapper.selectAll();  // 总数据
        List<Employee> list = employeeMapper.fuzzyQuery(employee);  // 总数据
        return PageInfo.of(list);     // 上面两者结合的关键【PageInfo将查到的数据封装为特定对象】
    }

    public void add(Employee employee) {
        employeeMapper.add(employee);
    }

    /**
     * 更新数据
     * @param employee
     */
    public void modify(Employee employee) {
        employeeMapper.modify(employee);
    }

    /**
     * 删除id=id的用户
     * @param id
     */
    public void delete(Integer id) {
        employeeMapper.delete(id);
    }

    /**
     * 批量删除
     * @param list
     */
    public void removeBatch(List<Integer> list) {
        employeeMapper.removeBatch(list);
    }
}

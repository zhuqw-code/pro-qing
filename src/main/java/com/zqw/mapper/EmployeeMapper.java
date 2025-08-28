package com.zqw.mapper;

import com.zqw.entity.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper{
    @Select("select * from employee")
    List<Employee> selectAll();

    @Select("select * from employee where id = #{id}")
    Employee selectById(Integer id);

    void add(Employee employee);

    void modify(Employee employee);

    /**
     * 根据Employee对象的参数信息进行模糊查询
     * @param employee 模糊查询的信息
     * @return 返回查询结果集
     */
    List<Employee> fuzzyQuery(Employee employee);

    /**
     * 根据id删除数据
     * @param id
     */
    @Delete("delete from employee where id = #{id}")
    void delete(Integer id);

    /**
     * 批量删除list集合中的id记录
     * @param list
     */
    void removeBatch(List<Integer> list);

    /**
     * 根据前端返回的账号和密码查询数据库是否含有该用户的信息
     * @param employee 账号和密码
     * @return 查询到的用户信息
     */
    @Select("select * from where username = #{username} and password = #{password}")
    Employee login(Employee employee);

    @Select("select * from employee where username = #{useranme}")
    Employee selectByUsername(String username);

    /**
     * 根据工号查询是否被暂用
     * @param no
     * @return
     */
    @Select("select * from employee where no = #{no}")
    Employee selectByNo(String no);
}

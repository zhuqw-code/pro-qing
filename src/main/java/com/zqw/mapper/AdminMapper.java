package com.zqw.mapper;

import com.zqw.entity.Admin;
import com.zqw.entity.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("select * from admin")
    List<Admin> selectAll();

    @Select("select * from admin where id = #{id}")
    Admin selectById(Integer id);

    void add(Admin admin);

    void modify(Admin admin);

    /**
     * 根据Employee对象的参数信息进行模糊查询
     * @param admin 模糊查询的信息
     * @return 返回查询结果集
     */
    List<Admin> fuzzyQuery(Admin admin);

    /**
     * 根据id删除数据
     * @param id
     */
    @Delete("delete from admin where id = #{id}")
    void delete(Integer id);

    /**
     * 批量删除list集合中的id记录
     * @param list
     */
    void removeBatch(List<Integer> list);

    /**
     * 根据前端返回的账号和密码查询数据库是否含有该用户的信息
     * @param admin 账号和密码
     * @return 查询到的用户信息
     */
    @Select("select * from where username = #{username} and password = #{password}")
    Admin login(Admin admin);

    @Select("select * from admin where username = #{useranme}")
    Admin selectByUsername(String username);
}

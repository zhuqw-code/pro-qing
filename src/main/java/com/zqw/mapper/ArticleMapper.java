package com.zqw.mapper;

import com.zqw.entity.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Select("select * from article")
    List<Article> selectAll();

    @Select("select * from article where id = #{id}")
    Article selectById(Integer id);

    void add(Article article);

    void modify(Article article);

    /**
     * 根据Employee对象的参数信息进行模糊查询
     * @param article 模糊查询的信息
     * @return 返回查询结果集
     */
    List<Article> fuzzyQuery(Article article);

    /**
     * 根据id删除数据
     * @param id
     */
    @Delete("delete from article where id = #{id}")
    void delete(Integer id);

    /**
     * 批量删除list集合中的id记录
     * @param list
     */
    void removeBatch(List<Integer> list);

    /**
     * 根据前端返回的账号和密码查询数据库是否含有该用户的信息
     * @param article 账号和密码
     * @return 查询到的用户信息
     */
    @Select("select * from where username = #{username} and password = #{password}")
    Article login(Article article);

    @Select("select * from article where username = #{useranme}")
    Article selectByUsername(String username);
}

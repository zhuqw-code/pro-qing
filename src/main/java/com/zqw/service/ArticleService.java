package com.zqw.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zqw.entity.Article;
import com.zqw.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    public List<Article> selectAll() {
        return articleMapper.selectAll();
    }

    public Article selectById(Integer id) {
        return articleMapper.selectById(id);
    }

    public List<Article> selectList(Article article) {
        System.out.println(article);
        return null;
    }

    public PageInfo<Article> pageQuery(Article article, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);     // 告诉PageHelper要查询哪个页面
        // List<Employee> list = employeeMapper.selectAll();  // 总数据
        List<Article> list = articleMapper.fuzzyQuery(article);  // 总数据
        return PageInfo.of(list);     // 上面两者结合的关键【PageInfo将查到的数据封装为特定对象】
    }

    public void add(Article article) {
        articleMapper.add(article);
    }

    /**
     * 更新数据
     * @param article
     */
    public void modify(Article article) {
        articleMapper.modify(article);
    }

    /**
     * 删除id=id的用户
     * @param id
     */
    public void delete(Integer id) {
        articleMapper.delete(id);
    }

    /**
     * 批量删除
     * @param list
     */
    public void removeBatch(List<Integer> list) {
        articleMapper.removeBatch(list);
    }
}

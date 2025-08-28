package com.zqw.controller;

import com.github.pagehelper.PageInfo;
import com.zqw.common.Result;
import com.zqw.entity.Article;
import com.zqw.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/all")
    public Result selectAll(){
        List<Article> list = articleService.selectAll();
        return Result.success(list);
    }

    @GetMapping("/id/{id}")
    public Result selectById(@PathVariable Integer id){    // 需要在url中指定参数名
        Article article = articleService.selectById(id);
        return Result.success(article);
    }

    @GetMapping("/page")
    public Result pageQuery(
                            Article article,      // 可将query参数中的归属于employee的属性赋值给该对象
                            @RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Article> page = articleService.pageQuery(article, pageNum, pageSize);
        return Result.success(page);
    }

    // Post请求实现新增数据
    @PostMapping("/add")
    public Result add(@RequestBody Article article){
        articleService.add(article);
        return Result.success();
    }
    // Put实现更新
    @PutMapping("/modify")
    public Result modify(@RequestBody Article article){
        articleService.modify(article);
        return Result.success();
    }



    // Delete删除单个信息
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        articleService.delete(id);
        return Result.success();
    }

    /**
     * 批量删除
     * @param list id数组
     * @return xxx
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Integer> list){
        articleService.removeBatch(list);
        return Result.success();
    }
}

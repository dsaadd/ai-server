package com.ai.server.controller;


import cn.hutool.core.date.DateTime;
import com.ai.server.beans.News;
import com.ai.server.common.Result;
import com.ai.server.exception.CustomException;
import com.ai.server.service.INewsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * <p>
 * 资讯表 前端控制器
 * </p>
 * @author bruce
 */
@RestController
@RequestMapping("/news")
@Tag(name = "资讯接口", description = "资讯接口AI") // 类级注解
public class NewsController {

    @Autowired
    INewsService newsService;

    /**
     * 分页查询
     *
     * @return
     */
    @Operation(summary = "根据标题模糊查询分页", description = "根据标题模糊查询分页") // 方法级注解
    @GetMapping("selectPage")
    public Result selectPage(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam("title") String title) {
        //使用分页插件
        PageHelper.startPage(pageNum, pageSize);
        //查询数据库，需要用到like查询
        List<News> list = newsService.list(new QueryWrapper<News>().like("title", title).eq("del", "0").orderByDesc("id"));
        //封装一下查询的结果集
        PageInfo<News> pageInfo = PageInfo.of(list);
        return Result.success(pageInfo);
    }

    /**
     * 新增接口
     *
     * @return
     */
    @Operation(summary = "新增资讯信息", description = "新增资讯信息") // 方法级注解
    @PostMapping("add")
    public Result add(@RequestBody News news) throws CustomException {
        News one = newsService.getOne(new QueryWrapper<News>().eq("title", news.getTitle()).eq("del", "0"));
        if (one == null) {
            news.setDel("0");//删除状态
            news.setCreateTime(DateTime.now().toString()); //设置系统时间
            boolean b = newsService.save(news);
            if (b) {
                return Result.success();
            } else {
                return Result.error();
            }
        } else {
            throw new CustomException("资讯不可重复");
        }
    }

    /**
     * 更新
     */
    @Operation(summary = "更新资讯信息", description = "更新资讯信息") // 方法级注解
    @PutMapping("update")
    public Result update(@RequestBody News news) {
        boolean b = newsService.updateById(news);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }


    /**
     * 删除
     */
    @Operation(summary = "根据ID删除资讯信息", description = "根据ID删除资讯信息") // 方法级注解
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        News news = new News();
        news.setId(id);
        news.setDel("1");//删除
        boolean b = newsService.updateById(news);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 更新资讯点击次数
     */
    @Operation(summary = "更新资讯点击次数", description = "更新资讯点击次数") // 方法级注解
    @PutMapping("updateCount")
    public Result updateCount(@RequestBody News news) {
        News n = newsService.getById(news.getId());
        n.setCount(n.getCount() + 1); //点击次数+1
        boolean b = newsService.updateById(n);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

}

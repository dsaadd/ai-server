package com.ai.server.controller;

import com.ai.server.beans.Book;
import com.ai.server.beans.House;
import com.ai.server.beans.Leavemsg;
import com.ai.server.common.Result;
import com.ai.server.service.IBookService;
import com.ai.server.service.ILeavemsgService;
import com.ai.server.service.ILoginlogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "登录日志接口", description = "登录日志接口AI") // 类级注解
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private IBookService houseService;

    @Autowired
    private ILeavemsgService leavemsgService;

    @Autowired
    private ILoginlogService loginlogService;

    @Operation(summary = "查询当前登录用户的登录日志", description = "查询当前登录用户的登录日志") // 方法级注解
    @GetMapping("/overview")
    public Result getStatisticsOverview() {
        Map<String, Object> statistics = new HashMap<>();

        // 获取总房源数
        Long totalHouse = houseService.count(new QueryWrapper<Book>().eq("del",0));
        statistics.put("totalHouse", totalHouse);

        // 获取活跃用户数（最近5天内登录）
        Long activeUsers = loginlogService.getActiveUserCount(5);
        statistics.put("activeUsers", activeUsers);

        // 留言总数
        Long totalLeavemsg = leavemsgService.count(new QueryWrapper<Leavemsg>().eq("del",0));
        statistics.put("", totalLeavemsg);

        // 预定金额
        Long bookHouse = houseService.count(new QueryWrapper<Book>().eq("del",0).eq("status",1));
        statistics.put("bookHouse", bookHouse);

        return Result.success(statistics);
    }
}

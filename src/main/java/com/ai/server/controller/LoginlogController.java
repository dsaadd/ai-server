package com.ai.server.controller;


import com.ai.server.beans.Loginlog;
import com.ai.server.common.Result;
import com.ai.server.service.ILoginlogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author bruce
 */
@Tag(name = "登录日志接口", description = "登录日志接口AI") // 类级注解
@RestController
@RequestMapping("/loginlog")
@Slf4j
public class LoginlogController {

    @Resource
    ILoginlogService loginlogService;

    /**
     * 分页查询
     * @return
     */
    @Operation(summary = "查询当前登录用户的登录日志", description = "查询当前登录用户的登录日志") // 方法级注解
    @GetMapping("selectPage")
    public Result selectPage(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam("username") String username,
                             HttpServletRequest request) {
        //使用分页插件
        PageHelper.startPage(pageNum, pageSize);
        //查询数据库，需要用到like查询
        List<Loginlog> list = loginlogService.list(new QueryWrapper<Loginlog>().eq("name",username).orderByDesc("id"));
        //封装一下查询的结果集
        PageInfo<Loginlog> pageInfo = PageInfo.of(list);
        return Result.success(pageInfo);
    }


}

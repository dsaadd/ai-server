package com.ai.server.controller;

import cn.hutool.core.date.DateUtil;
import com.ai.server.beans.Admins;
import com.ai.server.beans.Loginlog;
import com.ai.server.common.Iputil;
import com.ai.server.common.Result;
import com.ai.server.service.AdminsService;
import com.ai.server.service.ILoginlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name = "管理员接口", description = "管理员接口AI") // 类级注解
@RequestMapping("admins")
public class AdminsController {

    @Autowired
    AdminsService adminsService;

    @Autowired
    ILoginlogService loginlogService;

    /**
     * 管理员登录
     * @return
     */
    @Operation(summary = "管理员登录", description = "管理员登录") // 方法级注解
    @PostMapping("login")
    public Result login(@RequestBody Admins admins){
        log.info("登录的信息是:"+admins);
        Admins a = adminsService.login(admins.getUsername(),admins.getPassword());
        if(a!=null){
            //登录成功
            //记录登录的日志信息
            Loginlog loginlog=new Loginlog();
            loginlog.setName(admins.getUsername());
            String ip = Iputil.getIp();
            loginlog.setIp(ip);
            loginlog.setAddress(Iputil.getAddress(ip));
            loginlog.setLogintime(DateUtil.now());
            //存储到数据库
            loginlogService.save(loginlog);
            log.info("登录人日志信息:"+loginlog);

            return Result.success(a);
        }else{
            return Result.error("账号或密码错误！");
        }
    }

}

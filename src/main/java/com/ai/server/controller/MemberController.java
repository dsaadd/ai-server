package com.ai.server.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ai.server.beans.Loginlog;
import com.ai.server.beans.Members;
import com.ai.server.common.Iputil;
import com.ai.server.common.Result;
import com.ai.server.exception.CustomException;
import com.ai.server.service.ILoginlogService;
import com.ai.server.service.IMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author bruce
 */
@RestController
@RequestMapping("/member")
@Slf4j
@Tag(name = "会员接口", description = "会员接口AI") // 类级注解
public class MemberController {

    @Resource
    IMemberService memberService;

    @Autowired
    ILoginlogService loginlogService;

    /**
     * 分页查询
     * @return
     */
    @Operation(summary = "根据昵称模糊查询分页", description = "根据昵称模糊查询分页") // 方法级注解
    @GetMapping("selectPage")
    public Result selectPage(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam("nickname") String nickname){
        //使用分页插件
        PageHelper.startPage(pageNum,pageSize);
        //查询数据库，需要用到like查询
        List<Members> list = memberService.list(new QueryWrapper<Members>().like("nickname", nickname).eq("del", "0").orderByDesc("id"));
        //封装一下查询的结果集
        PageInfo<Members> pageInfo = PageInfo.of(list);
        return Result.success(pageInfo);
    }

    /**
     * 新增接口
     * @return
     */
    @Operation(summary = "新增会员信息", description = "新增会员信息") // 方法级注解
    @PostMapping("add")
    public Result add(@RequestBody Members member) throws CustomException {
        Members one = memberService.getOne(new QueryWrapper<Members>().eq("uname", member.getUname()).eq("del", "0"));
        if(one==null){
            member.setDel("0");//删除状态
            member.setCreatetime(DateTime.now().toString()); //设置系统时间
            boolean b = memberService.save(member);
            if(b){
                return Result.success();
            }else{
                return Result.error();
            }
        }else{
            throw new CustomException("会员账号已存在");
        }
    }

    /**
     *  更新
     */
    @Operation(summary = "更新会员信息", description = "更新会员信息") // 方法级注解
    @PutMapping("update")
    public Result update(@RequestBody Members member){
        boolean b = memberService.updateById(member);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**
     * 删除
     */
    @Operation(summary = "根据ID删除会员信息", description = "根据ID删除会员信息") // 方法级注解
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id){
        Members member = new Members();
        member.setId(id);
        member.setDel("1");//删除
        boolean b = memberService.updateById(member);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }


    /**
     * 会员登录
     * @return
     */
    @PostMapping("login")
    @Operation(summary = "会员登录", description = "会员登录") // 方法级注解
    public Result login(@RequestBody Members member){
        log.info("登录的信息是:"+member);
        Members a = memberService.getOne(new QueryWrapper<Members>().eq("uname", member.getUname()).eq("upass",member.getUpass()));
        if(a!=null){

            //记录登录的日志信息
            Loginlog loginlog=new Loginlog();
            loginlog.setName(member.getUname());
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


    /**
     * 新增接口
     * @return
     */
    @Operation(summary = "注册会员信息", description = "注册会员信息") // 方法级注解
    @PostMapping("register")
    public Result register(@RequestBody Members member) throws CustomException {
        Members one = memberService.getOne(new QueryWrapper<Members>().eq("uname", member.getUname()).eq("del", "0"));
        if(one==null){
            member.setRole("TENANT");
            member.setDel("0");//删除状态
            member.setCreatetime(DateTime.now().toString()); //设置系统时间
            boolean b = memberService.save(member);
            if(b){
                return Result.success();
            }else{
                return Result.error();
            }
        }else{
            throw new CustomException("会员账号已存在");
        }
    }

}

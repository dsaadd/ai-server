package com.ai.server.controller;


import cn.hutool.core.date.DateTime;
import com.ai.server.beans.Admins;
import com.ai.server.beans.Leavemsg;
import com.ai.server.beans.Members;
import com.ai.server.common.Result;
import com.ai.server.service.AdminsService;
import com.ai.server.service.ILeavemsgService;
import com.ai.server.service.IMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author bruce
 */
@RestController
@RequestMapping("/leavemsg")
@Slf4j
@Tag(name = "留言接口", description = "留言接口AI") // 类级注解
public class LeavemsgController {

    @Autowired
    ILeavemsgService leavemsgService;

    @Autowired
    IMemberService memberService;

    @Autowired
    AdminsService adminsService;

    /**
     * 分页查询
     *
     * @return
     */
    @Operation(summary = "根据内容查询留言分页", description = "根据内容查询留言分页") // 方法级注解
    @GetMapping("selectPage")
    public Result selectPage(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam("content") String content) {
        //使用分页插件
        PageHelper.startPage(pageNum, pageSize);
        //查询数据库，需要用到like查询
        List<Leavemsg> list = leavemsgService.list(new QueryWrapper<Leavemsg>().like("content", content).eq("del", "0").orderByDesc("id"));
        for (Leavemsg leavemsg : list) {
            Integer memberId = leavemsg.getMemberId();
            Members member = memberService.getById(memberId);
            leavemsg.setMemberName(member.getUname());

            Integer replyId = leavemsg.getReplyId();
            if(replyId!=null){
                Admins admins = adminsService.getById(replyId);
                leavemsg.setReplyName(admins.getUsername());
            }
        }

        //封装一下查询的结果集
        PageInfo<Leavemsg> pageInfo = PageInfo.of(list);
        return Result.success(pageInfo);
    }

    /**
     * 留言回复
     * @return
     */
    @PostMapping("reply/{reply_id}")
    @Operation(summary = "留言回复", description = "根据内容查询留言分页") // 方法级注解
    public Result reply(@PathVariable("reply_id") Integer reply_id, @RequestBody Leavemsg leavemsg) {
        log.info("reply_id==>" + reply_id);
        log.info("leavemsg==>" + leavemsg);
        leavemsg.setReplyId(reply_id); //回复人ID
        leavemsg.setReplyTime(DateTime.now().toString());
        boolean b = leavemsgService.updateById(leavemsg);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "根据ID删除留言信息", description = "根据ID删除留言信息") // 方法级注解
    public Result delete(@PathVariable("id") Integer id) {
        Leavemsg leavemsg = new Leavemsg();
        leavemsg.setId(id);
        leavemsg.setDel("1");//删除
        boolean b = leavemsgService.updateById(leavemsg);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 分页查询
     * @return
     */
    @Operation(summary = "根据内容查询会员留言分页", description = "根据内容查询会员留言分页") // 方法级注解
    @GetMapping("myleavemsg")
    public Result myleavemsg(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam("content") String content,
                             @RequestParam("memberId") String memberId) {
        //使用分页插件
        PageHelper.startPage(pageNum, pageSize);
        //查询数据库，需要用到like查询
        List<Leavemsg> list = leavemsgService.list(new QueryWrapper<Leavemsg>().like("content", content).eq("member_id",memberId).eq("del", "0").orderByDesc("id"));
        for (Leavemsg leavemsg : list) {
            Integer member_Id = leavemsg.getMemberId();
            Members member = memberService.getById(member_Id);
            leavemsg.setMemberName(member.getUname());

            Integer replyId = leavemsg.getReplyId();
            if(replyId!=null){
                Admins admins = adminsService.getById(replyId);
                leavemsg.setReplyName(admins.getUsername());
            }
        }

        //封装一下查询的结果集
        PageInfo<Leavemsg> pageInfo = PageInfo.of(list);
        return Result.success(pageInfo);
    }

    /**
     * 会员留言
     * @return
     */
    @Operation(summary = "会员留言", description = "会员留言") // 方法级注解
    @PostMapping("save/{memberId}")
    public Result save(@PathVariable("memberId") Integer memberId, @RequestBody Leavemsg leavemsg) {
        leavemsg.setMemberId(memberId);
        leavemsg.setCreateTime(DateTime.now().toString());
        leavemsg.setDel("0");
        boolean b = leavemsgService.save(leavemsg);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

}

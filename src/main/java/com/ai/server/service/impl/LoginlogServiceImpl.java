package com.ai.server.service.impl;

import com.ai.server.beans.Loginlog;
import com.ai.server.mapper.LoginlogMapper;
import com.ai.server.service.ILoginlogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author bruce
 */
@Service
public class LoginlogServiceImpl extends ServiceImpl<LoginlogMapper, Loginlog> implements ILoginlogService {

    @Autowired
    LoginlogMapper loginlogMapper;

    @Override
    public Long getActiveUserCount(int days) {
        // 计算从多少天前开始统计
        LocalDateTime startTime = LocalDateTime.now().minusDays(days);
        // 使用 QueryWrapper 统计去重的用户数
        QueryWrapper<Loginlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT name")
                .ge("logintime", startTime);
        // 获取去重后的用户列表并返回数量
        return baseMapper.selectCount(queryWrapper);
    }
}

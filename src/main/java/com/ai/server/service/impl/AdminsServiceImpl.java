package com.ai.server.service.impl;

import com.ai.server.beans.Admins;
import com.ai.server.mapper.AdminsMapper;
import com.ai.server.service.AdminsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminsServiceImpl extends ServiceImpl<AdminsMapper,Admins> implements AdminsService {

    @Autowired
    AdminsMapper adminsMapper;

    @Override
    public Admins login(String username, String password) {
        return adminsMapper.selectOne
                (new QueryWrapper<Admins>().eq("username", username).
                        eq("password",password).
                        eq("del", "0"));
    }
}

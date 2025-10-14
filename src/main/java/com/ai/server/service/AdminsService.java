package com.ai.server.service;

import com.ai.server.beans.Admins;
import com.ai.server.beans.Leavemsg;
import com.ai.server.mapper.AdminsMapper;
import com.ai.server.mapper.LeavemsgMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public interface AdminsService extends IService<Admins> {

    Admins login(String username, String password);
}

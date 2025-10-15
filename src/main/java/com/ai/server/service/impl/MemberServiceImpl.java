package com.ai.server.service.impl;

import com.ai.server.beans.Members;
import com.ai.server.mapper.BookMapper;
import com.ai.server.service.IMemberService;
import com.ai.server.mapper.MemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author bruce
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Members> implements IMemberService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public int insertBookUser(Integer id, String memberId) {
        return bookMapper.insertBookUser(memberId,id);
    }
}

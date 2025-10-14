package com.ai.server.service;

import com.ai.server.beans.Loginlog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 */
public interface ILoginlogService extends IService<Loginlog> {

    Long getActiveUserCount(int i);
}

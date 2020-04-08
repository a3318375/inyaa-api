package com.inyaa.log.service;


import com.inyaa.common.base.domain.Result;
import com.inyaa.common.base.service.BaseService;
import com.inyaa.log.domain.po.AuthUserLog;
import com.inyaa.log.domain.vo.AuthUserLogVO;

/**
 * 用户行为日志记录表:业务接口类
 * @author generator
 * @date 2019-12-25 09:10:17
 * @since 1.0
 */
public interface AuthUserLogService extends BaseService<AuthUserLog> {


    Result saveLogs(AuthUserLogVO authUserLogVO);

    /**
     * 查询用户行为日志记录表
     */
    Result<AuthUserLogVO> getLogs(Long id);

    
    /**
     * 分页查询用户行为日志记录表
     */
    Result<AuthUserLogVO> getLogsList(AuthUserLogVO authUserLogVO);

    Result<AuthUserLogVO> deleteLogs(Long id);
}
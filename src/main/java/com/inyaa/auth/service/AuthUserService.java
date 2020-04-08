package com.inyaa.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inyaa.auth.bean.UserInfo;
import com.inyaa.auth.dao.AuthTokenDao;
import com.inyaa.auth.dao.AuthUserDao;
import com.inyaa.common.base.domain.Result;
import com.inyaa.common.base.service.BaseService;
import com.inyaa.auth.domain.po.AuthUser;
import com.inyaa.auth.domain.vo.AuthUserVO;
import com.inyaa.common.util.PageUtil;
import com.inyaa.posts.domain.po.Posts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author inyaa
 * @since 2019-08-28
 */
@Service
public class AuthUserService {

    @Resource
    private AuthUserDao authUserDao;

    /**
     * 获取用户列表
     *
     * @param authUserVO
     * @return
     */
    public Result<UserInfo> getUserList(AuthUserVO authUserVO) {
        Page<UserInfo> pageParams = Optional.ofNullable(PageUtil.checkAndInitPage(authUserVO)).orElse(PageUtil.initPage());
        LambdaQueryWrapper<UserInfo> authUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(authUserVO.getKeywords())) {
            authUserLambdaQueryWrapper.like(UserInfo::getName, authUserVO.getKeywords());
        }
        if (StringUtils.isNotBlank(authUserVO.getName())) {
            authUserLambdaQueryWrapper.eq(UserInfo::getName, authUserVO.getName());
        }

        IPage<UserInfo> page = authUserDao.selectPage(pageParams, authUserLambdaQueryWrapper);

        return Result.createWithPaging(page);
    }

    public Result<UserInfo> getUserInfo(String name) {
        UserInfo user = authUserDao.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUsername, name));
        if (user != null) {
            Set<String> roleKeyList = authUserDao.getRoleKeyList(user.getId());
            user.setRoles(roleKeyList);
        }
        return Result.createWithModel(user);
    }
}
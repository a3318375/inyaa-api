package com.inyaa.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inyaa.auth.bean.UserInfo;
import com.inyaa.auth.domain.po.AuthUser;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author inyaa
 * @since 2019-08-28
 */
public interface AuthUserDao extends BaseMapper<UserInfo> {

    AuthUser selectAdmin();

    @Select("select role_key from sys_role where id in (select role_id from sys_user_role where user_id = #{id})")
    Set<String> getRoleKeyList(long id);
}

package com.inyaa.auth.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@TableName("sys_user")
public class UserInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username; // 用户名
    private String password; // 密码
    private String name;// 姓名
    private String email; // 邮箱
    private Date loginDate;// 最后登录日期
    private String loginIp;// 最后登录IP
    private boolean accountNonExpired; // 账号是否未过期
    private boolean accountNonLocked; // 账号是否未锁定
    private boolean credentialsNonExpired; // 账号凭证是否未过期
    private boolean enabled; // 账号是否可用

    @TableField(exist = false)
    private Set<String> roles; //该用户拥有角色ID集合

    private String organizationId;//所属机构ID
    private String organizationName;//所属机构名称

}
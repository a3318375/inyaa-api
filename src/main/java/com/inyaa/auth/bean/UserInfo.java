package com.inyaa.auth.bean;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {

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
    private String roleIds;//该用户拥有角色ID集合
    private String roleNames;//该用户拥有角色名字集合
    private String organizationId;//所属机构ID
    private String organizationName;//所属机构名称

}
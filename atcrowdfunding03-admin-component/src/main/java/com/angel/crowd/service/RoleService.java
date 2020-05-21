package com.angel.crowd.service;

import com.angel.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Bader
 * @create 2020--04--02 14:44
 */
public interface RoleService {
    //角色页面数据初始化
    PageInfo<Role> getRolePageInfo(String keyWord,Integer pageNum,Integer pageSize);

    void SaveRole(Role role);

    void RoleEdit(Role role);

    void reomove(List<Integer> roleIdList);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);


}

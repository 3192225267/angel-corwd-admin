package com.angel.crowd.service.impl;

import com.angel.crowd.entity.Role;
import com.angel.crowd.entity.RoleExample;
import com.angel.crowd.mapper.RoleMapper;
import com.angel.crowd.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Bader
 * @create 2020--04--02 14:48
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getRolePageInfo(String keyWord, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList = roleMapper.selectRoleListByKeyword(keyWord);
        return new PageInfo(roleList);
    }

    @Override
    public void SaveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void RoleEdit(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void reomove(List<Integer> roleIdList) {
        RoleExample example=new RoleExample();
        RoleExample.Criteria criteria=example.createCriteria();
        criteria.andIdIn(roleIdList);
        roleMapper.deleteByExample(example);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }


}

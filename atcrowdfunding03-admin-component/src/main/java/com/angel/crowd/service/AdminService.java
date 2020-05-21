package com.angel.crowd.service;

import com.angel.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {

    List<Admin> getAll();

    //登录
//    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    //查询全部（包含状态分页及关键字查询）
    PageInfo<Admin> getPageInfo(String keyWord, Integer pageNum, Integer pageSize);

    //根据id查询
    Admin getAdminByid(Integer adminid);

    //删除
    int remove(Integer adminId);

    //添加
    void addAdmin(Admin admin);

    //修改
    void editAdmin(Admin admin);

    void saveAdminRoleRelationship(Integer id,List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String loginAcct);


}

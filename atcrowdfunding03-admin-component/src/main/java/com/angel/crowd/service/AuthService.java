package com.angel.crowd.service;

import com.angel.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @author 刘振河
 * @create 2020--04--07 10:43
 */
public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelathinship(Map<String,List<Integer>> map);

    List<String> getAssignedAuthNameByAdminid(Integer adminId);
}

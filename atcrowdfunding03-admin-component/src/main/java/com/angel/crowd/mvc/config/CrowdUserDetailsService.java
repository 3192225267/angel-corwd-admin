package com.angel.crowd.mvc.config;

import com.angel.crowd.entity.Admin;
import com.angel.crowd.entity.Role;
import com.angel.crowd.service.AdminService;
import com.angel.crowd.service.AuthService;
import com.angel.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘振河
 * @create 2020--04--15 10:16
 * 加载用户权限类
 */
@Component
public class CrowdUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;

    @Autowired
    private RoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. 根据账号名称查询Admin对象
        Admin admin=adminService.getAdminByLoginAcct(username);

        // 2. 获取AdminID
        Integer adminId=admin.getId();

        // 3. 根据adminId查询角色信息
        List<Role> assignedRoleList=roleService.getAssignedRole(adminId);

        // 4. 根据adminId查询权限角色信息
       List<String> authNameList= authService.getAssignedAuthNameByAdminid(adminId);

        // 5.创建集合对象用来存储GrantedAuthoity
        List<GrantedAuthority> authorities=new ArrayList<>();

        // 6. 遍历assigedRooleLIst存入角色信息
        for (Role role:assignedRoleList
             ) {
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }

        // 7. 遍历authNameList存入权限信息
        for (String authName:authNameList
             ) {
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }
        // 8.封装Security对象
        SecurityAdmin securityAdmin=new SecurityAdmin(admin,authorities);

        return securityAdmin;
    }
}

package com.angel.crowd.mvc.config;

import com.angel.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author 刘振河
 * @create 2020--04--15 9:59
 */
public class SecurityAdmin extends User {
    private static final long serialVersionUID = 1L;
    private Admin originalAdmin;

    public SecurityAdmin(Admin admin, Collection<GrantedAuthority> authorities){
        super(admin.getLoginAcct(),admin.getUserPswd(),true,true,true,true,authorities);
        this.originalAdmin =admin;
        this.originalAdmin.setUserPswd(null);
}
    public Admin getOriginalAdmin() { return originalAdmin; }
}

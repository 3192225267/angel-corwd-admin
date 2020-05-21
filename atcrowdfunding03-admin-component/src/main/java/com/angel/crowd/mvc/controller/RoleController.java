package com.angel.crowd.mvc.controller;

import com.angel.crowd.entity.Role;
import com.angel.crowd.service.RoleService;
import com.angel.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Bader
 * @create 2020--04--02 14:57
 */
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasRole('部长')")
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity rolePage(
            @RequestParam(value = "keyWord", defaultValue = "") String keyWord,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        PageInfo<Role> pageInfo = roleService.getRolePageInfo(keyWord, pageNum, pageSize);
        return ResultEntity.successWithData(pageInfo);
    }

    @RequestMapping("role/save.json")
    public ResultEntity<Role> roleSave(Role role) {
        roleService.SaveRole(role);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("role/edit.json")
    public ResultEntity roleEdit(Role role) {
        roleService.RoleEdit(role);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("role/remove.json")
    public ResultEntity roleRemove(@RequestBody List<Integer> roleIdList){
        roleService.reomove(roleIdList);
        return ResultEntity.successWithoutData();
    }


}

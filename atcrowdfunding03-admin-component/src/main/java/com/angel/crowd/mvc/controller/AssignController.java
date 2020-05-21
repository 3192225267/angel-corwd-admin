package com.angel.crowd.mvc.controller;

import com.angel.crowd.entity.Auth;
import com.angel.crowd.entity.Role;
import com.angel.crowd.service.AdminService;
import com.angel.crowd.service.AuthService;
import com.angel.crowd.service.RoleService;
import com.angel.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 刘振河
 * @create 2020--04--06 11:40
 */
@Controller
public class AssignController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @RequestMapping("/assign/to/assign/role/page.html")
    public String assignRolePage(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyWord") String keyWord,
            ModelMap modelMap
    ) {

        // 查询已分配角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 查询未分配角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);

        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);
        modelMap.addAttribute("pageNum", pageNum);
        modelMap.addAttribute("keyWord", keyWord);
        return "assign_role";
    }


    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyWord") String keyWord,
            // 我们允许用户在页面上取消所有已分配角色再提交表单，所以可以不提供 roleIdList 请求参数
            // 设置 required=false 表示这个请求参数不是必须的
            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {

        adminService.saveAdminRoleRelationship(adminId, roleIdList);
        return "redirect:/admin/page.html?pageNum=" + pageNum + "&keyWord=" + keyWord;

    }
//    @RequestMapping("/assign/do/role/assign.html")
//    public String saveAdminRoleRelationship1(
//            @RequestParam("adminId") Integer adminId,
//            @RequestParam("pageNum") Integer pageNum,
//            @RequestParam("keyword") String keyword,
//            // 我们允许用户在页面上取消所有已分配角色再提交表单，所以可以不提供 roleIdList 请求参数
//            // 设置 required=false 表示这个请求参数不是必须的
//            @RequestParam(value="roleIdList", required=false) List<Integer> roleIdList ) {
//        adminService.saveAdminRoleRelationship(adminId, roleIdList);
//        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
//    }


    @RequestMapping("assgin/get/all/auth.json")
    @ResponseBody
    public ResultEntity<List<Auth>> getAllAuthor() {
        List<Auth> authList = authService.getAll();
        return ResultEntity.successWithData(authList);
    }

    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(@RequestParam("roleId") Integer roleId) {
        List<Integer> authList = authService.getAssignedAuthIdByRoleId(roleId);

        return ResultEntity.successWithData(authList);
    }

    @RequestMapping("assign/do/role/assign/auth.json")
    @ResponseBody
    public ResultEntity<String> saveRoleAuthRelathinship(
            @RequestBody Map<String,List<Integer>> map
    ){
        authService.saveRoleAuthRelathinship(map);
        return ResultEntity.successWithoutData();
    }
}

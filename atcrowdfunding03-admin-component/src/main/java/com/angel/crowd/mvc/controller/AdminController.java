package com.angel.crowd.mvc.controller;

import com.angel.crowd.constant.CrowdConstant;
import com.angel.crowd.entity.Admin;
import com.angel.crowd.service.AdminService;
import com.angel.crowd.util.RandomUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author Bader
 * @create 2020--03--28 20:40
 */
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    RandomUtils random = null;

   /* //登录
    @RequestMapping("admin/do/login.html")
    public String AdminLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session
    ) {
        Admin admin = adminService.getAdminByLoginAcct(loginAcct );
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main/page.html";
    }*/

    //退出登录

    @RequestMapping("/admin/do/logout.html")
    public String Logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }
    @RequestMapping("/admin/page.html")
    //查询用户数据（包括根据条件筛选用户数据）
    public String getPageInfo(
            @RequestParam(value = "keyWord", defaultValue = "") String keyWord,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelMap) {
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyWord, pageNum, pageSize);
        // 将分页数据存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-user";
    }

    /**
     * @param adminId 前台穿过来的ID（根据它进行删除）
     * @param pageNum 前台传过来的页码（保留分页状态）
     * @param keyWord 前台传过来的关键字（保留查询状态）
     * @return
     */
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyWord}.html")
    public String remove(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyWord") String keyWord) {
        int n = adminService.remove(adminId);

        return "redirect:/admin/page.html?pageNum=" + pageNum + "&keyWord=" + keyWord;
    }

    @PreAuthorize("hasAuthority('user:add')")
    @RequestMapping("/admin/add.html")
    public String adminAdd(Admin admin) {
        adminService.addAdmin(admin);
        return "redirect:/admin/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/edit/{adminId}/{pageNum}/{keyWord}.html")
    public String adminEdit(@PathVariable("adminId") Integer adminId,
                            @PathVariable(value = "keyWord") String keyWord,
                            @PathVariable(value = "pageNum") Integer pageNum,
                            ModelMap modelMap)
     {

        Admin admin = adminService.getAdminByid(adminId);
        modelMap.addAttribute("admin", admin);
        modelMap.addAttribute("keyWord",keyWord);
         modelMap.addAttribute("pageNum",pageNum);
        return "admin-edit";
    }

    @RequestMapping("/admin/edit.html")
    public String adminEdit(Admin admin,String keyWord,Integer pageNum) {
        adminService.editAdmin(admin);
        return "redirect:/admin/page.html?pageNum=" + pageNum + "&keyWord=" + keyWord;
    }

}

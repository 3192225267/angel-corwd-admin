package com.angel.crowd.mvc.controller;

import com.angel.crowd.entity.Menu;
import com.angel.crowd.service.MenuService;
import com.angel.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bader
 * @create 2020--04--04 11:44
 */
@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu> menuWholeTree() {
        // 1.查询全部的Menu
        List<Menu> menuList = menuService.getAll();

        // 2.声明一个变量来存储已找到的根节点
        Menu root = null;

        // 3.创建Map对象用来存储id和Menu对象的对应关系以便查找父节点
        Map<Integer, Menu> menuMap = new HashMap<>();

        // 4.遍历 MenuList，来填充 MenuMap
        for (Menu menu : menuList
                ) {
            Integer id = menu.getId();
            menuMap.put(id, menu);
        }

        // 5.再次遍历MenuList查找根节点，组装父节点
        for (Menu menu : menuList
                ) {
            // 6.获取当前 menu 对象的 pid 属性值
            Integer pid = menu.getPid();

            // 7.如果 pid 为 null，判定为根节点
            if (pid == null) {
                root = menu;
                // 8.如果当前节点是根节点，那么肯定没有父节点，不必继续执行
                continue;
            }
            // 9.如果 pid 不为 null，说明当前节点有父节点，那么可以根据 pid 到 menuMap 中 查找对应的 Menu 对象
            Menu father=  menuMap.get(pid);
            // 10.将当前节点存入父节点的 children 集合
            father.getChildren().add(menu);
        }
        // 11.经过上面的运算，根节点包含了整个树形结构，返回根节点就是返回整个树
        return ResultEntity.successWithData(root);
    }

    @RequestMapping("menu/save.json")
    public ResultEntity<String> saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }
    @RequestMapping("menu/edit.json")
    public ResultEntity<String> menuEdit(Menu menu){
        menuService.editMenu(menu);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("menu/remove.json")
    public ResultEntity menuRemove( Integer id){
        menuService.menuRemove(id);
        return ResultEntity.successWithoutData();
    }

}

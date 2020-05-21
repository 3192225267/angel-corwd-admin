package com.angel.crowd.service;

import com.angel.crowd.entity.Menu;

import java.util.List;

/**
 * @author Bader
 * @create 2020--04--04 11:42
 */
public interface MenuService {

    List<Menu> getAll();

    void saveMenu(Menu menu);

    void editMenu(Menu menu);

    void menuRemove(Integer id);
}

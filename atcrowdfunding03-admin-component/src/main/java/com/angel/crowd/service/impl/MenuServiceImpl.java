package com.angel.crowd.service.impl;

import com.angel.crowd.entity.Menu;
import com.angel.crowd.mapper.MenuMapper;
import com.angel.crowd.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Bader
 * @create 2020--04--04 11:42
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(null);
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insertSelective(menu);
    }

    @Override
    public void editMenu(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void menuRemove(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}

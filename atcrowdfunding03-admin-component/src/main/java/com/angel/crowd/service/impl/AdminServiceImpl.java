package com.angel.crowd.service.impl;

import com.angel.crowd.constant.CrowdConstant;
import com.angel.crowd.entity.Admin;
import com.angel.crowd.entity.AdminExample;
import com.angel.crowd.exception.LoginAcctAlreadyInUseException;
import com.angel.crowd.mapper.AdminMapper;
import com.angel.crowd.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    /**
     * @param //loginAcct 账号
     * @param //userPswd  密码
     * @return admin 用户对象
     */
   /* //登录
    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria amcriteria = example.createCriteria();
        //  将用户名作为条件去查询
        amcriteria.andLoginAcctEqualTo(loginAcct);
        // 如果查到这个用户名则将其存到List集合下标为0处
        List<Admin> adminList = adminMapper.selectByExample(example);
        //创建一个Admin对象准备取出List中查询的用户数据
        // 如果查到了
        if (adminList == null || adminList.size() == 0) {

            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (adminList.size() > 1) {

            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = adminList.get(0);

        if (admin == null) {

            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 如果 Admin 对象不为 null 则将数据库密码从 Admin 对象中取出
        String userPswdDB = admin.getUserPswd();
        //将表单中输入的密码明文进行加密
        String userPswdFrom = CrowdUtil.md5(userPswd);

        //比较两个密码是否一致
        if (!Objects.equals(userPswdDB, userPswdFrom)) {


            //不一致，抛异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        return admin;
    }*/

    //初始页面查询（包括分页状态以及关键字查询）
    @Override
    public PageInfo<Admin> getPageInfo(String keyWord, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> adminList = adminMapper.selectAdminListByKeyword(keyWord);
        PageInfo<Admin> pageInfo = new PageInfo<>(adminList);

        return pageInfo;
    }



    //删除用户
    @Override
    public int remove(Integer adminId) {
        return adminMapper.deleteByPrimaryKey(adminId);
    }

    //添加用户
    @Override
    public void addAdmin(Admin admin) {
        String pswd = admin.getUserPswd();
//       = CrowdUtil.md5(pswd);
        String md5pswd =passwordEncoder.encode(pswd);
        admin.setUserPswd(md5pswd);
        admin.setCreateTime(new Date());
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            // 为了不掩盖问题，如果当前捕获到的不是 DuplicateKeyException 类型的异常，则 把当前捕获到的异常对象继续向上抛出
            throw e;
        }
    }

    //修改用户信息
    @Override
    public void editAdmin(Admin admin) {
//        String pswd = admin.getUserPswd();
//        String md5pswd = CrowdUtil.md5(pswd);
//        admin.setUserPswd(md5pswd);
//        admin.setCreateTime(new Date());
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            // 为了不掩盖问题，如果当前捕获到的不是 DuplicateKeyException 类型的异常，则 把当前捕获到的异常对象继续向上抛出
            throw e;
        }
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 1.根据 adminId 删除旧的关联关系数据
        adminMapper.deleteOLdRelationship(adminId);
        // 2.根据 roleIdList 和 adminId 保存新的关联关系
        if (roleIdList != null && roleIdList.size() > 0) {
            adminMapper.insertNewRelationship(adminId, roleIdList);
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> adminList = adminMapper.selectByExample(example);
        Admin admin = adminList.get(0);
        return admin;
    }

    //根据id查询用户
    @Override
    public Admin getAdminByid(Integer adminid) {
        return adminMapper.selectByPrimaryKey(adminid);
    }

}

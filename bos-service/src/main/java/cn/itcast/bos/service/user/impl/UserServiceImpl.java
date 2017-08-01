package cn.itcast.bos.service.user.impl;

import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.dao.user.UserDao;
import cn.itcast.bos.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public void save(User user) {
        // TODO Auto-generated method stub
        userDao.save(user);
    }

    @Override
    public void delete(User user) {
        // TODO Auto-generated method stub
        // userDao.delete(id);
    }

    @Override
    public User findUserById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        // 1:登录 根据用户名和密码查询.. spring data jpa 提供大量封装 查询实现 JPQL完成登录查询
        // return userDao.login(username, password);
        // 2:不书写任何语句 spring data jpa 根据dao 方法名自动生成对应sql 语句
        // return userDao.findByEmailAndPassword(username, password);
        // 3: 优化操作 1 命名查询 将所有hql语句 全部集中到 实体类上
        // return userDao.login1(username, password);
        // 4: sql语句
        // return userDao.login2(username, password);
        // 5 占位符查询
        return userDao.login3(username, password);

    }

    @Override
    public User findUserByTelephone(String telephone) {
        return userDao.findByTelephone(telephone);
    }

    @Override
    public void gobackpassword(String telephone, String password) {
        userDao.gobackpassword(telephone, password);
    }

    @Override
    public void gobackpassword1(String existUser, String  password) {
        userDao.gobackpassword1(existUser, password);
    }


}

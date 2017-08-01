/*
package cn.itcast.activemq.producer.queue.user.test;

import cn.itcast.activemq.producer.queue.bos.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-domain.xml", "classpath:applicationContext-dao.xml",
        "classpath:applicationContext-service.xml" })
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void test1() {
        User user = new User();
        user.setEmail("15001932131@163.com");
        user.setPassword("111111");
        user.setTelephone("15001932131");
        userService.save(user);
    }
}
*/

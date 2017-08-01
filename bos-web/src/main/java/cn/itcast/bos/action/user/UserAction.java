package cn.itcast.bos.action.user;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.user.User;
import cn.itcast.activemq.producer.queue.bos.utils.RandStringUtils;
import cnm.QueueSender;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import java.util.concurrent.TimeUnit;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
// 值栈 中转站 业务需求需要数据 放到 root request session app.....setAttribute()
public class UserAction extends BaseAction<User> {
    // userAction_validCheckCode
    @Action(value = "userAction_validCheckCode", results = {@Result(name = "validCheckCode", type = "json")})
    public String validCheckCode() {
        String checkcode = getParameter("checkcode");
        String session_checkcode = (String) getSessionAttribute("key");
        if (checkcode.equalsIgnoreCase(session_checkcode)) {
            push(true);// 压入栈顶 json结果集源码 执行execute (没有配置root的情况下)将 栈顶元素进行json序列化
        } else {
            push(false);
        }
        return "validCheckCode";
    }

    @Action(value = "userAction_login", results = {@Result(name = "login_error", location = "/login.jsp"),
            @Result(name = "login_ok", type = "redirect", location = "/index.jsp"),
            @Result(name = "input", location = "/login.jsp")})
    public String login() {
        User existUser = facadeService.getUserService().findUserByUsernameAndPassword(model.getEmail(),
                model.getPassword());
        // 一次性验证码
        removeSessionAttribute("key");
        if (existUser == null) {
            this.addActionError(this.getText("login.emailorpassword.error"));// 国际化配置文件加载该数据
            return "login_error";
        } else {
            setSessionAttribute("existUser", existUser);
            System.out.println("用户放入action4444");
            return "login_ok";
        }
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    public QueueSender queueSender;

    @Action(value = "userAction_sendSms", results = {@Result(name = "sendSms", type = "json")})
    public String sendSms() {
        try {
            System.out.println("进入sendSms方法");
            final String code = RandStringUtils.getCode();
            System.out.println("验证码为:" + code);
            redisTemplate.opsForValue().set(model.getTelephone(), code, 120,
                    TimeUnit.SECONDS);// 120秒 有效找回 redis保存验证码
            System.out.println(code + "----------短信密码找回验证码----------");
            // SmsSystem.sendSms(code, model.getTelephone());// 没有ACTIVEMQ
            // 单线程发送给用户手机
            // 调用ActiveMQ jmsTemplate，发送一条消息给MQ
           /* Map map =new HashMap();
            map.put("code",code);
            map.put("telephone",model.getTelephone());
            queueSender.send("bos_sms",map);*/
            System.out.println("成功了!" + code);
            push(true);
        } catch (Exception e) {
            push(false);
            e.printStackTrace();
        }

        System.out.println("成功了!");
        return "sendSms";
    }

    @Action(value = "userAction_smsPassword", results = {@Result(name = "smsPassword", type = "json")})
    public String smsPassword() {
        // 1: 手机号是否存在系统中
        User existUser = facadeService.getUserService().findUserByTelephone(model.getTelephone());
        if (existUser == null) {
            // 数据库不存在
            push("2");
        } else {
            // 2:
            String checkcode = getParameter("checkcode");// 用户输入的验证码
            // redis验证码
            String redisCode = redisTemplate.opsForValue().get(model.getTelephone());
            if (StringUtils.isNotBlank(redisCode)) {
                // redis 存储用户验证码 没有失效
                if (redisCode.equals(checkcode)) {
                    // 验证码正确
                    System.out.println("验证码为:" + checkcode);
                    push("3");
                } else {
                    push("1");
                }
            } else {
                push("0");
            }
        }
        return "smsPassword";
    }


    // 找回密码 更新数据库
    @Action(value = "userAction_gobackpassword", results = {@Result(name = "gobackpassword", type = "json")})
    public String gobackpassword() {
        try {
            facadeService.getUserService().gobackpassword(model.getTelephone(), model.getPassword());
            // redisTemplate.delete(model.getTelephone()); // 手动清除redis指定手机号对应的验证码
            push(true);
        } catch (Exception e) {
            e.printStackTrace();
            push(false);
        }
        return "gobackpassword";
    }

    // 找回密码 更新数据库
    @Action(value = "userAction_gobackpassword1", results = {@Result(name = "gobackpassword1", type = "json")})
    public String gobackpassword1() {
        try {

            String password = model.getPassword();
            User existUser = (User) getSessionAttribute("existUser");
            String email = existUser.getEmail();
            facadeService.getUserService().gobackpassword1(email, password);
            // redisTemplate.delete(model.getTelephone()); // 手动清除redis指定手机号对应的验证码
            System.out.println("已修改");
            push(true);
        } catch (Exception e) {
            e.printStackTrace();
            push(false);
        }
        return "gobackpassword1";
    }
}

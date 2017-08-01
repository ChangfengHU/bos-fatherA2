package cn.itcast.bos.test;

import cn.itcast.bos.domain.user.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.opensymphony.xwork2.util.TextParseUtil;

import java.util.Set;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
public class fastjasc {
    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setEmail("eee@163.com");
        user.setPassword("1233");
        // 序列化两个参数 id email
        //String[] arr = new String[]{"id","email"};
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(User.class);
        Set<String> includes = filter.getIncludes();
        Set<String> excludes = filter.getExcludes();
        excludes.add("id");
        String s ="op.qw.qw";
        Set<String> strings = TextParseUtil.commaDelimitedStringToSet(s);
        System.out.println(strings);

        String jsonString = JSON.toJSONString(user, filter);
        System.out.println(jsonString);

    }
}

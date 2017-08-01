package cn.itcast.bos.action.user;


import cn.itcast.bos.domain.bc.Standard;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.action.base.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
// 值栈 中转站 业务需求需要数据 放到 root request session app.....setAttribute()
public class StandardAction extends BaseAction<Standard> {
    @Action(value = "standardAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/base/standard.jsp") })
    public String save() {
        // 添加 又是修改
        try {
            if (StringUtils.isBlank(model.getDeltag() + "")) {
                // 添加 操作 收派标准获取null会覆盖实体类Standard 里面的属性deltag 初始值
                // 所以添加操作 Standard 手动给予赋值 1
                model.setDeltag(1);
            }
            User existUser = (User) getSessionAttribute("existUser");
            model.setOperator(existUser.getEmail());
            model.setOperatorcompany(existUser.getStation());
            model.setOperationtime(new Timestamp(System.currentTimeMillis()));
            facadeService.getStandardService().save(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "save";
    }
    @Action(value = "standardAction_pageQuery", results = { @Result(name = "pageQuery", type = "json") })
    public String pageQuery() {
        HashMap<String, Object> map = new HashMap<>();
        System.out.println("11111");
        PageRequest pageRequest = new PageRequest(page-1, rows);
        Page<Standard> pageData = facadeService.getStandardService().pageQuery(pageRequest);
        // 3: easyui 需要 {"total":100,rows:[{},{},{}]}
        map.put("total", pageData.getTotalElements());
        System.out.println(pageData.getTotalElements());
        System.out.println(pageData.getContent());
        map.put("rows", pageData.getContent());
        push(map);// 将map压入栈顶 json-plugin插件会自动将map序列化成 easyui 需要的json格式数据
        return "pageQuery";
    }
    @Action(value = "standardAction_delBatch", results = { @Result(name = "delBatch", type = "json") })
    public String delBatch() {
        try {
            String ids = getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                String[] idsarr = ids.split(",");
                facadeService.getStandardService().delBatch(idsarr);
                push(true);
            } else {
                push(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            push(false);
        }
        return "delBatch";
    }
    @Action(value = "standardAction_goback", results = { @Result(name = "goback", type = "json") })
    public String goback() {
        try {
            String ids = getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                String[] idsarr = ids.split(",");
                facadeService.getStandardService().goback(idsarr);
                push(true);
            } else {
                push(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            push(false);
        }
        return "goback";
    }

    @Action(value = "standardAction_ajaxListName", results = { @Result(name = "ajaxListName", type = "fastjson", params = {
            "includeProperties", "name" }) })
    public String ajaxListName() {
        List<Standard> standards = facadeService.getStandardService().findAllInUse();
        push(standards);
        return "ajaxListName";
    }


}

package cn.itcast.bos.action.user;


import cn.itcast.activemq.producer.queue.bos.domain.bc.Staff;
import cn.itcast.bos.action.base.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
// 值栈 中转站 业务需求需要数据 放到 root request session app.....setAttribute()
public class StaffAction extends BaseAction<Staff> {
    @Action(value = "staffAction_validTelephone", results = { @Result(name = "validTelephone",     type = "json") })
    public String validTelephone() {
        Staff staff = facadeService.getStaffService().validTelephone(model.getTelephone());
        System.out.println(staff);
        if (staff == null) {
            push(true);
        } else {
            push(false);
    }
        return "validTelephone";
    }
    @Action(value = "staffAction_pageQuery")
    public String pageQuery() {
        System.out.println("进分页action");
        // 条件封装 Specification接口实现类中
        Specification<Staff> spec = new Specification<Staff>() {
            @Override
            public Predicate toPredicate(Root<Staff> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // root 代表查询目标对象实体模型 == Staff对象 cb 条件构建对象 CriteriaBuilder == Restictions.eq/like(HQL)
                List<Predicate> list = new ArrayList<Predicate>(); // 装载查询对象对象Predicate
                if (StringUtils.isNotBlank(model.getName())) {
                    list.add(cb.like(root.get("name").as(String.class), "%" + model.getName() + "%"));
                }
                if (StringUtils.isNotBlank(model.getTelephone())) {
                    list.add(cb.equal(root.get("telephone").as(String.class), model.getTelephone()));
                }
                if (StringUtils.isNotBlank(model.getStation())) {
                    list.add(cb.like(root.get("station").as(String.class), "%" + model.getStation() + "%"));
                }
                if (StringUtils.isNotBlank(model.getStandard())) {
                    list.add(cb.equal(root.get("standard").as(String.class), model.getStandard()));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p)); // 多个条件之间 and 关系
            }
        };
        Page<Staff> pageData = facadeService.getStaffService().pageQuery(getPageRequest(),spec);
        System.out.println("分页数据111"+pageData.getContent());
        setPageData(pageData);
        return "pageQuery";
    }

    @Action(value = "staffAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/base/staff.jsp") })
    public String save() {
        // 添加 又是修改
        try {
            System.out.println(model);
            facadeService.getStaffService().save(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "save";
    }
    @Action(value = "staffAction_goback", results = { @Result(name = "goback", type = "json") })
    public String goback() {
        try {
            System.out.println("进入还原页面");
            String ids = getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                String[] idsarr = ids.split(",");
                for (int i = 0; i < idsarr.length; i++) {
                    System.out.println(idsarr[i]);
                }
                facadeService.getStaffService().goback(idsarr);
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
    @Action(value = "staffAction_delBatch", results = { @Result(name = "delBatch", type = "json") })
    public String delBatch() {
        try {
            System.out.println("进入还原页面");
            String ids = getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                String[] idsarr = ids.split(",");
                for (int i = 0; i < idsarr.length; i++) {
                    System.out.println(idsarr[i]);
                }
                facadeService.getStaffService().delBatch(idsarr);
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
    @Action(value = "staffAction_ajaxListInUse", results = { @Result(name = "ajaxListInUse", type = "fastjson", params = { "includeProperties", "id,name" }) })
    public String ajaxListInUse() {
        System.out.println("q:"+getParameter("q"));
        String parameter = getParameter("q");
        List<Staff> staffs = facadeService.getStaffService().ajaxListInUse(parameter);
        push(staffs);
        return "ajaxListInUse";
    }

}

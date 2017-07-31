package cn.itcast.bos.action.user;

import cn.itcast.activemq.producer.queue.bos.domain.bc.DecidedZone;
import cn.itcast.activemq.producer.queue.bos.domain.bc.Staff;
import cn.itcast.bos.action.base.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.apache.struts2.ServletActionContext.getRequest;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class DecideZoneAction extends BaseAction<DecidedZone> {

    @Action(value = "decidedZoneAction_save", results = {@Result(name = "save", location = "/WEB-INF/pages/base/decidedzone.jsp")})
    public String save() {
        // 接受sid数组
        try {
            String[] sids = getRequest().getParameterValues("subid");
            facadeService.getDecidedZoneService().save(sids, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "save";
    }

    @Action(value = "decidedzoneAction_pageQuery")
    public String pageQuery() {
        // 接受sid数组
        try {
            System.out.println("进DecidedZone分页action");
            // 条件封装 Specification接口实现类中
            // 添加 又是修改
            Specification<DecidedZone> spec = getSpecification();
            PageRequest pageRequest = getPageRequest();
            Page<DecidedZone> pageQuery = facadeService.getDecidedZoneService().pageQuery(pageRequest,spec);
            setPageData(pageQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pageQuery";
    }

    private Specification<DecidedZone> getSpecification () {
        // model 瞬时态 分页 条件查询 ...
        // molde 数据 封装 Specification 实现类中
        // Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb); 方法 将请求参数 封装 Specification
        Specification<DecidedZone> spec = new Specification<DecidedZone>() {
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                // 将请求参数 model 数据 封装 Predicate
                // 1: root 表示 Subarea from Subarea join .... where .... 省市区条件 Region 关键字 SubArea 定区 DecidedZone oid
                // 2:cb 连接条件构建器 类似以前hibernate Restrictions.like/eq/gt
                List<Predicate> list = new ArrayList<Predicate>(); // 存放所有条件对象Predicate
                if (StringUtils.isNotBlank(model.getId())) {
                    // 连自己表
                    Predicate p1 = cb.equal(root.get("id").as(String.class), model.getId());
                    list.add(p1);
                }
                // 3: 连接 省市区 多表Region 查询
                if (model.getStaff() != null) {
                    // subarea 连接 region 表
                    Join staffJoin = root.join(root.getModel().getSingularAttribute("staff", Staff.class), JoinType.LEFT);
                    if (StringUtils.isNotBlank(model.getStaff().getStation())) {
                        Predicate p2 = cb.like(staffJoin.get("station").as(String.class), "%" + model.getStaff().getStation() + "%");
                        list.add(p2);
                    }

                }
                // 4: 定区是否管理分区
                // 4: 获取是否存在分区下拉框的值
                String association = getParameter("isAssociationSubarea");
                if (StringUtils.isNotBlank(association)) {
                    // "" 0 未关联分区的定区 1 关联定区
                    if ("1".equals(association)) {
                        Predicate p3 = cb.isNotEmpty(root.get("subareas").as(Set.class));
                        list.add(p3);
                    } else if ("0".equals(association)) {
                        Predicate p3 = cb.isEmpty(root.get("subareas").as(Set.class));
                        list.add(p3);
                    }
                }
                // List<Predicate> list = new ArrayList<Predicate>(); 集合 长度大小 由用户 表单请求参数 决定
                Predicate[] p = new Predicate[list.size()];// 定义数组泛型
                // list.toArray 返回的 Object 数组
                return cb.and(list.toArray(p));// Predicate数组 内部所有条件 and 关系
            }
        };
        return spec;
    }
}
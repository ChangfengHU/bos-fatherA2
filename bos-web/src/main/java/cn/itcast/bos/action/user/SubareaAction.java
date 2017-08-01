package cn.itcast.bos.action.user;

import cn.itcast.bos.domain.bc.DecidedZone;
import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.activemq.producer.queue.bos.utils.DownLoadUtils;
import cn.itcast.bos.action.base.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.apache.struts2.ServletActionContext.getRequest;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class SubareaAction extends BaseAction<Subarea> {
    @Action(value = "subareaAction_save", results = {@Result(name = "save", location = "/WEB-INF/pages/base/subarea.jsp")})
    public String save() {
        // 添加 又是修改
        facadeService.getSubareaService().save(model);
        return "save";
    }

    @Action(value = "subareaAction_update", results = {@Result(name = "update", type = "fastjson")})
    public String update() {
        try {
            System.out.println("" + getParameter("region[id]"));
            // 添加 又是修改
            Region region = new Region();
            region.setId(getParameter("region[id]"));
            model.setRegion(region);
            facadeService.getSubareaService().save(model);
            push(true);
        } catch (Exception e) {
            push(false);
            e.printStackTrace();
        }
        return "update";
    }

    @Action(value = "subareaAction_pageQuery")
    public String pageQuery() {
        System.out.println("进Subarea分页action");
        // 条件封装 Specification接口实现类中
        Specification<Subarea> spec = getSpecification();
        // 添加 又是修改
        Page<Subarea> pageQuery = facadeService.getSubareaService().pageQuery(getPageRequest(),spec);
        setPageData(pageQuery);
        return "pageQuery";
    }
    @Action(value = "subareaAction_queryAll", results = {@Result(name = "queryAll", type = "fastjson")})
    public String queryAll() {
        System.out.println("进subareaAction_pageQuery");
        // 条件封装 Specification接口实现类中
        // 添加 又是修改
        List<Subarea> list= facadeService.getSubareaService().Query(getParameter("deid"));
        push(list);
        return "queryAll";
    }
    private Specification<Subarea> getSpecification() {
        // 1: Sepcification接口 封装条件
        Specification<Subarea> spec = new Specification<Subarea>() {
            @Override
            public Predicate toPredicate(Root<Subarea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // root Subarea cb 建立条件
                List<Predicate> predicates = new ArrayList<Predicate>();

                // subarea表添加条件n 1 Region表添加条件 1 定区编号查询 OID
                // 单表连接自己
                if (StringUtils.isNotBlank(model.getAddresskey())) {
                    Predicate p1 = cb.like(root.get("addresskey").as(String.class), "%" + model.getAddresskey() + "%");
                    // p1 满足查询对象
                    predicates.add(p1);
                }
                // 主表Subarea n 连接从表 1 Region
                // 多方连接一方
                if (model.getRegion() != null) {
                    // 连接 region表 regionJoin 操作region表
                    Join<Subarea, Region> regionJoin = root.join(root.getModel().getSingularAttribute("region", Region.class), JoinType.LEFT);
                    // 多方 去连接一方 ....
                    if (StringUtils.isNotBlank(model.getRegion().getProvince())) {
                        Predicate p2 = cb.like(regionJoin.get("province").as(String.class), "%" + model.getRegion().getProvince() + "%");
                        // p2 满足查询对象
                        predicates.add(p2);
                    }
                    if (StringUtils.isNotBlank(model.getRegion().getCity())) {
                        Predicate p3 = cb.like(regionJoin.get("city").as(String.class), "%" + model.getRegion().getCity() + "%");
                        // p2 满足查询对象
                        predicates.add(p3);
                    }
                    if (StringUtils.isNotBlank(model.getRegion().getDistrict())) {
                        Predicate p4 = cb.like(regionJoin.get("district").as(String.class), "%" + model.getRegion().getDistrict() + "%");
                        // p2 满足查询对象
                        predicates.add(p4);
                    }
                }
                // 定区 比较对象 比较OID
                DecidedZone decidedZone = new DecidedZone();
                decidedZone.setId(getParameter("decidedzone.id"));
                System.out.println(3333);
                model.setDecidedZone(decidedZone);
                if (model.getDecidedZone() != null && StringUtils.isNotBlank(model.getDecidedZone().getId())) {
                    Predicate p5 = cb.equal(root.get("decidedZone").as(DecidedZone.class), model.getDecidedZone());
                    // p3 满足查询对象
                    predicates.add(p5);
                }

                Predicate ps[] = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(ps));

            }
        };
        return spec;
    }

    @Action(value = "subareaAction_download")
    public String download() {
        try {
            // 添加 又是修改
            List<Subarea> datas = facadeService.getSubareaService().findAllBySpecification(getSpecification());
            // 2: 下载 编写工作簿对象
            // workbook sheet row cell
            HSSFWorkbook book = new HSSFWorkbook();
            HSSFSheet sheet = book.createSheet("分区数据1");
            // excel标题
            HSSFRow first = sheet.createRow(0);
            first.createCell(0).setCellValue("分区编号");
            first.createCell(1).setCellValue("省");
            first.createCell(2).setCellValue("市");
            first.createCell(3).setCellValue("区");
            first.createCell(4).setCellValue("关键字");
            first.createCell(5).setCellValue("起始号");
            first.createCell(6).setCellValue("终止号");
            first.createCell(7).setCellValue("单双号");
            first.createCell(8).setCellValue("位置");
            // 数据体
            if (datas != null && datas.size() != 0) {
                for (Subarea s : datas) {
                    // 循环一次创建一行
                    int lastRowNum = sheet.getLastRowNum();// 获取当前excel最后一行行号
                    HSSFRow row = sheet.createRow(lastRowNum + 1);
                    row.createCell(0).setCellValue(s.getId());
                    row.createCell(1).setCellValue(s.getRegion().getProvince());
                    row.createCell(2).setCellValue(s.getRegion().getCity());
                    row.createCell(3).setCellValue(s.getRegion().getDistrict());
                    row.createCell(4).setCellValue(s.getAddresskey());
                    row.createCell(5).setCellValue(s.getStartnum());
                    row.createCell(6).setCellValue(s.getEndnum());
                    row.createCell(7).setCellValue(s.getSingle() + "");
                    row.createCell(8).setCellValue(s.getPosition());
                }
                // 第一个sheet数据完成
            }
            // 下载
            String filename = "分区数据.xls";
            HttpServletResponse response = getResponse();
            response.setHeader("Content-Disposition	", "attachment;filename=" + DownLoadUtils.getAttachmentFileName(filename, getRequest().getHeader("user-agent")));
            response.setContentType(ServletActionContext.getServletContext().getMimeType(filename));
            book.write(response.getOutputStream());
            return NONE;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Action(value = "subareaAction_noassociation", results = { @Result(name = "noassociation", type = "fastjson", params = { "includeProperties", "subid,addresskey,position" }) })
    public String noassociation() {
        // 添加 又是修改
        List<Subarea> subareas = facadeService.getSubareaService().noassociation();
        push(subareas);
        return "noassociation";
    }
}

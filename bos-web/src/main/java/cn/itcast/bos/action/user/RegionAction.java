package cn.itcast.bos.action.user;


import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.dao.bc.PinYin4jUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class RegionAction extends BaseAction<Region> {
    @Action(value = "regionAction_pageQuery")
    public String pageQuery(){

        PageRequest pageRequest = getPageRequest();
        Page<Region> page = facadeService.getRegionService().pageQuery(pageRequest);
        setPageData(page);
        return "pageQuery";
    }
    @Action(value = "regionAction_ajaxList",results = { @Result(name = "ajaxList", type = "fastjson", params = {"includeProperties", "id,name" }) })
    public String ajaxList(){
        System.out.println("q:"+getParameter("q"));
        String parameter = getParameter("q");
        List<Region> list = facadeService.getRegionService().ajaxList(parameter);
        System.out.println("进入查询");
        push(list);
        return "ajaxList";
    }
    // struts2 接受上传文件
    private File upload;
    public void setUpload(File upload) {
        this.upload = upload;
    }
    @Action(value = "regionAction_oneclickupload", results = { @Result(name = "oneclickupload", type = "json") })
    public String oneclickupload() {
        // 1: 加载excel文件 编程 工作簿对象
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(upload));
            // 2: sheet
            HSSFSheet sheet = workbook.getSheetAt(0);
            // 3: 第一行数据不需要封装List
            List<Region> regions = new ArrayList<Region>();
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                Region region = new Region();
                region.setId(row.getCell(0).getStringCellValue());
                String province = row.getCell(1).getStringCellValue();
                region.setProvince(province);// 省
                String city = row.getCell(2).getStringCellValue();
                region.setCity(city);// 市
                String district = row.getCell(3).getStringCellValue();
                region.setDistrict(district);// 区
                region.setPostcode(row.getCell(4).getStringCellValue());
                // 城市简码
                city = city.substring(0, city.length() - 1);// 去掉最后一个字 市 南京
                region.setCitycode(PinYin4jUtils.hanziToPinyin(city, ""));//
                // 城市中文拼音组成
                // pinyin4j
                // 完成
                // !
                // 简码制作 江苏省南京市鼓楼区 上海市 上海市 浦东新区
                // 江苏南京鼓楼 ----->JSNJGL
                province = province.substring(0, province.length() - 1);// 去掉最后一个中文字省
                district = district.substring(0, district.length() - 1);// 去掉最后一个字区
                String[] strings;// 数组存放所有的大写字母
                if (province.equalsIgnoreCase(city)) {
            // 直辖市
            strings = PinYin4jUtils.getHeadByString(province + district);
        } else {
            // 非直辖市
            strings = PinYin4jUtils.getHeadByString(province + city + district);
        }
        // 省市区关键字首字母 拼接字符串 遍历
        String shortcode = getHeadFromArray(strings);// 将数组里面所有的大写字母 拼接成一个字符串
        System.out.println("简码:"+shortcode);
        region.setShortcode(shortcode); // 省市区 每一个中文字首字母大写组成
        regions.add(region);

    }
    facadeService.getRegionService().save(regions);
    push(true);
} catch (IOException e) {
        e.printStackTrace();
        }

        return "oneclickupload";
    }
    // 将数组里面存放的首字母拼接成字符串
    private String getHeadFromArray(String[] strings) {
        if (strings == null || strings.length == 0) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (String s : strings) {
                sb.append(s);
            }
            return sb.toString();
        }
    }
}

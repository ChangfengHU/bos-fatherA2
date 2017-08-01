package cn.itcast.bos.service.user.impl;

import cn.itcast.bos.dao.bc.DecidedZoneDao;
import cn.itcast.bos.dao.bc.SubareaDao;
import cn.itcast.bos.domain.bc.DecidedZone;
import cn.itcast.bos.service.base.BaseInterface;
import cn.itcast.bos.service.user.DecidedZoneService;
import cn.itcast.mavencrm.domain.Customer;
import org.apache.cxf.jaxrs.client.WebClient;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Administrator on 2017/7/30 0030.
 */
@Service
@Transactional
public class DecidedZoneServiceImpl implements DecidedZoneService {
    @Autowired
    private DecidedZoneDao decidedZoneDao;
    @Autowired
    private SubareaDao subareaDao;

    @Override
    public void save(String[] sids, DecidedZone model) {
        System.out.println(model.getId());
        decidedZoneDao.save(model);
        if (sids != null && sids.length != 0) {
            // 定区表关联分区 实现
            for (String id : sids) {
                subareaDao.associationtoDecidedzone(id, model);
            }
        }
    }

    @Override
    public Page<DecidedZone> pageQuery(PageRequest pageRequest, Specification<DecidedZone> spec) {
        Page<DecidedZone> all = decidedZoneDao.findAll(spec, pageRequest);
        for (DecidedZone decidedZone : all) {
            Hibernate.initialize(decidedZone.getStaff());
        }
        return all;
    }

    @Override
    public List<Customer> findnoassociationcustomers() {
        String url= BaseInterface.CRM_BASE_URL;
        List<Customer> list=(List<Customer>) WebClient.create(url).accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        for (Customer customer : list) {
            System.out.println(customer);
        }
        return list.isEmpty()?null:list;
    }

    @Override
    public List<Customer> findassociationcustomers(String id) {
        String url= BaseInterface.CRM_BASE_URL;
        String url1=url+"/"+id;
        List<Customer> list1=(List<Customer>) WebClient.create(url1).accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        for (Customer customer : list1) {
            System.out.println(customer);
        }
        return list1.isEmpty()?null:list1;
    }

    @Override
    public void assigncustomerstodecidedzone(String id, String[] customerIds) {
        String url= BaseInterface.CRM_BASE_URL;

        url =url+"/"+id;
        if (customerIds != null && customerIds.length != 0) {
            StringBuilder sb = new StringBuilder();
            for (String cid : customerIds) {
                sb.append(cid).append(",");
            }
            String s = sb.substring(0, sb.length() - 1);// 1,2,3
            url = url + "/" + s;
        } else {
            url = url + "/tps";// 如果以tps出现 表示当前定区没有选择客户信息
        }
        WebClient.create(url).put(null);
    }


}

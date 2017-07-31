package cn.itcast.bos.service.user.impl;

import cn.itcast.activemq.producer.queue.bos.domain.bc.Staff;
import cn.itcast.bos.dao.bc.StaffDao;
import cn.itcast.bos.service.user.StaffService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/7/23 0023.
 */
@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;

    @Override
    public Staff validTelephone(String telephone) {
        return staffDao.findByTelephone(telephone);
    }

    @Override
    public void save(Staff model) {
        staffDao.save(model);
    }

    @Override
    public Page<Staff> pageQuery(PageRequest pageRequest,Specification<Staff> spec) {
        Page<Staff> staffPage = staffDao.findAll(spec,pageRequest);
        return staffPage;
    }

    @Override
    public void delBatch(String[] idsarr) {
        for (int i = 0; i < idsarr.length; i++) {
            String s = idsarr[i];
            staffDao.delBatch(s);
        }

    }

    @Override
    public void goback(String[] idsarr) {
        for (int i = 0; i < idsarr.length; i++) {
            String s = idsarr[i];
            staffDao.goback(s);
        }
    }

    @Override
    public List<Staff> ajaxListInUse(String parameter) {
        if (StringUtils.isNotBlank(parameter)) {
            // 条件查询
            return staffDao.findByProvinceOrCityOrDistrict("%" + parameter + "%");
        } else {
            return staffDao.ajaxListInUse();
        }

    }
}

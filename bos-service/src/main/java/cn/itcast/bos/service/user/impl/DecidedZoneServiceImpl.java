package cn.itcast.bos.service.user.impl;

import cn.itcast.activemq.producer.queue.bos.domain.bc.DecidedZone;
import cn.itcast.bos.dao.bc.DecidedZoneDao;
import cn.itcast.bos.dao.bc.SubareaDao;
import cn.itcast.bos.service.user.DecidedZoneService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Page<DecidedZone> all = decidedZoneDao.findAll(spec,pageRequest);
        for (DecidedZone decidedZone : all) {
            Hibernate.initialize(decidedZone.getStaff());
        }
        return all;
    }
}

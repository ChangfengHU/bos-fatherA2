package cn.itcast.bos.service.user.impl;

import cn.itcast.activemq.producer.queue.bos.domain.bc.Subarea;
import cn.itcast.bos.dao.bc.SubareaDao;
import cn.itcast.bos.service.user.SubareaService;
import org.hibernate.Hibernate;
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
public class SubareaServiceImpl implements SubareaService {
    @Autowired
    private SubareaDao subareaDao;


    @Override
    public void save(Subarea model) {
        subareaDao.save(model);
    }

    @Override
    public Page<Subarea> pageQuery(PageRequest request, Specification<Subarea> spec) {
        // 延迟对象 Region 立刻查询 数据库
        Page<Subarea> all = subareaDao.findAll(spec, request);
        List<Subarea> list = all.getContent();
        for (Subarea subarea : list) {
            Hibernate.initialize(subarea.getRegion());
            Hibernate.initialize(subarea.getDecidedZone());
        }
        return all;
    }

    @Override
    public List<Subarea> findAllBySpecification(Specification<Subarea> specification) {
        List<Subarea> all = subareaDao.findAll(specification);
        for (Subarea subarea : all) {
            Hibernate.initialize(subarea.getRegion());
        }
        return all;
    }

    @Override
    public List<Subarea> noassociation() {
        return subareaDao.noassociation();
    }



}

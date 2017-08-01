package cn.itcast.bos.service.user.impl;

import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.dao.bc.RegionDao;
import cn.itcast.bos.service.user.RegionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/7/23 0023.
 */
@Service
@Transactional
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionDao regionDao;


    @Override
    public void save(List<Region> regions) {
        regionDao.save(regions);
    }

    @Override
    public Page<Region> pageQuery(PageRequest pageRequest) {
        return regionDao.findAll(pageRequest);
    }

    @Override
    public List<Region> ajaxList(String param) {
        if (StringUtils.isNotBlank(param)) {
            // 条件查询
            return regionDao.findByProvinceOrCityOrDistrict("%" + param + "%");
        } else {
            return regionDao.findAll();
        }
    }
}

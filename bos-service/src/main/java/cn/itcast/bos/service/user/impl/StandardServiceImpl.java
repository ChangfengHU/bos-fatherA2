package cn.itcast.bos.service.user.impl;

import cn.itcast.activemq.producer.queue.bos.domain.bc.Standard;
import cn.itcast.bos.dao.bc.StandardDao;
import cn.itcast.bos.service.user.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StandardServiceImpl implements StandardService {
	@Autowired
	private StandardDao standardDao;


	@Override
	public void save(Standard model) {
		standardDao.save(model);
	}

	@Override
	public Page<Standard> pageQuery(PageRequest pageRequest) {
		return standardDao.findAll(pageRequest);
	}

	@Override
	public void delBatch(String[] idsarr) {
		for (int i = 0; i < idsarr.length; i++) {
			String s = idsarr[i];
			standardDao.delBatch(Integer.parseInt(s));
		}

	}

	@Override
	public void goback(String[] idsarr) {
		for (int i = 0; i < idsarr.length; i++) {
			String s = idsarr[i];
			standardDao.goback(Integer.parseInt(s));
		}
	}

	@Override
	public List<Standard> findAllInUse() {
        List<Standard> all = standardDao.findAll();
        return all;
    }


}


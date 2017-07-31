package cn.itcast.bos.service.user;

import cn.itcast.activemq.producer.queue.bos.domain.bc.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RegionService {

	void save(List<Region> regions);

	Page<Region> pageQuery(PageRequest pageRequest);


	List<Region> ajaxList(String spec);


}

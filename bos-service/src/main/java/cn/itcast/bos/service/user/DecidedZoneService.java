package cn.itcast.bos.service.user;

import cn.itcast.activemq.producer.queue.bos.domain.bc.DecidedZone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Administrator on 2017/7/30 0030.
 */
public interface DecidedZoneService {

    void save(String[] sids, DecidedZone model);

    Page<DecidedZone> pageQuery(PageRequest pageRequest, Specification<DecidedZone> spec);
}

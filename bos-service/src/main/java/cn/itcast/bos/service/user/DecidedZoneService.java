package cn.itcast.bos.service.user;

import cn.itcast.bos.domain.bc.DecidedZone;
import cn.itcast.mavencrm.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by Administrator on 2017/7/30 0030.
 */
public interface DecidedZoneService {

    void save(String[] sids, DecidedZone model);

    Page<DecidedZone> pageQuery(PageRequest pageRequest, Specification<DecidedZone> spec);

    List<Customer> findnoassociationcustomers();

    List<Customer> findassociationcustomers(String id);

    void assigncustomerstodecidedzone(String id, String[] customerIds);
}

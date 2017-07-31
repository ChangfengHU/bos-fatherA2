package cn.itcast.bos.service.user;

import cn.itcast.activemq.producer.queue.bos.domain.bc.DecidedZone;
import cn.itcast.activemq.producer.queue.bos.domain.bc.Subarea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
public interface SubareaService {
    void save(Subarea model);

    Page<Subarea> pageQuery(PageRequest request, Specification<Subarea> spec);

    List<Subarea> findAllBySpecification(Specification<Subarea> specification);

    List<Subarea> noassociation();


}

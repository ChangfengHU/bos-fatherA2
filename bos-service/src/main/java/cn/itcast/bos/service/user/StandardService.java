package cn.itcast.bos.service.user;

import cn.itcast.activemq.producer.queue.bos.domain.bc.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface StandardService {
    public void save(Standard model);

    Page<Standard> pageQuery(PageRequest pageRequest);

    void delBatch(String[] idsarr);

    void goback(String[] idsarr);


    List<Standard> findAllInUse();
}
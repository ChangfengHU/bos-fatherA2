package cn.itcast.bos.dao.bc;

import cn.itcast.activemq.producer.queue.bos.domain.bc.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface StandardDao extends JpaRepository<Standard, Integer> {
    @Modifying
    @Query("update Standard set deltag = 0 where id = ?1")
    public void delBatch(int id);
    @Query("from Standard where deltag = 1")
    public List<Standard> findAllInUse();
    @Modifying
    @Query("update Standard set deltag = 1 where id = ?1")
    void goback(int i);
}

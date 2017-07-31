package cn.itcast.bos.dao.bc;

import cn.itcast.activemq.producer.queue.bos.domain.bc.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaffDao extends JpaRepository<Staff, String> ,JpaSpecificationExecutor<Staff> {
    Staff findByTelephone(String telephone);
    @Modifying
    @Query("update Staff set deltag = 1 where id = ?1")
    void delBatch(String i);
    @Modifying
    @Query("update Staff set deltag = 0 where id = ?1")
    void goback(String s);
    @Query("from Staff where deltag = 0")
    List<Staff> ajaxListInUse();
    @Query("from Staff where name like ?1")
    List<Staff> findByProvinceOrCityOrDistrict(String s);
}

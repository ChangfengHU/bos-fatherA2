package cn.itcast.bos.dao.bc;

import cn.itcast.bos.domain.bc.DecidedZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2017/7/30 0030.
 */
public interface DecidedZoneDao extends JpaRepository<DecidedZone, String>, JpaSpecificationExecutor<DecidedZone> {
}

package cn.itcast.bos.service.user;

import cn.itcast.bos.domain.bc.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface StaffService {

    Staff validTelephone(String telephone);

    void save(Staff model);

    Page<Staff> pageQuery(PageRequest pageRequest,Specification<Staff> spec);

    void delBatch(String[] idsarr);

    void goback(String[] idsarr);

    List<Staff> ajaxListInUse(String parameter);
}
package cn.itcast.bos.service.facade;

import cn.itcast.bos.service.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacadeService {
	@Autowired
	private UserService userService;
	@Autowired
	private StandardService standardService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private SubareaService subareaService;
	@Autowired
	private DecidedZoneService decidedZoneService;

	public UserService getUserService() {
		return userService;
	}

	public StandardService getStandardService() {
		// TODO Auto-generated method stub
		return standardService;
	}
	public StaffService getStaffService() {
		// TODO Auto-generated method stub
		return staffService;
	}
	public RegionService getRegionService() {
		// TODO Auto-generated method stub
		return regionService;
	}


	public SubareaService getSubareaService() {
		return subareaService;
	}

	public DecidedZoneService getDecidedZoneService() {
		return decidedZoneService;
	}


}

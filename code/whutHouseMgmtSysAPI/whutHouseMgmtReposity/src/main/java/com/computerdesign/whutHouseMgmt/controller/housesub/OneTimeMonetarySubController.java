package com.computerdesign.whutHouseMgmt.controller.housesub;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.computerdesign.whutHouseMgmt.bean.Msg;
import com.computerdesign.whutHouseMgmt.bean.housesub.BeforePromoteData;
import com.computerdesign.whutHouseMgmt.bean.housesub.MonetarySubSelectModel;
import com.computerdesign.whutHouseMgmt.bean.housesub.MonetarySubVw;
import com.computerdesign.whutHouseMgmt.bean.housesub.OneTimeMonetarySub;
import com.computerdesign.whutHouseMgmt.bean.housesub.OneTimeMonetarySubVw;
import com.computerdesign.whutHouseMgmt.bean.housesub.StaffMonetarySub;
import com.computerdesign.whutHouseMgmt.bean.staffmanagement.Staff;
import com.computerdesign.whutHouseMgmt.service.housesub.BeforePromoteDataService;
import com.computerdesign.whutHouseMgmt.service.housesub.MonetarySubVwService;
import com.computerdesign.whutHouseMgmt.service.housesub.OneTimeMonetarySubService;
import com.computerdesign.whutHouseMgmt.service.housesub.StaffMonetarySubService;
import com.computerdesign.whutHouseMgmt.service.staffmanagement.StaffService;
import com.computerdesign.whutHouseMgmt.utils.ResponseUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RequestMapping(value="/oneTimeMonetarySub/")
@Controller
public class OneTimeMonetarySubController {

	@Autowired
	private OneTimeMonetarySubService oneTimeMonetarySubService;
	
	@Autowired
	private StaffMonetarySubService staffMonetarySubService;
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private MonetarySubVwService monetarySubVwService;

	@Autowired
	private BeforePromoteDataService beforePromoteDataService;
	
	/**
	 * 添加老职工晋升补贴
	 * @param staffId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addPromoteSub/{staffId}", method = RequestMethod.POST)
	public Msg addPromoteSub(@PathVariable("staffId") Integer staffId){
		Staff staff = staffService.get(staffId);
//		System.out.println(staff.getName());
		//如果晋升且是老职工
		Calendar calendar = Calendar.getInstance();
		calendar.set(1998, 11, 31, 0, 0, 0);
		
		System.out.println(staff.getJoinTime().getTime());
		System.out.println(calendar.getTime().getTime());
		
		if(staff.getPromoteFlag() && staff.getJoinTime().getTime() < calendar.getTime().getTime()){
			OneTimeMonetarySub oneTimeMonetarySub = new OneTimeMonetarySub();
			oneTimeMonetarySub.setStaffNo(staff.getNo());
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(new Date());
			oneTimeMonetarySub.setOneTimeSubYear(calendar2.get(Calendar.YEAR) + "");
			oneTimeMonetarySub.setRemark(calendar2.get(Calendar.YEAR) + "年晋升补贴");
			
			//计算补贴额
			BeforePromoteData beforePromoteData = beforePromoteDataService.selectByStaffId(staffId);
			//晋升前享受面积
			double beforePromoteArea = 0;
			try {
				beforePromoteArea = beforePromoteData.getMaxEnjoyArea();
			} catch (Exception e) {
				// TODO: handle exception
				return Msg.success();
			}
			
			//晋升后享受面积
			MonetarySubVw monetarySubVw = monetarySubVwService.getByStaffId(staffId);
			double afterPromoteArea = 0;
			try {
				afterPromoteArea = monetarySubVw.getMaxEnjoyArea();
			} catch (Exception e) {
				// TODO: handle exception
				return Msg.success();
			}
			if(afterPromoteArea > beforePromoteArea){
				BigDecimal subsidy = new BigDecimal(698 * (afterPromoteArea - beforePromoteArea));
				oneTimeMonetarySub.setOneTimeSubsidy(subsidy);
				oneTimeMonetarySubService.add(oneTimeMonetarySub);
				staff.setPromoteFlag(false);
				staffService.update(staff);
				return Msg.success().add("data", oneTimeMonetarySub);
			}else{
				staff.setPromoteFlag(false);
				staffService.update(staff);
				return Msg.success("享受面积没有提升，无补贴！");
			}
		}else{
			staff.setPromoteFlag(false);
			System.out.println(staff.getName());
			staffService.update(staff);
			return Msg.error("必须是老职工并晋升才有补贴！");
		}
	}
	
	/**
	 * 根据条件获取一次性补贴记录
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllOneTimeMonetarySubByCondition", method = RequestMethod.POST)
	public Msg getAllOneTimeMonetarySubByCondition(@RequestBody MonetarySubSelectModel monetarySubSelectModel ,@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		PageHelper.startPage(page, size);
		List<OneTimeMonetarySubVw> oneTimeMonetarySubVws = oneTimeMonetarySubService.getAllOneTimeMonetarySubByCondition(monetarySubSelectModel);
//		System.out.println(oneTimeMonetarySubVws);
//		String[] fileds = { "id", "staffId", "staffNo", "staffName", "deptId", "deptName", "oneTimeSubYear", "oneTimeSubsidy", "remark" };
//		List<Map<String, Object>> response = ResponseUtil.getResultMap(oneTimeMonetarySubVws, fileds);
		PageInfo pageInfo = new PageInfo(oneTimeMonetarySubVws);
		return Msg.success().add("data", pageInfo);
	}
	
	/**
	 * 获取所有一次性补贴记录
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllOneTimeMonetarySub", method = RequestMethod.GET)
	public Msg getAllOneTimeMonetarySub(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		PageHelper.startPage(page, size);
		List<OneTimeMonetarySubVw> oneTimeMonetarySubVws = oneTimeMonetarySubService.getAllOneTimeMonetarySub();
		System.out.println(oneTimeMonetarySubVws);
		PageInfo pageInfo = new PageInfo(oneTimeMonetarySubVws);
		return Msg.success().add("data", pageInfo);
	}

	/**
	 * 根据职工编号获取其所有补贴记录
	 * 
	 * @param staffNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getOneTimeMonetarySubByStaffNo/{staffNo}", method = RequestMethod.GET)
	public Msg getOneTimeMonetarySubByStaffNo(@PathVariable("staffNo") String staffNo) {
		if (staffNo != null) {
			List<OneTimeMonetarySubVw> oneTimeMonetarySubVws = oneTimeMonetarySubService
					.getOneTimeMonetarySubByStaffNo(staffNo);
			return Msg.success().add("data", oneTimeMonetarySubVws);
		} else {
			return Msg.error();
		}
	}

	/**
	 * 根据id删除一条一次性补贴记录
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteOneTimeMonetarySub/{id}", method = RequestMethod.DELETE)
	public Msg deleteOneTimeMonetarySub(@PathVariable("id") Integer id) {
		if (id != null) {
			oneTimeMonetarySubService.delete(id);
			return Msg.success("删除成功");
		} else {
			return Msg.error();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "addOneTimeMonetarySub", method = RequestMethod.POST)
	public Msg addOneTimeMonetarySub(@RequestBody OneTimeMonetarySub oneTimeMonetarySub) {
		if(oneTimeMonetarySub != null){
			Integer staffId = staffService.getStaffIdByStaffNo(oneTimeMonetarySub.getStaffNo());
			Staff staff = staffService.get(staffId);
			Calendar calendar = Calendar.getInstance();
			calendar.set(1998, 11, 31, 0, 0, 0);
			//老职工
			if(staff.getJoinTime().getTime() < calendar.getTime().getTime()){
				
				//借用逐月补贴视图计算住房补贴标准
				MonetarySubVw monetarySubVw = monetarySubVwService.getByStaffId(staffId);
				double enjoyHouseArea = 0.0;
				// 职务职称享受面积取最大值，获取职工住房补贴标准
				if (monetarySubVw.getTitleHouseArea() != null && monetarySubVw.getPostHouseArea() != null) {
					enjoyHouseArea = Math.max(monetarySubVw.getTitleHouseArea(), monetarySubVw.getPostHouseArea());
				} else if (monetarySubVw.getTitleHouseArea() == null && monetarySubVw.getPostHouseArea() != null) {
					enjoyHouseArea = monetarySubVw.getPostHouseArea();
				} else {
					enjoyHouseArea = monetarySubVw.getTitleHouseArea();
				}
				
				// 获取职工家庭已购住房
				// double buyHouseArea = monetarySubVw.getHouseBuildArea();
				double buyHouseArea = 0.0;
				if(monetarySubVw.getHouseUsedArea() != null){
					buyHouseArea = monetarySubVw.getHouseUsedArea();
				}
				
				//1998年以前实际工龄,工龄的计算？
				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(staff.getJoinTime());
				int jobYear = 1999 - calendar2.get(Calendar.YEAR);
//				System.out.println(jobYear);
				
				//住房公积金工龄,先认为是2002年
				int houseFundYear = 2002 - calendar2.get(Calendar.YEAR);
				
				//无房
				if(!staffMonetarySubService.isOwnHouse(staffId)){
					double subsidies = (22 * jobYear + 13 * houseFundYear) * enjoyHouseArea;
					System.out.println(subsidies);
					oneTimeMonetarySub.setOneTimeSubsidy(new BigDecimal(subsidies));
				}else if(enjoyHouseArea > buyHouseArea){
					//有房但未达标
					double subsidies = (22 * jobYear + 13 * houseFundYear) * (enjoyHouseArea - buyHouseArea);
					oneTimeMonetarySub.setOneTimeSubsidy(new BigDecimal(subsidies));
				}
				oneTimeMonetarySubService.add(oneTimeMonetarySub);
				return Msg.success("添加成功").add("data", oneTimeMonetarySub);
			}else{
				return Msg.error("新职工无一次性补贴");
			}
		}else{
			return Msg.error("传入参数有误，无法封装成对象");
		}
	}
	

}

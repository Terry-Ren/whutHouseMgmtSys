package com.computerdesign.whutHouseMgmt.controller.staffparam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.computerdesign.whutHouseMgmt.bean.Msg;
import com.computerdesign.whutHouseMgmt.bean.staffparam.StaffParameter;
import com.computerdesign.whutHouseMgmt.service.staffparam.StaffParameterService;

@RequestMapping("/staffParam/")
@Controller
public class StaffParameterController {

	@Autowired
	StaffParameterService StaffParameterService;
	
	@ResponseBody
	@RequestMapping(value="modify", method=RequestMethod.PUT)
	public Msg modifyStaffParameter(@RequestBody StaffParameter StaffParameter){
		System.out.println(StaffParameter);
		try{
			StaffParameterService.update(StaffParameter);
			return Msg.success().add("modifiedStaffParameter", StaffParameter);
		}catch(Exception e) {
			return Msg.error();
		}
		
	}

	/**
	 * 添加一条职工参数记录
	 * @param StaffParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Msg addStaffParameter(@RequestBody StaffParameter StaffParameter) {
//		System.out.println(StaffParameter);
		if(StaffParameter.getStaffParamName()!=null && StaffParameter.getParamTypeId() != null && StaffParameter.getParamTypeName()!=null){
			StaffParameterService.add(StaffParameter);
			return Msg.success().add("addedEmpParam", StaffParameter);
		}else{
			return Msg.error("必要信息不完整，添加失败");
		}
		
	}

	/**
	 * 根据StaffParamId删除对应记录
	 * @param StaffParamId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete/{StaffParamId}", method = RequestMethod.DELETE)
	public Msg deleteStaffParameter(@PathVariable("StaffParamId") Integer StaffParamId) {
		StaffParameter StaffParameter = StaffParameterService.get(StaffParamId);
		if (StaffParameter == null) {
			return Msg.error("数据库中无该记录");
		} else {
			try {
				StaffParameterService.delete(StaffParamId);
				return Msg.success().add("deletedStaffParam", StaffParameter);
			} catch (Exception e) {
				return Msg.error();
			}
		}

	}

	/**
	 * 根据paramTypeId获取对应类型职工参数
	 * @param paramTypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get/{paramTypeId}")
	public Msg getStaffParameter(@PathVariable("paramTypeId") Integer paramTypeId) {
		// 获取所有参数
		List<StaffParameter> staffParams = StaffParameterService.getAll();

		// 用于封装结果数据
		List<StaffParameter> staffParamsResult = new ArrayList<StaffParameter>();
		for (StaffParameter staffParam : staffParams) {
			if (staffParam.getParamTypeId() == paramTypeId) {
				staffParamsResult.add(staffParam);
			}
		}

		if (staffParamsResult != null) {
			return Msg.success().add("staffParamsResult", staffParamsResult);
		} else {
			return Msg.error();
		}
	}

}

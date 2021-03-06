package com.computerdesign.whutHouseMgmt.service.internetselecthouse;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.computerdesign.whutHouseMgmt.bean.internetselecthouse.StaffSelectHouse;
import com.computerdesign.whutHouseMgmt.bean.internetselecthouse.StaffSelectHouseExample;
import com.computerdesign.whutHouseMgmt.bean.internetselecthouse.StaffSelectHouseExample.Criteria;
import com.computerdesign.whutHouseMgmt.dao.internetselecthouse.StaffSelectHouseMapper;

@Service
public class StaffSelectHouseService {

	@Autowired
	private StaffSelectHouseMapper staffSelectHouseMapper;
	
	public void update(StaffSelectHouse staffSelectHouse){
		staffSelectHouseMapper.updateByPrimaryKeySelective(staffSelectHouse);
	}
	
	/**
	 * 根据staffId和RecordStatus获取一个StaffSelectHouse对象
	 * @param staffId
	 * @param RecordStatus
	 * @return
	 */
	public StaffSelectHouse getByStaffIdAndRecordStatus(Integer staffId, String recordStatus){
		StaffSelectHouseExample example = new StaffSelectHouseExample();
		Criteria criteria = example.createCriteria();
		criteria.andStaffIdEqualTo(staffId);
		criteria.andRecordStatusEqualTo(recordStatus);
		if(staffSelectHouseMapper.selectByExample(example).size() > 0){
			return staffSelectHouseMapper.selectByExample(example).get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据staffId获取一个StaffSelectHouse对象，允许RecordStatus为canselect和selected
	 * @param staffId
	 * @return
	 */
	public StaffSelectHouse getByStaffIdAndDoubleRecordStatus(Integer staffId){
		StaffSelectHouseExample example = new StaffSelectHouseExample();
		Criteria criteria = example.createCriteria();
		criteria.andStaffIdEqualTo(staffId);
		criteria.andRecordStatusIn(Arrays.asList("canselect","selected"));
		if(staffSelectHouseMapper.selectByExample(example).size() > 0){
			return staffSelectHouseMapper.selectByExample(example).get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据staffId获取一个StaffSelectHouse对象
	 * @param staffId
	 * @return
	 */
	public StaffSelectHouse getByStaffId(Integer staffId){
		StaffSelectHouseExample example = new StaffSelectHouseExample();
		Criteria criteria = example.createCriteria();
		criteria.andStaffIdEqualTo(staffId);
		if(staffSelectHouseMapper.selectByExample(example).size() > 0){
			return staffSelectHouseMapper.selectByExample(example).get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取StaffSelectHouse数据库中所有可以选房的数据
	 * @return
	 */
	public List<StaffSelectHouse> getAll(){
		StaffSelectHouseExample example = new StaffSelectHouseExample();
		Criteria criteria = example.createCriteria();
		criteria.andRecordStatusEqualTo("canselect");
		return staffSelectHouseMapper.selectByExample(example);
	}
	
	/**
	 * 获取所有能够选房的数据并且根据选房时间排序
	 * @return
	 */
	public List<StaffSelectHouse> getAllAndOrderBySelectTime(){
		StaffSelectHouseExample example = new StaffSelectHouseExample();
		example.setOrderByClause("SelectStart DESC");
		Criteria criteria = example.createCriteria();
		criteria.andRecordStatusEqualTo("canselect");
		return staffSelectHouseMapper.selectByExample(example);
	}
	
	public void insert(StaffSelectHouse staffSelectHouse){
		staffSelectHouseMapper.insertSelective(staffSelectHouse);
	}
	
}

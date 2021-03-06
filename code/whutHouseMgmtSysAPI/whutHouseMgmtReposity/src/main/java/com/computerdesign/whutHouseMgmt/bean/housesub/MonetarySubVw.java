package com.computerdesign.whutHouseMgmt.bean.housesub;

import java.util.Date;

public class MonetarySubVw {
    private Integer id;

    private Integer staffId;

    private String staffNo;

    private String staffName;

    private String year;

    private Long annualSal;

    private Long subsidies;

    private String remark;

    private Date joinTime;

    private Integer deptId;

    private String deptName;

    private Integer titleId;

    private String titleName;

    private Float titleHouseArea;

    private Integer postId;

    private String postName;

    private Integer typeId;

    private String typeName;

    private Float postHouseArea;

    private Double maxEnjoyArea;

    private Integer houseId;

    private String houseNo;

    private Double houseBuildArea;

    private Double houseUsedArea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo == null ? null : staffNo.trim();
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public Long getAnnualSal() {
        return annualSal;
    }

    public void setAnnualSal(Long annualSal) {
        this.annualSal = annualSal;
    }

    public Long getSubsidies() {
        return subsidies;
    }

    public void setSubsidies(Long subsidies) {
        this.subsidies = subsidies;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public Integer getTitleId() {
        return titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName == null ? null : titleName.trim();
    }

    public Float getTitleHouseArea() {
        return titleHouseArea;
    }

    public void setTitleHouseArea(Float titleHouseArea) {
        this.titleHouseArea = titleHouseArea;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName == null ? null : postName.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Float getPostHouseArea() {
        return postHouseArea;
    }

    public void setPostHouseArea(Float postHouseArea) {
        this.postHouseArea = postHouseArea;
    }

    public Double getMaxEnjoyArea() {
        return maxEnjoyArea;
    }

    public void setMaxEnjoyArea(Double maxEnjoyArea) {
        this.maxEnjoyArea = maxEnjoyArea;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo == null ? null : houseNo.trim();
    }

    public Double getHouseBuildArea() {
        return houseBuildArea;
    }

    public void setHouseBuildArea(Double houseBuildArea) {
        this.houseBuildArea = houseBuildArea;
    }

    public Double getHouseUsedArea() {
        return houseUsedArea;
    }

    public void setHouseUsedArea(Double houseUsedArea) {
        this.houseUsedArea = houseUsedArea;
    }
}
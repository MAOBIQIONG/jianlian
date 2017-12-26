package com.fh.entity.projects;

import java.io.Serializable; 

public class project implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	private String projectId;  
	private String projectName;
	private String projectUserId;
	private String principalName;
	private String phone;
	private String addr;
	private String dueTime;
	private String projectContent;
    private String projectTypeId;
    private String releaseTime;
    private String projectLevel;
    private Integer status;//0：已提交，1：已修改， 2：审核未通过  3：审核通过  4：中标确认中  5：已中标
    private String bidWinnerId;
    private String bidTime ;
    private Double bidPrice;
    private String noPassReason;
    private String auditBy;
    private String auditTime;
    private String modifyBy;
    private String modifyTime; 
    private String projectStageId;
    private String bidRequirements;
    private Double startPrice;
    private Integer collectionCounts;
    private String projectManagerId;
    
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectUserId() {
		return projectUserId;
	}
	public void setProjectUserId(String projectUserId) {
		this.projectUserId = projectUserId;
	}
	public String getPrincipalName() {
		return principalName;
	}
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getDueTime() {
		return dueTime;
	}
	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}
	public String getProjectContent() {
		return projectContent;
	}
	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	} 
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getProjectLevel() {
		return projectLevel;
	}
	public void setProjectLevel(String projectLevel) {
		this.projectLevel = projectLevel;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getBidWinnerId() {
		return bidWinnerId;
	}
	public void setBidWinnerId(String bidWinnerId) {
		this.bidWinnerId = bidWinnerId;
	}
	public String getBidTime() {
		return bidTime;
	}
	public void setBidTime(String bidTime) {
		this.bidTime = bidTime;
	}
	public Double getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}
	 
	public String getNoPassReason() {
		return noPassReason;
	}
	public void setNoPassReason(String noPassReason) {
		this.noPassReason = noPassReason;
	}
	public String getAuditBy() {
		return auditBy;
	}
	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getProjectTypeId() {
		return projectTypeId;
	}
	public void setProjectTypeId(String projectTypeId) {
		this.projectTypeId = projectTypeId;
	}
	public String getProjectStageId() {
		return projectStageId;
	}
	public void setProjectStageId(String projectStageId) {
		this.projectStageId = projectStageId;
	}
	public String getBidRequirements() {
		return bidRequirements;
	}
	public void setBidRequirements(String bidRequirements) {
		this.bidRequirements = bidRequirements;
	}
	public Double getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}
	public Integer getCollectionCounts() {
		return collectionCounts;
	}
	public void setCollectionCounts(Integer collectionCounts) {
		this.collectionCounts = collectionCounts;
	}
	public String getProjectManagerId() {
		return projectManagerId;
	}
	public void setProjectManagerId(String projectManagerId) {
		this.projectManagerId = projectManagerId;
	}  
}


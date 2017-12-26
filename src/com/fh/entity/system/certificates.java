 package com.fh.entity.system;
 
 public class certificates
 {
	 private String ID;
	 private String USERID;
	 private String CERTIFICATENAME;
	 private String CERTIFICATETYPE;
	 private Integer STATUS;
	 private String IMGPATH;
	 private String TIME;
	 private String COMPANYID;
	 private String NOPASSREASON;
	 
	public String getNOPASSREASON() {
		return NOPASSREASON;
	}
	public void setNOPASSREASON(String nOPASSREASON) {
		NOPASSREASON = nOPASSREASON;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public String getCERTIFICATENAME() {
		return CERTIFICATENAME;
	}
	public void setCERTIFICATENAME(String cERTIFICATENAME) {
		CERTIFICATENAME = cERTIFICATENAME;
	}
	public String getCERTIFICATETYPE() {
		return CERTIFICATETYPE;
	}
	public void setCERTIFICATETYPE(String cERTIFICATETYPE) {
		CERTIFICATETYPE = cERTIFICATETYPE;
	}
	
	
	public Integer getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(Integer sTATUS) {
		STATUS = sTATUS;
	}
	public String getIMGPATH() {
		return IMGPATH;
	}
	public void setIMGPATH(String iMGPATH) {
		IMGPATH = iMGPATH;
	}
	public String getTIME() {
		return TIME;
	}
	public void setTIME(String tIME) {
		TIME = tIME;
	}
	public String getCOMPANYID() {
		return COMPANYID;
	}
	public void setCOMPANYID(String cOMPANYID) {
		COMPANYID = cOMPANYID;
	}
	 
	 
 }


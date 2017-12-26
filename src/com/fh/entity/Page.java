package com.fh.entity;

import com.fh.util.PageData;
import com.fh.util.Tools;

public class Page {
	private int pageSize;
	private int pageCurrent;
	private int currentResult;
	private int totalNum;
	private String orderField;
	private String orderDirection;
	private boolean entityOrField;
	private PageData pd = new PageData();

	public Page() {
		try {
			/*this.pageSize = Integer.parseInt(Tools
					.readTxtFile("admin/config/PAGE.txt"));*/
			this.pageSize = 30;
		} catch (Exception e) {
			this.pageSize = 15;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCurrent() {
		//return pageCurrent == 0 ? 1 : pageCurrent;
		return pageCurrent;
	}

	public void setPageCurrent(int pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}

	public int getCurrentResult() {
		/*this.currentResult = ((getPageCurrent() - 1) * getPageSize());
		if (this.currentResult < 0)
			this.currentResult = 0;
		return this.currentResult;*/
		this.currentResult = getPageCurrent();
		return this.currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public PageData getPd() {
		return pd;
	}

	public void setPd(PageData pd) {
		this.pd = pd;
	}

} 
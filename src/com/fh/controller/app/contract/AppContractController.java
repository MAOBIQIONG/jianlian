package com.fh.controller.app.contract;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.app.AppController;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.contract.AppContractService;
import com.fh.util.AppUtil;
import com.fh.util.FileDownload;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/appcontract" })
public class AppContractController extends AppController{

	@Resource(name = "appcontractService")
	private AppContractService appcontractService;
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	
	//合同列表
	@RequestMapping({ "/querContractlist" })
	@ResponseBody
	public PageData querContractlist() throws Exception {
		PageData _result=new PageData();
		PageData pa = getPageData();
		List<PageData> list=new ArrayList<PageData>();
		list=this.appcontractService.querContractlist(pa);
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result;
	}
	
	//合同模板下载
	@RequestMapping({ "/downFile" })
	@ResponseBody
	public void downFile(HttpServletResponse response) throws Exception {	 
		PageData pa = getPageData(); 
		PageData data=appcontractService.findById(pa);
		FileDownload.fileDownload(response,data.getString("FILE_PATH"),data.getString("FILE_NAME"));
	} 
	
	//图片下载
	@RequestMapping({ "/downImg" })
	@ResponseBody
	public void downImg(HttpServletResponse response) throws Exception {	 
		PageData pa = getPageData(); 
		FileDownload.fileDownload(response,pa.getString("FILE_PATH"),pa.getString("FILE_NAME"));
	}
}

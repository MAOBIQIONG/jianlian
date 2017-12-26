package com.fh.controller.app.Apppoints;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fh.controller.app.AppController;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.app.Apppoints.AppPointsService;
import com.fh.service.system.appuser.AppuserService;
import com.fh.util.AppUtil;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping({ "/apppoints" })
public class AppPointsController extends AppController{

	@Resource(name = "apppointsService")
	private AppPointsService apppointsService;
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;  
	
	//积分获取明细列表
	@RequestMapping({ "/queryDetailed" })
	@ResponseBody
	public PageData queryDetailed() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> list=new ArrayList<PageData>();
		PageData data=new PageData();
		data.put("ID", pa.getString("userid"));
		
//		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
//		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
//		int totalSize=currentPage*pageSize; 
//		data.put("totalSize",totalSize);
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int start=(currentPage-1)*pageSize;
		data.put("start",start); 
		data.put("pageSize",pageSize); 
		
		
		list=this.apppointsService.queryDetailed(data);//获取积分列表
		PageData total_points=this.apppointsService.querytotal(data);//获取总积分
		PageData jifenzong=this.apppointsService.queryDetailedjifenzong(data);
		Map map=new HashMap();
		map.put("list",list);
		map.put("TOTAL_POINTS",total_points);
		map.put("listzong",list.size());
		map.put("jifenzong",jifenzong);
		_result = AppUtil.ss(map, "01", "成功","true", null);
		return _result;
	}
	
}

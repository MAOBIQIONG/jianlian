package com.fh.controller.app.company;
import java.util.HashMap;
import java.util.List; 
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.clan.AppClanService;
import com.fh.service.app.company.AppCategoryService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/appCategory" })
public class AppCategoryController extends BaseController {  
	
	@Resource(name = "appCategoryService")
	private AppCategoryService appCateService;  
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	
	@Resource(name = "appClanService")
	private AppClanService appClanService;
	 
	 //查询所有行业 
	@RequestMapping({ "/queryAllPIDCategory" })
	@ResponseBody
	public PageData queryAllPIDCategory()throws Exception{
		PageData _result=new PageData(); 
		List<PageData> list=this.appCateService.listByPId("0");
		for(int i=0;i<list.size();i++){
			PageData data=list.get(i);
			List<PageData> child=this.appCateService.listByPId(data.getString("value"));
			list.get(i).put("children",child);
		}
		List<PageData> clanList=this.appClanService.queryAll(null);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("clanList", clanList);
		map.put("catelist", list);
  		_result = AppUtil.ss(map, "01", "成功","true", null);
		return _result; 
	} 
} 
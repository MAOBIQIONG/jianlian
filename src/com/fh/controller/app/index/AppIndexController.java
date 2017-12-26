package com.fh.controller.app.index;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;  

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; 

import com.fh.controller.app.AppController;
import com.fh.service.app.AppCommonService; 
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.app.activity.AppActivityService; 
import com.fh.service.app.media.AppCommentsService;
import com.fh.service.app.media.AppMediaService;
import com.fh.service.app.media.AppRotationService;
import com.fh.service.app.project.AppProjectService;
import com.fh.util.AppUtil; 
import com.fh.util.PageData; 

@Controller
@RequestMapping({ "/appIndex" })
public class AppIndexController extends AppController{

	@Resource(name = "appmediaService")
	private AppMediaService appmediaService;
		
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	
	@Resource(name="appRotationservice")
	private AppRotationService appRotationservice;
	
	@Resource(name="appCommentsService")
	private AppCommentsService appCommService;  
	
	@Resource(name = "appProjectService")
	private AppProjectService appProjectService;
	
	@Resource(name = "appActivityService")
	private AppActivityService appActivityService;
	
	@Resource(name = "appusersService")
	private AppusersService appusersService;
	 
	//获取首页轮换大图
	@RequestMapping({ "/queryIndexByColno" })
	@ResponseBody
	public PageData queryIndexByColno()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		pa.put("LOCATION_NO","01");
		List<PageData> list=this.appRotationservice.queryByColno(pa); 
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result; 
	}
	
	//首页信息获取接口
	@RequestMapping({ "/indexInfo" })
	@ResponseBody
	public PageData indexInfo() throws Exception {
		long t1 = System.currentTimeMillis();
		
		PageData _result=new PageData();
		PageData pa =new PageData();
		pa.put("LOCATION_NO","01");
		List<PageData> piclist=this.appRotationservice.queryByColno(pa); //查询首页轮换大图
		pa.put("LOCATION_NO","08");
		List<PageData> advlist=this.appRotationservice.queryByColno(pa); //查询首页轮换大图
		List<PageData> hotMadia=appmediaService.queryHot();//查询建联快报
		List<PageData> hotList=appProjectService.queryHotProject();//查询热门项目
		PageData bidder=appProjectService.queryNewBidder(pa);//查询最新成交项目 
		List<PageData> hotActs=appActivityService.queryHotActivity(null);//查询热门活动
		for(PageData pageData : hotActs){   
			pageData.put("START_DATE",pageData.getString("START_DATE")!=null&&pageData.getString("START_DATE")!=""?pageData.getString("START_DATE").substring(0,16):"");
			pageData.put("END_DATE",pageData.getString("END_DATE")!=null&&pageData.getString("END_DATE")!=""?pageData.getString("END_DATE").substring(0,16):"");
			pageData.put("DUE_DATE",pageData.getString("DUE_DATE")!=null&&pageData.getString("DUE_DATE")!=""?pageData.getString("DUE_DATE").substring(0,16):"");
		}
		Map<String,Object> map=new HashMap<String,Object>(); 
		map.put("piclist",piclist);
		map.put("hotMadia",hotMadia);
		map.put("hotList",hotList);
		map.put("bidder",bidder);
		map.put("hotActs",hotActs);
		map.put("advlist",advlist);
		_result = AppUtil.ss(map, "01", "成功","true", null);
		
		long t2 = System.currentTimeMillis();
		System.out.println("/------------------华丽的分割线---------------------/");
		System.out.println("首页获取数据时间（毫秒）："+(t2-t1));
		System.out.println("/------------------华丽的分割线---------------------/");
		return _result;
	}
	

	//首页信息获取接口
	@RequestMapping({ "/validRight" })
	@ResponseBody
	public PageData validRight() throws Exception {
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData()); 
		List<PageData> userList=this.appusersService.querySimpleInfoById(pa);//获取用户基本信息  ，用于判断是否有权限 
		_result = AppUtil.ss(userList.get(0), "01", "成功","true", null);
		return _result;
	}
	
	
	//根据标题模糊查询
	@RequestMapping({ "/findTitle" })
	@ResponseBody
	public PageData findTitle() throws Exception {
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData());
		String TITLE=pa.getString("TITLE");
		if((TITLE!=null) && !("".equals(TITLE))){
			TITLE=new String(TITLE.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("TITLE", TITLE);
		}
		List<PageData> prolist=this.appProjectService.findProjectByTitle(pa);
		for(int i=0;i<prolist.size();i++){
			String param=prolist.get(i).getString("PROJECT_NAME");
			param = param.replace(pa.getString("TITLE"),"<font color=\"red\">"+pa.getString("TITLE")+"</font>");//关键字样式   
			prolist.get(i).put("PROJECT_NAME",param);
		}
		List<PageData> madlist=this.appmediaService.findMediaByTitle(pa);
		for(int i=0;i<madlist.size();i++){
			String param=madlist.get(i).getString("TITLE");
			param = param.replace(pa.getString("TITLE"),"<font color=\"red\">"+pa.getString("TITLE")+"</font>");//关键字样式   
			madlist.get(i).put("TITLE",param);
		}
		List<PageData> actList=this.appActivityService.findActityByName(pa);
		for(int i=0;i<actList.size();i++){
			String param=actList.get(i).getString("ACTIVITY_NAME");
			param = param.replace(pa.getString("TITLE"),"<font color=\"red\">"+pa.getString("TITLE")+"</font>");//关键字样式   
			actList.get(i).put("ACTIVITY_NAME",param);
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("prolist",prolist);
		map.put("madlist",madlist);
		map.put("actList",actList);
		map.put("keyWords",pa.getString("TITLE"));
		_result = AppUtil.ss(map, "01", "成功","true", null);
		return _result;
	}
	
}

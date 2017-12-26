package com.fh.controller.app.activity;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.service.app.AppCitiesService;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.AppDicService;
import com.fh.service.app.activity.AppActivityService;
import com.fh.service.app.collection.AppCollectionService;
import com.fh.service.app.media.AppRotationService;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/appActivity" })
public class AppActivityController extends BaseController {
	 
	@Resource(name = "appActivityService")
	private AppActivityService appActivityService; 
	
	@Resource(name = "appDicService")
	private AppDicService appDicService; 
	 
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	
	@Resource(name="appRotationservice")
	private AppRotationService appRotationservice;
	
	@Resource(name="appCitiesService")
	private AppCitiesService appCitiesService;
	
	@Resource(name = "appCollectionService")
	private AppCollectionService appColService;
	//活动首页获取接口
	@RequestMapping({ "/indexInfo" })
	@ResponseBody
	public PageData indexInfo() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		PageData data=new PageData(); 
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int totalSize=currentPage*pageSize; 
		data.put("totalSize",totalSize);  
		
		pa.put("LOCATION_NO","04"); 
		List<PageData> piclist=this.appRotationservice.queryByColno(pa); //查询活动首页轮换大图 
		List<PageData> typeList=this.appDicService.queryByPBM("activitytype");//活动类型
		List<PageData> actList=this.appActivityService.queryByParam(data);//查询活动列表
		for (PageData pageData : actList) {
			List<PageData> userlist=this.appActivityService.queryUserByAId(pageData);
			pageData.put("userlist",userlist);
			pageData.put("contlist",userlist.size());//某活动参与人总数 
			pageData.put("START_DATE",pageData.getString("START_DATE")!=null&&pageData.getString("START_DATE")!=""?pageData.getString("START_DATE").substring(0,16):"");
			pageData.put("END_DATE",pageData.getString("END_DATE")!=null&&pageData.getString("END_DATE")!=""?pageData.getString("END_DATE").substring(0,16):"");
			pageData.put("DUE_DATE",pageData.getString("DUE_DATE")!=null&&pageData.getString("DUE_DATE")!=""?pageData.getString("DUE_DATE").substring(0,16):"");
		} 
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("piclist",piclist);  
		map.put("typeList",typeList);
		map.put("actList",actList);
		_result = AppUtil.ss(map, "01", "成功","true", null); 
		return _result;
	}
	
	//<!--查询活动信息 --> 
	@RequestMapping({ "/queryAll" })
	@ResponseBody
	public PageData queryAll() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> list=new ArrayList<PageData>();
		list=this.appActivityService.queryByParam(null);
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result;
	} 
	
	//<!--根据参数查询活动信息 --> 
	@RequestMapping({ "/queryByParam" })
	@ResponseBody
	public PageData queryByParam() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int totalSize=currentPage*pageSize; 
		pa.put("totalSize",totalSize);  
		
		if(pa.containsKey("CREATE_DATE")){
			if("2".equals(pa.getString("CREATE_DATE"))){
				pa.put("CREATE_DATE",DateUtil.getDay(new Date()));
			}else{
				pa.put("CREATE_DATE",null);
			}
		}
		List<PageData> list=new ArrayList<PageData>(); 
		list=this.appActivityService.queryByParam(pa);
		for (PageData pageData : list) {
			List<PageData> userlist=this.appActivityService.queryUserByAId(pageData);
			pageData.put("userlist",userlist);
			pageData.put("contlist",userlist.size());//某活动参与人总数
			pageData.put("START_DATE",pageData.getString("START_DATE")!=null&&pageData.getString("START_DATE")!=""?pageData.getString("START_DATE").substring(0,16):"");
			pageData.put("END_DATE",pageData.getString("END_DATE")!=null&&pageData.getString("END_DATE")!=""?pageData.getString("END_DATE").substring(0,16):"");
			pageData.put("DUE_DATE",pageData.getString("DUE_DATE")!=null&&pageData.getString("DUE_DATE")!=""?pageData.getString("DUE_DATE").substring(0,16):"");
		}
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result;
	} 
	
	//<!--根据参数查询用户发布信息 --> 
	@RequestMapping({ "/queryBackupByParam" })
	@ResponseBody
	public PageData queryBackupByParam() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int totalSize=currentPage*pageSize; 
		pa.put("totalSize",totalSize);  
		String ACTIVITY_NAME=pa.getString("ACTIVITY_NAME");
		if ((ACTIVITY_NAME != null) && (!"".equals(ACTIVITY_NAME))) { 
			ACTIVITY_NAME=new String(ACTIVITY_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("ACTIVITY_NAME",ACTIVITY_NAME);
		}
		List<PageData> list=new ArrayList<PageData>(); 
		list=this.appActivityService.queryBackupByParam(pa); 
		PageData fbzong=this.appActivityService.queryBackupByParamfbhdzong(pa);
		
		PageData wsd=new PageData();
		wsd.put("list", list);
		wsd.put("listzong", list.size());
		wsd.put("fbzong", fbzong);
		for (PageData pageData : list) {
			List<PageData> userlist=this.appActivityService.queryUserByAId(pageData);
			pageData.put("userlist",userlist);
			pageData.put("contlist",userlist.size());//某活动参与人总数
			pageData.put("START_DATE",pageData.getString("START_DATE")!=null&&pageData.getString("START_DATE")!=""?pageData.getString("START_DATE").substring(0,16):"");
			pageData.put("END_DATE",pageData.getString("END_DATE")!=null&&pageData.getString("END_DATE")!=""?pageData.getString("END_DATE").substring(0,16):"");
			pageData.put("DUE_DATE",pageData.getString("DUE_DATE")!=null&&pageData.getString("DUE_DATE")!=""?pageData.getString("DUE_DATE").substring(0,16):"");
		}
		_result = AppUtil.ss(wsd, "01", "成功","true", null);
		return _result;
	} 
	
	//通过ID获取详细信息
	@RequestMapping({ "/queryById" })
	@ResponseBody
	public PageData queryById()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData activity = this.appActivityService.queryById(pa);//活动信息
		Object keys=activity.get("KEY_WORDS");
		if(keys!=null&&!"".equals(keys)){
		    String	key=keys+"";
			if(key.contains(",")){
				String[] keyWords=key.split(",");
				activity.put("keyWords",keyWords);
			} 
		}
		List<PageData> honorlist = this.appActivityService.queryHonorByAId(pa);//嘉宾信息
		List<PageData> userlist = this.appActivityService.queryUserByAId(pa);//参与者信息
		List<PageData> levellist = this.appActivityService.queryLevelByAId(pa);//可以参与活动的等级
		List<PageData> pageList=appActivityService.checkIsApply(pa);//判断当前用户是否已经报名参与这个活动
		List<PageData> colList=appColService.queryByUidAndOid(pa);//判断当前用户是否已经收藏这个活动
		if(colList!=null&&colList.size()>0){
			activity.put("isCollect",1);//已经收藏
		}else{
			activity.put("isCollect",0);//未收藏
		} 
		if(pageList!=null&&pageList.size()>0){
			activity.put("apply",1);//已经报名参与该活动
		}else{
			activity.put("apply",0);//未报名参与该活动
		} 
		activity.put("START_DATE",activity.getString("START_DATE")!=null&&activity.getString("START_DATE")!=""?activity.getString("START_DATE").substring(0,16):"");
		activity.put("END_DATE",activity.getString("END_DATE")!=null&&activity.getString("END_DATE")!=""?activity.getString("END_DATE").substring(0,16):"");
		activity.put("DUE_DATE",activity.getString("DUE_DATE")!=null&&activity.getString("DUE_DATE")!=""?activity.getString("DUE_DATE").substring(0,16):"");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("activity", activity);
		map.put("honorlist",honorlist);
		map.put("userlist",userlist);
		map.put("levellist",levellist);
		map.put("honorCount",honorlist.size());
		_result = AppUtil.ss(map, "01", "成功","true", null);
		return _result; 
	}
	
	//获取活动类型及城市信息、活动信息
	@RequestMapping({ "/queryActType" })
	@ResponseBody
	public PageData queryActType()throws Exception{
		PageData _result=new PageData();
		Map<String,Object> map=new HashMap<String,Object>();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		List<PageData> typeList=this.appDicService.queryByPBM("activitytype");
		//List<PageData> citiyList=this.appCitiesService.queryCitiesByParam(null);
		if(pa.getString("ID")!=null&&!"".equals(pa.getString("ID"))){
			PageData activity=this.appActivityService.queryById(pa); 
			activity.put("START_DATE",activity.getString("START_DATE")!=null&&activity.getString("START_DATE")!=""?activity.getString("START_DATE").substring(0,16):"");
			activity.put("END_DATE",activity.getString("END_DATE")!=null&&activity.getString("END_DATE")!=""?activity.getString("END_DATE").substring(0,16):"");
			activity.put("DUE_DATE",activity.getString("DUE_DATE")!=null&&activity.getString("DUE_DATE")!=""?activity.getString("DUE_DATE").substring(0,16):"");
			map.put("activity", activity);
		}else{
			map.put("activity", null);
		} 
		map.put("typeList", typeList);
		//map.put("citiyList",citiyList); 
		_result = AppUtil.ss(map, "01", "成功","true", null);
		return _result; 
	} 
	
	//<!--查询当前用户参与的项目列表 --> 
	@RequestMapping({ "/queryActByUId" })
	@ResponseBody
	public PageData queryActByUId()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int totalSize=currentPage*pageSize; 
		pa.put("totalSize",totalSize);  

		String ACTIVITY_NAME=pa.getString("ACTIVITY_NAME");
		if ((ACTIVITY_NAME != null) && (!"".equals(ACTIVITY_NAME))) { 
			ACTIVITY_NAME=new String(ACTIVITY_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("ACTIVITY_NAME",ACTIVITY_NAME);
		}
		List<PageData> list=this.appActivityService.queryActByUId(pa); 
		int listzong=list.size();
		PageData xmzong=this.appActivityService.queryActByUIdzong(pa);
		PageData wer=new PageData();
		wer.put("list", list);
		wer.put("listzong", listzong);
		wer.put("xmzong", xmzong);
		for (PageData pageData : list) {
			List<PageData> userlist=this.appActivityService.queryUserByAId(pageData);
			pageData.put("userlist",userlist);
			pageData.put("contlist",userlist.size());//某活动参与人总数
			pageData.put("START_DATE",pageData.getString("START_DATE")!=null&&pageData.getString("START_DATE")!=""?pageData.getString("START_DATE").substring(0,16):"");
			pageData.put("END_DATE",pageData.getString("END_DATE")!=null&&pageData.getString("END_DATE")!=""?pageData.getString("END_DATE").substring(0,16):"");
			pageData.put("DUE_DATE",pageData.getString("DUE_DATE")!=null&&pageData.getString("DUE_DATE")!=""?pageData.getString("DUE_DATE").substring(0,16):"");
		} 
		_result = AppUtil.ss(wer, "01", "成功","true", null);
		return _result; 
	}
	
	//<!--根据活动Id查询参与者信息 -->
	@RequestMapping({ "/queryUserByAId" })
	@ResponseBody
	public PageData queryUserByAId()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		List<PageData> list=new ArrayList<PageData>();
		
		list=this.appActivityService.queryUserByAId(pa);
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result; 
	}
	
	//我要参与活动
	@RequestMapping({ "/addActUser" })
	@ResponseBody
	public PageData addActUser()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		pa.put("ID",pa.getString("ACTIVITY_ID"));
	    List<PageData> pageList=appActivityService.checkIsApply(pa);//检查是否已经报名
	    List<PageData> can=appActivityService.checkCanApply(pa);//检查是否可以报名（根据等级判断）
	    if(can!=null&&can.size()>0){
	    	if(pageList!=null&&pageList.size()>0){//取消报名    
	 			Object obj=this.appActivityService.deleteApplyer(pa);
	 		    if(Integer.valueOf(obj.toString()) >= 1){//修改参与人数
	 		    	PageData data=new PageData(); 
	 				data.put("ACTIVITY_ID",pa.getString("ACTIVITY_ID"));
	 				data.put("PARTICIPANT_COUNTS",Integer.valueOf(obj.toString()));
	 				Object ob=appActivityService.updateminus(data); 
	 				if(Integer.valueOf(ob.toString()) >= 1){
	 					_result = AppUtil.ss(null, "01", "取消报名成功！","true",null);
	 				}else{
	 					  _result = AppUtil.ss(null, "90", "取消报名失败！","false",null);
	 				} 
	 		     }else{
	 			     _result = AppUtil.ss(null, "90", "取消报名失败！","false",null);
	 		     }  
	 	    }else{
	 	    	String id=this.get32UUID();
	 			pa.put("ID",id);
	 			pa.put("STATUS","01");//未审核
	 			pa.put("APPLY_DATE", new Date());
	 			Object obj=this.appActivityService.addActUser(pa);
	 		    if(Integer.valueOf(obj.toString()) >= 1){
	 		    	PageData data=new PageData(); 
	 				data.put("ACTIVITY_ID",pa.getString("ACTIVITY_ID"));
	 				data.put("PARTICIPANT_COUNTS",Integer.valueOf(obj.toString()));
	 				Object ob=appActivityService.updateCounts(data); 
	 				if(Integer.valueOf(ob.toString()) >= 1){
	 					_result = AppUtil.ss(null, "01", "报名成功！","true",null);
	 				}else{
	 					  _result = AppUtil.ss(null, "90", "报名失败！","false",null);
	 				} 
	 		     }else{
	 			     _result = AppUtil.ss(null, "90", "报名失败！","false",null);
	 		     }
	 	    } 
	    }else{
	    	_result = AppUtil.ss(null, "90", "您不能参与该活动","false",null);
	    } 
	     return _result;
	}
	
	//新增活动
	@RequestMapping({ "/addActivity" })
	@ResponseBody
	public PageData addActivity()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());  
		
		Object LIMIT_AMOUNT=pa.get("LIMIT_AMOUNT"); 
		if(LIMIT_AMOUNT==null||"".equals(LIMIT_AMOUNT)){
			pa.put("LIMIT_AMOUNT",0);
		}else{ 
			int amount=Integer.parseInt(LIMIT_AMOUNT+"");
			pa.put("LIMIT_AMOUNT",amount);
		}
		Object PRICE=pa.get("PRICE"); 
		if(PRICE==null||"".equals(PRICE)){
			pa.put("PRICE",0);
		}else{
			double amount=Double.parseDouble(PRICE+"");
			pa.put("PRICE",amount); 
		} 
		
		Object VIP_PRICE=pa.get("VIP_PRICE"); 
		if(VIP_PRICE==null||"".equals(VIP_PRICE)){
			pa.put("VIP_PRICE",0);
		}else{  
			double amount=Double.parseDouble(VIP_PRICE+"");
			pa.put("VIP_PRICE",amount);
		}
		
		Object VICE_PRE_PRICE=pa.get("VICE_PRE_PRICE"); 
		if(VICE_PRE_PRICE==null||"".equals(VICE_PRE_PRICE)){
			pa.put("VICE_PRE_PRICE",0);
		}else{  
			double amount=Double.parseDouble(VICE_PRE_PRICE+"");
			pa.put("VICE_PRE_PRICE",amount); 
		}
		
		Object EXE_VICE_PRE_PRICE=pa.get("EXE_VICE_PRE_PRICE");  
		if(EXE_VICE_PRE_PRICE==null||"".equals(EXE_VICE_PRE_PRICE)){
			pa.put("EXE_VICE_PRE_PRICE",0);
		}else{ 
			double amount=Double.parseDouble(EXE_VICE_PRE_PRICE+"");
			pa.put("EXE_VICE_PRE_PRICE",amount); 
		} 
		
		Object PRESIDENT_PRICE=pa.get("PRESIDENT_PRICE");  
		if(PRESIDENT_PRICE==null||"".equals(PRESIDENT_PRICE)){
			pa.put("PRESIDENT_PRICE",0);
		}else{ 
			double amount=Double.parseDouble(PRESIDENT_PRICE+"");
			pa.put("PRESIDENT_PRICE",amount); 
		} 
		
		String ACTIVITY_NAME=pa.getString("ACTIVITY_NAME");
		if ((ACTIVITY_NAME != null) && (!"".equals(ACTIVITY_NAME))) { 
			ACTIVITY_NAME=new String(ACTIVITY_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("ACTIVITY_NAME",ACTIVITY_NAME);
		}  
		String CITY=pa.getString("CITY");
		if ((CITY != null) && (!"".equals(CITY))) { 
			CITY=new String(CITY.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("CITY",CITY);
		}  
		String ACTIVITY_ADDR=pa.getString("ACTIVITY_ADDR");
		if ((ACTIVITY_ADDR != null) && (!"".equals(ACTIVITY_ADDR))) { 
			ACTIVITY_ADDR=new String(ACTIVITY_ADDR.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("ACTIVITY_ADDR",ACTIVITY_ADDR);
		}  
		String KEY_WORDS=pa.getString("KEY_WORDS");
		if ((KEY_WORDS != null) && (!"".equals(KEY_WORDS))) { 
			KEY_WORDS=new String(KEY_WORDS.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("KEY_WORDS",KEY_WORDS);
		} 
		String ACTIVITY_CONTENT=pa.getString("ACTIVITY_CONTENT");
		if ((ACTIVITY_CONTENT != null) && (!"".equals(ACTIVITY_CONTENT))) { 
			ACTIVITY_CONTENT=new String(ACTIVITY_CONTENT.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("ACTIVITY_CONTENT",ACTIVITY_CONTENT);
		} 
		
		String NOPASSREASON=pa.getString("NOPASSREASON");
		if ((NOPASSREASON != null) && (!"".equals(NOPASSREASON))) { 
			NOPASSREASON=new String(NOPASSREASON.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("NOPASSREASON",NOPASSREASON);
		}
		Object obj=new Object();
		if(pa.getString("ID")!=null&&!"".equals("ID")){//修改活动信息
			pa.put("STATUS","01");//未审核
			pa.put("CREATE_DATE", new Date()); 
			obj=this.appActivityService.upactivity(pa); 
		}else{//新增活动信息
			pa.put("ID", get32UUID()); 
			pa.put("CREATE_DATE", new Date()); 
			pa.put("STATUS","01");//未审核
			pa.put("COLLECT_COUNT",0);
			pa.put("PARTICIPANT_COUNTS",0);
			obj=this.appActivityService.save(pa); 
		}
		
	    if(Integer.valueOf(obj.toString()) >= 1){
	    	String ACTIVITY_ID=pa.getString("ID");
			_result = AppUtil.ss(ACTIVITY_ID, "01", "成功","true",null);
	    }else{
		     _result = AppUtil.ss(null, "92", "非法请求","false",null);
	    }
	    return _result;
	}
	
	
	//删除活动
	@RequestMapping({ "/delActivity" })
	@ResponseBody
	public PageData delActivity()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());  
		Object obj=this.appActivityService.delete(pa); 
		if(Integer.valueOf(obj.toString()) >= 1){ 
			_result = AppUtil.ss(null, "01", "成功","true",null);
	    }else{
		     _result = AppUtil.ss(null, "90", "失败","false",null);
	    }
		return _result; 
	} 
	
	//获取活动轮换大图
	@RequestMapping({ "/queryActByColno" })
	@ResponseBody
	public PageData queryActByColno()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		PageData pd=new PageData();
		pd.put("LOCATION_NO","04");
		List list=this.appRotationservice.queryByColno(pd); 
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result; 
	}
	
	//验证用户
	@RequestMapping({ "/validUser" })
	@ResponseBody
	public PageData validUser()throws Exception{
		PageData _result=new PageData();
		PageData pd=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		if(pa.getString("userid").equals(pa.getString("ID"))){
			pd.put("isSelf",1);
		}else{
			pd.put("isSelf",0);
		} 
		_result = AppUtil.ss(pd, "01", "成功","true", null);
		return _result; 
	} 
	
	
	//活动分享
	@RequestMapping({ "/queryfenxiangById" })
	public ModelAndView queryfenxiangById()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = getPageData();
		PageData data=this.appActivityService.queryById(pd);//活动详细信息
		mv.setViewName("app/fxactivity");
		mv.addObject("fenData", data); 
		mv.addObject("apppicPath", "http://jianlian.shanghai-cu.com/jianlian/uploadImg/img/"); 
		return mv;
	}
}


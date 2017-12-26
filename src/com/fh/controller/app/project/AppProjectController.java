package com.fh.controller.app.project;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.app.AppController;
import com.fh.entity.Page;
import com.fh.service.app.AppCitiesService;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.AppDicService;
import com.fh.service.app.collection.AppCollectionService;
import com.fh.service.app.company.AppCategoryService;
import com.fh.service.app.project.AppProjectBackupService;
import com.fh.service.app.project.AppProjectScheduleService;
import com.fh.service.app.project.AppProjectService;
import com.fh.service.projects.ProjectBackupService;
import com.fh.service.projects.ProjectScheduleService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/appProject" })
public class AppProjectController extends AppController{

	@Resource(name = "appProjectService")
	private AppProjectService appProjectService;
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	
	@Resource(name="appDicService")
	private AppDicService appDicService; 
	
	@Resource(name="appCitiesService")
	private AppCitiesService appCitiesService;
	
	@Resource(name = "appProjectBackupService")
	private AppProjectBackupService appBackupService;
	
	@Resource(name = "appProjectScheduleService")
	private AppProjectScheduleService appScheService;
	
	@Resource(name = "appCategoryService")
	private AppCategoryService appCateService;  
	
	@Resource(name = "appCollectionService")
	private AppCollectionService appColService;
	 
	//获取项目等级、项目阶段、项目竞标要求、项目类型 
	@RequestMapping({ "/listAllPk" })
	@ResponseBody
	public PageData listAllPk()throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		Object pid=pa.get("ID");
		List<PageData> typeList=this.appDicService.queryByPBM("projecttype");
		List<PageData> levelList=this.appDicService.queryByPBM("projectlevel");
		List<PageData> requirelList=this.appDicService.queryByPBM("viptype"); 
		List<PageData> cailiao=this.appCateService.listByPId("0");//查询一级材料 
		Map<String,Object> map=new HashMap<String,Object>(); 
		if(pid!=null){
			PageData project=appProjectService.queryByProId(pa); 
			map.put("project", project); 
			PageData data=new PageData();
			data.put("PROJECT_ID",pid.toString());
			List<PageData> cates=appProjectService.queryCateByPid(data);
			for(int i=0;i<cailiao.size();i++){
				if(cates!=null&&cates.size()>0){
					for(int j=0;j<cates.size();j++){
						PageData category=cailiao.get(i); 
						if(category.getString("value").equals(cates.get(j).getString("CATEGORY_ID"))){
							category.put("flag",true);//选中
							break;
						}else{
							category.put("flag",false);//选中 
						} 
					}
				}else{
					PageData category=cailiao.get(i);
					category.put("flag",false);//未选中
				} 
			} 
		}
		map.put("cailList", cailiao);
		map.put("typeList", typeList);
		map.put("levelList", levelList);
		map.put("requirelList", requirelList); 
		_result = AppUtil.ss(map, "01", "成功","true", null);
		return _result;
	}
	 
	//未中标项目列表,小于五的所有信息
	@RequestMapping({ "/querynostatuslist" })
	@ResponseBody
	public PageData querynostatuslist() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		
//		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
//		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
//		int totalSize=currentPage*pageSize; 
//		pa.put("totalSize",totalSize);  
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int start=(currentPage-1)*pageSize;
		pa.put("start",start); 
		pa.put("pageSize",pageSize); 
		
		String PROJECT_NAME=pa.getString("PROJECT_NAME");
		if ((PROJECT_NAME != null) && (!"".equals(PROJECT_NAME))) { 
			PROJECT_NAME=new String(PROJECT_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("PROJECT_NAME",PROJECT_NAME);
		}  
		List<PageData> list=new ArrayList<PageData>();
		list=this.appProjectService.queryNoBidderByParam(pa);
		PageData wzbzong= this.appProjectService.queryNoBidderByParamzong(pa);
		PageData hjk=new PageData();
		hjk.put("list", list);
		hjk.put("listzong", list.size());
		hjk.put("wzbzong", wzbzong);
		_result = AppUtil.ss(hjk, "01", "成功","true", null);
		return _result;
	}
	
	/*//根据项目名称搜索未中标列表
	@RequestMapping({ "/queryNoBidderByName"})
	@ResponseBody
	public PageData queryNoBidderByName() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int totalSize=currentPage*pageSize; 
		pa.put("totalSize",totalSize); 
	
		List<PageData> list=new ArrayList<PageData>();
		String PROJECT_NAME=pa.getString("PROJECT_NAME");
		if ((PROJECT_NAME != null) && (!"".equals(PROJECT_NAME))) { 
			PROJECT_NAME=new String(PROJECT_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("PROJECT_NAME",PROJECT_NAME);
		}  
		
		list=this.appProjectService.queryNoBidderByParam(pa);
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result;
	}*/
	
	@RequestMapping({ "/deleteProject" })
	@ResponseBody
	public PageData deleteProject() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());  
		Object ob=this.appProjectService.delete(pa);
	    if(Integer.valueOf(ob.toString()) >= 1){
		    _result = AppUtil.ss(null, "01", "删除项目成功","true",null);
	    }else{
		    _result = AppUtil.ss(null, "90", "删除项目失败","false",null);
	    } 
		return _result;
	} 

	//我要参与接单
	@RequestMapping({ "/addbidder" })
	@ResponseBody
	public PageData addbidder()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData page=new PageData();
		page.put("ID", pa.getString("PROJECT_ID"));
		page.put("userid", pa.getString("userid")); 
		PageData isApply=this.appProjectService.queryBiddedById(page);//查询该会员是否已经参与了接单
		if(isApply!=null){//已经参与，取消参与
			Object obj=this.appProjectService.deletebidder(pa); 
			if(Integer.valueOf(obj.toString()) >= 1){
			    PageData data=new PageData();
			    data.put("PROJECT_ID",pa.getString("PROJECT_ID")); 
			    data.put("PART_COUNT",Integer.valueOf(obj.toString())); 
			    Object ob=appProjectService.updateminus(data);
			    if(Integer.valueOf(ob.toString()) >= 1){
				    _result = AppUtil.ss(null, "01", "取消参与成功","true",null);
			    }else{
				    _result = AppUtil.ss(null, "90", "取消参与失败","false",null);
			    }  
		    }else{
			     _result = AppUtil.ss(null, "90", "取消参与失败","false",null);
		    }
		}else{
			pa.put("ID", get32UUID());
			pa.put("BID_DATE", new Date());
			Object obj=this.appProjectService.addbidder(pa); 
			if(Integer.valueOf(obj.toString()) >= 1){
			  PageData data=new PageData();
			  data.put("PROJECT_ID",pa.getString("PROJECT_ID")); 
			  data.put("PART_COUNT",Integer.valueOf(obj.toString())); 
			  Object ob=appProjectService.updateCounts(data);
			  if(Integer.valueOf(ob.toString()) >= 1){
				  _result = AppUtil.ss(null, "01", "参与成功","true",null);
			  }else{
				  _result = AppUtil.ss(null, "90", "参与失败","false",null);
			  }  
		    }else{
			     _result = AppUtil.ss(null, "90", "参与失败","false",null);
		    }
		} 
		  return _result;
	}
	
	//项目中标确认
	@RequestMapping({ "/upsummaryok" })
	@ResponseBody
	public PageData upsummaryok()throws Exception{
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData());
		PageData data = new PageData();
		data.put("ID", pa.getString("ID"));
		Object obj=this.appProjectService.upsummaryok(data);
		if(Integer.valueOf(obj.toString()) >= 1){
			_result = AppUtil.ss(null, "01", "成功","true",null);
	    }else{
		     _result = AppUtil.ss(null, "92", "非法请求","false",null);
	    } 
		return _result;
	}
	
	//获取项目列表
	@RequestMapping({ "/queryprojectlist" })
	@ResponseBody
	public PageData queryprojectlist() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int start=(currentPage-1)*pageSize;
		//pa.put("start",currentPage-1);
		pa.put("start",start); 
		pa.put("pageSize",pageSize); 
		
		String ADDR=pa.getString("ADDR");
		if ((ADDR != null) && (!"".equals(ADDR))) { 
			ADDR=new String(ADDR.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("ADDR",ADDR);
		}
		String PROJECT_NAME=pa.getString("PROJECT_NAME");
		if ((PROJECT_NAME != null) && (!"".equals(PROJECT_NAME))) { 
			PROJECT_NAME=new String(PROJECT_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("PROJECT_NAME",PROJECT_NAME);
		} 
		
		Page page=new Page(); 
		page.setPd(pa); 
		List<PageData> projectList=this.appProjectService.queryPageByParam(page); 
		
		PageData liezong=this.appProjectService.queryPageByParamprozong(page);
	/*	projectList=this.appProjectService.queryprojectlist(pa);*/
		List<PageData> typeList=this.appDicService.queryByPBM("projecttype"); 
		List<PageData> stageList=this.appDicService.queryByPBM("stage");  
		List<PageData> citiyList=this.appCitiesService.queryCitiesByParam(null);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("typeList", typeList);
		map.put("stageList", stageList);
		map.put("projectList", projectList);
		map.put("citiyList",citiyList);
		map.put("liezong",liezong);
		map.put("prozong",projectList.size());
		_result = AppUtil.ss(map, "01", "成功","true", null);
		return _result;
	}
	 
	
	//根据用户ID查询项目反馈信息
	@RequestMapping({ "/queryBackupByUId" })
	@ResponseBody
	public PageData queryBackupByUId() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		
//		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
//		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
//		int totalSize=currentPage*pageSize; 
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int start=(currentPage-1)*pageSize;
		pa.put("start",start); 
		pa.put("pageSize",pageSize); 
		
		int xmfkzong;
		PageData fankuserzon=new PageData();
//		pa.put("totalSize",totalSize);  
		List<PageData> list=new ArrayList<PageData>();
		String flag=pa.get("flag")+"";
		if("0".equals(flag)){//查询某用户发布的项目反馈信息
			list=this.appProjectService.queryBackupByUId(pa);
			xmfkzong=list.size();
			fankuserzon=this.appProjectService.queryBackupByUIdCOUNT(pa);
		}else{//查询某用户接单的项目反馈信息
			list=this.appProjectService.queryBidByUId(pa);
			xmfkzong=list.size();
			fankuserzon=this.appProjectService.queryBidByUIdjezong(pa);
		} 
		
		PageData jedan=new PageData();
		jedan.put("list", list);
		jedan.put("xmfkzong", xmfkzong);
		jedan.put("fankuserzon", fankuserzon);
		_result = AppUtil.ss(jedan, "01", "成功","true", null);
		return _result;
	}
	
	
	//根据项目Id查询项目详细信息
	@RequestMapping({ "/queryByParamByID" })
	@ResponseBody
	public PageData queryByParamByID() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		List<PageData> list=new ArrayList<PageData>();
		list=this.appProjectService.queryById(pa);
		PageData page=this.appProjectService.queryBiddedById(pa);
		List<PageData> colList=appColService.queryByUidAndOid(pa);
		if(page!=null){
			list.get(0).put("isBidder",1);//已经接单
		}else{
			list.get(0).put("isBidder",0);//未接单
		}
		if(colList!=null&&colList.size()>0){
			list.get(0).put("isCollect",1);//已经收藏
		}else{
			list.get(0).put("isCollect",0);//未收藏
		} 
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result;
	}
	
	//下单
	@RequestMapping({ "/saveProject" })
	@ResponseBody
	public PageData saveProject() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		
		PageData caill=new PageData();//保存材料
		Object START_PRICE=pa.get("START_PRICE");
		Object FLOOR_AREA=pa.get("FLOOR_AREA");  
		Object BUILD_LAYERS=pa.get("BUILD_LAYERS");
		Object BUILD_AREA=pa.get("BUILD_AREA");
    	//处理乱码
    	String PROJECT_NAME=pa.getString("PROJECT_NAME");
    	String ADDR=pa.getString("ADDR");
    	String PROJECT_CONTENT=pa.getString("PROJECT_CONTENT");
    	String AVAILABLE_MATERIALS=pa.getString("AVAILABLE_MATERIALS");
    	String DESCRIPTION=pa.getString("DESCRIPTION");
    	String RESOURCE=pa.getString("RESOURCE");   
    	String CITY=pa.getString("CITY");  
    	
	   if(START_PRICE!=null&&!"".equals(START_PRICE)){
		   pa.put("START_PRICE",Double.parseDouble(START_PRICE+""));
       }else{
    	   pa.put("START_PRICE",0);
       }
       if(FLOOR_AREA!=null&&!"".equals(FLOOR_AREA)){
    	   pa.put("FLOOR_AREA",Integer.parseInt(FLOOR_AREA+""));
       }else{
    	   pa.put("FLOOR_AREA",0);
       }
       if(BUILD_LAYERS!=null&&!"".equals(BUILD_LAYERS)){
    	   pa.put("BUILD_LAYERS",Integer.parseInt(BUILD_LAYERS+""));
       }else{
    	   pa.put("BUILD_LAYERS",0);
       }
       if(BUILD_AREA!=null&&!"".equals(BUILD_AREA)){
    	   pa.put("BUILD_AREA",Integer.parseInt(BUILD_AREA+""));
       }else{
    	   pa.put("BUILD_AREA",0);
       }
       if ((PROJECT_NAME != null) && (!"".equals(PROJECT_NAME))) { 
			PROJECT_NAME=new String(PROJECT_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("PROJECT_NAME",PROJECT_NAME);
		} 
       if ((ADDR != null) && (!"".equals(ADDR))) { 
			ADDR=new String(ADDR.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("ADDR",ADDR);
		}
       if ((PROJECT_CONTENT != null) && (!"".equals(PROJECT_CONTENT))) { 
    	   PROJECT_CONTENT=new String(PROJECT_CONTENT.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("PROJECT_CONTENT",PROJECT_CONTENT);
		} 
       if ((AVAILABLE_MATERIALS != null) && (!"".equals(AVAILABLE_MATERIALS))) { 
    	   AVAILABLE_MATERIALS=new String(AVAILABLE_MATERIALS.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("AVAILABLE_MATERIALS",AVAILABLE_MATERIALS);
		} 
       if ((DESCRIPTION != null) && (!"".equals(DESCRIPTION))) { 
    	   DESCRIPTION=new String(DESCRIPTION.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("DESCRIPTION",DESCRIPTION);
		} 
       if ((RESOURCE != null) && (!"".equals(RESOURCE))) { 
    	   RESOURCE=new String(RESOURCE.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("RESOURCE",RESOURCE);
		}
       if ((CITY != null) && (!"".equals(CITY))) { 
    	   CITY=new String(CITY.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("CITY",CITY);
		}
       Object obj=null; 
       pa.put("STATUS",1);//待审核
       if(pa.getString("ID")!=null&&!"".equals("ID")){
    	   String NO_PASS_REASON=pa.getString("NO_PASS_REASON");
    	   if ((NO_PASS_REASON != null) && (!"".equals(NO_PASS_REASON))) { 
    		   NO_PASS_REASON=new String(NO_PASS_REASON.getBytes("ISO-8859-1"),"UTF-8").trim();
    			pa.put("NO_PASS_REASON",NO_PASS_REASON);
    	   }
    	   pa.put("PROJECT_ID",pa.getString("ID"));
    	   pa.put("MODIFY_BY",pa.getString("userid"));
    	   pa.put("MODIFY_DATE",new Date());
    	   obj=this.appProjectService.edit(pa);
    	   Object object=this.appProjectService.deletePROJECT_CATEGORY(pa);
    	   PageData data1=new PageData(); 
		   data1.put("STATUS",1);  
  		   data1.put("PROJECT_ID", pa.getString("PROJECT_ID")); 
  	   	   data1.put("USER_ID",pa.getString("userid"));
  	   	   data1.put("DATE", new Date()); 
  	   	   data1.put("OPER_BY","");
  	   	   data1.put("DESCRIPTION","项目审核中！");  
		   data1.put("TARGET_ID",""); 
		   //保存项目反馈信息  
		   Object pbject= this.appBackupService.edit(data1); 
    	   if(Integer.valueOf(obj.toString()) >= 1&&Integer.valueOf(pbject.toString()) >= 1){
	   			_result = AppUtil.ss(pa.getString("PROJECT_ID"), "01", "修改项目成功","true",null);
	   	   }else{
	   		    _result = AppUtil.ss(pa.getString("PROJECT_ID"), "90", "修改项目失败","false",null);
	   	   } 
       }else{ 
    	   pa.put("COLLECTION_COUNTS",0);
    	   pa.put("PROJECT_ID",get32UUID());
    	   pa.put("MODIFY_BY",pa.getString("userid"));
    	   pa.put("MODIFY_DATE",new Date());
    	   pa.put("RELEASE_DATE",new Date());
    	   pa.put("PROJECT_STAGE_ID","13");//默认属于工程筹备阶段
    	   obj=this.appProjectService.save(pa);
    	   
    	   if(Integer.valueOf(obj.toString()) >= 1){
    		   //向项目阶段表中添加信息
        	   PageData sc=getPageData(); 
        	   PageData data=getPageData(); 
        	   sc.put("ID",get32UUID()); 
      		   sc.put("DATE",new Date());
      		   sc.put("OPER_BY",pa.getString("userid"));
      		   sc.put("PROJECT_ID",pa.getString("PROJECT_ID"));
      		   sc.put("SCHEDULE",pa.getString("PROJECT_STAGE_ID"));  
      		   Object pbje= this.appScheService.save(sc);  
      		   PageData data1=new PageData(); 
    		   data1.put("STATUS",1); 
    		   data1.put("ID", get32UUID());
	  		   data1.put("PROJECT_ID", pa.getString("PROJECT_ID")); 
	  	   	   data1.put("USER_ID",pa.getString("userid"));
	  	   	   data1.put("DATE", new Date()); 
	  	   	   data1.put("OPER_BY","");
	  	   	   data1.put("DESCRIPTION","项目审核中！");  
    		   data1.put("TARGET_ID",""); 
    		   //保存项目反馈信息  
    		   Object pbject= this.appBackupService.save(data1); 
      		   if(Integer.valueOf(pbje.toString()) >= 1&&Integer.valueOf(pbject.toString()) >= 1){
	     			_result = AppUtil.ss(pa.getString("PROJECT_ID"), "01", "新增项目成功","true",null);
	     	    }else{
	     		     _result = AppUtil.ss(pa.getString("PROJECT_ID"), "90", "新增项目失败","false",null);
	     	    } 
	   	    }else{
	   		    _result = AppUtil.ss(pa.getString("PROJECT_ID"), "90", "失败","false",null);
	   	    }  
       } 
       
		   	caill.put("PROJECT_ID", pa.getString("PROJECT_ID"));
			caill.put("DATE",new Date());
			String jk= pa.getString("AVAILABLE");
			String[] strs=jk.split(","); 
			for(int i=0;i<strs.length;i++){
				if(strs[i]!=null&&!"".equals(strs[i])){
					caill.put("ID", get32UUID());
					caill.put("CATEGORY_ID", strs[i]);
					this.appProjectService.saveproject_category(caill);
				} 
			}  
		return _result; 
	}
	
	//编辑
	@RequestMapping({ "/editProject" })
	@ResponseBody
	public PageData editProject() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		String START_PRICE=pa.getString("START_PRICE");
    	String FLOOR_AREA=pa.getString("FLOOR_AREA");  
    	String BUILD_LAYERS=pa.getString("BUILD_LAYERS");
    	String BUILD_AREA=pa.getString("BUILD_AREA");
    	//处理乱码
    	String PROJECT_NAME=pa.getString("PROJECT_NAME");
    	String ADDR=pa.getString("ADDR");
    	String PROJECT_CONTENT=pa.getString("PROJECT_CONTENT");
    	String AVAILABLE_MATERIALS=pa.getString("AVAILABLE_MATERIALS");
    	String DESCRIPTION=pa.getString("DESCRIPTION");
    	String RESOURCE=pa.getString("RESOURCE");  
        
	   if(START_PRICE!=null&&!"".equals(START_PRICE)){
		   pa.put("START_PRICE",START_PRICE);
       }else{
    	   pa.put("START_PRICE",0);
       }
       if(FLOOR_AREA!=null&&!"".equals(FLOOR_AREA)){
    	   pa.put("FLOOR_AREA",FLOOR_AREA);
       }else{
    	   pa.put("FLOOR_AREA",0);
       }
       if(BUILD_LAYERS!=null&&!"".equals(BUILD_LAYERS)){
    	   pa.put("BUILD_LAYERS",BUILD_LAYERS);
       }else{
    	   pa.put("BUILD_LAYERS",0);
       }
       if(BUILD_AREA!=null&&!"".equals(BUILD_AREA)){
    	   pa.put("BUILD_AREA",BUILD_AREA);
       }else{
    	   pa.put("BUILD_AREA",0);
       }
       if ((PROJECT_NAME != null) && (!"".equals(PROJECT_NAME))) { 
			PROJECT_NAME=new String(PROJECT_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("PROJECT_NAME",PROJECT_NAME);
		} 
       if ((ADDR != null) && (!"".equals(ADDR))) { 
			ADDR=new String(ADDR.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("ADDR",ADDR);
		}
       if ((PROJECT_CONTENT != null) && (!"".equals(PROJECT_CONTENT))) { 
    	   PROJECT_CONTENT=new String(PROJECT_CONTENT.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("PROJECT_CONTENT",PROJECT_CONTENT);
		} 
       if ((AVAILABLE_MATERIALS != null) && (!"".equals(AVAILABLE_MATERIALS))) { 
    	   AVAILABLE_MATERIALS=new String(AVAILABLE_MATERIALS.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("AVAILABLE_MATERIALS",AVAILABLE_MATERIALS);
		} 
       if ((DESCRIPTION != null) && (!"".equals(DESCRIPTION))) { 
    	   DESCRIPTION=new String(DESCRIPTION.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("DESCRIPTION",DESCRIPTION);
		} 
       if ((RESOURCE != null) && (!"".equals(RESOURCE))) { 
    	   RESOURCE=new String(RESOURCE.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("RESOURCE",RESOURCE);
		} 
		pa.put("STATUS",1);//待审核
		pa.put("COLLECTION_COUNTS",0);
		pa.put("MODIFY_BY",pa.getString("userid"));
		pa.put("MODIFY_DATE",new Date());
		Object obj=this.appProjectService.edit(pa);
		if(Integer.valueOf(obj.toString()) >= 1){
			_result = AppUtil.ss(null, "01", "成功","true",null);
	    }else{
		     _result = AppUtil.ss(null, "92", "非法请求","false",null);
	    } 
		return _result; 
	} 
}


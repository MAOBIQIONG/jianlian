package com.fh.controller.app.collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.app.AppController;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.activity.AppActivityService;
import com.fh.service.app.collection.AppCollectionService;
import com.fh.service.app.media.AppMediaService;
import com.fh.service.app.project.AppProjectService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/appConllection" })
public class AppCollectionController extends AppController{

	@Resource(name = "appCollectionService")
	private AppCollectionService appCollectionService;
	
	@Resource(name = "appmediaService")
	private AppMediaService appmediaService;
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;  
	
	@Resource(name = "appProjectService")
	private AppProjectService appProjectService;
	
	@Resource(name = "appActivityService")
	private AppActivityService appActivityService; 
	
	//收藏获取接口
	@RequestMapping({ "/findCollection" })
	@ResponseBody
	public PageData findConllection() throws Exception {
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
		
		List<PageData> pd=this.appCollectionService.findConllection(pa);
		int shouc=pd.size();
		PageData sczoshu=this.appCollectionService.findCollectionsczong(pa);
		
		PageData sdf=new PageData();
		sdf.put("pd", pd);
		sdf.put("shouc", shouc);
		sdf.put("sczoshu", sczoshu);
		_result = AppUtil.ss(sdf, "01", "成功","true", null);
		return _result;
	}
	
	//收藏添加接口
	@RequestMapping({ "/saveCollection" })
	@ResponseBody
	public PageData saveCollection()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		pa.put("USER_ID", pa.getString("userid"));
		PageData page =this.appCollectionService.checkCollection(pa); 
		if(page==null){
			String id=this.get32UUID();
			pa.put("ID", id);
			pa.put("COLLECT_DATE", new Date());
			Object obj=appCollectionService.saveCollection(pa);
			if(Integer.valueOf(obj.toString()) >= 1){
				PageData data=new PageData();
				String tablename=pa.getString("TYPE");
				Object ob=0;
				if("jl_app_media".equals(tablename)||"JL_APP_MEDIA".equals(tablename)){
					data.put("COLLECT_COUNT",Integer.valueOf(obj.toString()));
					data.put("MEDIA_ID",pa.getString("OBJECT_ID")); 
					ob=appmediaService.updateCounts(data);
				}
				if("project".equals(tablename)||"PROJECT".equals(tablename)){
					data.put("COLLECTION_COUNTS",Integer.valueOf(obj.toString()));
					data.put("PROJECT_ID",pa.getString("OBJECT_ID")); 
					ob=appProjectService.updateCounts(data);
				}
				if("activity".equals(tablename)||"ACTIVITY".equals(tablename)){
					data.put("COLLECT_COUNT",Integer.valueOf(obj.toString()));
					data.put("ACTIVITY_ID",pa.getString("OBJECT_ID")); 
					ob=appActivityService.updateCounts(data);
				} 
				if(Integer.valueOf(ob.toString()) >= 1){
					_result = AppUtil.ss(null, "01", "收藏成功","true",null);
				}else{
					_result = AppUtil.ss(null, "90", "收藏失败","false",null);
				}
			}else{
				 _result = AppUtil.ss(null, "90", "失败","false",null);
			} 
		}else{//已经关注了，要解除关注 
 		    Object obj =this.appCollectionService.delCollection(page);
 		    if(Integer.valueOf(obj.toString()) >= 1){
 				PageData data=new PageData();
 				String tablename=pa.getString("TYPE");
 				Object ob=0;
 				if("jl_app_media".equals(tablename)||"JL_APP_MEDIA".equals(tablename)){  
 					data.put("MEDIA_ID",pa.getString("OBJECT_ID"));
 					PageData pd=appmediaService.queryById(data);
 					int count=Integer.parseInt(pd.get("COLLECT_COUNT")+"");
 					if(count>1){
 						count=count-1;
 					}else{
 						count=0;
 					}
 					data.put("COLLECT_COUNT",count);
 					data.put("MEDIA_ID",pa.getString("OBJECT_ID"));
 					ob=appmediaService.updateminus(data);
 				}
 				if("project".equals(tablename)||"PROJECT".equals(tablename)){  
 					data.put("ID",pa.getString("OBJECT_ID"));
 					PageData pd=appProjectService.queryByProId(data); 
 					int count=Integer.parseInt(pd.get("COLLECTION_COUNTS")+"");
 					if(count>1){
 						count=count-1;
 					}else{
 						count=0;
 					}
 					data.put("COLLECTION_COUNTS",count);
 					data.put("PROJECT_ID",pa.getString("OBJECT_ID")); 
 					ob=appProjectService.updateminus(data);
 				}
 				if("activity".equals(tablename)||"ACTIVITY".equals(tablename)){
 					data.put("ID",pa.getString("OBJECT_ID"));
 					PageData pd=appActivityService.queryByParam(data).get(0);
 					int count=Integer.parseInt(pd.get("COLLECT_COUNT")+"");
 					if(count>1){
 						count=count-1;
 					}else{
 						count=0;
 					}
 					data.put("COLLECT_COUNT",count); 
 					data.put("ACTIVITY_ID",pa.getString("OBJECT_ID")); 
 					ob=appActivityService.updateminus(data);
 				} 
 				if(Integer.valueOf(ob.toString()) >= 1){
 					_result = AppUtil.ss(null, "01", "取消收藏成功","true",null);
 				}else{
 					_result = AppUtil.ss(null, "90", "取消收藏失败","false",null);
 				}
 			}else{
 				 _result = AppUtil.ss(null, "90", "失败","false",null);
 			}
		} 
		return _result; 
	}
	
	//收藏删除接口
	@RequestMapping({ "/delCollection" })
	@ResponseBody
	public PageData delCollection() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		Object obj=this.appCollectionService.delCollection(pa);
		if (Integer.valueOf(obj.toString()) >= 1) {
			_result = AppUtil.ss(null, "01", "成功","true", null);
		} else {
			_result = AppUtil.ss(null, "02", "失败", "false", null);
		}
		return _result;
	}
	
}

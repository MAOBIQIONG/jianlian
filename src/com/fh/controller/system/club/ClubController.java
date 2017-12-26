package com.fh.controller.system.club;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.activity.ActivityService;
import com.fh.service.system.clan.ClanService;
import com.fh.service.system.club.ClubService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.SystemLog;

@Controller
@RequestMapping({ "/club" })
public class ClubController extends BaseController {
	String menuUrl = "club/listclub.do";

	@Resource(name = "clubService")
	private ClubService clubService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService;
	
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd=null;
		this.clubService.updatedelhd(pd);
		mv.setViewName("system/clubs/club-reservation");
		return mv;
	}

	//查询所有公司信息
		@RequestMapping({ "/queryClub" })
		@ResponseBody
		public String queryClub(Page page)throws Exception{
			PageData pd = new PageData();
			pd = getPageData();

			JSONObject getObj = new JSONObject();
			String sEcho = "0";// 记录操作的次数  每次加1  
			String iDisplayStart = "0";// 起始  
			String iDisplayLength = "10";// size  
			String mDataProp = "mDataProp_";
			String sortName = "";//排序字段
			String iSortCol_0 = "";//排序索引
			String sSortDir_0 = "";//排序方式
			//获取jquery datatable当前配置参数  
			JSONArray jsonArray = JSONArray.fromObject(pd.getString("aoData"));  
			for (int i = 0; i < jsonArray.size(); i++)  
			{  
			    try  
			    {  
			        JSONObject jsonObject = (JSONObject)jsonArray.get(i);  
			        if (jsonObject.get("name").equals("sEcho")){ 
			            sEcho = jsonObject.get("value").toString();  
			        }
			        else if (jsonObject.get("name").equals("iDisplayStart")){
			            iDisplayStart = jsonObject.get("value").toString(); 
			        }
			        else if (jsonObject.get("name").equals("iDisplayLength")){ 
			            iDisplayLength = jsonObject.get("value").toString(); 
			        }
			        else if (jsonObject.get("name").equals("sSortDir_0")){
			        	sSortDir_0 = jsonObject.get("value").toString(); 
			        }
			        else if (jsonObject.get("name").equals("iSortCol_0")){
			        	iSortCol_0 = jsonObject.get("value").toString();
			        	mDataProp = mDataProp+""+iSortCol_0;
			        	for (int j = 0; j < jsonArray.size(); j++)  {
			        		JSONObject jsonObject1 = (JSONObject)jsonArray.get(j);  
			        		if(jsonObject1.get("name").equals(mDataProp)){
			        			sortName = jsonObject1.get("value").toString(); 
			        		}
			        	}
			        }
			        
			    }  
			    catch (Exception e) { 
			    	e.printStackTrace();
			        break;  
			    }  
			}  
			page.setPd(pd);
			page.setOrderDirection(sSortDir_0);
			page.setOrderField(sortName);
			page.setPageSize(Integer.valueOf(iDisplayLength));
			page.setPageCurrent(Integer.valueOf(iDisplayStart));
			int initEcho = Integer.parseInt(sEcho) + 1;  
			
			List<PageData> data = this.clubService.listByParam(page);
			Object  total = this.clubService.findCount(page).get("counts");//查询记录的总行数
			getObj.put("sEcho", initEcho);// 不知道这个值有什么用
			getObj.put("iTotalRecords", total);//实际的行数
			getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
			for(int i=0;i<data.size();i++){ 
			   Object enddate=data.get(i).get("END_DATE");
			   java.sql.Date cardate=(java.sql.Date)((PageData)data.get(i)).get("CREATE_DATE"); 
			   java.sql.Date moddate=(java.sql.Date)((PageData)data.get(i)).get("MODIFY_DATE"); 
			   if(enddate==null||"".equals(enddate)){
				   ((PageData)data.get(i)).put("END_DATE","");
			   }else{
				   String END_DATE =enddate+"";
				   ((PageData)data.get(i)).put("END_DATE",END_DATE);
			   } 
			   if(cardate==null||"".equals(cardate)){
				   ((PageData)data.get(i)).put("CREATE_DATE","");
			   }else{
				   String CREATE_DATE =DateUtil.getTime(new Date(cardate.getTime()));
				   ((PageData)data.get(i)).put("CREATE_DATE",CREATE_DATE);
			   } 
			   if(moddate==null||"".equals(moddate)){
				   ((PageData)data.get(i)).put("MODIFY_DATE","");
			   }else{
				   String MODIFY_DATE =DateUtil.getTime(new Date(moddate.getTime()));
				   ((PageData)data.get(i)).put("MODIFY_DATE",MODIFY_DATE);
			   } 
			   
			}
			getObj.put("aaData", data);//要以JSON格式返回
			return getObj.toString(); 
		}
		
		@RequestMapping({ "/addClub" })
		@SystemLog(methods="俱乐部管理",type="编辑")
		@ResponseBody
		public String addClub()throws Exception{
			PageData pd = new PageData();
			try {
				pd = getPageData();
				  if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){ 
					  	pd.put("ID", get32UUID());
					  	pd.put("CREATE_DATE",new Date());
					  	pd.put("CREATE_BY", getUser().getID());
			    		//添加
			    		this.clubService.addClub(pd);
		    	  }else{ 
		    		//修改
		    		  pd.put("MODIFY_DATE",new Date());
					  	pd.put("MODIFY_BY", getUser().getID());
		    	 	 pd.put("ID",pd.getString("ID"));
		    	 	 //修改
		    	 	this.clubService.upClub(pd);
		    	  } 
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
				JSONObject getObj = new JSONObject();
				getObj.put("statusCode", 200);
				return getObj.toString();
		}
		
		//通过ID获取数据
		@RequestMapping({ "/queryById" })
		public ModelAndView queryById()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			PageData Cert=this.clubService.queryById(pd);
			List typeList=this.dicService.queryByPBM("activitytype");  
			mv.addObject("typeList", typeList); 
			mv.setViewName("system/clubs/add-club");
			mv.addObject("clubData", Cert); 
			return mv;
		}
		
		//批量删除
		@RequestMapping({ "/delClub" })
		@SystemLog(methods="俱乐部管理",type="批量删除")
		@ResponseBody
		public String delClub() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				String dasid = pd.getString("ID");
				String[] auserId= dasid.split(",");
				this.clubService.delClub(auserId);
				obj.put("statusCode", "200");
				obj.put("message", "操作成功");
				obj.put("closeCurrent", false);
			    	
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
			JSONObject getObj = new JSONObject();
			getObj.put("status", "1");
			return getObj.toString();
		}
		
		//通过ID获取数据
		@RequestMapping({ "/queryshById" })
		public ModelAndView queryshById()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			PageData Cert=this.clubService.queryById(pd);
			List typeList=this.dicService.queryByPBM("activitytype");  
			mv.addObject("typeList", typeList); 
			mv.setViewName("system/clubs/club-reviewed");
			mv.addObject("clubData", Cert); 
			return mv;
		}
		
		@RequestMapping({ "/delClubbyid" })
		@SystemLog(methods="俱乐部管理",type="删除")
		@ResponseBody
		public JSONObject delClubbyid() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				this.clubService.delClubbyid(pd);
				obj.put("statusCode", "200");
				obj.put("message", "操作成功");
				obj.put("closeCurrent", false);
			    	
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
			return obj;
		}
		
		//根据活动名称进行查找
		@RequestMapping({ "/querybyname" })
		public String querybyname()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			List dataList=this.clubService.querybyname(pd);
			mv.setViewName("system/clubs/club-reservation");
			mv.addObject("clubmap", dataList);
			return "redirect:/club/queryClub";
		}
		
		//审核/不审核
//		@RequestMapping({ "/upstatus" })
//		@ResponseBody
//		public String upstatus() {
//			PageData page = new PageData();
//			try {
//				PageData pd = getPageData();
//				PageData pagedata=(PageData) this.activityService.queryById(pd);
//				if("1".equals(pagedata.get("STATUS").toString())){ 
//					page.put("STATUS", 2);//禁止
//				}  
//				else if("2".equals(pagedata.get("STATUS").toString())){
//					page.put("STATUS", 1); //启用
//				}
//				page.put("ID", pagedata.getString("ID")); 
//				this.activityService.upstatus(pd);
//			} catch (Exception e) {
//				this.logger.error(e.toString(), e);
//			}
//			JSONObject getObj = new JSONObject();
//			getObj.put("statusCode",200);
//			System.out.println(getObj.get("statusCode"));
//			return getObj.toString();
//		}
}


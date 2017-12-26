package com.fh.controller.system.company;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.company.CertificatesService;
import com.fh.service.system.company.PointsService;
import com.fh.service.system.company.TbcompanyService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.user.TbUserService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.SystemLog;

@Controller
@RequestMapping({ "/Points" })
public class PointsController extends BaseController {
	String menuUrl = "Points/listPoints.do";

	@Resource(name = "pointsservice")
	private PointsService pointsservice;
	
	@Resource(name = "tbuserService")
	private TbUserService tbuserService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService;  
	   
		@RequestMapping({ "/querypoint" })
		public ModelAndView querypoint()throws Exception{
			ModelAndView mv = getModelAndView();
			mv.setViewName("system/company/points");
			return mv;
		}
		
		@RequestMapping(value={"/queryPageList"}) 
		@ResponseBody
		public String queryPageList(Page page) throws Exception {
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
			 
			String REAL_NAME = pd.getString("REAL_NAME"); 
			if ((REAL_NAME != null) && (!"".equals(REAL_NAME))) { 
				//String param=new String(REAL_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
				pd.put("REAL_NAME",REAL_NAME);
			}  
			page.setPd(pd);
			page.setOrderDirection(sSortDir_0);
			page.setOrderField(sortName);
			page.setPageSize(Integer.valueOf(iDisplayLength));
			page.setPageCurrent(Integer.valueOf(iDisplayStart));
			int initEcho = Integer.parseInt(sEcho) + 1;  
			 
			List dataList = this.pointsservice.listByParam(page);
			Object	total = this.pointsservice.findCount(page).get("counts");//查询记录的总行数
			 
			getObj.put("sEcho", initEcho);// 不知道这个值有什么用
			getObj.put("iTotalRecords", total);//实际的行数
			getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
			
			for(int i=0;i<dataList.size();i++){
			   java.sql.Date DATE=(java.sql.Date)((PageData)dataList.get(i)).get("DATE");
			   if(DATE==null||"".equals(DATE)){
				   ((PageData)dataList.get(i)).put("DATE","");
			   }else{
				   String DUE_DATE =DateUtil.getTime(new Date(DATE.getTime()));
				   ((PageData)dataList.get(i)).put("DATE",DUE_DATE);
			   }
			} 
			getObj.put("aaData", dataList);//要以JSON格式返回
			return getObj.toString();
		} 
		
		@RequestMapping({ "/addpoint" })
		@SystemLog(methods="积分管理",type="编辑")
		@ResponseBody
		public String addpoint()throws Exception{
			PageData pd = new PageData();
			try {
				pd = getPageData();
				  if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){ 
					  	pd.put("ID", get32UUID());
					  	pd.put("DATE",new Date());
			    		//添加
			    		this.pointsservice.addpoint(pd); 
			    		tbuserService.editPoint(pd);
		    	  }else{ 
		    		//修改
		    	 	 pd.put("ID",pd.getString("ID"));
		    	 	 pd.put("DATE",new Date());
		    	 	 PageData page=this.pointsservice.querypointid(pd);
		    	 	 Object oldPoint=page.get("POINTS");
		    	 	 Object point=pd.get("POINTS");
		    	 	 PageData data=new PageData();
		    	 	 data.put("USER_ID",pd.getString("USER_ID")); 
		    	 	 if(point!=null){
		    	 		 int newPoint=Integer.parseInt(point+"")-Integer.parseInt(oldPoint+"");
		    	 		 data.put("POINTS",newPoint);
		    	 	 }
		    	 	 //修改
		    	 	this.pointsservice.uppoint(pd); 
		    	 	this.tbuserService.editPoint(data);
		    	  } 
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
				JSONObject getObj = new JSONObject();
				getObj.put("statusCode", 200);
				return getObj.toString();
		}
		
		//通过ID获取数据
		@RequestMapping({ "/querypointid" })
		public ModelAndView querycertifiid()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			List userList = this.tbuserService.querytbuser(null);
			List typeList=this.dicService.queryByPBM("points"); 
			mv.addObject("userList", userList); 
			mv.addObject("typeList", typeList); 
			PageData Cert=this.pointsservice.querypointid(pd);
			mv.setViewName("system/company/add-Point");
			mv.addObject("PointsData", Cert); 
			return mv;
		}
		
		//批量删除
		@RequestMapping({ "/delpoint" })
		@ResponseBody
		public String delpoint() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				String dasid = pd.getString("ID");
				String[] auserId= dasid.split(",");
				this.pointsservice.delpoint(auserId);
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
		
		@RequestMapping({ "/delpointbyid" })
		@SystemLog(methods="积分管理",type="删除")
		@ResponseBody
		public JSONObject delpointbyid() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
				//this.projectService.delete(pd);
				 PageData page=this.pointsservice.querypointid(pd);
	    	 	 Object oldPoint=page.get("POINTS");
	    	 	 PageData data=new PageData();
	    	 	 data.put("USER_ID",page.getString("USER_ID"));  
	    	 	 if(oldPoint!=null){
	    	 		 int point=0-Integer.parseInt(oldPoint+"");
	    	 		 data.put("POINTS", point);
	    	 	 }  
	    	 	this.tbuserService.editPoint(data);
				this.pointsservice.delpointbyid(pd);
				obj.put("statusCode", "200");
				obj.put("message", "操作成功");
				obj.put("closeCurrent", false);
			    	
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
			return obj;
		}
		
		//根据证件名称进行查找
		@RequestMapping({ "/querypointname" })
		public String querypointname()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			List dataList=this.pointsservice.querypointname(pd);
			mv.setViewName("system/company/points");
			mv.addObject("pointmap", dataList);
			return "redirect:/Points/querypoint";
		}
}


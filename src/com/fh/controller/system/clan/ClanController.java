package com.fh.controller.system.clan;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.activity.ActivityService;
import com.fh.service.system.area.AreaService;
import com.fh.service.system.clan.ClanService;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.SystemLog;
import com.fh.util.fileConfig;

@Controller
@RequestMapping({ "/clan" })
public class ClanController extends BaseController {
	String menuUrl = "clan/listclan.do";

	@Resource(name = "clanService")
	private ClanService clanService;
	
	@Resource(name = "areaService")
	private AreaService areaService; 
	
	//上传文件存放路径
	public static String serverBasePath = fileConfig.getString("serverBasePath");
		
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		List<PageData> areas=areaService.queryAllParent(null);
		mv.addObject("areas", areas);
		PageData pd=null;
		this.clanService.updatedelhd(pd);
		mv.setViewName("system/clan/clanindex");
		return mv;
	}

	//查询所有公司信息
		@RequestMapping({ "/queryClan" })
		@ResponseBody
		public String queryClan(Page page)throws Exception{
			
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
			 
			String contractName = pd.getString("NAME");
			
			if ((contractName != null) && (!"".equals(contractName))) { 
				//String param=new String(contractName.getBytes("ISO-8859-1"),"UTF-8").trim();
				pd.put("NAME",contractName);
			} 
			page.setPd(pd);
			page.setOrderDirection(sSortDir_0);
			page.setOrderField(sortName);
			page.setPageSize(Integer.valueOf(iDisplayLength));
			page.setPageCurrent(Integer.valueOf(iDisplayStart));
			int initEcho = Integer.parseInt(sEcho) + 1;  
			
			List<PageData> data = this.clanService.listByParam(page);
			Object  total = this.clanService.findCount(page).get("counts");//查询记录的总行数
			getObj.put("sEcho", initEcho);// 不知道这个值有什么用
			getObj.put("iTotalRecords", total);//实际的行数
			getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
			for(int i=0;i<data.size();i++){
				 Object cardate=data.get(i).get("CREATE_DATE");  
				 if(cardate==null||"".equals(cardate)){
					   data.get(i).put("CREATE_DATE","");
				 }else{
					   String START_DATE =cardate+""; 
					   data.get(i).put("CREATE_DATE",START_DATE);
				 }  
			}
			getObj.put("aaData", data);//要以JSON格式返回
			return getObj.toString();
			
		} 
		 
		@RequestMapping({ "/addClan" })
		@SystemLog(methods="活动管理",type="编辑")
		@ResponseBody 
		public JSONObject addClan(MultipartHttpServletRequest request) throws Exception {
			JSONObject obj = new JSONObject();
			PageData pd = new PageData();  
			
			MultipartFile file=request.getFile("IMG_PATH");
			String ID=request.getParameter("ID"); 
			String INTRODUCE=new String(request.getParameter("INTRODUCE").getBytes("ISO_8859-1"),"UTF-8");
			String TYPE=request.getParameter("TYPE");
			String NAME=new String(request.getParameter("NAME").getBytes("ISO_8859-1"),"UTF-8");
			String AREA_CODE=request.getParameter("AREA_CODE");
			String ZONE_CODE=request.getParameter("ZONE_CODE");
			 
			pd.put("INTRODUCE", INTRODUCE); 
	   		pd.put("NAME", NAME); 
	   		pd.put("TYPE", TYPE); 
	   		pd.put("AREA_CODE", AREA_CODE); 
	   		pd.put("ZONE_CODE", ZONE_CODE); 
	   		pd.put("NUMBER_COUNTS", 0); 
			pd.put("CREATE_DATE", new Date()); 
			pd.put("CREATE_BY",getUser().getID());
			if ((file != null) && (!file.isEmpty())) {
				String filePath =serverBasePath; 
				String fileName = FileUpload.fileUp(file,filePath,get32UUID()); 
				pd.put("IMG_PATH", fileName); 
			} 
			
			if(ID==null||"".equals(ID)){ //添加
			  	pd.put("ID", get32UUID());  
			  	this.clanService.addClan(pd);
    	    }else{  //修改 
    		   pd.put("ID",ID);
    		   this.clanService.upClan(pd);
    	    } 
			obj.put("statusCode", 200);  
			return obj;
		}  
		
		//查看部落图片
		@RequestMapping({ "/look" })
		public ModelAndView look()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData(); 
			PageData clan=this.clanService.queryById(pd);
			mv.setViewName("system/clan/look-img");
			mv.addObject("clan", clan); 
			return mv;
		}
		
		//通过ID获取数据
		@RequestMapping({ "/queryById" })
		public ModelAndView queryById()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			List<PageData> area =areaService.queryAllParent(null);
			//String userId = pd.getString("userId");
			/*if ((panyId == null) || ("".equals(panyId))) {
			       pd.put("userId",get32UUID());
			       
			}*/
			PageData Cert=this.clanService.queryById(pd);
			mv.setViewName("system/clan/add-clan");
			mv.addObject("clanData", Cert); 
			mv.addObject("area", area); 
			return mv;
		}
		
		//批量删除
		@RequestMapping({ "/delClan" })
		@SystemLog(methods="城市建联管理",type="批量删除")
		@ResponseBody
		public String delClan() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				String dasid = pd.getString("ids");
				String[] auserId= dasid.split(",");
				this.clanService.delClan(auserId);
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
		
		@RequestMapping({ "/delClanbyid" })
		@SystemLog(methods="活动管理",type="删除")
		@ResponseBody
		public JSONObject delClanbyid() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
				//this.projectService.delete(pd);
				this.clanService.delClanbyid(pd);
				obj.put("statusCode", "200");
				obj.put("message", "操作成功");
				obj.put("closeCurrent", false);
			    	
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
			return obj;
		}
		
		@RequestMapping(value={"/toMember"})
		public ModelAndView list() throws Exception {
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData(); 
			mv.addObject("CLAN_ID", pd.get("CLAN_ID")); 
			mv.setViewName("system/clan/clan_member_list");
			return mv;
		} 
		
		@RequestMapping(value={"/queryMemberList"}) 
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
			 
			String NAME = pd.getString("NAME"); 
			if ((NAME != null) && (!"".equals(NAME))) { 
				pd.put("REAL_NAME",NAME);
			}  
			page.setPd(pd);
			page.setOrderDirection(sSortDir_0);
			page.setOrderField(sortName);
			page.setPageSize(Integer.valueOf(iDisplayLength));
			page.setPageCurrent(Integer.valueOf(iDisplayStart));
			int initEcho = Integer.parseInt(sEcho) + 1;
			
			List<PageData> data = this.clanService.listPage(page);
			Object	total = this.clanService.findMemberCount(page).get("counts");//查询记录的总行数 
			
			getObj.put("sEcho", initEcho);// 不知道这个值有什么用
			getObj.put("iTotalRecords", total);//实际的行数
			getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
			
			for(int i=0;i<data.size();i++){
			   Object date=data.get(i).get("REGISTER_DATE"); 
			   if(date==null||"".equals(date)){
				   data.get(i).put("REGISTER_DATE","");
			   }else{
				   String DATE =date+"";
				   data.get(i).put("REGISTER_DATE",DATE);
			   }
			} 
			getObj.put("aaData", data);//要以JSON格式返回
			return getObj.toString();
		}  
		 
		
		//获取当前登录用户
		public User getUser(){
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession(); 
			User user = (User) session.getAttribute("sessionUser"); 
			return user;
		} 
}


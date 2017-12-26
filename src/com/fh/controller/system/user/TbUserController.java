package com.fh.controller.system.user;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PUT;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.clan.ClanService;
import com.fh.service.system.company.TbcompanyService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.user.TbUserService;
import com.fh.service.system.user.UserService;
import com.fh.util.ChineseToEnglish;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.SystemLog;
import com.fh.util.excelexception.Exceldworup;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

@Controller
@RequestMapping({ "/tbuser" })
public class TbUserController extends BaseController {
	String menuUrl = "tbuser.do";

	@Resource(name = "tbuserService")
	private TbUserService tbuserService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService;  
	
	@Resource(name = "clanService")
	private ClanService clanService;
	
	@Resource(name = "tbcompanyService")
	private TbcompanyService comService;
	
	ChineseToEnglish  cte=new ChineseToEnglish();

	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/company/members");
		return mv;
	}
	
	@RequestMapping({ "/hyList" })
	public ModelAndView hyList(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/company/members1");
		return mv;
	}
	
	@RequestMapping({ "/querytbuser" })
	@ResponseBody
	public String querytbuser(Page page)throws Exception{
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
		 
		String contractName = pd.getString("REAL_NAME"); 
		if ((contractName != null) && (!"".equals(contractName))) { 
			//String param=new String(contractName.getBytes("ISO-8859-1"),"UTF-8").trim();
			pd.put("REAL_NAME",contractName);
		}  
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;  
		
		List<PageData> data = this.tbuserService.listByParam(page);
		Object  total = this.tbuserService.findCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		for(int i=0;i<data.size();i++){
			java.sql.Date duedate=(java.sql.Date)((PageData)data.get(i)).get("DUE_DATE");
			Object reddate=data.get(i).get("REGISTER_DATE");
			Object lldate=data.get(i).get("LAST_LOGIN_DATE");
		   if(duedate==null||"".equals(duedate)){
			   ((PageData)data.get(i)).put("DUE_DATE","");
		   }else{
			   String DUE_DATE =DateUtil.getTime(new Date(duedate.getTime()));
			   ((PageData)data.get(i)).put("DUE_DATE",DUE_DATE);
		   }
		   if(reddate==null||"".equals(reddate)){
			   ((PageData)data.get(i)).put("REGISTER_DATE","");
		   }else{
			   String REGISTER_DATE =reddate+"";
			   ((PageData)data.get(i)).put("REGISTER_DATE",REGISTER_DATE);
		   }
		   if(lldate==null||"".equals(lldate)){
			   ((PageData)data.get(i)).put("LAST_LOGIN_DATE","");
		   }else{
			   String LAST_LOGIN_DATE =lldate+"";
			   ((PageData)data.get(i)).put("LAST_LOGIN_DATE",LAST_LOGIN_DATE);
		   }
		   
		}
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	}
	//批量删除
	@RequestMapping({ "/delTbuser" })
	@SystemLog(methods="会员管理",type="批量删除")
	@ResponseBody
	public String delTbuser() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			pd = getPageData();
			//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
			//this.projectService.delete(pd);
			String dasid = pd.getString("ID");
			String[] auserId= dasid.split(",");
			this.tbuserService.delTbuser(auserId);
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
	
	//禁止/解禁
	@RequestMapping({ "/upstatus" })
	@ResponseBody
	public String upstatus() {
		PageData page = new PageData();
		try {
			PageData pd = getPageData();
			PageData pagedata=(PageData) this.tbuserService.queryById(pd);
			if("1".equals(pagedata.get("STATUS").toString())){ 
				page.put("STATUS", 2);//禁止
			}
			else if("2".equals(pagedata.get("STATUS").toString())){
				page.put("STATUS", 1); //启用
			}
			page.put("ID", pagedata.getString("ID")); 
			this.tbuserService.upstatus(page);
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("statusCode",200);
		System.out.println(getObj.get("statusCode"));
		return getObj.toString();
	}
	
	@RequestMapping({ "/adduserid" })
	@ResponseBody
	public ModelAndView adduserid()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData data = new PageData();
		data = getPageData();
		PageData company=this.tbuserService.queryById(data); 
		List<PageData> levelList=this.dicService.queryByPBM("viptype");
		mv.addObject("levelList", levelList);
		List<PageData> clanList=this.clanService.queryAll(null);
		mv.addObject("clanList", clanList); 
		mv.setViewName("system/company/article-add");
		mv.addObject("data", company); 
		return mv;
	}
	
	@RequestMapping({ "/querybyid" })
	@ResponseBody
	public ModelAndView querybyid()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData data = new PageData();
		data = getPageData();
		PageData company=this.tbuserService.queryById(data);
		
		List<PageData> levelList=this.dicService.queryByPBM("viptype");

		mv.addObject("levelList", levelList);
		List<PageData> clanList=this.clanService.queryAll(null);
		mv.setViewName("system/company/article-add");
		mv.addObject("data", company); 
		
		mv.addObject("clanList", clanList); 
		return mv;
	}
	
	@RequestMapping({ "/querybyid1" })
	@ResponseBody
	public ModelAndView querybyid1()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData data = new PageData();
		data = getPageData();
		PageData company=this.tbuserService.queryById(data);
		
		List<PageData> levelList=this.dicService.queryByPBM("viptype");

		mv.addObject("levelList", levelList);
		List<PageData> clanList=this.clanService.queryAll(null);
		mv.setViewName("system/company/article-add1");
		mv.addObject("data", company); 
		
		mv.addObject("clanList", clanList); 
		return mv;
	}
	
	//完善资料
	@RequestMapping({ "/querybywanid" })
	@SystemLog(methods="会员管理",type="完善资料")
	@ResponseBody
	public ModelAndView querybywanid()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData data = new PageData();
		data = getPageData();
		PageData company=this.tbuserService.queryById(data);
		mv.setViewName("system/company/wanshan-members");
		mv.addObject("data", company); 
		return mv;
	} 
	
	//完善入会时间
		@RequestMapping({ "/querybywantime" })
		@SystemLog(methods="会员管理",type="完善入会时间")
		@ResponseBody
		public ModelAndView querybywantime()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData data = new PageData();
			data = getPageData();
			PageData company=this.tbuserService.queryById(data);
			mv.setViewName("system/company/wanshan-rhtime");
			mv.addObject("data", company); 
			return mv;
		} 
	
	//完善信息
		@RequestMapping({ "/querybywsxx" })
		@SystemLog(methods="会员管理",type="完善信息")
		@ResponseBody
		public ModelAndView querybywsxx()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData data = new PageData();
			data = getPageData();
			PageData company=this.tbuserService.queryById(data);
			List<PageData> ru=(List<PageData>) this.tbuserService.queryRy(company);
			List<PageData> zy=(List<PageData>) this.tbuserService.queryZy(company);
			List<PageData> xq=(List<PageData>) this.tbuserService.queryXq(company);
			mv.setViewName("system/company/wsxx-members");
			mv.addObject("ru", ru); 
			mv.addObject("zy", zy); 
			mv.addObject("xq", xq);
			mv.addObject("data", company); 
			return mv;
		} 
	//添加修改
		@RequestMapping({ "/adduser" })
		@SystemLog(methods="会员管理",type="编辑")
		@ResponseBody
		public String adduser()throws Exception{
			ModelAndView mv = getModelAndView(); 
			PageData pd = new PageData();
			try {
				pd = getPageData();
				  if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){ 
					    pd.put("VALERIE", cte.getPingYinToHeaderChar(pd.getString("REAL_NAME")));
					  	pd.put("ID", get32UUID());
						pd.put("CREATE_DATE",new Date());
					  	pd.put("CREATE_BY", getUser().getID());
					  	pd.put("STATUS", 01);
						pd.put("TOTAL_POINTS", 0); 
			    		//添加
			    		this.tbuserService.adduser(pd);
			    		this.clanService.updateaddClanCounts(pd);
		    	  }else{ 
		    		//修改 
		    	 	PageData data=tbuserService.queryById(pd); 
		    	 	pd.put("VALERIE", cte.getPingYinToHeaderChar(pd.getString("REAL_NAME")));
		    	 	pd.put("MODIFY_DATE",new Date());
				  	pd.put("MODIFY_BY", getUser().getID());
				    if("02".equals(pd.getString("VIP_LEVEL"))){
				    	pd.put("IS_VIP", 0);
				    }
		    	 	 //修改
		    	 	this.tbuserService.uptbuser(pd); 
		    	 	this.clanService.updatedelClanCounts(data);
		    	 	this.clanService.updateaddClanCounts(pd);
		    	  } 
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}  
			JSONObject getObj = new JSONObject();
			getObj.put("status", "1");
			return getObj.toString();
		}
	//重置密码
	@RequestMapping({ "/uppassword" })
	@ResponseBody
	public String uppassword() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		try {
			pd = getPageData();
			pd.put("PASSWORD", 123456);
			this.tbuserService.uppassword(pd);
			
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");
		return getObj.toString();
	}
	
	@RequestMapping({ "/delmembyid" })
	@SystemLog(methods="会员管理",type="删除")
	@ResponseBody
	public JSONObject delmembyid() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			pd = getPageData();
			this.tbuserService.delmembyid(pd);
			obj.put("statusCode", "200");
			obj.put("message", "操作成功");
			obj.put("closeCurrent", false);
		    	
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
		return obj;
	}
	
	//根据用户姓名进行查找
		@RequestMapping({ "/querybyname" })
		public String querybyname()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			List dataList=this.tbuserService.querybyname(pd);
			mv.setViewName("system/company/business");
			mv.addObject("panymap", dataList);
			return "redirect:/tbuser/querytbuser";
		}
		//分配
		@RequestMapping({ "/fenpei" })
		@SystemLog(methods="会员管理",type="分配")
		@ResponseBody
		public ModelAndView fenpei() throws Exception {
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();  
			PageData pds = this.tbuserService.queryById(pd); 
			List<PageData> managerList=this.userService.findSecretary();
			mv.addObject("managerList", managerList);
			mv.addObject("msg", "check");
			mv.addObject("panymap", pds); 
			
			mv.setViewName("system/company/fenpei-managers");
			return mv;
		} 
		
		//录入会员信息
		@RequestMapping({ "/userinfo_add" }) 
		@ResponseBody
		public ModelAndView userinfo_add() throws Exception {
			ModelAndView mv = getModelAndView();  
			List<PageData> levelList=this.dicService.queryByPBM("viptype");
			mv.addObject("levelList", levelList);    
			mv.setViewName("system/company/user_add");
			return mv;
		}  
		
		//修改录入会员信息
		@RequestMapping({ "/userinfo_edit" }) 
		@ResponseBody
		public ModelAndView userinfo_edit() throws Exception {
			ModelAndView mv = getModelAndView(); 
			PageData data = new PageData(); 
			data = getPageData();
			PageData user=this.tbuserService.queryuserandcom(data); 
			List<PageData> levelList=this.dicService.queryByPBM("viptype");
			mv.addObject("levelList", levelList);   
			mv.addObject("data", user);  
			mv.setViewName("system/company/user_edit");
			return mv;
		}  
		 
		
		@RequestMapping({ "/saveUserInfo" })
		@ResponseBody 
		public JSONObject saveUserInfo() throws Exception{ 
			PageData pd = new PageData(); 
			JSONObject obj = new JSONObject();
			pd = getPageData(); 
		    pd.put("VALERIE", cte.getPingYinToHeaderChar(pd.getString("REAL_NAME"))); 
			pd.put("REGISTER_DATE",new Date());  
			String password = new SimpleHash("SHA-1",pd.getString("USER_NAME"),pd.getString("PASSWORD")).toString();
			pd.put("PASSWORD",password);  
		    pd.put("CARD_NO",pd.getString("USER_NAME")); 
			pd.put("CREATE_DATE",new Date());
		  	pd.put("CREATE_BY", getUser().getID());
		  	pd.put("STATUS","01");
			pd.put("TOTAL_POINTS", 0);
			pd.put("CLAN_ID","e960966a28ab4d70afb3f8414b8f0002");
			pd.put("IS_VIP",1); 
			String com_id=get32UUID(); 
			if(pd.getString("COMPANY_NAME")!=null&&!"".equals(pd.getString("COMPANY_NAME"))){
				pd.put("COMPANY_ID",com_id);  
			} 
			pd.put("ID", get32UUID());
			//添加
	    	this.tbuserService.adduser(pd); 
	    	
	    	if(pd.getString("COMPANY_NAME")!=null&&!"".equals(pd.getString("COMPANY_NAME"))){
	    		PageData data=new PageData(); 
	    		data.put("ID",com_id);
	    		data.put("COMPANY_NAME",pd.getString("COMPANY_NAME")); 
	    		data.put("CREATE_DATE",new Date());
	    		data.put("CREATE_BY", getUser().getID()); 
			  	comService.addcompany(data);
	    	}  
    		PageData data=new PageData();
    		data.put("CLAN_ID","e960966a28ab4d70afb3f8414b8f0002"); 
    		clanService.updatedelClanCounts(data);
    		obj.put("statusCode", "200"); 
    		return obj; 
		}
		
		@RequestMapping({ "/editUserInfo" })
		@ResponseBody 
		public JSONObject editUserInfo() throws Exception{ 
			PageData pd = new PageData(); 
			JSONObject obj = new JSONObject();
			pd = getPageData(); 
		    pd.put("VALERIE", cte.getPingYinToHeaderChar(pd.getString("REAL_NAME")));  
			String password = new SimpleHash("SHA-1",pd.getString("USER_NAME"),pd.getString("PASSWORD")).toString();
			pd.put("PASSWORD",password);  
		    pd.put("CARD_NO",pd.getString("USER_NAME"));   
		  	pd.put("STATUS","01"); 
			pd.put("IS_VIP",1);  
			
			String com_id=pd.getString("COMPANY_ID");
			
			if(com_id!=null&&!"".equals(com_id)){
				pd.put("COMPANY_ID",com_id);
			}else{
				pd.put("COMPANY_ID", get32UUID());
			}  
			//修改录入会员信息
    		this.tbuserService.edituser(pd);  
			
			PageData data=new PageData();  
			data.put("ID",pd.getString("COMPANY_ID"));
    		data.put("COMPANY_NAME",pd.getString("COMPANY_NAME"));  
			if(com_id!=null&&!"".equals(com_id)){  
			  	comService.upcom(data);
			}else{ 
				if(pd.getString("COMPANY_NAME")!=null&&!"".equals(pd.getString("COMPANY_NAME"))){
					data.put("CREATE_DATE",new Date());
					data.put("CREATE_BY", getUser().getID()); 
				  	comService.addcompany(data);
				} 
			} 
    		obj.put("statusCode", "200");
    		return obj; 
		}
		
		//录入内部员工信息
		@RequestMapping({ "/innerUser_add" }) 
		@ResponseBody
		public ModelAndView innerUser_add() throws Exception {
			ModelAndView mv = getModelAndView(); 
			mv.setViewName("system/company/inner_user_add");
			return mv;
		} 
		
		@RequestMapping({ "/saveInnerUser" })
		@ResponseBody 
		public JSONObject saveInnerUser() throws Exception{ 
			PageData pd = new PageData(); 
			JSONObject obj = new JSONObject();
			pd = getPageData();
			pd.put("ID", get32UUID());
		    pd.put("VALERIE", cte.getPingYinToHeaderChar(pd.getString("REAL_NAME"))); 
			pd.put("REGISTER_DATE",new Date());  
			String password = new SimpleHash("SHA-1",pd.getString("USER_NAME"),pd.getString("PASSWORD")).toString();
			pd.put("PASSWORD",password);  
		    pd.put("CARD_NO",pd.getString("USER_NAME")); 
			pd.put("CREATE_DATE",new Date());
		  	pd.put("CREATE_BY", getUser().getID());
		  	pd.put("STATUS","01");
			pd.put("CLAN_ID","e960966a28ab4d70afb3f8414b8f0002");
			pd.put("TOTAL_POINTS", 0); 
    		//添加
    		this.tbuserService.adduser(pd);  
    		PageData data=new PageData();
    		data.put("CLAN_ID","e960966a28ab4d70afb3f8414b8f0002"); 
    		clanService.updatedelClanCounts(data);
    		obj.put("statusCode", "200"); 
    		return obj; 
		}
		
		//跳转至修改会员身份标识页面
		@RequestMapping({ "/user_identify" }) 
		@ResponseBody
		public ModelAndView user_identify() throws Exception {
			ModelAndView mv = getModelAndView();
			PageData pd = getPageData();  
			PageData data = this.tbuserService.queryById(pd); 
			List<PageData> levelList=this.dicService.queryByPBM("viptype");
			mv.addObject("levelList", levelList);   
			mv.addObject("data", data);   
			mv.setViewName("system/company/user_identify");
			return mv;
		} 
		
		//修改会员身份标识 
		@RequestMapping({ "/updateIdentity" })  
	@ResponseBody
	public String updateIdentity()throws Exception{ 
		PageData pd = new PageData();
		try {
			pd = getPageData();   
    	 	pd.put("MODIFY_DATE",new Date());
		  	pd.put("MODIFY_BY", getUser().getID()); 
    	 	 //修改
    	 	this.tbuserService.uptbuser(pd);  
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}  
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");
		return getObj.toString();
	}
		
		/**
		 * 保存用户荣誉，资源，需求信息
		 * @return
		 * @throws Exception
		 */
		@RequestMapping({ "/adduserxx" })
		@ResponseBody 
		public JSONObject adduserxx() throws Exception{ 
			PageData pd = new PageData(); 
			JSONObject obj = new JSONObject();
			pd = getPageData();
    		//添加
    		this.tbuserService.editXx(pd);  
    		obj.put("status", "1"); 
    		return obj; 
		}
		/**
		 * 根据会员账号或卡号查询会员信息
		 * @return
		 * @throws Exception
		 */
		@RequestMapping({ "/findU" })
		@ResponseBody
		public String findGsd()throws Exception{
			PageData pd = new PageData();
			pd = getPageData();
			PageData user=(PageData) this.tbuserService.findU(pd);
			JSONObject getObj = new JSONObject();
			getObj.put("user", user);
			return getObj.toString(); 
		}
		
		//验证卡号是否重复
		@RequestMapping({ "/has" })
		public void has(PrintWriter out) {
			PageData pd = new PageData();
			try {
				pd = getPageData();
				if (this.tbuserService.findBmCount(pd) != null)
					out.write("error");
				else {
					out.write("success");
				}
				out.close();
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
		}
		
		@RequestMapping(value = "exportfeedback")
	    @ResponseBody
	    public String exportFeedBack(HttpServletResponse response) throws Exception{
	    	JSONObject getObj = new JSONObject();
	        String fileName = "会员信息"+System.currentTimeMillis()+".xls"; //文件名 
	        String sheetName = "会员信息";//sheet名   
	        String []title = new String[]{"会员名称","会员账号","所属公司","职位","联系电话","邮箱","卡号"};//标题
	        PageData pd=null;
	        List<PageData> list = this.tbuserService.doexlelist(pd);//内容list
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	        String [][]values = new String[list.size()][];
	        for(int i=0;i<list.size();i++){
	            values[i] = new String[title.length];
	            //将对象内容转换成string
	            PageData obj = list.get(i);  
	            values[i][0] = obj.getString("REAL_NAME")+"";
	            values[i][1] = obj.getString("USER_NAME");
	            values[i][2] = obj.getString("C_NAME");
	            values[i][3] = obj.getString("POSITION");
	            values[i][4] = obj.getString("PHONE");
	            values[i][5] = obj.getString("EMAIL");
	            values[i][6] = obj.getString("VIP_NAME");
	            values[i][6] = obj.getString("LEVEL_NAME");
	            values[i][6] = obj.getString("CARD_NO"); 
	            
	        }
	        
	        HSSFWorkbook wb = Exceldworup.getHSSFWorkbook(sheetName, title, values, null);
	        
	        //将文件存到指定位置  
	        try {  
	             this.setResponseHeader(response, fileName);  
	             OutputStream os = response.getOutputStream();  
	             wb.write(os);  
	             os.flush();  
	             os.close();  
	        } catch (Exception e) {  
	             e.printStackTrace();  
	        }  
	        return getObj.toString();
	    } 
     public void setResponseHeader(HttpServletResponse response, String fileName) {  
         try {  
              try {  
                   fileName = new String(fileName.getBytes(),"ISO8859-1");  
              } catch (UnsupportedEncodingException e) {  
                   // TODO Auto-generated catch block  
                   e.printStackTrace();  
              }  
              response.setContentType("application/octet-stream;charset=ISO8859-1");  
              response.setHeader("Content-Disposition", "attachment;filename="+ fileName);  
              response.addHeader("Pargam", "no-cache");  
              response.addHeader("Cache-Control", "no-cache");  
         } catch (Exception ex) {  
              ex.printStackTrace();  
         }  
    }
}


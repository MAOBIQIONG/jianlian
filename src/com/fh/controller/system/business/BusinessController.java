package com.fh.controller.system.business;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.app.AppCitiesService;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.system.activity.ActivityService;
import com.fh.service.system.area.AreaService;
import com.fh.service.system.business.BusinessService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.notification.SysNotificationService; 
import com.fh.util.FileUpload;
import com.fh.util.PageData; 
import com.fh.util.PushUtil;
import com.fh.util.SystemLog;
import com.fh.util.fileConfig; 
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/business" })
public class BusinessController extends BaseController {
	String menuUrl = "business/listactivity.do";

	@Resource(name = "businessService")
	private BusinessService businessService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService;  
	
	@Resource(name="appCitiesService")
	private AppCitiesService appCitiesService;
	
	@Resource(name="sysNotificationService")
	private SysNotificationService sysNotifService;
	
	@Resource(name = "appusersService")
	private AppusersService appusersService;
	
	@Resource(name = "areaService")
	private AreaService areaService;
	
	//上传文件存放路径
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/business/business-list");
		return mv;
	}
	//查询商学院信息
		@RequestMapping({ "/queryList" })
		@ResponseBody
		public String queryList(Page page)throws Exception{
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
			
			List<PageData> acts = this.businessService.listByParam(page);
			Object  total = this.businessService.findCount(page).get("counts");//查询记录的总行数
			getObj.put("sEcho", initEcho);// 不知道这个值有什么用
			getObj.put("iTotalRecords", total);//实际的行数
			getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
			for(PageData act:acts){  
				 act.put("DUE_DATE",act.get("DUE_DATE")==null?"":act.get("DUE_DATE").toString());
				 act.put("END_DATE",act.get("END_DATE")==null?"":act.get("END_DATE").toString());
				 act.put("START_DATE",act.get("START_DATE")==null?"":act.get("START_DATE").toString()); 
				 act.put("CREATE_DATE",act.get("CREATE_DATE")==null?"":act.get("CREATE_DATE").toString()); 
			}  
			getObj.put("aaData", acts);//要以JSON格式返回
			return getObj.toString();
		}
		
		
		@RequestMapping({ "/addactivity" })
		@SystemLog(methods="活动管理",type="编辑")
		@ResponseBody
		public String addactivity(MultipartHttpServletRequest request)throws Exception{
			JSONObject obj = new JSONObject();
			PageData pd = new PageData();
			try {
				  int num=-1;  
				  pd = getPageData();   
				  String STATUS=pd.getString("STATUS");
				  MultipartFile DETAILS_IMG=request.getFile("DETAILS_IMG");
				  MultipartFile YY_IMG=request.getFile("YY_IMG");
				  if((DETAILS_IMG != null) && (!DETAILS_IMG.isEmpty())){
					  String filePath =serverBasePath; 
						String fileName = FileUpload.fileUp(DETAILS_IMG,filePath,get32UUID());
						pd.put("DETAILS_IMG", fileName); 
				  }
				  if((YY_IMG != null) && (!YY_IMG.isEmpty())){
					  String filePath =serverBasePath; 
						String fileName = FileUpload.fileUp(YY_IMG,filePath,get32UUID());
						pd.put("YY_IMG", fileName); 
				  }
				  pd.put("ACT_NAME", request.getParameter("ACT_NAME")); 
				  pd.put("HDORSX", request.getParameter("HDORSX")); 
				  pd.put("ACT_TYPE", request.getParameter("ACT_TYPE")); 
				  pd.put("START_DATE", request.getParameter("START_DATE"));
				  pd.put("END_DATE", request.getParameter("END_DATE"));
				  pd.put("DUE_DATE", request.getParameter("DUE_DATE"));
				  pd.put("ACT_CITY", request.getParameter("ACT_CITY"));
				  pd.put("ACT_ADDR", request.getParameter("ACT_ADDR"));
				  pd.put("LINK_URL", request.getParameter("LINK_URL"));
				  pd.put("LIMIT_COUNT", request.getParameter("LIMIT_COUNT"));
				  pd.put("IS_FREE", request.getParameter("IS_FREE"));
				  pd.put("PRICE", request.getParameter("PRICE"));
				  pd.put("VIP_PRICE", request.getParameter("VIP_PRICE"));
				  pd.put("VICE_PRE_PRICE", request.getParameter("VICE_PRE_PRICE"));
				  pd.put("EXE_VICE_PRE_PRICE", request.getParameter("EXE_VICE_PRE_PRICE"));
				  pd.put("PRESIDENT_PRICE", request.getParameter("PRESIDENT_PRICE"));
				  pd.put("FEE_DESCRIPTION", request.getParameter("FEE_DESCRIPTION"));
				  pd.put("ACT_CONTENT", request.getParameter("ACT_CONTENT"));
				  pd.put("SPONSOR", request.getParameter("SPONSOR"));
				  pd.put("HEADPHONE", request.getParameter("HEADPHONE"));
				  pd.put("DETAILS_IMG", request.getParameter("DETAILS_IMG"));
				  pd.put("YY_IMG", request.getParameter("YY_IMG"));
				  pd.put("STATUS",pd.get("STATUS")==null||"".equals(pd.get("STATUS"))?"01":pd.getString("STATUS"));
				  pd.put("LIMIT_COUNT",pd.get("LIMIT_COUNT")==null||"".equals(pd.get("LIMIT_COUNT"))?num:Integer.parseInt(pd.getString("LIMIT_COUNT")));
				  pd.put("PRICE",pd.get("PRICE")==null||"".equals(pd.get("PRICE"))?0:Double.parseDouble(pd.getString("PRICE")));
				  pd.put("VIP_PRICE",pd.get("VIP_PRICE")==null||"".equals(pd.get("VIP_PRICE"))?0:Double.parseDouble(pd.getString("VIP_PRICE")));
				  pd.put("VICE_PRE_PRICE",pd.get("VICE_PRE_PRICE")==null||"".equals(pd.get("VICE_PRE_PRICE"))?0:Double.parseDouble(pd.getString("VICE_PRE_PRICE")));
				  pd.put("EXE_VICE_PRE_PRICE",pd.get("EXE_VICE_PRE_PRICE")==null||"".equals(pd.get("EXE_VICE_PRE_PRICE"))?0:Double.parseDouble(pd.getString("EXE_VICE_PRE_PRICE")));
				  pd.put("PRESIDENT_PRICE",pd.get("PRESIDENT_PRICE")==null||"".equals(pd.get("PRESIDENT_PRICE"))?0:Double.parseDouble(pd.getString("PRESIDENT_PRICE")));
				  if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){ 
					  	pd.put("ID", get32UUID());
					  	pd.put("CREATE_DATE",new Date());
					  	pd.put("CREATE_BY", getUser().getID());
					  	pd.put("COLLECT_COUNT",0); 
						pd.put("PART_COUNT",0);
			    		//添加
			    		this.businessService.save(pd);
		    	  }else{ 
		    	 	    pd.put("ID",pd.getString("ID")); 
		    	 	    //修改
		    	 	    this.businessService.edit(pd);
			    	 	String level=pd.getString("LEVEL");
						if(level!=null&&!"".equals(level)){
							PageData data=new PageData();
							data.put("ACTIVITY_ID",pd.getString("ID"));
				    	 	this.businessService.deleteLevel(data);
				    	 	data.put("DATE",new Date());
				    	 	String[] strs=level.split(",");
							for(int i=0;i<strs.length;i++){
							   String bianma=strs[i];
							   if(bianma!=null&&!"".equals(bianma)){
								  data.put("ID",get32UUID()); 
								  data.put("BIANMA",bianma); 
								  this.businessService.addactivityLevel(data);
							   }
						    } 
				         }
		    	 	 if("02".equals(STATUS)||"03".equals(STATUS)){//审核
		    	 		if(pd.getString("USER_ID")!=null&&!"".equals(pd.getString("USER_ID"))){//只有是会员才给通知
		    	 			PageData notif=new PageData();
			    	 		notif.put("ID", get32UUID()); 
			    	 		String content = "";
			    	 		if("02".equals(STATUS)){
			    	 			content = "您发布的"+pd.getString("ACT_NAME")+"活动审核未通过！";
			    	 			notif.put("CONTENT",content);
			    	 			notif.put("DESCRIPTION",pd.getString("NOPASSREASON")); 
			    	 		}else{
			    	 			content = "您发布的"+pd.getString("ACT_NAME")+"活动审核已通过！";
			    	 			notif.put("CONTENT",content);
			    	 			notif.put("DESCRIPTION",pd.getString("NOPASSREASON")); 
			    	 		} 
			    	 		notif.put("USER_ID", pd.getString("USER_ID"));
			    	 		notif.put("CREATE_DATE", new Date());
			    	 		notif.put("TABLE_NAME","activity");
			    	 		notif.put("OBJECT_ID",pd.getString("ID"));
			    	 		notif.put("STATUS",STATUS); 
			    	 		sysNotifService.save(notif);
			    	 		
							//推送消息
			    	 		PageData pagedata = new PageData();
			    	 		pagedata.put("ID", pd.getString("USER_ID"));
							PageData user = this.appusersService.queryById(pagedata);
			    	 		if( user != null ){
			    	 			//推送审核消息
//								NotificationTemplate template = null;
//								if( user.getString("PLATFORM") == "1" ){
//									template = PushUtil.notificationTemplateDemo(); 
//									template.setTitle("上海建联");
//									template.setText(content);
//								}
			    	 			String jsonStr = "{'type':'notice','title':'上海建联','content':'"+content+"'}";//透传内容
								TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
								PushUtil pushApp=new PushUtil();
								String alias = pd.getString("USER_ID");
								pushApp.pushToSingle(tmTemplate,alias); 
			    	 		}
		    	 		}   
		    	 	} 
		    	  } 
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
			obj.put("statusCode", 200);  
			return obj.toString();
		}
		
		//通过ID获取数据
		@RequestMapping({ "/queryById" })
		public ModelAndView queryById()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData(); 
			pd.put("pid","0");
			List<PageData> hdtype=this.dicService.queryByPBM("activitytype");  
			List<PageData> areaList = this.areaService.queryAllParent(pd);
			List<PageData> cityList = this.areaService.querybyPid(pd);   
			mv.addObject("areaList", areaList);  
			mv.addObject("cityList", cityList); 
			mv.addObject("hdlxlist",hdtype); 
			mv.setViewName("system/business/add-business");
			return mv;
		}
		
		//通过ID获取数据
		@RequestMapping({ "/toEdit" })
		public ModelAndView toEdit()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData(); 
			pd.put("pid","0");
			PageData Cert=this.businessService.queryById(pd);
			List<PageData> hdtype=this.dicService.queryByPBM("activitytype");  
			List<PageData> areaList = this.areaService.queryAllParent(pd);
			List<PageData> cityList = this.areaService.querybyPid(pd);  
			mv.addObject("areaList", areaList);  
			mv.addObject("cityList", cityList); 
			mv.addObject("hdlxlist",hdtype);
			mv.addObject("activityData",getAct(Cert)); 
			mv.setViewName("system/activity/edit-activity");
			return mv;
		}
		
		//通过ID获取数据
		@RequestMapping({ "/queryshById" })
		public ModelAndView queryshById()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData(); 
			pd.put("pid","0");
			List<PageData> hdtype=this.dicService.queryByPBM("activitytype");
			List<PageData> areaList = this.areaService.queryAllParent(pd);
			List<PageData> cityList = this.areaService.querybyPid(pd);   
			PageData Cert=this.businessService.queryById(pd);  
			List<PageData> userLevelList=this.dicService.queryByPBM("viptype");  
			String act_id=pd.getString("ID");
			if(act_id!=null&&!"".equals(act_id)){
				PageData data=new PageData();
				data.put("activity_id",act_id);
				List<PageData> levels=businessService.queryLevelbyAid(data);
				for(int i=0;i<userLevelList.size();i++){
					if(levels!=null&&levels.size()>0){
						for(int j=0;j<levels.size();j++){
							PageData userLevel=userLevelList.get(i); 
							if(userLevel.getString("BIANMA").equals(levels.get(j).getString("BIANMA"))){
								userLevel.put("flag",1);//选中
								break;
							} 
						}
					}else{
						PageData userLevel=userLevelList.get(i);
						userLevel.put("flag",0);//未选中
					} 
				}
			}
			mv.addObject("userLevelList", userLevelList); 
			mv.addObject("activityData",getAct(Cert)); 
			mv.addObject("hdlxlist",hdtype); 
			mv.addObject("areaList", areaList);  
			mv.addObject("cityList", cityList); 
			mv.setViewName("system/activity/activity-check");
			return mv;
		}
		
		//批量删除
		@RequestMapping({ "/delactivity" })
		@SystemLog(methods="活动管理",type="批量删除")
		@ResponseBody
		public String delactivity() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				String dasid = pd.getString("ID");
				String[] auserId= dasid.split(",");
				this.businessService.delactivity(auserId);
				this.businessService.delActivityHonor(auserId);
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
		
		@RequestMapping({ "/delactivitybyid" })
		@SystemLog(methods="活动管理",type="删除")
		@ResponseBody
		public JSONObject delactivitybyid() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData(); 
				this.businessService.delete(pd);
				this.businessService.delActHonorById(pd);
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
			List dataList=this.businessService.querybyname(pd);
			mv.setViewName("system/activity/activity-management");
			mv.addObject("activitymap", dataList);
			return "redirect:/activity/queryactivity";
		}
		
		//审核/不审核
		@RequestMapping({ "/upstatus" })
		@ResponseBody
		public String upstatus() {
			PageData page = new PageData();
			try {
				PageData pd = getPageData();
				PageData pagedata=(PageData) this.businessService.queryById(pd);
				if("1".equals(pagedata.get("STATUS").toString())){ 
					page.put("STATUS", 2);//禁止
				}  
				else if("2".equals(pagedata.get("STATUS").toString())){
					page.put("STATUS", 1); //启用
				}
				page.put("ID", pagedata.getString("ID")); 
				this.businessService.upstatus(pd);
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
			JSONObject getObj = new JSONObject();
			getObj.put("statusCode",200);
			System.out.println(getObj.get("statusCode"));
			return getObj.toString();
		} 
		 
		//活动图片页面
		@RequestMapping({ "/toImages"})
		public ModelAndView toImages()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			PageData data=this.businessService.queryById(pd); 
			String imgPath=data.getString("ACTIVITY_IMG"); 
			if(imgPath!=null&&!"".equals(imgPath)&&imgPath.contains(",")){
				String[] img=imgPath.split(",");
				mv.addObject("imgList",img);  
			}else{
				mv.addObject("imgList",imgPath); 
			} 
			mv.addObject("ACTIVITY_ID", pd.get("ID")); 
			mv.addObject("data",data);
			mv.setViewName("system/activity/activity-images-list"); 
			return mv;
		} 
		
		//活动图片页面
		@RequestMapping({ "/toImgAdd"})
		public ModelAndView toImgAdd()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData(); 
			mv.addObject("ACT_ID", pd.get("ACT_ID")); 
			mv.setViewName("system/activity/activity-images-add"); 
			return mv;
		}  
		
		@RequestMapping({ "/addActImg" })
		@ResponseBody 
		public JSONObject addActImg(MultipartHttpServletRequest request) throws Exception {
			JSONObject obj = new JSONObject();
			PageData pd = new PageData();  
			
			MultipartFile file=request.getFile("ACT_IMG");
			String ID=request.getParameter("ACT_ID");
			pd.put("ID",ID);
			if ((file != null) && (!file.isEmpty())) {
				String filePath =serverBasePath; 
				String fileName = FileUpload.fileUp(file,filePath,get32UUID());
				pd.put("ACT_IMG", fileName); 
			}  
    	 	this.businessService.updateImgPath(pd);
			obj.put("statusCode", 200);  
			return obj;
		}
		
		//城市匹配
		public PageData getAct(PageData pd){
			String code=pd.getString("AREACODE");
			if("310000".equals(code)){
				pd.put("PARENTID","310000");
			}
			if("110000".equals(code)){
				pd.put("PARENTID","110000");
			}
			if("120000".equals(code)){
				pd.put("PARENTID","120000");
			}
			if("500000".equals(code)){
				pd.put("PARENTID","500000");
			}
			return pd;
		} 
		/*
		//批量删除
		@RequestMapping({ "/delImg" })
		@ResponseBody
		public String delImg() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				PageData page=new PageData();
				PageData data=this.actService.queryById(pd); 
				String ACTIVITY_IMG="";
				String imgPath=data.getString("ACTIVITY_IMG"); 
				String imgs = pd.getString("act_img");
				
				String[] path=imgPath.split(",");
				for(int i=0;i<path.length;i++){
					if(imgs.contains(",")){
						String[] img=imgs.split(",");
						for(int j=0;j<img.length;j++){
							if(path[i].equals(img[j])){
								break;
							}else{ 
								if(ACTIVITY_IMG!=null&&!"".equals(ACTIVITY_IMG)){
									if(ACTIVITY_IMG.indexOf(path[i])==-1){
										ACTIVITY_IMG=ACTIVITY_IMG+","+path[i];
									} 
								}else{
									ACTIVITY_IMG=path[i];
								} 
							}
						}
					}else{
						if(path[i].equals(imgs)){
							
						}else{
							if(ACTIVITY_IMG!=null&&!"".equals(ACTIVITY_IMG)){
								ACTIVITY_IMG=ACTIVITY_IMG+","+path[i];
							}else{
								ACTIVITY_IMG=path[i];
							} 
						}
					}
					
				} 
				page.put("ID",pd.getString("ID"));
				page.put("ACTIVITY_IMG",ACTIVITY_IMG);  
				this.actService.updateImgPath(page);
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
			obj.put("statusCode", "200");
			obj.put("message", "操作成功"); 
			return obj.toString();
		}*/
}


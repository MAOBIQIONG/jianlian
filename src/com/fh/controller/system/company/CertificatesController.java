package com.fh.controller.system.company;


import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.system.company.CertificatesService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.notification.SysNotificationService;
import com.fh.service.system.user.TbUserService;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.PushUtil;
import com.fh.util.SystemLog;
import com.fh.util.fileConfig;
import com.fh.util.excelexception.Exceldworup;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.ping.PingConfig;

@Controller
@RequestMapping({ "/Certifi" })
public class CertificatesController extends BaseController {
	String menuUrl = "Certifi/listCertifi.do";

	@Resource(name = "certificatesservice")
	private CertificatesService certificatesservice;
	
	@Resource(name = "tbuserService")
	private TbUserService tbuserService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService; 
	
	@Resource(name="sysNotificationService")
	private SysNotificationService sysNotifService;
	
	@Resource(name = "appusersService")
	private AppusersService appusersService;
	
	//上传文件存放路径
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	 
		@RequestMapping({ "/querycertifi" })
		public ModelAndView querycertifi()throws Exception{
			ModelAndView mv = getModelAndView(); 
			PageData pd=null;
			this.certificatesservice.updatedelhd(pd);
			mv.setViewName("system/company/certificates"); 
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
			page.setPd(pd);
			page.setOrderDirection(sSortDir_0);
			page.setOrderField(sortName);
			page.setPageSize(Integer.valueOf(iDisplayLength));
			page.setPageCurrent(Integer.valueOf(iDisplayStart));
			int initEcho = Integer.parseInt(sEcho) + 1;  
			 
			List<PageData> dataList = this.certificatesservice.listByParam(page);
			Object	total = this.certificatesservice.findCount(page).get("counts");//查询记录的总行数
			 
			getObj.put("sEcho", initEcho);// 不知道这个值有什么用
			getObj.put("iTotalRecords", total);//实际的行数
			getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
			for(PageData data:dataList){  
				 data.put("DATE",data.get("DATE")==null?"":data.get("DATE").toString());
			} 
			getObj.put("aaData", dataList);//要以JSON格式返回
			return getObj.toString();
		} 
		
		/*@RequestMapping({ "/addcertifi" })
		@ResponseBody
		public String addcertifi()throws Exception{
			PageData pd = new PageData();
			try { 
					pd = getPageData();
					PageData data=new PageData();
					data.put("ID",pd.getString("USER_ID"));
					PageData user=this.tbuserService.queryById(data);
					pd.put("COMPANY_ID",user.getString("COMPANY_ID")); 
					pd.put("DATE", new Date()); 
				   if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){ //添加
					  	pd.put("ID", get32UUID()); 
					  	//pd.put("IMG_PATH", get32UUID());  
			    		this.certificatesservice.addcertifi(pd);
		    	   }else{  //修改 
		    	 	   this.certificatesservice.upcertifi(pd);
		    	   } 
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
				JSONObject getObj = new JSONObject();
				getObj.put("statusCode", 200);
				return getObj.toString();
		}
		*/
		
		//查看证件
		@RequestMapping({ "/look" })
		public ModelAndView look()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData(); 
			PageData Cert=this.certificatesservice.querycertifiid(pd);
			if("4".equals(Cert.getString("CERTIFICATE_TYPE"))){
				String imgs=Cert.getString("IMG_PATH");
				if(imgs!=null&&!"".equals(imgs)){
					String[] img_path=imgs.split(",");
					mv.addObject("img_path", img_path); 
					Cert.put("is_extra",1);//第四类非空的
				}else{
					Cert.put("is_extra",0);//第四类空的
				}
			}else{
				Cert.put("is_extra",0);//不是第四类
			}
			mv.setViewName("system/company/look-Cert");
			mv.addObject("CertData", Cert); 
			return mv;
		}
				
		//通过ID获取数据
		@RequestMapping({ "/querycertifiid" })
		public ModelAndView querycertifiid()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			List userList = this.tbuserService.querytbuser(null);
			List typeList=this.dicService.queryByPBM("certificatetype"); 
			mv.addObject("userList", userList); 
			mv.addObject("typeList", typeList); 
			PageData Cert=this.certificatesservice.querycertifiid(pd);
			mv.setViewName("system/company/add-Cert");
			mv.addObject("CertData", Cert); 
			return mv;
		}
		
		//通过ID到审核
		@RequestMapping({ "/querycertifish" })
		public ModelAndView querycertifish()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			List userList = this.tbuserService.querytbuser(null);
			List typeList=this.dicService.queryByPBM("certificatetype"); 
			mv.addObject("userList", userList); 
			mv.addObject("typeList", typeList); 
			PageData Cert=this.certificatesservice.querycertifiid(pd);
			mv.setViewName("system/company/Cert-reviewed");
			mv.addObject("CertData", Cert); 
			return mv;
		}
		
		//批量删除
		@RequestMapping({ "/delcert" })
		@SystemLog(methods="实名认证管理",type="批量删除")
		@ResponseBody
		public String delcert() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				String dasid = pd.getString("ID");
				String[] auserId= dasid.split(",");
				this.certificatesservice.delcert(auserId);
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
		
		@RequestMapping({ "/delcertbyid" })
		@SystemLog(methods="实名认证管理",type="删除")
		@ResponseBody
		public JSONObject delcertbyid() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
				//this.projectService.delete(pd);
				this.certificatesservice.delcertbyid(pd);
				obj.put("statusCode", "200");
				obj.put("message", "操作成功");
				obj.put("closeCurrent", false);
			    	
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
			return obj;
		}
		
		//根据证件名称进行查找
		@RequestMapping({ "/querycertname" })
		public String querycertname()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			List dataList=this.certificatesservice.querycertname(pd);
			mv.setViewName("system/company/certificates");
			mv.addObject("certimap", dataList);
			return "redirect:/Certifi/querycertifi";
		}
		
		//审核
		@RequestMapping({ "/upstatus" })
		@SystemLog(methods="实名认证管理",type="审核")
		@ResponseBody
		public String upstatus() { 
			try {
				PageData pd = getPageData();
				pd.put("DATE",new Date());
				this.certificatesservice.upcertifi(pd);
				PageData notif=new PageData();
    	 		notif.put("ID", get32UUID());
    	 		
    	 		String content = "";
    	 		if("02".equals(pd.getString("STATUS"))){
    	 			content = "您上传的"+pd.getString("CERTIFICATE_NAME")+"证件审核未通过！";
    	 			notif.put("CONTENT",content);
    	 			notif.put("DESCRIPTION",pd.getString("NOPASSREASON")); 
    	 		}else{
    	 			content = "您上传的"+pd.getString("CERTIFICATE_NAME")+"证件审核已通过！";
    	 			notif.put("CONTENT",content);
    	 			notif.put("DESCRIPTION",pd.getString("NOPASSREASON")); 
    	 		} 
    	 		notif.put("USER_ID", pd.getString("USER_ID"));
    	 		notif.put("CREATE_DATE", new Date());
    	 		notif.put("TABLE_NAME","certificates");
    	 		notif.put("OBJECT_ID",pd.getString("ID"));
    	 		notif.put("STATUS",pd.getString("STATUS")); 
    	 		sysNotifService.save(notif);
    	 		
    	 		//推送消息
    	 		PageData pagedata = new PageData();
    	 		pagedata.put("ID", pd.getString("USER_ID"));
				PageData user = this.appusersService.queryById(pagedata);
    	 		if( user != null ){
    	 			//推送审核消息
//					NotificationTemplate template = null;
//					if( user.getString("PLATFORM") == "1" ){
//						template = PushUtil.notificationTemplateDemo(); 
//						template.setTitle("上海建联");
//						template.setText(content);
//					}
    	 			String jsonStr = "{'type':'notice','title':'上海建联','content':'"+content+"'}";//透传内容
					TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
					PushUtil pushApp=new PushUtil();
					String alias = pd.getString("USER_ID");
					pushApp.pushToSingle(tmTemplate,alias); 
    	 		}
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
			JSONObject getObj = new JSONObject();
			getObj.put("statusCode",200); 
			return getObj.toString();
		}
		
		@RequestMapping({ "/addcertifi" })
		@ResponseBody 
		public JSONObject addcertifi(MultipartHttpServletRequest request) throws Exception {
			JSONObject obj = new JSONObject();
			PageData pd = new PageData();  
			
			MultipartFile file=request.getFile("IMG_PATH");
			String ID=request.getParameter("ID"); 
			String USER_ID=request.getParameter("USER_ID");  
			String CERTIFICATE_TYPE=request.getParameter("CERTIFICATE_TYPE");
			String CERTIFICATE_NAME=new String(request.getParameter("CERTIFICATE_NAME").getBytes("ISO_8859-1"),"UTF-8");
			
			PageData data = new PageData();
			data.put("ID",USER_ID);
			PageData user=this.tbuserService.queryById(data);
			
			pd.put("USER_ID", USER_ID);
			pd.put("STATUS", "01");//未审核
	   		pd.put("CERTIFICATE_NAME", CERTIFICATE_NAME); 
	   		pd.put("CERTIFICATE_TYPE", CERTIFICATE_TYPE);
			pd.put("COMPANY_ID",user.getString("COMPANY_ID")); 
			pd.put("DATE", new Date());  
			 
			if ((file != null) && (!file.isEmpty())) {
				String filePath =serverBasePath; 
				String fileName = FileUpload.fileUp(file,filePath,get32UUID());
				pd.put("IMG_PATH", fileName); 
			}  
			if(ID==null||"".equals(ID)){ //添s加
			  	pd.put("ID", get32UUID());  
	    		this.certificatesservice.addcertifi(pd);
    	    }else{  //修改 
    		   pd.put("ID",ID);
    	 	   this.certificatesservice.upcertifi(pd);
    	    } 
			obj.put("statusCode", 200);  
			return obj;
		}  
		
		//获取当前登录用户
		public User getUser(){
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession(); 
			User user = (User) session.getAttribute("sessionUser"); 
			return user;
		}
		
		@RequestMapping(value = "exportfeedback")
	    @ResponseBody
	    public String exportFeedBack(HttpServletResponse response) throws Exception{
	    	JSONObject getObj = new JSONObject();
	        String fileName = "实名认证信息"+System.currentTimeMillis()+".xls"; //文件名 
	        String sheetName = "实名认证信息";//sheet名 
	        String []title = new String[]{"会员名称","公司名称","证件名称","时间","状态"};//标题
	        PageData pd=null;
	        List<PageData> list = this.certificatesservice.doexlelist(pd);//内容list
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	        String [][]values = new String[list.size()][];
	        for(int i=0;i<list.size();i++){
	            values[i] = new String[title.length];
	            //将对象内容转换成string
	            PageData obj = list.get(i);  
	            values[i][0] = obj.getString("REAL_NAME")+"";
	            values[i][1] = obj.getString("COMPANY_NAME");
	            values[i][2] = obj.getString("CERTIFICATE_NAME");
	            values[i][3] = sdf.format(DateFormat.getDateInstance().parse((String) obj.get("DATE")));  
	            values[i][4] = obj.getString("STATUS_NAME"); 
	             
	            
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


package com.fh.controller.system.press;


import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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

import net.sf.json.JSONObject;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.notification.SysNotificationService;
import com.fh.service.system.press.Jl_app_mediaService;
import com.fh.service.system.press.PressService;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.PushUtil;
import com.fh.util.ResultUtil;
import com.fh.util.SystemLog;
import com.fh.util.fileConfig;
import com.fh.util.excelexception.Exceldworup;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/Media" })
public class Jl_app_mediaController extends BaseController {
	String menuUrl = "Media/listMedia.do";

	@Resource(name = "jl_app_mediaservice")
	private Jl_app_mediaService jl_app_mediaservice;
	
	@Resource(name="sysNotificationService")
	private SysNotificationService sysNotifService;
	
	//上传文件存放路径
	public static String videosBasePath = fileConfig.getString("videosBasePath");
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/press/media");
		return mv;
	}
	//查询所有公司信息
		@RequestMapping({ "/querymedia" })
		@ResponseBody
		public String querymedia(Page page)throws Exception{
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
			for (int i = 0; i < jsonArray.size(); i++) {  
			    try{  
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
			
			List<PageData> datas = this.jl_app_mediaservice.listByParam(page);
			Object  total = this.jl_app_mediaservice.findCount(page).get("counts");//查询记录的总行数
			getObj.put("sEcho", initEcho);// 不知道这个值有什么用
			getObj.put("iTotalRecords", total);//实际的行数
			getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
			for(PageData data:datas){
				ResultUtil.resetRes(data,new String[]{"PUBLISH_DATE","CREATE_DATE","MODIFY_DATE"});
			}
			getObj.put("aaData", datas);//要以JSON格式返回
			return getObj.toString();
		}
		
		
		@RequestMapping({ "/querymediaid" })
		public ModelAndView querymediaid()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData(); 
			PageData pressmap=this.jl_app_mediaservice.querymediaid(pd);
			List<PageData> lanlist=this.jl_app_mediaservice.querybybhname(pd);
			mv.setViewName("system/press/add-Media");
			mv.addObject("lanlist", lanlist); 
			mv.addObject("mediaData", pressmap); 
			return mv;
		}
		
		@RequestMapping({ "/addmediaImgsAndVedios" })
		@SystemLog(methods="新闻管理",type="编辑")
		@ResponseBody
		public JSONObject addmediaImgsAndVedios(MultipartHttpServletRequest request)throws Exception{
			JSONObject obj = new JSONObject();
			PageData pd = new PageData(); 
			pd = getPageData();
			MultipartFile file=request.getFile("VIDEOS");
			MultipartFile IMGS=request.getFile("IMGS");  
			String ID=request.getParameter("ID"); 
			pd.put("TITLE", request.getParameter("TITLE")); 
			pd.put("PUBLISH_DATE", request.getParameter("PUBLISH_DATE")); 
			pd.put("TYPE_ID", request.getParameter("TYPE_ID")); 
			pd.put("LINK_URL", request.getParameter("LINK_URL")); 
			pd.put("COLUMN_ID", request.getParameter("COLUMN_ID")); 
			pd.put("IS_TOP", request.getParameter("IS_TOP")); 
			pd.put("RESOURCE", request.getParameter("RESOURCE")); 
			pd.put("SUMMARY", request.getParameter("SUMMARY")); 
			pd.put("CONETENT", request.getParameter("CONETENT")); 
			pd.put("MORS", request.getParameter("MORS"));  
			
			pd.put("IS_RECOMMEND", 1); 
			pd.put("COMMENT_COUNT", 0); 
			pd.put("COLLECT_COUNT", 0); 
			pd.put("SHARE_COUNT", 0);  
			if ((file != null) && (!file.isEmpty())) {
				String filePath =videosBasePath; 
				String fileName = FileUpload.fileUp(file,filePath,get32UUID());
				pd.put("VIDEOS", fileName); 
			} 
			if ((IMGS != null) && (!IMGS.isEmpty())) {
				String filePath =serverBasePath; 
				String fileName = FileUpload.fileUp(IMGS,filePath,get32UUID());
				pd.put("IMGS", fileName); 
			} 
			if(ID==null||"".equals(ID)){ //添加
				pd.put("ID", get32UUID());
			  	pd.put("CREATE_DATE",new Date());
			  	pd.put("CREATE_BY", getUser().getID());
			  	this.jl_app_mediaservice.addmedia(pd); 
    	    }else{  //修改 
    	    	pd.put("MODIFY_DATE",new Date());
			  	pd.put("MODIFY_BY", getUser().getID());
			  	pd.put("ID",ID);
    		   this.jl_app_mediaservice.upmedia(pd);
    	    } 
			obj.put("ID",pd.getString("ID"));  
			obj.put("statusCode", 200);  
			return obj;
		}
		
		@RequestMapping({ "/addmedia" }) 
		@ResponseBody
		public JSONObject addmedia()throws Exception{
			JSONObject obj = new JSONObject();
			PageData pd = new PageData(); 
			pd = getPageData(); 
		    this.jl_app_mediaservice.upmedia(pd);  
			obj.put("statusCode", 200);  
			return obj;
		}
		
		//批量删除
		@RequestMapping({ "/delmedia" })
		@SystemLog(methods="新闻管理",type="批量删除")
		@ResponseBody
		public String delmedia() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
				//this.projectService.delete(pd);
				String dasid = pd.getString("ID");
				String[] auserId= dasid.split(",");
				this.jl_app_mediaservice.delmedia(auserId);
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
		
		//根据图片编号进行查找
		@RequestMapping({ "/querymediatitle" })
		public String querymediatitle()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			List dataList=this.jl_app_mediaservice.querymediatitle(pd);
			mv.setViewName("system/press/media");
			mv.addObject("mediamap", dataList);
			return "redirect:/Media/querymedia";
		}
		
		//推荐/不推荐
		@RequestMapping({ "/upISMMEND" })
		@ResponseBody
		public String upISMMEND() {
			PageData page = new PageData();
			try {
				PageData pd = getPageData();
				PageData pagedata=(PageData) this.jl_app_mediaservice.querymediaid(pd);
				if("1".equals(pagedata.get("IS_RECOMMEND").toString())){ 
					page.put("IS_RECOMMEND", 2);//关闭
				}
				else if("2".equals(pagedata.get("IS_RECOMMEND").toString())){
					page.put("IS_RECOMMEND", 1); //显示
				}
				page.put("ID", pagedata.getString("ID")); 
				this.jl_app_mediaservice.upISMMEND(page);
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
			JSONObject getObj = new JSONObject();
			getObj.put("statusCode",200);
			System.out.println(getObj.get("statusCode"));
			return getObj.toString();
		}
		
		//置顶/不置顶
		@RequestMapping({ "/upISTOP" })
		@SystemLog(methods="新闻管理",type="修改置顶状态")
		@ResponseBody
		public String upISTOP() {
			PageData page = new PageData();
			try {
				PageData pd = getPageData();
				PageData pagedata=(PageData) this.jl_app_mediaservice.querymediaid(pd);
				if("1".equals(pagedata.get("IS_TOP").toString())){ 
					page.put("IS_TOP", 2);//关闭
				}
				else if("2".equals(pagedata.get("IS_TOP").toString())){
					page.put("IS_TOP", 1); //显示
				}
				page.put("ID", pagedata.getString("ID")); 
				this.jl_app_mediaservice.upISTOP(page);
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
			JSONObject getObj = new JSONObject();
			getObj.put("statusCode",200);
			System.out.println(getObj.get("statusCode"));
			return getObj.toString();
		}
		//推送
		@RequestMapping({ "/topush" })
		@SystemLog(methods="新闻管理",type="单个推送")
		@ResponseBody
		public String topush() { 
			JSONObject getObj = new JSONObject();
			try {
				PageData pd = getPageData();
				String status=pd.getString("status");
				String title=pd.getString("title");
				if(status!=null && "02".equals(status)){
				  String bt="上海建联官方";
				  String nr=title+"新闻发布成功，快去了解吧";
				  String jsonStr = "{'type':'media','ID':'"+pd.getString("ID")+"','title':'"+bt+"','content':'"+nr+"'}";//透传内容
				  TransmissionTemplate tmTemplates = PushUtil.transmissionTemplateDemo(bt,nr,jsonStr);
   				  PushUtil pushApp=new PushUtil();

     			  
     			 //存储消息内容
 				 /*PageData notif=new PageData();
 		 		 notif.put("CREATE_DATE", new Date());
 		 		 notif.put("TABLE_NAME","");
 		 		 notif.put("OBJECT_ID","");
 		 		 notif.put("STATUS","01"); 
 		 		 notif.put("CONTENT",nr);
     			 notif.put("ID", get32UUID());
     		     notif.put("USER_ID", "jianlian");//默认为系统通知消息
     		     Integer val = (Integer)sysNotifService.save(notif);
     		     if( val > 0 ){*/
     		    	 String result=pushApp.pushToAll(tmTemplates);
         		     System.out.println("向全部用户推送："+result.toString());
     		     //}
				}else{
					getObj.put("statusCode", "400");
					System.out.println("单个推送新闻失败");
				}
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			} 
			getObj.put("statusCode",200);
			System.out.println(getObj.get("statusCode"));
			return getObj.toString();
		}
		//根据ID进行删除
		@RequestMapping({ "/delmediaByid" })
		@SystemLog(methods="新闻管理",type="删除")
		@ResponseBody
		public JSONObject delmediaByid() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
				//this.projectService.delete(pd);
				this.jl_app_mediaservice.delmediaByid(pd);
				obj.put("statusCode", "200");
				obj.put("message", "操作成功");
				obj.put("closeCurrent", false);
			    	
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
			return obj;
		}
		
		//上下线新闻
		@RequestMapping({ "/updates" })
		@SystemLog(methods="新闻管理",type="新闻上下线")
		@ResponseBody
		public JSONObject updates() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				String status=pd.getString("status");
				String title=pd.getString("title");
				String MORS=pd.getString("MORS");
				this.jl_app_mediaservice.updates(pd);
				if("1".equals(MORS)){
				  String bt="上海建联官方";
				  String nr=title+"新闻发布成功，快去了解吧";
				  String jsonStr = "{'type':'media','ID':'"+pd.getString("ID")+"','title':'"+bt+"','content':'"+nr+"'}";//透传内容
				  TransmissionTemplate tmTemplates = PushUtil.transmissionTemplateDemo(bt,nr,jsonStr);
   				  PushUtil pushApp=new PushUtil();
     			  
     			 //存储消息内容
  				/* PageData notif=new PageData();
  		 		 notif.put("CREATE_DATE", new Date());
  		 		 notif.put("TABLE_NAME","");
  		 		 notif.put("OBJECT_ID","");
  		 		 notif.put("STATUS","01"); 
  		 		 notif.put("CONTENT",nr);
      			 notif.put("ID", get32UUID());
      		     notif.put("USER_ID", "jianlian");//默认为系统通知消息
      		     Integer val = (Integer)sysNotifService.save(notif);
      		     if( val > 0 ){*/
      		    	String result=pushApp.pushToAll(tmTemplates);
         		    System.out.println("向全部用户推送："+result.toString()); 
      		     //}
				}
				obj.put("statusCode", "200"); 
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
			return obj;
		}
		
		//获取当前登录用户
		public User getUser(){
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession(); 
			User user = (User) session.getAttribute("sessionUser"); 
			return user;
		} 
		
	//查看媒体图片
	@RequestMapping({ "/look" })
	public ModelAndView look()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData(); 
		PageData mes=this.jl_app_mediaservice.querymediaid(pd);
		mv.setViewName("system/press/look-Media");
		mv.addObject("kanData", mes); 
		return mv;
	} 
	
	@RequestMapping(value = "exportfeedback")
    @ResponseBody
    public String exportFeedBack(HttpServletResponse response) throws Exception{
    	JSONObject getObj = new JSONObject();
        String fileName = "媒体信息"+System.currentTimeMillis()+".xls"; //文件名 
        String sheetName = "媒体信息";//sheet名  
        String []title = new String[]{"标题","显示发布人","发布时间","类型（视频、文字）","栏目ID","是否推荐","是否置顶","类别","状态"};//标题
        PageData pd=null;
        List<PageData> list = this.jl_app_mediaservice.doexlelist(pd);//内容list
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String [][]values = new String[list.size()][];
        for(int i=0;i<list.size();i++){
            values[i] = new String[title.length];
            //将对象内容转换成string
            PageData obj = list.get(i);  
            values[i][0] = obj.getString("TITLE")+"";
            values[i][1] = obj.getString("UNAME");
            values[i][2] = sdf.format(obj.get("PUBLISH_DATE"));
            values[i][3] = obj.getString("SHOW_SP");
            values[i][4] = obj.getString("TC_NAME");
            values[i][5] = obj.getString("SHOW_TJ");
            values[i][6] = obj.getString("SHOW_ZD");
            
            
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


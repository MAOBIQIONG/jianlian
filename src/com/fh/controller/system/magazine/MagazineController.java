package com.fh.controller.system.magazine;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.fh.service.system.magazine.JlqkService; 
import com.fh.util.FileUpload;
import com.fh.util.PageData; 
import com.fh.util.SystemLog;
import com.fh.util.fileConfig;

@Controller
@RequestMapping({ "/magazine" })
public class MagazineController extends BaseController {
	
	@Resource(name = "jlqkService")
	private JlqkService jlqkService;  
	
	//上传文件存放路径
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/magazine/magazine_list");
		return mv;
	} 
	
	@RequestMapping(value={"/queryList"}) 
	@ResponseBody
	public String queryList(Page page) throws Exception {
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
		for (int i = 0; i < jsonArray.size(); i++){  
		    try {  
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
		List<PageData> dataList =null;
		Object  total =null;//查询记录的总行数 
		dataList = this.jlqkService.listByParam(page);
		total = this.jlqkService.findCount(page).get("counts");//查询记录的总行数 
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData data:dataList){  
			 data.put("CREATE_DATE",data.get("CREATE_DATE")==null?"":data.get("CREATE_DATE").toString()); 
		} 
		getObj.put("aaData", dataList);//要以JSON格式返回
		return getObj.toString();
	}   
	 
	@RequestMapping({ "/goEdit" })
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			pd = this.jlqkService.queryById(pd);  
			mv.addObject("pd",pd);  
		} catch (Exception e) { 
			e.printStackTrace();
		}  
		mv.setViewName("system/magazine/magazine_edit");
		return mv;
	}
	 
	 @RequestMapping({ "/edit" })
	 @ResponseBody 
	 public JSONObject edit(MultipartHttpServletRequest request) throws Exception {
		JSONObject obj = new JSONObject();
		PageData pd = new PageData();  
		
		MultipartFile file=request.getFile("COVER_PATH");
		String ID=request.getParameter("ID"); 
		String TITLE=new String(request.getParameter("TITLE").getBytes("ISO_8859-1"),"UTF-8"); 
		String VERSION=new String(request.getParameter("VERSION").getBytes("ISO_8859-1"),"UTF-8");
		String QK_PATH=new String(request.getParameter("QK_PATH").getBytes("ISO_8859-1"),"UTF-8");
		String ABOUT=new String(request.getParameter("ABOUT").getBytes("ISO_8859-1"),"UTF-8");
		pd.put("USER_ID", getUser().getID()); 
   		pd.put("TITLE", TITLE); 
   		pd.put("VERSION", VERSION);
		pd.put("QK_PATH",QK_PATH); 
		pd.put("ABOUT",ABOUT);  
		pd.put("CREATE_DATE", new Date());  
		 
		if ((file != null) && (!file.isEmpty())) {
			String filePath =serverBasePath; 
			String fileName = FileUpload.fileUp(file,filePath,get32UUID());
			pd.put("COVER_PATH", fileName); 
		}  
		if(ID==null||"".equals(ID)){ //添s加
		  	pd.put("ID", get32UUID());  
    		this.jlqkService.save(pd);
	    }else{  //修改 
		   pd.put("ID",ID);
	 	   this.jlqkService.edit(pd);
	    } 
			obj.put("statusCode", 200);  
			return obj;
		}  
	 
	    @RequestMapping({ "/delete" })
		@SystemLog(methods="期刊管理",type="删除")
		@ResponseBody
		public JSONObject delete() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData(); 
				this.jlqkService.delete(pd);
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
} 
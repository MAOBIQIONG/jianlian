package com.fh.controller.system.sxy;

import io.rong.models.CodeSuccessResult;

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
import com.fh.service.system.sxy.sxyService; 
import com.fh.util.FileUpload;
import com.fh.util.PageData; 
import com.fh.util.PushUtil;
import com.fh.util.ResultUtil;  
import com.fh.util.SystemLog;
import com.fh.util.fileConfig;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/sxypro" })
public class SxyController extends BaseController {  
	//上传文件存放路径
	public static String videosBasePath = fileConfig.getString("videosBasePath");
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	
	@Resource(name = "sxyService")
	private sxyService sxyService;
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/sxy/sxy_index_list");
		return mv;
	}
	 
	
	
	//去添加教授页面
	@RequestMapping({ "/addjs" })
	public ModelAndView addjs()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();  
		mv.setViewName("system/sxy/sxy_js_add");
		mv.addObject("pd", pd); 
		return mv;
	}
	
	//去编辑教授页面
	@RequestMapping({ "/toeditjs" })
	public ModelAndView toeditjs()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();  
		PageData mes=this.sxyService.findjsById(pd);
		mv.setViewName("system/sxy/sxy_js_edit"); 
		mv.addObject("editData", mes); 
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
		
		List<PageData>	projectsList = this.sxyService.queryByStatus(page);
		Object total = this.sxyService.findCountByStatus(page).get("counts");//查询记录的总行数 
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData pro:projectsList){ 
			ResultUtil.resetRes(pro,new String[]{"CREATE_DATE"}); 
		} 
		getObj.put("aaData", projectsList);//要以JSON格式返回
		return getObj.toString();
	}   
	
	
	 @RequestMapping({"/toAdd"})
	 public ModelAndView toAdd(){ 
		ModelAndView mv = getModelAndView();    
	    mv.setViewName("system/sxy/sxy_add"); 
	    return mv;
	 }  
	 
	 
	 
	 @RequestMapping({"/save"})
	 @SystemLog(methods="商学院管理",type="商学院添加、编辑")
	 @ResponseBody
	 public JSONObject save(MultipartHttpServletRequest request)throws Exception{ 
		 JSONObject obj = new JSONObject();
	     PageData pd = new PageData();  
	     try { 
	    	 String groupName = "";
	    	 MultipartFile COVER_PATH=request.getFile("COVER_PATH");  
	    	 
	    	 pd=getPageData();  
	    	 if ((COVER_PATH != null) && (!COVER_PATH.isEmpty())) {
				String filePath =serverBasePath; 
				String fileName = FileUpload.fileUp(COVER_PATH,filePath,get32UUID());
				pd.put("COVER_PATH", fileName); 
			} 
	    	 pd.put("TITLE", request.getParameter("TITLE")); 
	    	 pd.put("CONETENT", request.getParameter("CONETENT")); 
	    	 ResultUtil.resetResInt(pd,new String[]{"TITLE","CONETENT"});  
	         PageData data = new PageData();//用来保存返回信息  
	       if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){//新增  
	    		pd.put("ID",get32UUID());//新增时生成32位随机数
	    		pd.put("STATUS","02");//待审核
	    		pd.put("COMMENT_COUNT",0);//评论总数
	    		pd.put("SHARE_COUNT",0);//分享总数
	    		pd.put("TYPE",0);//(0商学院，1文章)
	    		pd.put("LIKE_COUNT",0);//点赞总数
	    		pd.put("COLLECT_COUNT",0);//收藏总数
	    		pd.put("VIEW_COUNT",0);//浏览量
	    		pd.put("CREATE_BY",getUser().getID()); //创建人
	    		pd.put("CREATE_DATE",new Date()); //创建时间  
	    		//保存商学院信息
	    		Object ob=this.sxyService.save(pd); 
    	  }else{//修改 
	    	 	 data.put("ID", pd.getString("ID")); 
		   		 data.put("TITLE",pd.getString("TITLE")); 
		   		data.put("CONETENT",pd.getString("CONETENT")); 
		   		data.put("COVER_PATH",pd.getString("COVER_PATH")); 
		   		data.put("MODIFY_BY",getUser().getID());//修改人
		   		data.put("MODIFY_DATE",new Date());//修改时间
		   		 //修改项目反馈信息
				 this.sxyService.edit(data);   
    	  	  }  
		   } catch (Exception e) {
		        this.logger.error(e.toString(), e); 
		   }  
	     	obj.put("ID",pd.getString("ID"));  
			obj.put("statusCode", 200);  
			return obj;
	   } 
	 
	@RequestMapping({ "/goEdit" })
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			pd = this.sxyService.findById(pd);   
			mv.addObject("pd", pd); 
			mv.addObject("msg", "edit");
		    mv.setViewName("system/sxy/sxy_edit");  
		} catch (Exception e) { 
			e.printStackTrace();
		}  
		return mv;
	}
	 
	 
	//上下线商学院
	@RequestMapping({ "/updates" })
	@SystemLog(methods="商学院管理",type="商学院上下线")
	@ResponseBody
	public JSONObject updates() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			pd = getPageData(); 
			this.sxyService.updates(pd);
			obj.put("statusCode", "200");
		    	
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
		return obj;
	}
	
	//根据ID进行删除
	@RequestMapping({ "/delsxyByid" })
	@SystemLog(methods="商学院管理",type="删除")
	@ResponseBody
	public JSONObject delsxyByid() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject(); 
		try {
			pd = getPageData(); 
			try {
				this.sxyService.delsxyByid(pd);
				this.sxyService.gldel(pd);
			} catch (Exception e) {
				obj.put("statusCode", "400");
				obj.put("message", "操作失败");
			}
			
			obj.put("statusCode", "200");
			obj.put("message", "操作成功");
			obj.put("closeCurrent", false);
		    	
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
		return obj;
	}
	
	 
	
	//查看封面图片
	@RequestMapping({ "/look" })
	public ModelAndView look()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData(); 
		PageData mes=this.sxyService.findById(pd);
		mv.setViewName("system/sxy/look_Sxy");
		mv.addObject("kanData", mes); 
		return mv;
	}
	
	/**
	 * 跳教授列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/tojslist"})
	public ModelAndView tojslist() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();   
		mv.addObject("SXY_ID",pd.get("ID"));  
		mv.setViewName("system/sxy/sxy_js_list");
		return mv;
	}
	
	@RequestMapping(value={"/queryjsList"}) 
	@ResponseBody
	public String queryjsList(Page page) throws Exception {
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
		
		List<PageData>	projectsList = this.sxyService.queryByStatus1(page);
		Object total = this.sxyService.findCountByStatus1(page).get("counts");//查询记录的总行数 
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData pro:projectsList){ 
			ResultUtil.resetRes(pro,new String[]{"CREATE_DATE"}); 
		} 
		getObj.put("aaData", projectsList);//要以JSON格式返回
		return getObj.toString();
	}   
	
	
	//根据ID删除教授
	@RequestMapping({ "/deljsByid" })
	@SystemLog(methods="教授管理",type="删除")
	@ResponseBody
	public JSONObject deljsByid() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			pd = getPageData(); 
			this.sxyService.deljsByid(pd);
			obj.put("statusCode", "200");
			obj.put("message", "操作成功");
			obj.put("closeCurrent", false);
		    	
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
		return obj;
	}
	
	 @RequestMapping({"/jssave"})
	 @SystemLog(methods="教授管理",type="教授添加、编辑")
	 @ResponseBody
	 public JSONObject jssave(MultipartHttpServletRequest request)throws Exception{ 
		 JSONObject obj = new JSONObject();
	     PageData pd = new PageData();  
	     try {  
	    	 MultipartFile PRO_IMG=request.getFile("PRO_IMG");  
	    	 MultipartFile PRO_DETAILS_IMG=request.getFile("PRO_DETAILS_IMG");  
	    	 pd=getPageData();  
	    	 if ((PRO_IMG != null) && (!PRO_IMG.isEmpty())) {
				String filePath =serverBasePath; 
				String fileName = FileUpload.fileUp(PRO_IMG,filePath,get32UUID());
				pd.put("PRO_IMG", fileName); 
			} 
	    	 
	    	 if ((PRO_DETAILS_IMG != null) && (!PRO_DETAILS_IMG.isEmpty())) {
					String filePath =serverBasePath; 
					String fileName = FileUpload.fileUp(PRO_DETAILS_IMG,filePath,get32UUID());
					pd.put("PRO_DETAILS_IMG", fileName); 
				} 
	    	 pd.put("PRO_NAME", request.getParameter("PRO_NAME")); 
	    	 pd.put("PRO_TITLE", request.getParameter("PRO_TITLE")); 
	    	 ResultUtil.resetResInt(pd,new String[]{"PRO_NAME","PRO_TITLE"});  
	         PageData data = new PageData();//用来保存返回信息  
	       if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){//新增  
	    		pd.put("ID",get32UUID());//新增时生成32位随机数  
	    		pd.put("CREATE_BY",getUser().getID()); //创建人
	    		pd.put("CREATE_DATE",new Date()); //创建时间  
	    		//保存教授信息
	    		Object ob=this.sxyService.jssave(pd); 
	    		if(Integer.valueOf(ob.toString()) >= 1){
	    			//保存关联表信息
	    			pd.put("SXY_ID", request.getParameter("SXY_ID"));  
	    			pd.put("GID", get32UUID());
	    			Object glob=this.sxyService.glsave(pd);
	    			if(Integer.valueOf(glob.toString()) >= 1){
	    				System.out.println("保存关联表信息成功");
	    			}else{
	    				System.out.println("保存关联表信息失败");
	    				obj.put("statusCode", 400);  
	    			} 
	    		}else{
	    			System.out.println("保存教授信息失败");
	    			obj.put("statusCode", 400);  
	    		}
	       }else{//修改 
	    	 	 data.put("ID", pd.getString("ID")); 
		   		 data.put("PRO_NAME",pd.getString("PRO_NAME")); 
		   		data.put("PRO_TITLE",pd.getString("PRO_TITLE")); 
		   		data.put("PRO_IMG",pd.getString("PRO_IMG")); 
		   		data.put("PRO_DETAILS_IMG",pd.getString("PRO_DETAILS_IMG"));
		   		data.put("MODIFY_BY",getUser().getID());//修改人
		   		data.put("MODIFY_DATE",new Date());//修改时间
		   		 //修改项目反馈信息
				 this.sxyService.jsedit(data);   
    	  	  }  
		   } catch (Exception e) {
		        this.logger.error(e.toString(), e); 
		   }  
	     	obj.put("ID",pd.getString("ID"));  
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
} 
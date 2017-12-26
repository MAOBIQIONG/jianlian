package com.fh.controller.system.recom;

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
import com.fh.service.system.Recom.RecomService;
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
@RequestMapping({ "/recompro" })
public class RecomController extends BaseController {  
	//上传文件存放路径
	public static String videosBasePath = fileConfig.getString("videosBasePath");
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	
	@Resource(name = "recomService")
	private RecomService recomService;
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/Recom/Recom_index_list");
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
		
		List<PageData>	projectsList = this.recomService.queryByStatus(page);
		Object total = this.recomService.findCountByStatus(page).get("counts");//查询记录的总行数 
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
	    mv.setViewName("system/Recom/Recom_add"); 
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
	         pd.put("MODIFY_BY",getUser().getID());//修改人
    		 pd.put("MODIFY_DATE",new Date());//修改时间
	         PageData data = new PageData();//用来保存返回信息  
	       if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){//新增  
	    		pd.put("ID",get32UUID());//新增时生成32位随机数
	    		pd.put("STATUS","02");//待审核
	    		pd.put("COMMENT_COUNT",0);//评论总数
	    		pd.put("SHARE_COUNT",0);//分享总数
	    		pd.put("TYPE",1);//(0商学院，1文章)
	    		pd.put("LIKE_COUNT",0);//点赞总数
	    		pd.put("COLLECT_COUNT",0);//收藏总数
	    		pd.put("VIEW_COUNT",0);//浏览量
	    		pd.put("CREATE_BY",getUser().getID()); //创建人
	    		pd.put("CREATE_DATE",new Date()); //创建时间  
	    		//保存商学院信息
	    		Object ob=this.recomService.save(pd); 
    	  }else{//修改 
	    	 	 data.put("ID", pd.getString("ID")); 
		   		 data.put("TITLE",pd.getString("TITLE")); 
		   		data.put("CONETENT",pd.getString("CONETENT")); 
		   		data.put("COVER_PATH",pd.getString("COVER_PATH")); 
		   		 //修改项目反馈信息
				 this.recomService.edit(data);   
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
			pd = this.recomService.findById(pd);   
			mv.addObject("pd", pd); 
			mv.addObject("msg", "edit");
		    mv.setViewName("system/Recom/Recom_edit");  
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
			this.recomService.updates(pd);
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
			this.recomService.delsxyByid(pd);
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
		PageData mes=this.recomService.findById(pd);
		mv.setViewName("system/Recom/look_Recom");
		mv.addObject("kanData", mes); 
		return mv;
	}
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
} 
package com.fh.controller.system.contractmodel;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.contractmodel.ContractModelService; 
import com.fh.util.DateUtil;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload; 
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.SystemLog;

@Controller
@RequestMapping({ "/contractModel" })
public class ContractModelController extends BaseController {
 
		@Resource(name = "contractModelService")
		private ContractModelService contractModelService;
		
		@RequestMapping
		public ModelAndView list() throws Exception {
			ModelAndView mv = getModelAndView();  
			mv.setViewName("system/contractmodel/list");
			return mv;
		}
		
		@RequestMapping({ "/queryByParam" })
		@ResponseBody
		public String queryByParam(Page page) throws Exception {
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
			 
			String contractType = pd.getString("MODEL_TYPE");
			String contractName = pd.getString("MODEL_NAME");
			String StartDate = pd.getString("StartTime");
			String EndDate = pd.getString("EndTime");
			
			if ((contractType != null) && (!"".equals(contractType))) { 
				//String param=new String(contractType.getBytes("ISO-8859-1"),"UTF-8").trim();
				pd.put("MODEL_TYPE",contractType);
			} 
			if ((contractName != null) && (!"".equals(contractName))) { 
				//String param=new String(contractName.getBytes("ISO-8859-1"),"UTF-8").trim();
				pd.put("MODEL_NAME",contractName);
			} 
			if ((StartDate != null) && (!"".equals(StartDate))) {
				StartDate = StartDate + " 00:00:00";
				pd.put("START_DATE", StartDate);
			}
			if ((EndDate != null) && (!"".equals(EndDate))) {
				EndDate = EndDate + " 00:00:00";
				pd.put("END_DATE", EndDate);
			}
		 
			page.setPd(pd);
			page.setOrderDirection(sSortDir_0);
			page.setOrderField(sortName);
			page.setPageSize(Integer.valueOf(iDisplayLength));
			page.setPageCurrent(Integer.valueOf(iDisplayStart));
			int initEcho = Integer.parseInt(sEcho) + 1;  
			
			List data = this.contractModelService.listByParam(page);
			Object  total = this.contractModelService.findCount(page).get("counts");//查询记录的总行数
			getObj.put("sEcho", initEcho);// 不知道这个值有什么用
			getObj.put("iTotalRecords", total);//实际的行数
			getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
			for(int i=0;i<data.size();i++){
			   java.sql.Date date=(java.sql.Date)((PageData)data.get(i)).get("UPLOAD_DATE"); 
			   if(date==null||"".equals(date)){
				   ((PageData)data.get(i)).put("UPLOAD_DATE","");
			   }else{
				   String UPLOAD_DATE =DateUtil.getTime(new Date(date.getTime()));
				   ((PageData)data.get(i)).put("UPLOAD_DATE",UPLOAD_DATE);
			   } 
			}
			getObj.put("aaData", data);//要以JSON格式返回
			return getObj.toString();
		} 
		
		@RequestMapping({ "/goAdd" })
		public ModelAndView goAdd() throws Exception {
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData(); 
			mv.setViewName("system/contractmodel/edit"); 
			mv.addObject("pd", pd);  
			return mv;
		}
		
		@RequestMapping({ "/goEdit" })
		public ModelAndView goEdit() throws Exception {
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();  
			pd = this.contractModelService.findById(pd);
			mv.setViewName("system/contractmodel/edit"); 
			mv.addObject("pd", pd);  
			return mv;
		}
		
		//合同模板上传
		@RequestMapping(value={"/uploadFile"})
		@SystemLog(methods="合同模板管理",type="上传")
		@ResponseBody
		//@RequestParam(value = "modelFile", required = false) 中value指file的name,如果不一致或不绑定，file为空
		public JSONObject uploadFile( @RequestParam(value = "modelFile", required = false) MultipartFile file,MultipartHttpServletRequest request) throws Exception {
			JSONObject obj = new JSONObject();
			PageData pd = new PageData();
			String fileName="";
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();

			User user = (User) session.getAttribute("sessionUser");
			
			String modelName=new String(request.getParameter("MODEL_NAME").getBytes("ISO_8859-1"),"UTF-8");
			String modelType=new String(request.getParameter("MODEL_TYPE").getBytes("ISO_8859-1"),"UTF-8");
			String modelContent=new String(request.getParameter("FILE_NAME").getBytes("ISO_8859-1"),"UTF-8");
			String Id=request.getParameter("ID"); 
			
			pd.put("MODEL_NAME", modelName);
	   		pd.put("MODEL_TYPE", modelType); 
	   		pd.put("MODEL_NAME", modelName);
	   		pd.put("UPLOAD_BY",user.getNAME());
	   		pd.put("UPLOAD_DATE", new Date());
			
			if ((file != null) && (!file.isEmpty())) {
				String filePath = PathUtil.getClasspath() + "uploadFiles/file/";  
				fileName = FileUpload.fileUp(file, filePath,get32UUID());
				pd.put("FILE_NAME",fileName);
	    		pd.put("FILE_PATH", filePath+fileName);
				if(Id==null||"".equals(Id)){//新增  
		    		pd.put("ID",get32UUID());  
		    		pd.put("MODEL_STATUS","00");
		    		this.contractModelService.saveContractModel(pd);
		    	}else{//修改 
		    		pd.put("ID",Id); 
		    		pd.put("MODEL_STATUS","01");
		    	 	this.contractModelService.updateContractModel(pd);
		    	}  
				 obj.put("statusCode", "200");
				 obj.put("message", "操作成功"); 
			}  
			return obj;  
		}  
		
		@RequestMapping({ "/delete" })
		@SystemLog(methods="合同模板管理",type="删除")
		@ResponseBody
		public JSONObject delete() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData(); 
				this.contractModelService.deleteContractModelById(pd);
				obj.put("statusCode", "200");
				obj.put("message", "操作成功"); 
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
			return obj;
		}
		
		//批量删除
		@RequestMapping({ "/delMulty" })
		@ResponseBody
		public String delMulty() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData(); 
				String Id = pd.getString("ID");
				if(Id==null||"".equals(Id)){
					obj.put("msg", "要删除记录的ID未传入！"); 
				}else{
					if(Id.indexOf(",") >= 0){
						this.contractModelService.delMulty(Id.split(","));
					}else{
						this.contractModelService.deleteContractModelById(pd);
					} 
					obj.put("statusCode", "200");
					obj.put("msg", "操作成功"); 
				} 
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
			return obj.toString();
		} 
		
		//合同模板下载
		@RequestMapping({ "/downFile" })
		@SystemLog(methods="合同模板管理",type="下载")
		public void downFile(HttpServletResponse response) throws Exception {
			PageData pd = new PageData();
			pd = getPageData(); 
			PageData data=contractModelService.findById(pd);
			FileDownload.fileDownload(response,data.getString("FILE_PATH"),data.getString("FILE_NAME"));
		} 
}

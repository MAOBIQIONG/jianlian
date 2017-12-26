package com.fh.controller.system.company;
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
import com.fh.service.system.company.CategoryService;
import com.fh.util.PageData;
import com.fh.util.SystemLog;

@Controller
@RequestMapping({ "/category" })
public class CategoryController extends BaseController {  
	
	@Resource(name = "categoryService")
	private CategoryService cateService; 
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/company/category_list");
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
		 
		String NAME = pd.getString("NAME"); 
		String PID = pd.getString("PID"); 
		if ((NAME != null) && (!"".equals(NAME))) {  
			pd.put("NAME",NAME);
		}
		if ((PID != null) && (!"".equals(PID))) { 
			pd.put("PID",PID.trim());
		}else{ 
			pd.put("PID","0");
		}
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;
		
		List data = this.cateService.listByParam(page);
		Object	total = this.cateService.findCount(page).get("counts");//查询记录的总行数 
		
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样 
		 
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	} 
	
	 
	 @RequestMapping(value={"/save"}) 
	 @SystemLog(methods="行业管理",type="编辑")
	 @ResponseBody
	 public String save()throws Exception{ 
	     PageData pd = new PageData(); 
	     try { 
	    	 pd=getPageData(); 
	    	 String PID=pd.getString("PID");
	    	 if(PID!=null&&!"".equals(PID)){
	    		 pd.put("PID",PID);
	    	 }else{
	    		 pd.put("PID","0");
	    	 }
		     if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){//新增  
		    	 pd.put("ID",get32UUID()); 
		    	 this.cateService.save(pd); 
	    	 }else{//修改    
	    	 	 this.cateService.edit(pd); 
	    	 } 
	     } catch (Exception e) {
	           this.logger.error(e.toString(), e); 
	     }  
	    JSONObject getObj = new JSONObject();
		getObj.put("statusCode", "200");//
		return getObj.toString(); 
	   }
	 
	 @RequestMapping({ "/toChired" })
	 public ModelAndView toChired() throws Exception {
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			mv.setViewName("system/company/chiredlist");
			mv.addObject("PID", pd.get("PID"));
			return mv;
	}
	 
	@RequestMapping({ "/toEdit" })
	public ModelAndView toEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			pd = this.cateService.findById(pd);    
			mv.addObject("pd", pd); 
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		mv.addObject("msg", "edit");
		mv.setViewName("system/company/category_edit"); 
		return mv;
	} 
	
	@RequestMapping({ "/toAdd" })
	public ModelAndView toAdd() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		mv.addObject("PID", pd.getString("PID"));
		mv.setViewName("system/company/category_add");  
		return mv;
	}
	
	@RequestMapping({ "/delete" })
	@SystemLog(methods="行业管理",type="删除")
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData(); 
			this.cateService.delete(pd); 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	} 
} 
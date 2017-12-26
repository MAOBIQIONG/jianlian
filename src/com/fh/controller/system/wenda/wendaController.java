package com.fh.controller.system.wenda;
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
import com.fh.service.system.wenda.JlHdService;
import com.fh.service.system.wenda.JlWtService;
import com.fh.util.PageData;
import com.fh.util.SystemLog;

@Controller
@RequestMapping({ "/question" })
public class wendaController extends BaseController {
	 
	@Resource(name = "questionService")
	private JlWtService wtService; 
	
	@Resource(name = "answerService")
	private JlHdService hdService;  

	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/wenda/list");
		return mv;
	}
	
	@RequestMapping({ "/queryByParam" })
	@ResponseBody
	public String queryByParam(Page page)throws Exception{
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
		
		List<PageData> data = this.wtService.listByParam(page);
		Object  total = this.wtService.findCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		for(PageData wd:data){ 
		   Object date=wd.get("CREATE_DATE");  
		   if(date==null||"".equals(date)){
			   wd.put("CREATE_DATE","");
		   }else{ 
			   wd.put("CREATE_DATE",date.toString());
		   } 
		}
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	}
	
	@RequestMapping({ "/delById" })
	@SystemLog(methods="问答管理",type="删除")
	@ResponseBody
	public JSONObject delById() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			pd = getPageData();
			this.wtService.delById(pd);
			obj.put("statusCode", "200");
			obj.put("message", "操作成功"); 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj;
	}
	
	
	@RequestMapping({ "/toAnswer" })
	public ModelAndView toAnswer(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData(); 
		mv.addObject("WT_ID", pd.get("WT_ID"));
		mv.setViewName("system/wenda/huida_list");
		return mv;
	}
	
	@RequestMapping({ "/queryHdsByParam" })
	@ResponseBody
	public String queryHdsByParam(Page page)throws Exception{
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
		
		List<PageData> data = this.hdService.listByParam(page);
		Object  total = this.hdService.findCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		for(PageData wd:data){ 
		   Object date=wd.get("DATE");  
		   if(date==null||"".equals(date)){
			   wd.put("DATE","");
		   }else{ 
			   wd.put("DATE",date.toString());
		   } 
		}
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	}
	
	@RequestMapping({ "/delByHDId" })
	@SystemLog(methods="回答管理",type="删除")
	@ResponseBody
	public JSONObject delByHDId() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			pd = getPageData();
			Object res=this.hdService.delByHDId(pd);
			PageData page=new PageData();
			page.put("ID",pd.getString("WT_ID")); 
			PageData wt=this.wtService.queryById(page); 
		    int  count=Integer.parseInt(wt.get("COMMENT_COUNT")+""); 
			if(count>1){ 
				page.put("COMMENT_COUNT",count-1);  
			}else{ 
				page.put("COMMENT_COUNT",0);  
			} 
			this.wtService.edit(page);  
			obj.put("statusCode", "200");
			obj.put("message", "操作成功"); 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj;
	}
}


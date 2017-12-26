package com.fh.controller.system.feedback;

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
import com.fh.service.system.feedback.FeedBackService;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/feedback" })
public class FeedBackController extends BaseController{
	
	@Resource(name = "feedBackService")
	private FeedBackService feedBackService;
	
	@RequestMapping
	public ModelAndView list() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd=null;
		this.feedBackService.updatedelhd(pd);
		mv.setViewName("system/feedback/feedback_list");
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
		    catch (Exception e)  
		    {  
		        break;  
		    }  
		}  
		
		String REAL_NAME = pd.getString("REAL_NAME");
		String StartDate = pd.getString("StartTime");
		String EndDate = pd.getString("EndTime");
		
		if ((REAL_NAME != null) && (!"".equals(REAL_NAME))) {
			REAL_NAME = REAL_NAME.trim();
			pd.put("REAL_NAME", REAL_NAME);
			getObj.put("REAL_NAME",REAL_NAME);
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
		 
		List<PageData> feedbackList = this.feedBackService.listPageByParam(page);
		Object  total = (Long) this.feedBackService.findCount(page).get("counts");
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(int i=0;i<feedbackList.size();i++){ 
		   Object date=feedbackList.get(i).get("FEEDBACK_DATE"); 
		   if(date==null||"".equals(date)){
			   feedbackList.get(i).put("FEEDBACK_DATE","");
		   }else{
			   String FEEDBACK_DATE =date+""; 
			   feedbackList.get(i).put("FEEDBACK_DATE",FEEDBACK_DATE);
		   }  
		}
		getObj.put("aaData", feedbackList);//要以JSON格式返回
		return getObj.toString();
	}
	
	//查看图片
	@RequestMapping({ "/look" })
	public ModelAndView look()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData(); 
		PageData feedback=this.feedBackService.queryById(pd);
		mv.setViewName("system/feedback/lookimg");
		mv.addObject("data", feedback); 
		return mv;
	}
}

 

	
 
	 
	

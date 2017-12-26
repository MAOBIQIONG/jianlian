package com.fh.controller.system.operLog;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.operLog.OperLogService;
import com.fh.util.DateUtil;
import com.fh.util.PageData; 
@Controller
@Aspect
@RequestMapping({ "/operLog" })
public class OperLogController extends BaseController { 

	@Resource(name = "operLogService")
	private OperLogService operLogService; 

	/*@Pointcut("execution(* com.fh.controller..*.*(..)) ")
  	private void handlerMethods(){
  		 System.out.println("切入点");
  	}  */
  	
  	/*@After("handlerMethods()")
  	public void log() { 
  		PageData pd=new PageData();
  		pd.put("ID",get32UUID());
  		pd.put("USER_ID","USER_ID");
  		pd.put("IP_ADDR", "127.0.0.1");
  		pd.put("OPER_TYPE","OPER_TYPE");
  		pd.put("OPER_OBJECT","OPER_OBJECT");
  		pd.put("OPER_DATE",new Date()); 
  		try {
			operLogService.saveOperLog(pd);
		} catch (Exception e) { 
			e.printStackTrace();
		}
  		 System.out.println("+++++++++++++++==========");
         System.out.println("+++++++++++++++==========");
  	}*/
	
	
	@RequestMapping
	public ModelAndView list() throws Exception {
		
		ModelAndView mv = getModelAndView(); 
		mv.setViewName("system/operlog/operlog_list");
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
		 
		String operType = pd.getString("OPER_TYPE");
		String operObject = pd.getString("OPER_OBJECT");
		String StartDate = pd.getString("StartTime");
		String EndDate = pd.getString("EndTime");
		
		if ((operType != null) && (!"".equals(operType))) { 
			/*String param=new String(operType.getBytes("ISO-8859-1"),"UTF-8").trim();
			pd.put("OPER_TYPE",param);*/
			pd.put("OPER_TYPE",operType);
		} 
		if ((operObject != null) && (!"".equals(operObject))) { 
			//String param=new String(operObject.getBytes("ISO-8859-1"),"UTF-8").trim();
			pd.put("OPER_OBJECT",operObject);
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
		List<PageData> data = this.operLogService.listByParam(page); 
		Object  total = this.operLogService.findCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(int i=0;i<data.size();i++){
		   Object date=data.get(i).get("OPER_DATE"); 
		   if(date==null||"".equals(date)){
			   ((PageData)data.get(i)).put("OPER_DATE","");
		   }else{
			   String OPER_DATE =date+"";
			   ((PageData)data.get(i)).put("OPER_DATE",OPER_DATE);
		   } 
		}
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
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
					this.operLogService.delMulty(Id.split(","));
				}else{
					this.operLogService.deleteOperLogById(pd);
				} 
				obj.put("statusCode", "200");
				obj.put("msg", "操作成功"); 
			} 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj.toString();
	} 
}

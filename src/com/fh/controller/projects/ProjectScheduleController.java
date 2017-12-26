package com.fh.controller.projects;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.projects.ProjectScheduleService;
import com.fh.service.projects.ProjectService;
import com.fh.service.system.dictionaries.DictionariesService; 
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.SystemLog;
import com.fh.util.excelexception.Exceldworup;

@Controller
@RequestMapping({ "/proSche" })
public class ProjectScheduleController extends BaseController { 
	
	@Resource(name = "projectScheduleService")
	private ProjectScheduleService proScheService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService;
	
	@Resource(name = "projectService")
	private ProjectService projectService;
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("projects/project_schedule_list");
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
		 
		String PROJECT_NAME = pd.getString("PROJECT_NAME"); 
		if ((PROJECT_NAME != null) && (!"".equals(PROJECT_NAME))) { 
			//String param=new String(PROJECT_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
			pd.put("PROJECT_NAME",PROJECT_NAME);
		}  
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;
		
		List data = this.proScheService.listByParam(page);
		Object	total = this.proScheService.findCount(page).get("counts");//查询记录的总行数 
		
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(int i=0;i<data.size();i++){
		   java.sql.Date date=(java.sql.Date)((PageData)data.get(i)).get("DATE"); 
		   if(date==null||"".equals(date)){
			   ((PageData)data.get(i)).put("DATE","");
		   }else{
			   String DATE =DateUtil.getTime(new Date(date.getTime()));
			   ((PageData)data.get(i)).put("DATE",DATE);
		   }
		} 
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	} 
	
	 
	 @RequestMapping(value={"/save"}) 
	 @SystemLog(methods="进度跟踪",type="编辑")
	 @ResponseBody
	 public String save()throws Exception{ 
	     PageData pd=getPageData(); 
	     PageData data = new PageData(); 
	     try {  
	    	 pd.put("DATE",new Date());
	    	 pd.put("OPER_BY",getUser().getID());
	    	 data.put("ID",pd.getString("PROJECT_ID"));
	    	 data.put("PROJECT_STAGE_ID",pd.getString("SCHEDULE"));
	    	 data.put("MODIFY_BY",getUser().getID());
	    	 data.put("MODIFY_DATE",new Date());
	       if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){//新增  
	    		pd.put("ID",get32UUID()); 
	    		this.proScheService.save(pd); 
    	   }else{//修改    
    	 	  this.proScheService.edit(pd); 
    	   }
	       this.projectService.edit(data);//修改项目进度
	     } catch (Exception e) {
	           this.logger.error(e.toString(), e); 
	     }  
	    JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	   }  
	 
	@RequestMapping({ "/toEdit" })
	public ModelAndView toEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData(); 
		List userList=null;
		try {
			pd = getPageData();  
			pd = this.proScheService.findById(pd);  
			List stageList=this.dicService.queryByPBM("stage"); 
			List projectList=this.projectService.listAll(null);
			mv.addObject("stageList", stageList); 
			mv.addObject("projectList", projectList); 
			mv.addObject("pd", pd); 
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		mv.addObject("msg", "edit");
	    mv.setViewName("projects/project_schedule_edit");  
		return mv;
	}
	
	@RequestMapping({ "/delete" })
	@SystemLog(methods="进度跟踪",type="删除")
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData();
			//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
			this.proScheService.delete(pd); 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	}
	
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser");
		
		return user;
	}
	
	//导出Excel
	@RequestMapping(value = "exportfeedback")
    @ResponseBody
    public String exportFeedBack(HttpServletResponse response) throws Exception{
    	JSONObject getObj = new JSONObject();
        String fileName = "项目进度跟踪信息"+System.currentTimeMillis()+".xls"; //文件名 
        String sheetName = "项目进度跟踪信息";//sheet名 
        String []title = new String[]{"项目名称","项目阶段","操作人","操作时间"};//标题
        PageData pd=null;
        List<PageData> list = this.proScheService.doexlelist(pd);//内容list
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String [][]values = new String[list.size()][];
        for(int i=0;i<list.size();i++){
            values[i] = new String[title.length];
            //将对象内容转换成string
            PageData obj = list.get(i);  
            values[i][0] = obj.getString("PROJECT_NAME")+""; 
            values[i][1] = obj.getString("BIANMA_NAME");
            values[i][2] = obj.getString("CREATE_NAME");
            values[i][3] = sdf.format(obj.get("DATE")); 
            
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
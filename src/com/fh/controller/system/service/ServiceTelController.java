package com.fh.controller.system.service;


import java.util.Date;
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
import com.fh.service.system.service.TelService; 
import com.fh.util.PageData;  
import com.fh.util.SystemLog; 

@Controller
@RequestMapping({ "/service" })
public class ServiceTelController extends BaseController {
	 
	@Resource(name = "telService")
	private TelService telService;  
 
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/service/service-list");
		return mv;
	}
	//查询所有公司信息
		@RequestMapping({ "/queryList" })
		@ResponseBody
		public String queryList(Page page)throws Exception{
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
			
			List<PageData> tels = this.telService.listByParam(page);
			Object  total = this.telService.findCount(page).get("counts");//查询记录的总行数
			getObj.put("sEcho", initEcho);// 不知道这个值有什么用
			getObj.put("iTotalRecords", total);//实际的行数
			getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
			for(PageData tel:tels){  
				tel.put("CREATE_DATE",tel.get("CREATE_DATE")==null?"":tel.get("CREATE_DATE").toString()); 
			}  
			getObj.put("aaData", tels);//要以JSON格式返回
			return getObj.toString();
		}
		
		@RequestMapping({ "/save" })
		@SystemLog(methods="活动管理",type="编辑")
		@ResponseBody
		public String save()throws Exception{
			PageData pd = new PageData();
			JSONObject getObj = new JSONObject();
			try { 
				    pd = getPageData(); 
					if (this.telService.findBmCount(pd) != null){//判断编码是否存在
						getObj.put("msg", "此编码已存在");
						getObj.put("statusCode", "");
					}else{ 
						if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){ 
						  	pd.put("ID", get32UUID());
						  	pd.put("CREATE_DATE",new Date());
						  	pd.put("CREATE_BY", getUser().getID());
						  	pd.put("COLLECT_COUNT",0); 
							pd.put("PART_COUNT",0);
				    		//添加
				    		this.telService.save(pd);
						}else{ 
			    	 	    pd.put("ID",pd.getString("ID")); 
			    	 	    //修改
			    	 	    this.telService.edit(pd); 
						}
						getObj.put("statusCode", 200);
					} 
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				} 
				return getObj.toString();
		}
		
	 
		
	//通过ID获取数据
	@RequestMapping({ "/toEdit" })
	public ModelAndView toEdit()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();  
		PageData data=this.telService.queryById(pd); 
		mv.addObject("data",data); 
		mv.setViewName("system/service/edit-tel");
		return mv;
	} 
		
	@RequestMapping({ "/del" })
	@SystemLog(methods="客服管理",type="删除")
	@ResponseBody
	public JSONObject del() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			pd = getPageData(); 
			this.telService.delete(pd); 
			obj.put("statusCode", "200");  
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj;
	} 
}


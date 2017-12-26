package com.fh.controller.system.menu;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.service.system.menu.MenuService;
import com.fh.util.PageData;
import com.fh.util.SystemLog;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping({ "/menu" })
public class MenuController extends BaseController {

	@Resource(name = "menuService")
	private MenuService menuService;

	@RequestMapping
	public ModelAndView list() throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/menu/list");
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
		
		String MENU_NAME = pd.getString("MENU_NAME");
		String PARENT_ID = pd.getString("PARENT_ID");
		if ((MENU_NAME != null) && (!"".equals(MENU_NAME))) {
			MENU_NAME = MENU_NAME.trim();
			pd.put("MENU_NAME", MENU_NAME);
			getObj.put("MENU_NAME",MENU_NAME);
		}
		if ((PARENT_ID != null) && (!"".equals(PARENT_ID))) {
			PARENT_ID = PARENT_ID.trim();
		}else{
			PARENT_ID = "0";
		}
		pd.put("PARENT_ID", PARENT_ID); 

		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;   
		 
		List data = this.menuService.listPageByParam(page);
		Object  total = (Long) this.menuService.findCount(page).get("counts");
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	}
	
	@RequestMapping({ "/toChired" })
	public ModelAndView todic_chired() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		mv.setViewName("system/menu/chiredlist");
		mv.addObject("PARENT_ID", pd.get("PARENT_ID"));
		return mv;
	}

	@RequestMapping({ "/toAdd" })
	public ModelAndView toAdd() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		mv.addObject("PARENT_ID", pd.getString("PARENT_ID"));
		mv.setViewName("system/menu/add"); 
		return mv;
	}

/*	@RequestMapping({ "/add" })
	public ModelAndView add(Menu menu) throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		try {
			menu.setMENU_ID(String.valueOf(Integer.parseInt(this.menuService
					.findMaxId(pd).get("MID").toString()) + 1));

			String PARENT_ID = menu.getPARENT_ID();
			if (!"0".equals(PARENT_ID)) {
				pd.put("MENU_ID", PARENT_ID);
				pd = this.menuService.getMenuById(pd);
				menu.setMENU_TYPE(pd.getString("MENU_TYPE"));
			}

			this.menuService.saveMenu(pd);
			mv.addObject("msg", "success");
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
			mv.addObject("msg", "failed");
		}

		mv.setViewName("save_result");
		return mv;
	}*/
	
	@RequestMapping({ "/save" })
	@SystemLog(methods="菜单管理",type="编辑")
	@ResponseBody
	public JSONObject save() throws Exception {
		PageData pd = new PageData();
		pd = getPageData();
		try { 
			String ID=pd.getString("MENU_ID");
			String PARENT_ID=pd.getString("PARENT_ID");
			if(ID!=null&&!"".equals(ID)){
				this.menuService.edit(pd);
			}else{
				if(PARENT_ID==null||"".equals(PARENT_ID)){
					pd.put("PARENT_ID",0);
				}
				Object MENU_ID=this.menuService.findMaxId(pd).get("MID");
				if(MENU_ID==null||"".equals(MENU_ID)){
					MENU_ID="1";
				}else{
					MENU_ID=String.valueOf(Integer.parseInt(MENU_ID.toString()) + 1);
				}
				pd.put("MENU_ID",MENU_ID.toString());
				this.menuService.saveMenu(pd);
			}  
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject obj = new JSONObject();
		obj.put("statusCode", "200");
		obj.put("message", "操作成功"); 
		return obj;
	}

	@RequestMapping({ "/toEdit" })
	public ModelAndView toEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		try {
			pd = getPageData();
			pd = this.menuService.getMenuById(pd);
			mv.addObject("pd", pd);
			mv.setViewName("system/menu/edit");
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return mv;
	}

	/*@RequestMapping({ "/toEditicon" })
	public ModelAndView toEditicon(String MENU_ID) throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		try {
			pd = getPageData();
			pd.put("MENU_ID", MENU_ID);
			mv.addObject("pd", pd);
			mv.setViewName("system/menu/menu_icon");
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return mv;
	}

	@RequestMapping({ "/editicon" })
	public ModelAndView editicon() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		try {
			pd = getPageData();
			pd = this.menuService.editicon(pd);
			mv.addObject("msg", "success");
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
			mv.addObject("msg", "failed");
		}
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping({ "/edit" })
	public ModelAndView edit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		try {
			pd = getPageData();

			String PARENT_ID = pd.getString("PARENT_ID");
			if ((PARENT_ID == null) || ("".equals(PARENT_ID))) {
				PARENT_ID = "0";
				pd.put("PARENT_ID", PARENT_ID);
			}

			if ("0".equals(PARENT_ID)) {
				this.menuService.editType(pd);
			}

			pd = this.menuService.edit(pd);
			mv.addObject("msg", "success");
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
			mv.addObject("msg", "failed");
		}
		mv.setViewName("save_result");
		return mv;
	}*/
	
	/*@RequestMapping({ "/save" })
	@ResponseBody
	public JSONObject save() throws Exception {
		PageData pd = new PageData();
		try {
			pd = getPageData();

			String PARENT_ID = pd.getString("PARENT_ID");
			if ((PARENT_ID == null) || ("".equals(PARENT_ID))) {
				PARENT_ID = "0";
				pd.put("PARENT_ID", PARENT_ID);
			}

			if ("0".equals(PARENT_ID)) {
				this.menuService.editType(pd);
			}

			pd = this.menuService.edit(pd);
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject obj = new JSONObject();
		obj.put("statusCode", "200");
		obj.put("message", "操作成功");
		obj.put("closeCurrent", true);
		obj.put("tabid", "");
		obj.put("forward", "");
		obj.put("forwardConfirm", "");
		return obj;
	}*/

	@RequestMapping({ "/sub" })
	public void getSub(@RequestParam String MENU_ID,
			HttpServletResponse response) throws Exception {
		try {
			List subMenu = this.menuService.listSubMenuByParentId(MENU_ID);
			JSONArray arr = JSONArray.fromObject(subMenu);

			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			String json = arr.toString();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
	}
	
	@RequestMapping({ "/del" })
	@SystemLog(methods="字典管理",type="删除")
	@ResponseBody
	public JSONObject del(@RequestParam String MENU_ID) throws Exception {
		PageData pd = new PageData();
		pd = getPageData();
		this.menuService.deleteMenuById(MENU_ID);
		JSONObject obj = new JSONObject();
		obj.put("statusCode", "200");
		obj.put("message", "操作成功");
		obj.put("closeCurrent", false);
		obj.put("tabid", "");
		obj.put("forward", "");
		obj.put("forwardConfirm", "");
		return obj;
	}
} 
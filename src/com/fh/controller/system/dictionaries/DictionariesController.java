package com.fh.controller.system.dictionaries;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.menu.MenuService;
import com.fh.util.PageData;
import com.fh.util.SystemLog;
import com.fh.util.TreeNode;

@Controller
@RequestMapping({ "/dictionaries" })
public class DictionariesController extends BaseController {

	@Resource(name = "menuService")
	private MenuService menuService;
	

	@Resource(name = "dictionariesService")
	private DictionariesService dictionariesService;
	List<PageData> szdList;

	@RequestMapping
	public ModelAndView list() throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/dictionaries/list");
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
		
		String NAME = pd.getString("NAME");
		String PARENT_ID = pd.getString("PARENT_ID");
		if ((NAME != null) && (!"".equals(NAME))) {
			NAME = NAME.trim();
			pd.put("NAME", NAME);
			getObj.put("NAME",NAME);
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
		 
		List bidNoticeList = this.dictionariesService.dictlistPage(page);
		Object  total = (Long) this.dictionariesService.findCountByParam(page).get("counts");
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		getObj.put("aaData", bidNoticeList);//要以JSON格式返回
		return getObj.toString();
	}
	
	@RequestMapping({ "/toChired" })
	public ModelAndView todic_chired() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		mv.setViewName("system/dictionaries/chiredlist");
		mv.addObject("PARENT_ID", pd.get("PARENT_ID"));
		return mv;
	}
	
	@RequestMapping({ "/save" })
	@SystemLog(methods="字典管理",type="编辑")
	@ResponseBody
	public String save() throws Exception { 
		JSONObject obj = new JSONObject();
		PageData pd = new PageData();
		pd = getPageData(); 
		String ZD_ID = pd.getString("ZD_ID");
		String PARENT_ID = pd.getString("PARENT_ID"); 
		
		if (this.dictionariesService.findBmCount(pd) != null){//判断编码是否存在
			obj.put("msg", "此编码已存在");
			obj.put("statusCode", "");
		}else{ 
			if ((ZD_ID == null)|| ("".equals(ZD_ID))) {//新增
				if (("0".equals(PARENT_ID))) {  
					pd.put("P_BM", pd.getString("BIANMA"));
				} else {
					pd.put("P_BM", pd.get("PP_BM")+"_"+pd.get("BIANMA")); 
				}
				pd.put("ZD_ID", get32UUID());
				this.dictionariesService.save(pd);
			} else {//修改 
				if ("0".equals(PARENT_ID))
					pd.put("P_BM", pd.getString("BIANMA"));
				else {
					pd.put("P_BM", pd.get("PP_BM")+"_"+pd.get("BIANMA")); 
				} 
				this.dictionariesService.edit(pd);
			}
			obj.put("msg", "操作成功");
			obj.put("statusCode", "200");
		} 
		return obj.toString();
	} 
	
	@RequestMapping({ "/dictTree" })
	@ResponseBody
	public List<TreeNode> dictionariesTree() {
		PageData pd = getPageData();
		List<TreeNode> list = new ArrayList<TreeNode>();
		Page page = getPage();
		page.setPd(pd);
		try {
			List<PageData> listAll = this.dictionariesService
					.dictlistPage(page);
			for (PageData pageData : listAll) {
				TreeNode _treeNode = new TreeNode();
				_treeNode.setName(pageData.getString("NAME"));
				_treeNode.setPARENT_ID(pageData.get("PARENT_ID").toString());
				_treeNode.setId(pageData.get("ZD_ID").toString());
				if (pageData.get("JB").toString().equals("3"))
					_treeNode.setIsParent(Boolean.valueOf(false));
				else {
					_treeNode.setIsParent(Boolean.valueOf(true));
				}
				list.add(_treeNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	

	public void getZDname(String PARENT_ID) {
		logBefore(this.logger, "递归");
		try {
			PageData pdps = new PageData();
			pdps.put("ZD_ID", PARENT_ID);
			pdps = this.dictionariesService.findById(pdps);
			if (pdps != null) {
				this.szdList.add(pdps);
				String PARENT_IDs = pdps.getString("PARENT_ID");
				getZDname(PARENT_IDs);
			}
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
	}

	 
	@RequestMapping({ "/toEdit" })
	public ModelAndView toEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		String ZD_ID = pd.getString("ZD_ID");
		String PARENT_ID = pd.getString("PARENT_ID");
		
		PageData data = new PageData();
		if(ZD_ID != null && ZD_ID != ""){
			data = this.dictionariesService.findById(pd);
		}else{
			if(PARENT_ID != null && PARENT_ID != ""){
				pd.put("ZD_ID", PARENT_ID);
				PageData da1 = this.dictionariesService.findById(pd);
				data.put("PP_BM", da1.get("BIANMA"));
				data.put("PARENT_ID", PARENT_ID);
			}else{
				data.put("PARENT_ID", "0");
			}
		}
		mv.setViewName("system/dictionaries/edit");
		mv.addObject("pd", data);
		return mv;
	}

	@RequestMapping({ "/has" })
	public void has(PrintWriter out) {
		PageData pd = new PageData();
		try {
			pd = getPageData();
			if (this.dictionariesService.findBmCount(pd) != null)
				out.write("error");
			else {
				out.write("success");
			}
			out.close();
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
	}

	@RequestMapping({ "/del" })
	@SystemLog(methods="字典管理",type="删除")
	@ResponseBody
	public JSONObject del() {
		PageData pd = new PageData();
		String errInfo = "";
		try {
			pd = getPageData();

			if (Integer.parseInt(this.dictionariesService.findCount(pd).get("ZS").toString()) != 0) {
				errInfo = "false";
			} else {
				this.dictionariesService.delete(pd);
				errInfo = "success";
			}
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}

		JSONObject obj = new JSONObject();
		obj.put("statusCode", "200");
		obj.put("message", "操作成功");
		return obj;
	}
} 
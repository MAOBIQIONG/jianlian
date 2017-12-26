package com.fh.controller.app.connections;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.app.AppController;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.AppDicService;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.app.company.AppCategoryService;
import com.fh.service.app.company.AppcompanyService;
import com.fh.service.app.connections.AppConnectionService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/appconnection" })
public class AppConnectionController extends AppController {

	@Resource(name = "appconnectionService")
	private AppConnectionService appconnectionService;

	@Resource(name = "appCommonService")
	private AppCommonService appCommonService;

	@Resource(name = "appcompanyService")
	private AppcompanyService appComService;

	@Resource(name = "appCategoryService")
	private AppCategoryService appCateService;

	@Resource(name = "appDicService")
	private AppDicService appDicService;

	@Resource(name = "appusersService")
	private AppusersService appUsersService;

	// 我的人脉列表
	@RequestMapping({ "/querConnectionUser" })
	@ResponseBody
	public PageData querConnectionUser() throws Exception {
		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		// PageData data=new PageData();
		// data.put("USER_ID", pa.getString("USER_ID"));
		List<PageData> list = new ArrayList<PageData>();
		String REAL_NAME = pa.getString("NAME");
		if ((REAL_NAME != null) && (!"".equals(REAL_NAME))) {
			String param = new String(REAL_NAME.getBytes("ISO-8859-1"), "UTF-8")
					.trim();
			pa.put("NAME", param);
		}
		list = this.appconnectionService.querConnectionUser(pa);

		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = new PageData();
		List<PageData> lists = null;
		for (PageData pageData : list) {
			if (pageData.getString("VALERIE").equals(
					map.get(pageData.getString("VALERIE")))) {
				lists.add(pageData);
			} else {
				lists = new ArrayList<PageData>();
				map.put(pageData.getString("VALERIE"),
						pageData.getString("VALERIE"));
				lists.add(pageData);
			}
			result.put(map.get(pageData.getString("VALERIE")), lists);
		}
		_result = AppUtil.ss(result, "01", "成功", "true", null);
		return _result;
	}
	
	// 我的人脉-个人信息详情
	@RequestMapping({ "/queroneUser" })
	@ResponseBody
	public PageData queroneUser() throws Exception {
		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData data = new PageData();
		data.put("userid", pa.getString("userid"));
		data.put("USER_ID", pa.getString("USER_ID"));
		List<PageData> list = new ArrayList<PageData>();
		list = this.appconnectionService.queroneUser(pa);
		List<PageData> amount = this.appconnectionService.checkConnected(data);
		String keys = list.get(0).getString("ASSESS");
		String ronyu = list.get(0).getString("HONOR");
		if (keys != null && !"".equals(keys)) {
			keys =""+","+keys;
			String[] assess = keys.split(",");
			list.get(0).put("assess", assess);
		}
		if (ronyu != null && !"".equals(ronyu)) { 
			String[] HONOR = ronyu.split(",");
			list.get(0).put("HONOR", HONOR);
		}
		if (amount.size()== 0) {
			list.get(0).put("connect", 1);// 已经关注了
		} else {
			list.get(0).put("connect", 0);// 还没关注
		}
		_result = AppUtil.ss(list, "01", "成功", "true", null);
		return _result;
	}

	// 我的人脉-关注
	@RequestMapping({ "/addconcern" })
	@ResponseBody
	public PageData addconcern() throws Exception {
		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> page = this.appconnectionService.checkConnected(pa);
		if (page.size()==0) {
			PageData data = new PageData();
			data.put("ID", get32UUID());
			data.put("DATE", new Date());
			data.put("USER_ID", pa.getString("userid"));
			data.put("CONTRACTS_USER_ID", pa.getString("USER_ID"));
			// 添加关注
			Object obj = this.appconnectionService.addconcern(data);
			if (Integer.valueOf(obj.toString()) >= 1) {
				_result = AppUtil.ss(null, "01", "关注成功", "true", null);
			} else {
				_result = AppUtil.ss(null, "92", "关注失败", "false", null);
			}
		} else {// 已经关注了，要解除关注
			Object obj = this.appconnectionService.delconcern(page.get(0));
			if (Integer.valueOf(obj.toString()) >= 1) {
				_result = AppUtil.ss(null, "01", "取消关注成功", "true", null);
			} else {
				_result = AppUtil.ss(null, "92", "取消关注失败", "false", null);
			}
		}
		return _result;
	}

	// 我的人脉列表
	@RequestMapping({ "/querUsername" })
	@ResponseBody
	public PageData querUsername() throws Exception {
		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> list = new ArrayList<PageData>();
		PageData data = new PageData();
		data.put("ID", pa.getString("ID"));
		String REAL_NAME = pa.getString("REAL_NAME");
		if ((REAL_NAME != null) && (!"".equals(REAL_NAME))) {
			String param = new String(REAL_NAME.getBytes("ISO-8859-1"), "UTF-8")
					.trim();
			data.put("REAL_NAME", param);
		}
		list = this.appconnectionService.querUsername(data);
		_result = AppUtil.ss(list, "01", "成功", "true", null);
		return _result;
	}

	// 我的人脉-个人信息评论
	@RequestMapping({ "/queronepinlun" })
	@ResponseBody
	public PageData queronepinlun() throws Exception {
		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> list = new ArrayList<PageData>();
		list = this.appconnectionService.queronepinlun(pa);
		_result = AppUtil.ss(list, "01", "成功", "true", null);
		return _result;
	}

	// 拓展人脉推荐列表获取
	@RequestMapping({ "/quertuijian" })
	@ResponseBody
	public PageData quertuijian() throws Exception {
		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> list = new ArrayList<PageData>();
		PageData com = appComService.queryByUId(pa);

		if (com != null) {
			PageData data = new PageData();
			data.put("CATEGORY_ID", com.getString("CATEGORY_ID"));
			data.put("userid", pa.getString("userid"));
			if (pa.containsKey("COMPANY_NAME")) {
				String COMPANY_NAME = pa.getString("COMPANY_NAME");
				if ((COMPANY_NAME != null) && (!"".equals(COMPANY_NAME))) {
					String param = new String(
							COMPANY_NAME.getBytes("ISO-8859-1"), "UTF-8")
							.trim();
					data.put("COMPANY_NAME", param);
				}
			}

//			int currentPage = Integer.parseInt(pa.get("currentPage") + "");// 当前页
//			int pageSize = Integer.parseInt(pa.get("pageSize") + "");// 每页条数
//			int totalSize = currentPage * pageSize;
//			data.put("totalSize", totalSize);
			int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
			int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
			int start=(currentPage-1)*pageSize;
			data.put("start",start); 
			data.put("pageSize",pageSize);

			list = this.appconnectionService.quertuijian(data);
			int tzong=this.appconnectionService.quertuijian(data).size();
			PageData tjzong=this.appconnectionService.quertuijianzong(data);
			PageData jkl=new PageData();
			jkl.put("list", list);
			jkl.put("tzong", tzong);
			jkl.put("tjzong", tjzong);
			_result = AppUtil.ss(jkl, "01", "成功", "true", null);
		} else {
			_result = AppUtil.ss(null, "92", "失败", "false", null);
		}
		return _result;
	}

	// 拓展人脉、搜索人脉列表
	@RequestMapping({ "/extendsInfo" })
	@ResponseBody
	public PageData extendsInfo() throws Exception {
		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> cateList = this.appCateService.listByPId("0");
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> typeList = this.appDicService.queryByPBM("viptype");
		map.put("cateList", cateList);// 行业分类
		map.put("typeList", typeList);// 会员类型
		_result = AppUtil.ss(map, "01", "成功", "true", null);
		return _result;
	}

	// 根据会员类型、行业分类、公司名称进行搜索
	@RequestMapping({ "/queryExtendsByParam" })
	@ResponseBody
	public PageData queryExtendsByParam() throws Exception {
		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		String COMPANY_NAME = pa.getString("COMPANY_NAME");
		if ((COMPANY_NAME != null) && (!"".equals(COMPANY_NAME))) {
			COMPANY_NAME = new String(COMPANY_NAME.getBytes("ISO-8859-1"),
					"UTF-8").trim();
			pa.put("COMPANY_NAME", COMPANY_NAME);
		}
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int start=(currentPage-1)*pageSize;
		pa.put("start",start); 
		pa.put("pageSize",pageSize);
		
		List<PageData> userList = this.appUsersService.queryExtendsByParam(pa);
		_result = AppUtil.ss(userList, "01", "成功", "true", null);
		return _result;
	}
}

package com.fh.controller.app;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping({ "/appDic" })
public class AppDicController extends AppController {

	/*@Resource(name = "appDicService")
	private AppDicService appDicService;
	@Resource(name = "appUserService")
	private AppUserService appUserService;
	@Resource(name = "appUsertokenService")
	private AppUsertokenService appUsertokenService;
	@Resource(name = "appCommonService")
	private AppCommonService appCommonService;

	*//**
	 * 获取字典表信息
	 * 
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping({ "/findList" })
	@ResponseBody
	public PageData findList() throws Exception {
		PageData _restult = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());

		List<PageData> list = appDicService.findAll();

		_restult = AppUtil.ss(list, "01", "成功", "true", "");
		return _restult;
	}

	@RequestMapping({ "/findById" })
	@ResponseBody
	public PageData findById() throws Exception {
		List<PageData> list = new ArrayList<PageData>();

		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());

		list = this.appDicService.findById(pa);
		_result = AppUtil.ss(list, "01", "成功", "true", "");
		return _result;
	}
*/
}

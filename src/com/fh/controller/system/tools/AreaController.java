package com.fh.controller.system.tools;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.fh.service.system.area.AreaService;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/area" })
public class AreaController extends BaseController {
	@Resource(name="areaService")
	AreaService areaService;

	@RequestMapping({ "/getArea" })
	@ResponseBody
	public Object severTest() throws Exception{
		PageData pd = new PageData();
		pd = getPageData();
		List<PageData> list = this.areaService.queryAllParent(null);
		PageData selNull = new PageData();
		selNull.put("value", "");
		selNull.put("label", "--请选择--");
		//list.add(0,selNull);
		return JSON.toJSON(list);
	}

}

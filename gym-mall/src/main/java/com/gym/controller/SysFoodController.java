package com.gym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gym.common.vo.JsonResult;

import com.gym.service.SysFoodService;




@RequestMapping("/food/")
@Controller
public class SysFoodController {
	@Autowired
	private SysFoodService sysFoodService;
	//	
	//	@RequestMapping("doFindFoodObjects")
	//	public JsonResult doFindFoodObjects() {
	//		return new JsonResult(
	//				  sysFoodService.findAllFood());
	//	}
	@GetMapping("doFindFoodObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
			String food,
			Integer pageCurrent) {
		return new JsonResult(
				sysFoodService.findFoodObjects(food, pageCurrent));
	}


}

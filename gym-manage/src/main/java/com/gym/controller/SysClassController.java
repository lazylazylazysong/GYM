package com.gym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gym.entity.SysClass;
import com.gym.service.SysClassService;
import com.gym.vo.JsonResult;
import com.gym.vo.PageObject;

@RequestMapping("/class/")
@Controller
public class SysClassController {
	@Autowired
	private SysClassService sysClassService;

	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObjects(SysClass entity) {
		sysClassService.saveObject(entity);
		return new JsonResult("save ok");
	}
	
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id){
		sysClassService.deleteObject(id);
		return new JsonResult("delete ok");
	}
	
	@RequestMapping("doDeleteObjects")
	  @ResponseBody
	  public JsonResult doDeleteObjects(Integer... ids){
		sysClassService.deleteObjects(ids);
		  return new JsonResult("delete ok");
	  }
	
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObjects(SysClass entity){
		sysClassService.updateObject(entity);
		return new JsonResult("update ok");
	}
	
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String name, Integer pageCurrent) {
		PageObject<SysClass> pageObject = sysClassService.findPageObjects(name, pageCurrent);
		return new JsonResult(pageObject);
	}
	@RequestMapping("doFindObjectByName")
	@ResponseBody
	public JsonResult doFindObjectById(String name) {
		SysClass object = sysClassService.findObjectByName(name);
		return new JsonResult(object);
	}

	
}

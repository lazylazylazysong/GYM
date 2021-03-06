package com.gym.controller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gym.entity.SysEmployee;
import com.gym.exception.ServiceException;
import com.gym.service.SysEmployeeService;
import com.gym.vo.JsonResult;
import com.gym.vo.JsonResults;



@RequestMapping("/Employee/")
@Controller
public class SysEmployeeController extends BaseController{
	@Autowired
	private SysEmployeeService sysEmployeeService;
	private static final long AVATAR_MAX_SIZE=10*1024*1024;
	private static final List<String> AVATAR_TYPES=new ArrayList<String>();

	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer... ids){
		sysEmployeeService.deleteObject(ids);
		return new JsonResult("delete ok");
	}                                                               
	@RequestMapping("doFindObject")
	@ResponseBody
	public JsonResult doFindObject(String name,
			Integer pageCurrent){
		PageInfo<SysEmployee> pageObject=
				sysEmployeeService.findObject(name,pageCurrent); 
		return new JsonResult(pageObject);

	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysEmployee entity) {
		sysEmployeeService.saveObject(entity);
		return new JsonResult("save ok");
	}
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer Id) {
		return new JsonResult(sysEmployeeService.findById(Id));
	}
	/**
	 * 有file文件时
	 * @param movieDto 封装了需要传递过来的参数
	 * @param file 图片file
	 */
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysEmployee entity) {
		sysEmployeeService.updateObject(entity);
		return new JsonResult("update ok");
	}
	/**
	 * 有file文件时
	 * @param movieDto 封装了需要传递过来的参数
	 * @param file 图片file
	 */
	@RequestMapping("change_avatar")
	@ResponseBody
	public JsonResults<String> changeAvatar(@RequestParam("file") MultipartFile file,String id,
			    HttpServletRequest request,HttpSession session){
		
		// 空文件验证
		if(file.isEmpty()) {
			throw new ServiceException("文件上传异常！文件不能为空");
		}
		// 文件大小验证
		long fileSize=file.getSize();
		if(fileSize>AVATAR_MAX_SIZE) {
			throw new ServiceException("文件上传异常！文件大小超过上限:"+(AVATAR_MAX_SIZE/(1024*20))+"kb");
		}
		System.out.println(file.getContentType());
		// 文件类型验证
		   AVATAR_TYPES.add("image/jpeg");
		  if(!AVATAR_TYPES.contains(file.getContentType())) { throw new
		  ServiceException("文件上传异常！文件类型不正确，允许的类型有："+AVATAR_TYPES); }
		 
		
		// 生成文件名
		String oFilename=file.getOriginalFilename();
		Integer index=oFilename.lastIndexOf(".");
		String suffix="";
		if(index!=-1) {
			suffix=oFilename.substring(index);
		}
		String filename=UUID.randomUUID().toString()+suffix;

		// 生成目标路径
		String filePath=request.getServletContext().getRealPath("upload");
		System.out.println(filePath);
		File parent=new File(filePath);
		if(!parent.exists()) {
			parent.mkdirs();//创建对应的目录
		}
		
		File dest=new File(parent,filename);
		// 将用户上传的头像保存到服务器上
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			throw new ServiceException("文件上传异常！"+e.getMessage());
			// throw new FileStateException("文件上传异常！"+e.getMessage(),e);
		} catch (IOException e) {
			throw new ServiceException("文件上传异常！"+e.getMessage());
		}

		// 将头像在服务器的路径保存到数据库
		String avatar="/upload/"+filename;
		sysEmployeeService.changeAvatar(Integer.valueOf(id),avatar);
		 
		JsonResults<String> jr=new JsonResults<>(SUCCESS);
		jr.setData(avatar);
		return jr;
		// return new JsonResult<String>(SUCCESS, avatar);
	}
}

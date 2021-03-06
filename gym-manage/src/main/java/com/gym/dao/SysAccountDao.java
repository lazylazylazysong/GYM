package com.gym.dao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gym.entity.SysAccount;
@Mapper
public interface SysAccountDao {
	List<SysAccount> findPageObjects(@Param("name") String name);
	int getRowCount(@Param("name") String name);
	int deleteObjects(@Param("ids")Integer... ids);
	int insertObject(SysAccount entity);
	int updateObject(SysAccount entity);
	SysAccount findById(Integer id);
}

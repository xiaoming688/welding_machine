package com.welding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.welding.model.AdminUser;
import com.welding.model.SysUser;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserDao extends BaseMapper<SysUser> {
    /**
     * 通过名字查询用户信息
     */
    @Select("SELECT * FROM admin_user WHERE nickname = #{name}")
    AdminUser findUserByName(@Param("name") String name);


    @Select("SELECT id, nickname FROM admin_user")
    List<Map<String, Object>> findUserInfos();
}

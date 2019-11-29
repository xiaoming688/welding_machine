package com.welding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.welding.model.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SysUserDao extends BaseMapper<SysUser> {

    @Select("SELECT * FROM sys_menu WHERE id = #{id}")
    SysUser queryUserById(@Param("id") Integer id);

    @Insert({"insert into sys_user (user_no, user_name, password, telephone, office_phone, email) " +
            "values(#{userNo}, #{userName}, #{password}, #{telephone}, #{officePhone}, #{email})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void add(SysUser user);

    @Select("select m.id as menuId, m.menu_name as menuName, ur.user_id as userId, " +
            "ur.role_id as roleId from sys_menu m " +
            "left join sys_role_menu rm on rm.menu_id = m.id " +
            "left join sys_user_role ur on ur.role_id = rm.role_id " +
            "where ur.user_id= #{id}")
    List<Map<String, String>> queryUserPermission(@Param("id") Integer id);
}

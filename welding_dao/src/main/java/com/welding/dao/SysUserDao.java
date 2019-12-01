package com.welding.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.welding.dao.pojo.UserListVo;
import com.welding.model.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Select("select u.user_no as userNo, u.user_name as userName, u.password, " +
            "u.telephone, u.email, u.group_id as groupName," +
            "sr.role_name as roleName from sys_user u " +
            "left join sys_user_role ur on ur.user_id = u.id " +
            "left join sys_role sr on sr.id = ur.role_id  ${ew.customSqlSegment}")
    IPage<UserListVo> queryUserListPage(Page page, @Param(Constants.WRAPPER) QueryWrapper<SysUser> wrapper);


}

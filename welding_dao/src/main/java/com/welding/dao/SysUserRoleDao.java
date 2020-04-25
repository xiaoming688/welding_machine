package com.welding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.welding.dao.pojo.SysUserRoleBo;
import com.welding.model.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {

    @Select("select ur.role_id as roleId, ur.user_id as userId, rm.role_name as roleName, " +
            "rm.role_key as roleKey from sys_user_role ur " +
            "left join sys_role rm on rm.id = ur.role_id " +
            "where ur.user_id= #{id}")
    List<Map<String, String>> queryUserRoles(@Param("id") Integer id);


}

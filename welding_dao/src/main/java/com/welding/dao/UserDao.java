package com.welding.dao;

import com.welding.model.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    /**
     * 通过名字查询用户信息
     */
    @Select("SELECT * FROM admin_user WHERE nickname = #{name}")
    AdminUser findUserByName(@Param("name") String name);
}

package com.welding.web.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.welding.constants.Constants;
import com.welding.dao.SysUserDao;
import com.welding.dao.SysUserRoleDao;
import com.welding.dao.pojo.ProduceGroupListVo;
import com.welding.dao.pojo.WelderListVo;
import com.welding.model.SysRole;
import com.welding.model.SysUser;
import com.welding.model.SysUserRole;
import com.welding.model.WeldingProduceGroup;
import com.welding.util.MData;
import com.welding.util.PageData;
import com.welding.web.config.shiro.ShiroUtils;
import com.welding.web.pojo.AddWelderDto;
import com.welding.web.pojo.GetWelderListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MM
 * @create 2019-12-11 9:21
 **/
@Slf4j
@Service
public class WeldingWeldingService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    /**
     * 添加焊工
     *
     * @param addWelderDto
     * @return
     */
    public MData addWelder(AddWelderDto addWelderDto) {
        MData result = new MData();

        SysUser sysUser = sysUserService.queryUserByWeldingNo(addWelderDto.getWeldingNo());
        if (sysUser != null) {
            return result.error("用户编码已存在");
        }
        //焊工角色
        SysRole role = sysUserService.queryWeldingRole();

        SysUser user = new SysUser();
        user.setUserName(addWelderDto.getUserName());
        user.setAccountNo(addWelderDto.getAccountNo());
        user.setWeldingNo(addWelderDto.getWeldingNo());
        user.setStampNo(addWelderDto.getStampNo());
        user.setCardNo(addWelderDto.getCardNo());
        user.setSex(addWelderDto.getSex());
        user.setBirthday(DateUtil.parseDate(addWelderDto.getBirthday()));
        user.setTechnicalLevel(addWelderDto.getTechnicalLevel());
        user.setStatus(Constants.ACTIVE);

        String salt = ShiroUtils.randomSalt();
        user.setSalt(salt);
        user.setPassword(ShiroUtils.encryptPassword(Constants.DEFAULT_PASSWORD, salt));
        user.setGroupId(addWelderDto.getGroupId());

        //添加用户
        sysUserDao.insert(user);
        //添加角色
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        sysUserRoleDao.insert(userRole);

        return result;
    }


    public PageData<WelderListVo> getWelderList(GetWelderListDto getWelderListDto) {
        PageData<WelderListVo> pageData = new PageData<>();

        String weldingNo = getWelderListDto.getWeldingNo();
        String userName = getWelderListDto.getUserName();

        Integer pageNo = getWelderListDto.getPageNo();
        Integer pageSize = getWelderListDto.getPageSize();

        List<Integer> userIdList = sysUserService.queryWelderUserIdList();

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();

        wrapper.in("u.id", userIdList);
        if(userIdList.isEmpty()){
            pageData.setData(new ArrayList<>());
            pageData.setPage(pageNo);
            pageData.setTotal(0);
            return pageData;
        }

        if (!StringUtils.isEmpty(userName)) {
            wrapper.eq("u.user_name", userName);
        }
        if (!StringUtils.isEmpty(weldingNo)) {
            wrapper.eq("u.welding_no", weldingNo);
        }

        wrapper.ne("u.status", Constants.DELETE);

        IPage<WelderListVo> page = new Page<>(pageNo, pageSize);

        IPage<WelderListVo> pageRecords = sysUserService.queryWelderListPage(page, wrapper);
        pageData.setData(pageRecords.getRecords());
        pageData.setSize(pageSize);
        pageData.setPage(Long.valueOf(pageRecords.getCurrent()).intValue());
        pageData.setTotal(Long.valueOf(pageRecords.getTotal()).intValue());

        return pageData;

    }


}

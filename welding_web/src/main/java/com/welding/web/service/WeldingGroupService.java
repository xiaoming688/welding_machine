package com.welding.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.welding.constants.Constants;
import com.welding.dao.WeldingProduceGroupDao;
import com.welding.dao.pojo.ProduceGroupListVo;
import com.welding.dao.pojo.ProduceParentGroupVo;
import com.welding.model.WeldingProduceGroup;
import com.welding.util.MData;
import com.welding.util.PageData;
import com.welding.web.pojo.AddProduceGroupDto;
import com.welding.web.pojo.DeleteProduceGroupDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author MM
 * @create 2019-12-06 9:32
 **/
@Slf4j
@Service
public class WeldingGroupService {

    @Autowired
    private WeldingProduceGroupDao weldingProduceGroupDao;


    public PageData<ProduceGroupListVo> getProduceGroupData(Integer pageNo, Integer pageSize, String groupName) {
        PageData<ProduceGroupListVo> pageData = new PageData<>();

        QueryWrapper<WeldingProduceGroup> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(groupName)) {
            wrapper.eq("group_name", groupName);
        }
        wrapper.ne("status", Constants.DELETE);

        IPage<ProduceGroupListVo> page = new Page<>(pageNo, pageSize);

        IPage<ProduceGroupListVo> pageRecords = weldingProduceGroupDao.queryGroupListPage(page, wrapper);
        pageData.setData(pageRecords.getRecords());
        pageData.setPage(Long.valueOf(pageRecords.getCurrent()).intValue());
        pageData.setTotal(Long.valueOf(pageRecords.getTotal()).intValue());

        return pageData;
    }

    /**
     * 获取上级组织
     *
     * @return
     */
    public List<ProduceParentGroupVo> getProduceParentGroup() {
        return weldingProduceGroupDao.getParentGroup();
    }

    /**
     * 添加生产组织
     *
     * @param adWorkerGroup
     * @return
     */
    public MData addProduceGroup(AddProduceGroupDto adWorkerGroup) {
        MData result = new MData();

        String groupName = adWorkerGroup.getGroupName();
        WeldingProduceGroup group = getProduceGroupByName(groupName);
        if (group != null) {
            return result.error("该生产组织已存在");
        }

        WeldingProduceGroup produceGroup = new WeldingProduceGroup();
        produceGroup.setAddress(adWorkerGroup.getAddress());
        produceGroup.setEmail(adWorkerGroup.getEmail());
        produceGroup.setGroupName(adWorkerGroup.getGroupName());
        produceGroup.setParentId(adWorkerGroup.getParentGroupId() == null ? 0 : adWorkerGroup.getParentGroupId());
        produceGroup.setGroupType(adWorkerGroup.getGroupType());
        produceGroup.setLeaderUserName(adWorkerGroup.getLeaderName());
        produceGroup.setStatus(Constants.ACTIVE);

        weldingProduceGroupDao.insert(produceGroup);

        return result;
    }

    private WeldingProduceGroup getProduceGroupByName(String groupName) {
        QueryWrapper<WeldingProduceGroup> wrapper = new QueryWrapper<>();
        wrapper.eq("group_name", groupName);
        //是否需要处理删除状态。。todo
        return weldingProduceGroupDao.selectOne(wrapper);
    }

    /**
     * 删除生产组织
     *
     * @param deleteProduceGroup
     * @return
     */
    public MData deleteProduceGroup(DeleteProduceGroupDto deleteProduceGroup) {

        MData result = new MData();
        String produceGroupName = deleteProduceGroup.getGroupName();
        WeldingProduceGroup produceGroup = getProduceGroupByName(produceGroupName);

        if (produceGroup == null) {
            return result.error("组织名称有误");
        }
        WeldingProduceGroup produceGroupUpdate = new WeldingProduceGroup();
        produceGroupUpdate.setId(produceGroup.getId());
        produceGroupUpdate.setStatus(Constants.DELETE);

        weldingProduceGroupDao.updateById(produceGroupUpdate);

        return result;
    }
}

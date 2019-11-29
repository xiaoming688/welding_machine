package com.welding.dao;

import com.welding.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author MM
 * @create 2018-08-27 10:24
 **/
public interface BaseDao<T extends BaseModel> {

    Integer add(T o);

    void del(T t);

    List<T> queryAll();

    public T queryById(Serializable id);

    public void update(T o);
}

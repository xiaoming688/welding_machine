package com.welding.web;

import com.welding.web.config.shiro.ShiroUtils;

public class TestMain {
    public static void main(String[] args) {
        System.out.println(ShiroUtils.encryptPassword("123456", "fa92dc"));
    }

}

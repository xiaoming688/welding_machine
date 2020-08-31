package com.welding.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

    public static final Integer DEFAULT_PAGE_NO = 1;

    public static final Integer DEFAULT_PAGE_SIZE = 10;

    public final static String LOGINTYPE_ACCOUNT_PASSWORD = "account_password";

    public final static String DELETE = "deleted";
    public static final String ACTIVE = "active";
    public static final String ROLE_WELDER = "welder";
    public static final String DEFAULT_PASSWORD = "123456";

    public static final String SYNC_CURRENT = "http://smart.gdngg.com.cn:8085/synConditionDatas";
    public static final String SYNC_HISTORY = "http://smart.gdngg.com.cn:8085/synHistoryConditionDatas";

    public static final String SYNC_SUCCESS = "success";
    public static final String SYNC_FAIL = "fail";
    public static final Integer SYNC_IS_HISTORY = 1;
    public static final Integer SYNC_NOT_IS_HISTORY = 0;
    public static final Object SYNC_INIT = "init";
    public static final int NETTY_SERVER_PORT = 59987;
    public static final String DEFAULT_PROJECTID = "3KLNJBiS99iXDOsSSpfcf8";
    public static final String DEFAULT_REFURMUNIT = "3";

    public static class LOG {
        public static final String ADD = "add";
        public static final String LOGIN = "login";
        public static final String LOGOUT = "logout";
    }

    public static class User {
        public static final String BLOCKED = "blocked";
        public static final String DELETED = "deleted";
    }

    public static Map<String, String> warnMap = null;

    /**
     * 异常信息说明：WARN=0,无异常；WARN=1,电流异常；WARN=2,电压异常；WARN=5,送丝速度异常；
     * WARN=10,焊速异常； WARN=3,电流、电压异常；WARN=6,电流、送丝速度异常；
     * WARN=11,电流、焊速异常；WARN=7,电压、送丝速度异常；WARN=12,电压、焊速异常；
     * WARN=15,送丝速度、焊速异常；WARN=8,电流、电压、送丝速度异常；WARN=13,电流、电压、焊速异常；
     * WARN=16,电流、送丝速度、焊速异常；WARN=17,电压、送丝速度、焊速异常；
     * WARN=18,电流、电压、送丝速度、焊速异常；
     */
//    static {
//        warnMap = new HashMap<>();
//        warnMap.put("0", 1);
//        warnMap.put("1", 1);
//        warnMap.put("OTHER", new String[]{"23", "260"});
//    }

}

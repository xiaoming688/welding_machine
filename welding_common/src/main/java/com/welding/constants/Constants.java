package com.welding.constants;

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

    public static class LOG {
        public static final String ADD = "add";
        public static final String LOGIN = "login";
        public static final String LOGOUT = "logout";
    }

    public static class User {
        public static final String BLOCKED = "blocked";
        public static final String DELETED = "deleted";
    }
}

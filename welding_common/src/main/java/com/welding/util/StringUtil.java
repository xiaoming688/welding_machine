package com.welding.util;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author MM
 */
public class StringUtil extends StringUtils {

    private static Pattern INTEGER_PATTERN = Pattern.compile("^[-\\+]?[0-9]+$");
    private static Pattern NUERIC_PATTERN = Pattern.compile("[0-9]+");

    /**
     * 首字母大写
     *
     * @return 首字目大写
     */
    public static String firstCharUpperCase(String s) {
        StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
        sb.append(s.substring(1, s.length()));
        return sb.toString();
    }


    /**
     * 是否是一个整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        return INTEGER_PATTERN.matcher(str).matches();
    }

    /**
     * 是否是纯数字
     *
     * @param str
     * @return
     */
    public static boolean isPureNumeric(String str) {
        if (str == null) {
            return false;
        }
        return NUERIC_PATTERN.matcher(str).matches();
    }


    /**
     * 一次性判断多个或单个对象为空。
     *
     * @param objects
     * @return 只要有一个元素为Blank，则返回true
     * @author zhou-baicheng
     */
    public static boolean isBlank(Object... objects) {
        Boolean result = false;
        for (Object object : objects) {
            if (null == object || "".equals(object.toString().trim())
                    || "null".equals(object.toString().trim())) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static String getRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val.toLowerCase();
    }

    /**
     * 一次性判断多个或单个对象不为空。
     *
     * @param objects
     * @return 只要有一个元素不为Blank，则返回true
     * @author zhou-baicheng
     */
    public static boolean isNotBlank(Object... objects) {
        return !isBlank(objects);
    }

    public static boolean isBlank(String... objects) {
        Object[] object = objects;
        return isBlank(object);
    }

    public static boolean isNotBlank(String... objects) {
        Object[] object = objects;
        return !isBlank(object);
    }

    public static boolean isBlank(String str) {
        Object object = str;
        return isBlank(object);
    }

    public static boolean isNotBlank(String str) {
        Object object = str;
        return !isBlank(object);
    }

    /**
     * 判断一个字符串在数组中存在几个
     *
     * @param baseStr
     * @param strings
     * @return
     */
    public static int indexOf(String baseStr, String[] strings) {

        if (null == baseStr || baseStr.length() == 0 || null == strings)
            return 0;

        int i = 0;
        for (String string : strings) {
            boolean result = baseStr.equals(string);
            i = result ? ++i : i;
        }
        return i;
    }


    public static String trimToEmpty(Object str) {
        return (isBlank(str) ? "" : str.toString().trim());
    }



    /**
     * 把Map转换成get请求参数类型,如 {"name"=20,"age"=30} 转换后变成 name=20&age=30
     *
     * @param map
     * @return
     */
    public static String mapToGet(Map<? extends Object, ? extends Object> map) {
        String result = "";
        if (map == null || map.size() == 0) {
            return result;
        }
        Set<? extends Object> keys = map.keySet();
        for (Object key : keys) {
            result += ((String) key + "=" + (String) map.get(key) + "&");
        }

        return isBlank(result) ? result : result.substring(0, result.length() - 1);
    }

    /**
     * 把一串参数字符串,转换成Map 如"?a=3&b=4" 转换为Map{a=3,b=4}
     *
     * @param args
     * @return
     */
    public static Map<String, ? extends Object> getToMap(String args) {
        if (isBlank(args)) {
            return null;
        }
        args = args.trim();
        //如果是?开头,把?去掉
        if (args.startsWith("?")) {
            args = args.substring(1, args.length());
        }
        String[] argsArray = args.split("&");

        Map<String, Object> result = new HashMap<String, Object>();
        for (String ag : argsArray) {
            if (!isBlank(ag) && ag.indexOf("=") > 0) {

                String[] keyValue = ag.split("=");
                //如果value或者key值里包含 "="号,以第一个"="号为主 ,如  name=0=3  转换后,{"name":"0=3"}, 如果不满足需求,请勿修改,自行解决.

                String key = keyValue[0];
                String value = "";
                for (int i = 1; i < keyValue.length; i++) {
                    value += keyValue[i] + "=";
                }
                value = value.length() > 0 ? value.substring(0, value.length() - 1) : value;
                result.put(key, value);

            }
        }

        return result;
    }

    /**
     * 转换成Unicode
     *
     * @param str
     * @return
     */
    public static String toUnicode(String str) {
        String as[] = new String[str.length()];
        String s1 = "";
        for (int i = 0; i < str.length(); i++) {
            int v = str.charAt(i);
            if (v >= 19968 && v <= 171941) {
                as[i] = Integer.toHexString(str.charAt(i) & 0xffff);
                s1 = s1 + "\\u" + as[i];
            } else {
                s1 = s1 + str.charAt(i);
            }
        }
        return s1;
    }

    /**
     * 合并数据
     *
     * @param v
     * @return
     */
    public static String merge(Object... v) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < v.length; i++) {
            sb.append(v[i]);
        }
        return sb.toString();
    }

    /**
     * 字符串转urlcode
     *
     * @param value
     * @return
     */
    public static String strToUrlcode(String value) {
        try {
            value = java.net.URLEncoder.encode(value, "utf-8");
            return value;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * urlcode转字符串
     *
     * @param value
     * @return
     */
    public static String urlcodeToStr(String value) {
        try {
            value = java.net.URLDecoder.decode(value, "utf-8");
            return value;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断字符串是否包含汉字
     *
     * @param txt
     * @return
     */
    public static Boolean containsCN(String txt) {
        if (isBlank(txt)) {
            return false;
        }
        for (int i = 0; i < txt.length(); i++) {

            String bb = txt.substring(i, i + 1);

            boolean cc = Pattern.matches("[\u4E00-\u9FA5]", bb);
            if (cc)
                return cc;
        }
        return false;
    }

    /**
     * 去掉HTML代码
     *
     * @param news
     * @return
     */
    public static String removeHtml(String news) {
        String s = news.replaceAll("amp;", "").replaceAll("<", "<").replaceAll(">", ">");

        Pattern pattern = Pattern.compile("<(span)?\\sstyle.*?style>|(span)?\\sstyle=.*?>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(s);
        String str = matcher.replaceAll("");

        Pattern pattern2 = Pattern.compile("(<[^>]+>)", Pattern.DOTALL);
        Matcher matcher2 = pattern2.matcher(str);
        String strhttp = matcher2.replaceAll(" ");


        String regEx = "(((http|https|ftp)(\\s)*((\\:)|：))(\\s)*(//|//)(\\s)*)?"
                + "([\\sa-zA-Z0-9(\\.|．)(\\s)*\\-]+((\\:)|(:)[\\sa-zA-Z0-9(\\.|．)&%\\$\\-]+)*@(\\s)*)?"
                + "("
                + "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])"
                + "|([\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*)*[\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*[\\sa-zA-Z]*"
                + ")"
                + "((\\s)*(\\:)|(：)(\\s)*[0-9]+)?"
                + "(/(\\s)*[^/][\\sa-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*";
        Pattern p1 = Pattern.compile(regEx, Pattern.DOTALL);
        Matcher matchhttp = p1.matcher(strhttp);
        String strnew = matchhttp.replaceAll("").replaceAll("(if[\\s]*\\(|else|elseif[\\s]*\\().*?;", " ");


        Pattern patterncomma = Pattern.compile("(&[^;]+;)", Pattern.DOTALL);
        Matcher matchercomma = patterncomma.matcher(strnew);
        String strout = matchercomma.replaceAll(" ");
        String answer = strout.replaceAll("[\\pP‘’“”]", " ")
                .replaceAll("\r", " ").replaceAll("\n", " ")
                .replaceAll("\\s", " ").replaceAll("　", "");


        return answer;
    }

    /**
     * 把数组的空数据去掉
     *
     * @param array
     * @return
     */
    public static List<String> array2Empty(String[] array) {
        List<String> list = new ArrayList<String>();
        for (String string : array) {
            if (StringUtils.isNotBlank(string)) {
                list.add(string);
            }
        }
        return list;
    }

    /**
     * 把数组转换成set
     *
     * @param array
     * @return
     */
    public static Set<?> array2Set(Object[] array) {
        Set<Object> set = new TreeSet<Object>();
        for (Object id : array) {
            if (null != id) {
                set.add(id);
            }
        }
        return set;
    }

    /**
     * serializable toString
     *
     * @param serializable
     * @return
     */
    public static String toString(Serializable serializable) {
        if (null == serializable) {
            return null;
        }
        try {
            return (String) serializable;
        } catch (Exception e) {
            return serializable.toString();
        }
    }


    public static String joinLetter(String splitter, List<String> strLst) {
        String retStr = "";
        if (strLst.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (String s : strLst) {
                sb.append(s + splitter);
            }
            retStr = sb.toString().substring(0, sb.toString().length() - 1);
        }
        return retStr;
    }


    public static String filterEmoji(String source) {
        if (!StringUtils.isEmpty(source)) {
            Pattern emoji = Pattern.compile("[\\uE000-\\uEFFF]|[\\uD83C\\uDC04-\\uD83C\\uDE1A]|[\\uD83D\\uDC66-\\uD83D\\uDC69]|[\\uD83D\\uDC66\\uD83C\\uDFFB-\\uD83D\\uDC69\\uD83C\\uDFFF]|[\\uD83D\\uDE45\\uD83C\\uDFFB-\\uD83D\\uDE4F\\uD83C\\uDFFF]|[\\uD83C\\uDC00-\\uD83D\\uDFFF]|[\\uD83E\\uDD10-\\uD83E\\uDDC0]|[\\uD83D\\uDE00-\\uD83D\\uDE4F]|[\\uD83D\\uDE80-\\uD83D\\uDEF6]");
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("");
                return source;
            }
            return source;
        }

        return source;
    }

    public static String filterSpecial(String source) {
        if (!StringUtil.isEmpty(source)) {
            if (source.contains("�") || source.contains("✊")) {
                return source.replace("�", "").replace("✊", "");
            }
        }
        return source;
    }

    public static String substringByNum(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }


    public static List<String> splitByNum(String source, Integer length) {
        int size = source.length() / length;

        if (source.length() % length != 0) {
            size += 1;
        }
        List<String> list = new ArrayList<String>();

        for (int index = 0; index < size; index++) {
            String childStr = substringByNum(source, index * length, (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }


    public static boolean isEnglish(String charaString) {

        return charaString.matches("^[a-zA-Z]*");

    }

    public static boolean isPhone(String phone) {

        String regex = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }


}
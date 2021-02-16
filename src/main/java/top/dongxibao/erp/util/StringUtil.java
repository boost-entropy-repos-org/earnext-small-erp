package top.dongxibao.erp.util;

import java.util.Random;
import java.util.UUID;

public class StringUtil {


    public static String padleft(String s, int len, char c) {
        s = s.trim();
        StringBuilder d = new StringBuilder(len);
        int fill = len - s.length();
        while (fill-- > 0) {
            d.append(c);
        }
        d.append(s);
        return d.toString();
    }

    public static String padleft(int i, int len, char c) {
        String s = String.valueOf(i).trim();
        StringBuilder d = new StringBuilder(len);
        int fill = len - s.length();
        while (fill-- > 0) {
            d.append(c);
        }
        d.append(s);
        return d.toString();
    }

    public static String padright(String s, int len, char c) {
        s = s.trim();
        StringBuilder d = new StringBuilder(len);
        int fill = len - s.length();
        d.append(s);
        while (fill-- > 0) {
            d.append(c);
        }
        return d.toString();
    }

    public static String int2StaticLengthString(int len, char fillChar, int val) {
        String valStr = String.valueOf(val);
        String rst = "";
        for (int i = 0; i < len - valStr.length(); i++) {
            rst += fillChar;
        }
        rst += valStr;
        return rst;
    }

    /**
     *   /test/soedit ----> soedit
     * @param path
     * @return
     */
    public static String getLastPath(String path) {
        int i = path.lastIndexOf("/");
        return path.substring(i + 1);
    }

    /**
     * 移除字符串中最后一个字符
     * @param string
     * @param remove
     * @return
     */
    public static String removeLastCharInString(String string, String remove) {
        return string.substring(0, string.lastIndexOf(remove));
    }

/*    public static String parseDouble2StringOfFenInch(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        String val = df.format(d);
        val = val.replace(".", "");
        return val;
    }

    public static String parseDouble2StringOfF2(double d) {
        String val = "" + d;
        int dotIndex = val.indexOf(".");
        val = val.substring(0, dotIndex + 3);
        return val;
    }

    public static String parseDouble2StringOfF2_type2(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

    public static String parseDouble2StringOfFenWithoutZero(double d) {
        DecimalFormat df = new DecimalFormat("#");
        return df.format(d * 100);
    }*/

    public static String uniqueUUID() {
        return uniqueUUID(null);
    }

    public static String uniqueUUID(String dashReplacer) {
        UUID uuid = UUID.randomUUID();
        if (dashReplacer != null) {
            return uuid.toString().replace("-", dashReplacer);
        }
        return uuid.toString();
    }

    public static boolean whetherUUID(String uid) {
        if (!uid.contains("-")) {
            return false;
        }
        try {
            UUID.fromString(uid);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public static String firstCharUpperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String firstCharLowerCase(String s) {
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    //生成随机数
    public static String getFixLenthString(int strLength) {

        Random rm = new Random();

        // 获得随机数
        int pross = (int) ((1 + rm.nextDouble()) * Math.pow(10, strLength));

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }

    public static void main(String[] args) {
        System.out.println(getFixLenthString(7));

    }

//    public static String buildSign(Object bean, String md5Key) throws IOException, IllegalArgumentException,
//            IllegalAccessException {
//        List<String> toSignList = buildToSignArray(bean);
//        // 排序
//        String[] toSignArray = toSignList.toArray(new String[toSignList.size()]);
//        Arrays.sort(toSignArray);
//        StringBuilder build = new StringBuilder();
//        for (String s : toSignArray) {
//            build.append(s).append("&");
//        }
//        build.deleteCharAt(build.length() - 1);
//        //   XLog.d("待签名字串: %s", build.toString() + md5Key);
//        return MD5Util.getStringMD5String(build.toString() + md5Key, "UTF-8");
//        // return MD5Util.getStringMD5String(build.toString() + md5Key);
//    }
//
//    private static List<String> buildToSignArray(Object obj) throws IllegalArgumentException,
//            IllegalAccessException {
//        List<String> ret = new ArrayList<String>();
//        Class<?> clazz = obj.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field fn : fields) {
//            if ("sign".equals(fn.getName())) {
//                continue;
//            }
//            fn.setAccessible(true);
//            Object fnVal = fn.get(obj);
//            if (StringUtils.isEmpty(fnVal)) {
//                continue;
//            } else if (fn.getType().getClass().getName().startsWith("com.qdums.")) {
//                ret.add(fn.getName() + "=" + new Gson().toJson(fnVal));
//            } else {
//                ret.add(fn.getName() + "=" + fnVal);
//            }
//        }
//        return ret;
//    }

}

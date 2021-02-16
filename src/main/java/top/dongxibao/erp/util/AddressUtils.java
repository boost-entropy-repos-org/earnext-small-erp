package top.dongxibao.erp.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取地址类
 *
 * @author Dongxibao
 * @date 2021-1-11
 */
@Slf4j
public class AddressUtils {

    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        String address = UNKNOWN;
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        try {
            RestTemplate restTemplate = SpringUtils.getBean(RestTemplate.class);
            Map<String, Object> map = new HashMap<>();
            map.put("ip",ip);
            map.put("&json",true);
            String rspStr = restTemplate.getForObject(IP_URL, String.class, map);
            if (StrUtil.isEmpty(rspStr)) {
                log.error("获取地理位置异常 {}", ip);
                return UNKNOWN;
            }
            log.info("rspStr接口返回：{}", rspStr);
            rspStr = rspStr.replace("if(window.IPCallBack) {IPCallBack(", "").trim();
            rspStr = rspStr.substring(0, rspStr.length() - 3);
            Map<String, String> json2Map = JsonUtils.json2Map(rspStr, String.class, String.class);
            String region = json2Map.get("pro");
            String city = json2Map.get("city");
            return String.format("%s %s", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}; {}", ip, e);
        }
        return address;
    }
}

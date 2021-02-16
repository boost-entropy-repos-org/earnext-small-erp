package top.dongxibao.erp.common;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理时间转换问题
 *
 * @author Dongxibao
 * @date 2020-11-27
 **/
@Component
public class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 封装排序
     * @param orderByColumn
     * @param isAsc
     * @return
     */
    public String packOrderBy(String orderByColumn, String isAsc) {
        if (StrUtil.isEmpty(orderByColumn)) {
            return " a.create_time " + isAsc;
        }
        return "a." + StrUtil.toUnderlineCase(orderByColumn) + " " + isAsc;
    }
}

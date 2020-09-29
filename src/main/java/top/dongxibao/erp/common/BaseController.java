package top.dongxibao.erp.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import top.dongxibao.erp.util.StrUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理时间转换问题
 *
 * @author Dongxibao
 * @date 2020-06-07
 **/
@Component
public class BaseController {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
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
        if (StringUtils.isEmpty(orderByColumn)) {
            return " a.create_time " + isAsc;
        }
        return "a." + StrUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }
}

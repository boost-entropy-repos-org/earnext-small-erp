package top.dongxibao.erp.mapper.system;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 *@ClassName ActivitiMapper
 *@Description ActivitiMapper
 *@Author Dongxibao
 *@Date 2021/1/26
 *@Version 1.0
 */
public interface ActivitiMapper {

    /**
     * 通用查询
     * @param sql
     * @return
     */
    Map select(@Param("sql") String sql);

    /**
     * 通用修改
     * @param updateSql
     * @return
     */
    int update(@Param("sql") String updateSql);
}

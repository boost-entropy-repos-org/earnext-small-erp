package top.dongxibao.erp.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import top.dongxibao.erp.entity.system.SysDept;
import top.dongxibao.erp.entity.system.SysDept;

import java.util.List;

/**
 * 部门Service 接口
 * 
 * @author Dongxibao
 * @date 2020-06-14
 */
public interface SysDeptService extends IService<SysDept> {
    /**
     * 根据条件查询
     * @param sysDept
     * @return
     */
    List<SysDept> selectByCondition(SysDept sysDept);

    /**
     * 树形结构
     * @return
     */
    List<SysDept> selectTree();

    boolean save(SysDept entity);

    boolean updateById(SysDept entity);
}

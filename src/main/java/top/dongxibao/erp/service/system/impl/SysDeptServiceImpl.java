package top.dongxibao.erp.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dongxibao.erp.entity.system.SysDept;
import top.dongxibao.erp.entity.system.SysDept;
import top.dongxibao.erp.mapper.system.SysDeptMapper;
import top.dongxibao.erp.service.system.SysDeptService;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门Service业务层处理
 *
 * @author Dongxibao
 * @date 2020-06-14
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Override
    public List<SysDept> selectByCondition(SysDept sysDept) {
        return baseMapper.selectByCondition(sysDept);
    }

    @Override
    public List<SysDept> selectTree() {
        // 顶级parentId为0
        List<SysDept> sysDepts = baseMapper.selectList(null);
        return buildTree(sysDepts);
    }

    // TODO：新增修改 祖级节点修改待验证
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(SysDept entity) {
        // 父节点
        SysDept parent = baseMapper.selectById(entity.getParentId());
        if (parent == null) {
        } else if (parent.getAncestors() != null) {
            entity.setAncestors(parent.getAncestors() + "," + entity.getParentId());
        } else {
            entity.setAncestors(String.valueOf(entity.getParentId()));
        }
        return super.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(SysDept entity) {
        // 修改后的父节点信息
        SysDept newParentDept = baseMapper.selectById(entity.getParentId());
        // 修改前的部门信息
        SysDept oldDept = baseMapper.selectById(entity.getId());
        if (newParentDept != null && oldDept != null) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getId();
            String oldAncestors = oldDept.getAncestors();
            entity.setAncestors(newAncestors);
            updateDeptChildren(entity.getId(), newAncestors, oldAncestors);
        }
        return super.updateById(entity);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = baseMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            baseMapper.updateDeptChildren(children);
        }
    }

    /**
     * 使用递归方法建树
     * @param sysDeptList
     * @return
     */
    public static List<SysDept> buildTree(List<SysDept> sysDeptList) {
        List<SysDept> trees = new ArrayList<>();
        for (SysDept sysDept : sysDeptList) {
            if (0L == sysDept.getParentId()) {
                trees.add(findChildren(sysDept, sysDeptList));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param sysDeptList
     * @return
     */
    public static SysDept findChildren(SysDept sysDept, List<SysDept> sysDeptList) {
        for (SysDept it : sysDeptList) {
            if (sysDept.getId().equals(it.getParentId())) {
                if (sysDept.getChildren() == null) {
                    sysDept.setChildren(new ArrayList<>());
                }
                sysDept.getChildren().add(findChildren(it, sysDeptList));
            }
        }
        return sysDept;
    }
}

package top.dongxibao.erp.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dongxibao.erp.entity.system.SysDept;
import top.dongxibao.erp.entity.system.TreeSelect;
import top.dongxibao.erp.mapper.system.SysDeptMapper;
import top.dongxibao.erp.mapper.system.SysRoleMapper;
import top.dongxibao.erp.service.system.SysDeptService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门表
 *
 * @author Dongxibao
 * @date 2021-01-12
 */
@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysDept getById(Long id) {
        SysDept sysDept = sysDeptMapper.getById(id);
        return sysDept;
    }

    @Override
    public List<SysDept> selectByCondition(SysDept sysDept) {
        return sysDeptMapper.selectByCondition(sysDept);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysDept save(SysDept sysDept) {
        sysDeptMapper.insert(sysDept);
        return sysDept;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysDept updateById(SysDept sysDept) {
        sysDeptMapper.update(sysDept);
        return sysDept;
    }

    @Override
    public boolean removeById(Long id) {
        return sysDeptMapper.deleteById(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(List<Long> idList) {
        return sysDeptMapper.deleteBatchIds(idList) > 0;
    }

    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<Long> selectDeptListByRoleId(Long roleId) {
        return sysDeptMapper.selectDeptListByRoleId(roleId);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> returnList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        for (SysDept dept : depts) {
            tempList.add(dept.getId());
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext(); ) {
            SysDept dept = iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext()) {
            SysDept n = it.next();
            if (n.getParentId() != null && n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

}

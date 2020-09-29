package top.dongxibao.erp.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.dongxibao.erp.entity.system.SysMenu;
import top.dongxibao.erp.entity.system.SysMenu;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.mapper.system.SysMenuMapper;
import top.dongxibao.erp.service.system.SysMenuService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单权限Service业务层处理
 *
 * @author Dongxibao
 * @date 2020-06-13
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> selectByCondition(SysMenu sysMenu) {
        return baseMapper.selectByCondition(sysMenu);
    }

    @Override
    public Set<String> selectMenuPermsByUserId(Long id) {
        List<String> perms = baseMapper.selectMenuPermsByUserId(id);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public boolean save(SysMenu entity) {
        // 校验重复
        List<SysMenu> sysMenus = baseMapper.checkSysMenu(entity);
        if (CollectionUtils.isNotEmpty(sysMenus)) {
            throw new ServiceException(entity.getPermsCode() + " 已存在", 400);
        }
        return super.save(entity);
    }

    @Override
    public boolean updateById(SysMenu entity) {
        // 校验重复
        List<SysMenu> sysMenus = baseMapper.checkSysMenu(entity);
        if (CollectionUtils.isNotEmpty(sysMenus)) {
            throw new ServiceException(entity.getPermsCode() + " 已存在", 400);
        }
        return super.updateById(entity);
    }
}

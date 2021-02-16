package top.dongxibao.erp.controller.system;

import top.dongxibao.erp.annotation.Log;
import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.common.ResultPage;
import top.dongxibao.erp.entity.system.SysRole;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.service.system.SysRoleService;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;
import java.util.List;

/**
 * 角色信息表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
@Slf4j
@Api(tags = "角色信息表")
@RestController
@RequestMapping("sys/role")
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询角色信息表
     */
    @ApiOperation("查询角色信息表列表")
    @GetMapping
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "desc") String isAsc,
                       @RequestParam(value = "roleName", required = false) String roleName,
                       @RequestParam(value = "roleCode", required = false) String roleCode,
                       @RequestParam(value = "status", required = false) Integer status,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        SysRole sysRole = new SysRole();
        sysRole.setBeginTime(beginTime);
        sysRole.setEndTime(endTime);
        if (StrUtil.isNotBlank(roleName)) {
            sysRole.setRoleName("%" + roleName + "%");
        }
        if (StrUtil.isNotBlank(roleCode)) {
            sysRole.setRoleCode("%" + roleCode + "%");
        }
        sysRole.setStatus(status);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SysRole> list = sysRoleService.selectByCondition(sysRole);
        return Result.ok(ResultPage.restPage(list));
    }

    /**
     * 获取角色信息表详细信息
     */
    @ApiOperation("获取角色信息表详细信息")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return Result.ok(sysRoleService.getById(id));
    }

    /**
     * 新增角色信息表
     */
    @Log(title = "角色信息", businessType = BusinessType.INSERT)
    @ApiOperation("新增角色信息表")
    @PostMapping
    public Result insert(@RequestBody SysRole sysRole) {
        if (sysRoleService.checkSysRoleExist(sysRole)) {
            throw new ServiceException("新增角色'" + sysRole.getRoleCode() + "'失败，角色权限已存在");
        }
        sysRole = sysRoleService.save(sysRole);
        return Result.ok(sysRole);
    }

    /**
     * 修改角色信息表
     */
    @Log(title = "角色信息", businessType = BusinessType.UPDATE)
    @ApiOperation("修改角色信息表")
    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Long id,
                         @RequestBody SysRole sysRole) {
        sysRole.setId(id);
        if (sysRoleService.checkSysRoleExist(sysRole)) {
            throw new ServiceException("修改角色'" + sysRole.getRoleCode() + "'失败，角色权限已存在");
        }
        sysRole = sysRoleService.updateById(sysRole);
        return Result.ok(sysRole);
    }

    /**
     * 删除角色信息表
     */
    @Log(title = "角色信息", businessType = BusinessType.DELETE)
    @ApiOperation("删除角色信息表")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        if (id == null) {
            return Result.fail("id为空");
        }
        boolean delete = sysRoleService.removeById(id);
        return Result.of(delete, null);
    }

    /**
     * 批量删除角色信息表
     *
     * @return 结果
     */
    @Log(title = "角色信息", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除角色信息表")
    @DeleteMapping("/deleteAll")
    public Result deleteAll(@RequestBody List<Long> idList) {
        if (idList == null || idList.size() < 1) {
            return Result.fail("id为空");
        }
        boolean delete = sysRoleService.removeByIds(idList);
        return Result.of(delete, null);
    }
}

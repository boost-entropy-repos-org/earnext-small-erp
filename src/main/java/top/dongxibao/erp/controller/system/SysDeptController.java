package top.dongxibao.erp.controller.system;

import top.dongxibao.erp.annotation.Log;
import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.common.ResultPage;
import top.dongxibao.erp.entity.system.SysDept;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.service.system.SysDeptService;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门表
 *
 * @author Dongxibao
 * @date 2021-01-12
 */
@Slf4j
@Api(tags = "部门表")
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends BaseController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 查询部门表
     */
    @ApiOperation("查询部门表列表")
    @GetMapping
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                       @RequestParam(value = "deptCode", required = false) String deptCode,
                       @RequestParam(value = "deptName", required = false) String deptName,
                       @RequestParam(value = "leader", required = false) String leader,
                       @RequestParam(value = "status", required = false) Integer status,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "desc") String isAsc,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        SysDept sysDept = new SysDept();
        sysDept.setBeginTime(beginTime);
        sysDept.setEndTime(endTime);
        if (StrUtil.isNotBlank(deptCode)) {
            sysDept.setDeptCode("%" + deptCode + "%");
        }
        if (StrUtil.isNotBlank(deptName)) {
            sysDept.setDeptName("%" + deptName + "%");
        }
        if (StrUtil.isNotBlank(leader)) {
            sysDept.setLeader("%" + leader + "%");
        }
        sysDept.setStatus(status);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SysDept> list = sysDeptService.selectByCondition(sysDept);
        return Result.ok(ResultPage.restPage(list));
    }

    /**
     * 查询获取部门下拉树列表
     */
    @ApiOperation("查询获取部门下拉树列表")
    @GetMapping("/tree_select")
    public Result treeSelect(SysDept dept) {
        List<SysDept> depts = sysDeptService.selectByCondition(dept);
        return Result.ok(sysDeptService.buildDeptTreeSelect(depts));
    }

    /**
     * 获取部门下拉树列表
     */
    @ApiOperation("获取部门下拉树列表")
    @GetMapping("/tree_list")
    public Result treeList(SysDept dept) {
        List<SysDept> depts = sysDeptService.selectByCondition(dept);
        return Result.ok(sysDeptService.buildDeptTree(depts));
    }

    /**
     * 加载对应角色部门列表树
     */
    @GetMapping("/role_dept_tree/{roleId}")
    public Result roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysDept> depts = sysDeptService.selectByCondition(new SysDept());
        Map<String, Object> map = new HashMap<>();
        map.put("checkedKeys", sysDeptService.selectDeptListByRoleId(roleId));
        map.put("depts", sysDeptService.buildDeptTreeSelect(depts));
        return Result.ok(map);
    }

    /**
     * 获取部门表详细信息
     */
    @ApiOperation("获取部门表详细信息")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return Result.ok(sysDeptService.getById(id));
    }

    /**
     * 新增部门表
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @ApiOperation("新增部门表")
    @PostMapping
    public Result insert(@RequestBody SysDept sysDept) {
        sysDept = sysDeptService.save(sysDept);
        return Result.ok(sysDept);
    }

    /**
     * 修改部门表
     */
    @ApiOperation("修改部门表")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Long id,
                         @RequestBody SysDept sysDept) {
        sysDept.setId(id);
        sysDept = sysDeptService.updateById(sysDept);
        return Result.ok(sysDept);
    }

    /**
     * 删除部门表
     */
    @ApiOperation("删除部门表")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        if (id == null) {
            return Result.fail("id为空");
        }
        boolean delete = sysDeptService.removeById(id);
        return Result.of(delete, null);
    }

    /**
     * 批量删除部门表
     * @return 结果
     */
    @ApiOperation("批量删除部门表")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping
    public Result deleteAll(@RequestBody List<Long> idList) {
        if (idList == null || idList.size() < 1) {
            return Result.fail("id为空");
        }
        boolean delete = sysDeptService.removeByIds(idList);
        return Result.of(delete, null);
    }
}

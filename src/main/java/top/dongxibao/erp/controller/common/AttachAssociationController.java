package top.dongxibao.erp.controller.common;

import java.util.List;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.annotation.Log;
import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.common.ResultPage;
import top.dongxibao.erp.entity.common.AttachAssociation;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.service.common.AttachAssociationService;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;

/**
 * 附件关联Controller
 *
 * @author Dongxibao
 * @date 2020-07-05
 */
@Api(tags = "附件关联")
@RestController
@RequestMapping("/common/association")
public class AttachAssociationController extends BaseController {
    @Autowired
    private AttachAssociationService attachAssociationService;

    /**
     * 查询附件关联列表
     */
    @ApiOperation("查询附件关联列表")
    @PreAuthorize("@ss.hasPermi('common:association:page')")
    @GetMapping("/page")
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(value = "associationId") Long associationId,
                       @RequestParam(value = "attachType", required = false, defaultValue = "0") Integer attachType,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "asc") String isAsc,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        AttachAssociation attachAssociation = new AttachAssociation();
        attachAssociation.setBeginTime(beginTime);
        attachAssociation.setEndTime(endTime);
        attachAssociation.setAssociationId(associationId);
        attachAssociation.setAttachType(attachType);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));

        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<AttachAssociation> list = attachAssociationService.selectByCondition(attachAssociation);
        return Result.ok(ResultPage.restPage(list));
    }

    /**
     * 获取附件关联详细信息
     */
    @ApiOperation("获取附件关联详细信息")
    @PreAuthorize("@ss.hasPermi('common:association:get')")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return Result.ok(attachAssociationService.getById(id));
    }

    /**
     * 新增附件关联
     */
    @Log(title = "附件关联模块", businessType = BusinessType.INSERT)
    @ApiOperation("新增附件关联")
    @PreAuthorize("@ss.hasPermi('common:association:insert')")
    @PostMapping
    public Result insert(@RequestBody AttachAssociation attachAssociation) {
        AttachAssociation insert = attachAssociationService.save(attachAssociation);
        return Result.ok(insert);
    }

    /**
     * 修改附件关联
     */
    @Log(title = "附件关联模块", businessType = BusinessType.UPDATE)
    @ApiOperation("修改附件关联")
    @PreAuthorize("@ss.hasPermi('common:association:update')")
    @PutMapping("/{id}")
    public Result update(@RequestBody AttachAssociation attachAssociation, @PathVariable("id") Long id) {
        attachAssociation.setId(id);
        AttachAssociation update = attachAssociationService.updateById(attachAssociation);
        return Result.ok(update);
    }

    /**
     * 删除附件关联
     */
    @Log(title = "附件关联模块", businessType = BusinessType.DELETE)
    @ApiOperation("删除附件关联")
    @PreAuthorize("@ss.hasPermi('common:association:delete')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        boolean delete = attachAssociationService.removeById(id);
        return Result.of(delete, null);
    }
}

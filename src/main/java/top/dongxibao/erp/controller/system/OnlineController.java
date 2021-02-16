package top.dongxibao.erp.controller.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.common.Page;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.constant.SecurityConstants;
import top.dongxibao.erp.security.service.OnlineUserService;

import java.util.Set;

/**
 * @author Dongxibao
 * @date 2021-01-12
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/online")
@Api(tags = "系统：在线用户管理")
public class OnlineController {

    private final OnlineUserService onlineUserService;

    @ApiOperation("查询在线用户")
    @GetMapping
    public Result query(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                        @RequestParam(value = "filter", required = false) String filter) {
        return Result.ok(new Page<>(onlineUserService.getAll(filter), pageNum, pageSize));
    }

    @ApiOperation("踢出用户")
    @DeleteMapping
    public Result delete(@RequestBody Set<String> keys) {
        for (String key : keys) {
            onlineUserService.kickOut(SecurityConstants.LOGIN_TOKEN_KEY + key);
        }
        return Result.ok();
    }
}

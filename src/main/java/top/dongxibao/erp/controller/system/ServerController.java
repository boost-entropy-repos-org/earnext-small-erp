package top.dongxibao.erp.controller.system;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.entity.server.ServerUtil;

/**
 * 服务器监控
 *
 * @author Dongxibao
 * @date 2020-07-11
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {
    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping()
    public Result getInfo() throws Exception {
        ServerUtil server = new ServerUtil();
        server.copyTo();
        return new Result(server);
    }
}

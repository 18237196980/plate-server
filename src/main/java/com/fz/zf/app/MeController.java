package com.fz.zf.app;


import com.fz.zf.model.common.Constast;
import com.fz.zf.util.ApiResult;
import com.fz.zf.util.RedisUtil;
import com.fz.zf.util.UserInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@Slf4j
public class MeController extends BaseController {

    @Autowired
    RedisUtil redisUtil;

    @PostMapping("logout")
    public ApiResult logout() {
        try {
            String userId = UserInfoUtils.getCurrentUserId();
            redisUtil.del(Constast.ZF_TOKEN + userId);
            return ApiResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error();
        }
    }

}

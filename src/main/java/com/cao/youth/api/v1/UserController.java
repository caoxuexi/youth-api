/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://talelin.com
 * @免费专栏 $ http://course.talelin.com
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2019-07-07 00:55
 */
package com.cao.youth.api.v1;

import com.cao.youth.core.interceptors.ScopeLevel;
import com.cao.youth.dto.SuccessDTO;
import com.cao.youth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/wx_info")
    @ScopeLevel
    public SuccessDTO updateUserInfo(@RequestBody Map<String,Object> user) {
        userService.updateUserWxInfo(user);
        return new SuccessDTO();
    }
}

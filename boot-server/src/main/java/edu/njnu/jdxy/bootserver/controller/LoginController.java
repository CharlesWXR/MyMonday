package edu.njnu.jdxy.bootserver.controller;

import edu.njnu.jdxy.bootserver.pojo.Result;
import edu.njnu.jdxy.bootserver.pojo.User;
import edu.njnu.jdxy.bootserver.service.UserService;
import edu.njnu.jdxy.bootserver.service.impl.UserServiceImpl;
import edu.njnu.jdxy.bootserver.utils.ResultCode;
import edu.njnu.jdxy.bootserver.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/exclude/login", method = RequestMethod.POST)
public class LoginController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("")
    public Result loginUser(@RequestParam("identity") String identity, @RequestParam("password") String password) {
        log.info("Controller: loginUser() invoked");
        User verified = userService.login(identity, password);
        if (verified != null) {
            String token = TokenUtil.sign(Integer.toString(verified.getId()), verified.getName());
            Map<String, Object> res = new HashMap<>();
            res.put("token", token);
            res.put("user", verified);
            return Result.success(res);
        }
        else {
            return Result.fail(ResultCode.USER_NOT_FOUND, null);
        }
    }
}

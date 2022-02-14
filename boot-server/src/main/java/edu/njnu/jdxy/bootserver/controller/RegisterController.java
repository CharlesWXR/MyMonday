package edu.njnu.jdxy.bootserver.controller;

import edu.njnu.jdxy.bootserver.pojo.Result;
import edu.njnu.jdxy.bootserver.pojo.User;
import edu.njnu.jdxy.bootserver.service.UserService;
import edu.njnu.jdxy.bootserver.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/exclude/reg", method = RequestMethod.POST)
public class RegisterController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("")
    public Result regUser(@RequestBody Map<String, Object> params) {
        User user = new User();

        List<String> removeKeys = new ArrayList<>();
        for (String key : params.keySet()) {
            String s = (String)params.get(key);
            if (s == null || s.length() == 0)
                removeKeys.add(key);
        }
        for (String key : removeKeys) {
            params.remove(key);
        }
        log.info(params.toString());
        try {
            BeanUtils.populate(user, params);
        } catch (Exception e) {
            log.error("Controller: /exclude/reg: failed to populate user: {} \n {}", params.toString(), e.getMessage());
            return Result.fail(ResultCode.BAD_REQUEST, null);
        }

        int id = userService.register(user);
        if (-1 == id) {
            log.warn("Controller: /exclude/reg: failed to register user: {}", user.toString());
            return Result.fail(ResultCode.USER_EXIST, null);
        }
        else {
            log.info("Controller: /exclude/reg: registered successfully userID: {}", id);
            Map<String, Integer> res = new HashMap<String, Integer>();
            res.put("ID", id);
            return Result.success(res);
        }
    }
}

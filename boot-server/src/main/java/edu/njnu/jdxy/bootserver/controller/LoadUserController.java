package edu.njnu.jdxy.bootserver.controller;

import edu.njnu.jdxy.bootserver.pojo.Result;
import edu.njnu.jdxy.bootserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/user")
class LoadUserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result searchUser(@RequestParam("identity") String identity) {
        log.info("Controller: searchUser invoked!");
        return Result.success(userService.searchUsers(identity));
    }
}

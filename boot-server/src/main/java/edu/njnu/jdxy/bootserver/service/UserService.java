package edu.njnu.jdxy.bootserver.service;

import edu.njnu.jdxy.bootserver.pojo.MiniUser;
import edu.njnu.jdxy.bootserver.pojo.User;

import java.util.List;

public interface UserService {
    User login(String identity, String password);

    int register(User user);

    List<MiniUser> searchUsers(String identity);
}

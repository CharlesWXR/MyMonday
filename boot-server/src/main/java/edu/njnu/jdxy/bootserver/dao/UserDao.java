package edu.njnu.jdxy.bootserver.dao;

import edu.njnu.jdxy.bootserver.pojo.MiniUser;
import edu.njnu.jdxy.bootserver.pojo.User;

import java.util.List;

public interface UserDao {
    User validateUserByName(String userName, String password);

    User validateUserByEmail(String userEmail, String password);

    User validateUserByStaffId(String staff_id, String password);

    MiniUser queryForMiniUserBuID(int id);

    int insertUser(User user);

    List<MiniUser> searchUserByName(String userName);

    List<MiniUser> searchUserByStaffId(String staff_id);
}

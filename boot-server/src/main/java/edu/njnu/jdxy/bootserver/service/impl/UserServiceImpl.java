package edu.njnu.jdxy.bootserver.service.impl;

import edu.njnu.jdxy.bootserver.dao.PrivilegeDao;
import edu.njnu.jdxy.bootserver.dao.UserDao;
import edu.njnu.jdxy.bootserver.dao.impl.PrivilegeDaoImpl;
import edu.njnu.jdxy.bootserver.dao.impl.UserDaoImpl;
import edu.njnu.jdxy.bootserver.pojo.MiniUser;
import edu.njnu.jdxy.bootserver.pojo.Privilege;
import edu.njnu.jdxy.bootserver.pojo.User;
import edu.njnu.jdxy.bootserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    @Override
    public User login(String identity, String password) {
        User verified = null;
        if ((verified = userDao.validateUserByName(identity, password)) == null
            && (verified = userDao.validateUserByStaffId(identity, password)) == null
            && (verified = userDao.validateUserByEmail(identity, password)) == null) {
            return null;
        }
        else {
            List<Privilege> privileges = privilegeDao.getPrivilegesByUserId(verified.getId());
            verified.setPrivileges(privileges);
            return verified;
        }
    }

    @Override
    public int register(User user) {
        user.setDepartment_id(1);
        return userDao.insertUser(user);
    }

    @Override
    public List<MiniUser> searchUsers(String identity) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(identity);
        if (m.matches()) {
            return userDao.searchUserByStaffId(identity);
        }
        else {
            return userDao.searchUserByName(identity);
        }
    }


}

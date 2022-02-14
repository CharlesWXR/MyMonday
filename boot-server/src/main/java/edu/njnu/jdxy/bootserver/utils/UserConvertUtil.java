package edu.njnu.jdxy.bootserver.utils;

import edu.njnu.jdxy.bootserver.pojo.MiniUser;
import edu.njnu.jdxy.bootserver.pojo.User;

public class UserConvertUtil {
    static public MiniUser convert2MiniUser(User user) {
        MiniUser miniUser = new MiniUser();
        if (null != user) {
            miniUser.setId(user.getId());
            miniUser.setName(user.getName());
            miniUser.setStaff_id(user.getStaff_id());
            miniUser.setEmail(user.getEmail());
            miniUser.setQq(user.getQq());
            miniUser.setPhone(user.getPhone());
            miniUser.setAvatar_path(user.getAvatar_path());
            miniUser.setDepartment_id(user.getDepartment_id());
        }
        else
            miniUser.setId(-1);
        return miniUser;
    }
}

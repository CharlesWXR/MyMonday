package edu.njnu.jdxy.bootserver.dao;

import edu.njnu.jdxy.bootserver.pojo.UpdateReply;

import java.util.List;

public interface UpdateReplyDao {
    List<UpdateReply> getRepliesByUpdateID(int updateID);
}

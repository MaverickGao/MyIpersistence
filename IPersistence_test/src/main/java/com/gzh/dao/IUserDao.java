package com.gzh.dao;

import com.gzh.pojo.User;

import java.util.List;

/**
 * dao层接口
 *
 * @author 高智恒
 */
public interface IUserDao {

    /**
     * 查询所有用户
     *
     * @return 所有用户集合
     */
    List<User> selectList() throws Exception;


    /**
     * 根据条件进行用户查询
     *
     * @param user 查询条件
     * @return 用户对象
     */
    User selectOne(User user) throws Exception;
}

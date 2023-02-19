package com.gzh.pojo;

/**
 * 用户表实体类
 *
 * @author 高智恒
 */
public class User {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }
}

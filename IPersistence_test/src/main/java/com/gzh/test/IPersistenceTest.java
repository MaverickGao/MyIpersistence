package com.gzh.test;

import com.gzh.dao.IUserDao;
import com.gzh.io.Resources;
import com.gzh.pojo.User;
import com.gzh.sqlSession.SqlSession;
import com.gzh.sqlSession.SqlSessionFactory;
import com.gzh.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {

    @Test
    public void test() {
        try {
            // 第一步：解析数据库配置文件
            InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
            SqlSession sqlSession = sqlSessionFactory.openSession();

            //调用
            User user = new User();
            user.setId(1);
            user.setUserName("gaozhiheng");

            IUserDao userDao = sqlSession.getMapper(IUserDao.class);

            List<User> all = userDao.selectList();
            for (User user1 : all) {
                System.out.println(user1.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

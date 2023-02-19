package com.gzh.sqlSession;

import com.gzh.pojo.Configuration;

/**
 * 默认SqlSessionFactory
 *
 * @author 高智恒
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}

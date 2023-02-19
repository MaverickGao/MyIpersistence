package com.gzh.sqlSession;

/**
 * sqlSession工厂
 *
 * @author 高智恒
 */
public interface SqlSessionFactory {

    /**
     * 生产Session
     *
     * @return SQLSession
     */
    SqlSession openSession();
}

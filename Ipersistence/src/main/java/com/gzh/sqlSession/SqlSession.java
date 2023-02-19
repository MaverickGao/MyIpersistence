package com.gzh.sqlSession;

import java.util.List;

/**
 * sqlSession接口
 *
 * @author 高智恒
 */
public interface SqlSession {

    /**
     * 查询所有接口
     *
     * @param statementId mapper唯一ID
     * @param params      入参
     * @param <E>         返回结果泛型
     * @return 返回查询结果列表
     */
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    /**
     * 根据条件查询单个数据
     *
     * @param statementId mapper唯一ID
     * @param params      入参
     * @param <T>         返回结果泛型
     * @return 返回查询结果
     */
    <T> T selectOne(String statementId, Object... params) throws Exception;


    /**
     * 为Dao接口生成代理实现类
     *
     * @param mapperClass 目标类
     * @param <T>         返回结果泛型
     * @return 实现类
     */
    <T> T getMapper(Class<?> mapperClass);
}

package com.gzh.sqlSession;

import com.gzh.pojo.Configuration;
import com.gzh.pojo.MappedStatement;

import java.util.List;

/**
 * sql执行器接口
 *
 * @author 高智恒
 */
public interface Executor {

    /**
     * 查询方法执行器
     *
     * @param configuration   {@link Configuration}
     * @param mappedStatement {@link MappedStatement}
     * @param params          传入参数
     * @param <E>             结果泛型
     * @return 查询结果
     */
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;
}

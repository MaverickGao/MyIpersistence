package com.gzh.sqlSession;

import com.gzh.pojo.Configuration;
import com.gzh.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

/**
 * 默认SqlSession
 *
 * @author 高智恒
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 查询所有接口
     *
     * @param statementId mapper唯一ID
     * @param params      入参
     * @param <E>         返回结果泛型
     * @return 返回查询结果列表
     */
    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        //将要去完成对SimpleExecutor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        // 根据statementId定位到Mapper的sql信息
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        // 执行查询动作
        return simpleExecutor.query(configuration, mappedStatement, params);
    }

    /**
     * 根据条件查询单个数据
     *
     * @param statementId mapper唯一ID
     * @param params      入参
     * @param <T>         返回结果泛型
     * @return 返回查询结果
     */
    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1) {
            return (T) objects.get(0);
        } else {
            throw new RuntimeException("查询结果为空或者返回结果过多");
        }
    }

    /**
     * 为Dao接口生成代理实现类
     *
     * @param mapperClass 目标类
     * @param <T>         返回结果泛型
     * @return 实现类
     */
    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用JDK动态代理 实例化Dao，并返回执行结果
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 底层都还是去执行JDBC代码 //根据不同情况，来调用 selectList 或者 selectOne
                // 准备参数 1：statementId :sql语句的唯一标识：namespace.id= 接口全限定名.方法名
                // 方法名：findAll
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;
                // 准备参数2：params:args
                // 获取被调用方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                // 判断是否进行了 泛型类型参数化
                if (genericReturnType instanceof ParameterizedType) {
                    return selectList(statementId, args);
                }
                return selectOne(statementId, args);
            }
        });
        return (T) proxyInstance;
    }
}

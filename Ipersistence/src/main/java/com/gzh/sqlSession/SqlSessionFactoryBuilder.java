package com.gzh.sqlSession;

import com.gzh.config.XMLConfigBuilder;
import com.gzh.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * SqlSession工厂构造器
 *
 * @author 高智恒
 */
public class SqlSessionFactoryBuilder {

    /**
     * 构造SqlSessionFactory
     *
     * @param inputStream 输入流
     * @return {@link SqlSessionFactory}
     */
    public SqlSessionFactory build(InputStream inputStream) throws PropertyVetoException, DocumentException {
        // 首先调用数据库资源解析构造器，解析出来数据库资源
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);

        // 创建sqlSessionFactory对象
        // 用于生产sqlSession，会话对象
        return new DefaultSqlSessionFactory(configuration);
    }
}

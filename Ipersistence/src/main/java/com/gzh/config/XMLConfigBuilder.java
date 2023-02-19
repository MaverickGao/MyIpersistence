package com.gzh.config;

import com.gzh.io.Resources;
import com.gzh.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * 数据库资源配置文件解析器
 *
 * @author 高智恒
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 解析配置文件，封装到Configuration对象中
     *
     * @param inputStream 读取配置文件为输入流
     * @return {@link Configuration}
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        // 读取配置文件输入流，准备解析
        Document document = new SAXReader().read(inputStream);
        // 读取根标签<configuration>
        Element rootElement = document.getRootElement();
        // 拿到<property>标签信息
        List<Element> list = rootElement.selectNodes("//property");
        // Properties类是以Map的形式承接数据库配置资源
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }
        // 将数据库资源放到C3P0数据库连接池
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        // 封装线程池
        configuration.setDataSource(comboPooledDataSource);

        // 将解析到的mapper.xml信息封装
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            // 拿到Mapper.xml的绝对路径，转化为输入流，放到Mapper.xml解析器中解析
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsSteam = Resources.getResourceAsSteam(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsSteam);
        }
        return configuration;
    }
}

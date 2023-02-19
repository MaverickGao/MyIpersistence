package com.gzh.config;

import com.gzh.pojo.Configuration;
import com.gzh.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * sqlMapper配置文件解析器
 *
 * @author 高智恒
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析Mapper.xml文件
     *
     * @param inputStream Mapper.xml文件转化为输入流
     */
    public void parse(InputStream inputStream) throws DocumentException {
        // 读取配置文件输入流，准备解析
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        // 拿到根标签 namespace 用来拼接唯一 statementId
        String namespace = rootElement.attributeValue("namespace");
        List<Element> list = rootElement.selectNodes("//select");
        for (Element element : list) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramterType = element.attributeValue("paramterType");
            String sqlText = element.getTextTrim();
            // 将解析到的sql信息封装到statement中
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sqlText);
            // 唯一statementId：namespace + "." + id
            configuration.getMappedStatementMap().put(namespace  + "." + id, mappedStatement);
        }
    }
}

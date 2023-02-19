package com.gzh.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * sqlMapConfig.xml解析封装类
 *
 * @author 高智恒
 */
public class Configuration {

    /**
     * 数据资源
     */
    private DataSource dataSource;

    /**
     * 解析后的Mapper数据
     * key: statementId
     * value: 封装好的MappedStatement对象
     */
    Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}

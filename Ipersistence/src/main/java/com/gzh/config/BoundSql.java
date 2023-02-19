package com.gzh.config;

import com.gzh.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * sql解析处理类
 *
 * @author 高智恒
 */
public class BoundSql {

    /**
     * 解析后的SQL
     */
    private String sqlText;

    /**
     * 解析后的入参集合
     */
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}

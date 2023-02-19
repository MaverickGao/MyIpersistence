package com.gzh.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 入参转换处理器
 *
 * @author 高智恒
 */
public class ParameterMappingTokenHandler implements TokenHandler {

    /**
     * 参数转换映射Map
     */
    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();

    /**
     * 转换 #{XXX}参数为?
     *
     * @param content 待处理
     * @return ?
     */
    @Override
    public String handleToken(String content) {
        parameterMappings.add(buildParameterMapping(content));
        return "?";
    }

    /**
     * 构造参数处理对象
     *
     * @param content 待处理
     * @return 构造对象
     */
    private ParameterMapping buildParameterMapping(String content) {
        return new ParameterMapping(content);
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}

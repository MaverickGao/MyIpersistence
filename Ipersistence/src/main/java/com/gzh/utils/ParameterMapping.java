package com.gzh.utils;

/**
 * 解析后的入参缓存
 *
 * @author 高智恒
 */
public class ParameterMapping {

    /**
     * 入参
     */
    private String content;

    public ParameterMapping(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

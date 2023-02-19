package com.gzh.utils;

/**
 * 占位符处理类接口
 *
 * @author 高智恒
 */
public interface TokenHandler {

    /**
     * 占位符处理
     *
     * @param content 待处理
     * @return 已处理
     */
    String handleToken(String content);
}

package com.gzh.io;

import java.io.InputStream;

/**
 * 资源加载模块
 *
 * @author 高智恒
 */
public class Resources {

    /**
     * 根据配置文件的路径，将配置文件加载为字节输入流，存储在内存中
     *
     * @param path 配置文件路径
     * @return 字节输入流
     */
    public static InputStream getResourceAsSteam(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}

package com.gzh.sqlSession;

import com.gzh.config.BoundSql;
import com.gzh.pojo.Configuration;
import com.gzh.pojo.MappedStatement;
import com.gzh.utils.GenericTokenParser;
import com.gzh.utils.ParameterMapping;
import com.gzh.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * sql执行器实现类
 *
 * @author 高智恒
 */
public class SimpleExecutor implements Executor {

    /**
     * 查询方法执行器
     *
     * @param configuration   {@link Configuration}
     * @param mappedStatement {@link MappedStatement}
     * @param params          传入参数
     * @param <E>             结果泛型
     * @return 查询结果
     */
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 执行器实际上就是JDBC的操作
        // 步骤一：注册驱动，获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();
        // 步骤二：获取SQL语句，还需要对#{}里面的值进行解析存储
        BoundSql boundSql = getBoundSql(mappedStatement.getSql());
        // 步骤三：获取预处理对象 preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        // 步骤四：设置参数
        // 获取到了参数的全路径
        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterTypeClass = getClassType(paramterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            // 反射
            Field declaredField = paramterTypeClass.getDeclaredField(content);
            // 暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);

            preparedStatement.setObject(i + 1, o);

        }


        // 步骤五：执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);

        List<Object> objects = new ArrayList<>();

        // 步骤六： 封装返回结果集
        while (resultSet.next()) {
            Object o = resultTypeClass.newInstance();
            //元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {

                // 字段名
                String columnName = metaData.getColumnName(i);
                // 字段的值
                Object value = resultSet.getObject(columnName);

                // 使用反射或者内省，根据数据库表和实体的对应关系，完成封装
                // 如果字段名包含下划线，将下划线去掉，并将后面首字母大写
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(parseUpper(columnName), resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);
            }
            objects.add(o);
        }
        return (List<E>) objects;
    }

    /**
     * 如果字段名包含下划线，将下划线去掉，并将后面首字母大写
     *
     * @param text 例如 user_name
     * @return userName
     */
    private String parseUpper(String text) {
        if (text.contains("_")) {
            String newText = "";
            String[] strings = text.split("_");
            for (int i = 0; i < strings.length; i++) {
                String thisText = strings[i];
                if (i > 0) {
                    thisText = thisText.substring(0, 1).toUpperCase() + thisText.substring(1);
                }
                newText = newText + thisText;
            }
            return newText;
        }
        return text;
    }

    /**
     * 对象转换
     *
     * @param paramterType
     * @return Class<?>
     * @throws ClassNotFoundException
     */
    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if (paramterType != null) {
            return Class.forName(paramterType);
        }
        return null;
    }

    /**
     * 完成对#{}的解析工作：
     * 1.将#{}使用？进行代替，
     * 2.解析出#{}里面的值进行存储
     *
     * @param sql 待解析sql
     * @return 解析后的SQL
     */
    private BoundSql getBoundSql(String sql) {
        // 标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        // 解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        // #{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(parseSql, parameterMappings);
    }
}

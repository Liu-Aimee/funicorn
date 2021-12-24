package com.funicorn.basic.common.datasource.filter;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aimme
 * @since 2021/1/27 10:32
 * mybatis-plus 模糊查询时，处理特殊字符 %、
 */
@SuppressWarnings("unused")
@Component
@Intercepts(
        { @Signature(type = Executor.class, method = "query", args =
                { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
                @Signature(type = Executor.class, method = "query", args =
                        { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }) })
public class QueryEscapeInterceptor implements Interceptor {

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final String ROOT_SQL_NODE = "sqlSource.rootSqlNode";
    private static final String FUZZY_QUERY_IDENTIFIER = "like";

    @Override
    @SneakyThrows
    public Object intercept(Invocation invocation) {
        Object parameter = invocation.getArgs()[1];
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        MetaObject metaMappedStatement = MetaObject.forObject(statement, DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        BoundSql boundSql = statement.getBoundSql(parameter);
        if (metaMappedStatement.hasGetter(ROOT_SQL_NODE))
        {
            SqlNode sqlNode = (SqlNode) metaMappedStatement.getValue(ROOT_SQL_NODE);
            getBoundSql(statement.getConfiguration(), boundSql.getParameterObject(), sqlNode);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof Executor ? Plugin.wrap(target, this) : target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    protected static void getBoundSql(Configuration configuration, Object parameterObject, SqlNode sqlNode)
    {
        DynamicContext context = new DynamicContext(configuration, parameterObject);
        sqlNode.apply(context);
        String contextSql = context.getSql();
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
        String sql = modifyLikeSql(contextSql, parameterObject);
        SqlSource sqlSource = sqlSourceParser.parse(sql, parameterType, context.getBindings());

        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        for (Map.Entry<String, Object> entry : context.getBindings().entrySet())
        {
            boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
        }
    }

    @SuppressWarnings("all")
    protected static String modifyLikeSql(String sql, Object parameterObject)
    {
        if (!sql.toLowerCase().contains(FUZZY_QUERY_IDENTIFIER))
        {
            return sql;
        }
        String reg = "\\bLIKE\\b.*\\#\\{\\b.*\\}";
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);

        List<String> replaceFiled = new ArrayList<>();

        while (matcher.find())
        {
            int n = matcher.groupCount();
            for (int i = 0; i <= n; i++)
            {
                String output = matcher.group(i);
                if (output != null)
                {
                    String key = getParameterKey(output);
                    if (!replaceFiled.contains(key))
                    {
                        replaceFiled.add(key);
                    }
                }
            }
        }
        // 修改参数
        MetaObject metaObject = MetaObject.forObject(parameterObject, DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        for (String key : replaceFiled)
        {
            Object val = metaObject.getValue(key);
            if (val instanceof String && (val.toString().contains("%") || val.toString().contains("_")))
            {
                String value = val.toString();
                //去掉第一个%
                value = value.substring(1);
                //去掉最后一个%
                value = value.substring(0,value.length() - 1);

                if (StringUtils.isNotBlank(value)){
                    value = value.replaceAll("%", "\\\\%")
                            .replaceAll("\\\\", "\\\\\\\\");

                    val = "%" + value + "%";
                }

                metaObject.setValue(key, val);
            }
        }
        return sql;
    }

    private static String getParameterKey(String input)
    {
        String key = "";
        String[] temp = input.split("#");
        if (temp.length > 1)
        {
            key = temp[1];
            key = key.replace("{", "").replace("}", "").split(",")[0];
        }
        return key.trim();
    }
}

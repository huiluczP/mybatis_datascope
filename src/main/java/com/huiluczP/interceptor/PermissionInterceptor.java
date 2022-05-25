package com.huiluczP.interceptor;

import com.huiluczP.Annotation.Permission;
import com.huiluczP.config.PermissionConfig;
import com.huiluczP.util.HttpUtil;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
@Component
public class PermissionInterceptor implements Interceptor {

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();

    @Autowired
    private PermissionConfig permissionConfig;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取sql信息
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        System.out.println("原sql为: " + sql);

        // 获取元数据
        MetaObject metaResultSetHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, REFLECTOR_FACTORY);
        MappedStatement mappedStatement = (MappedStatement) metaResultSetHandler.getValue("delegate.mappedStatement");

        // 获取调用方法
        String id = mappedStatement.getId();
        String className = id.substring(0, id.lastIndexOf("."));
        String methodName = id.substring(id.lastIndexOf(".") + 1);
        System.out.println("调用方法为: " + id);

        // 注解查询
        Class clazz = Class.forName(className);
        Method method = clazz.getDeclaredMethod(methodName);
        boolean needPermission = method.isAnnotationPresent(Permission.class);

        // 对注解方法进行权限处理
        if(needPermission){
            System.out.println("需要进行sql权限变化");

            // 获取权限信息
            HttpSession session = HttpUtil.getSession();
            String permission = (String) session.getAttribute("permission");
            Map<String, String> map = permissionConfig.getPermissionMap();
            for(String key:map.keySet()){
                System.out.println(key);
            }
            String canSelectPermission = null;
            if(map.containsKey(permission)){
                canSelectPermission = map.get(permission);
            }
            System.out.printf("当前权限:%s, 可查询范围:%s%n", permission, canSelectPermission);

            // 修改sql
            String newSql = String.format("select * from (%s) `range` where `range`.permission in (%s)", sql, canSelectPermission);
            // String newSql = "select * from account where permission in (\"advertise\")";
            System.out.println("修改后的sql为: " + newSql);
            // 反射修改handler中的sql以执行
            Class boundClass = boundSql.getClass();
            Field field = boundClass.getDeclaredField("sql");
            field.setAccessible(true);
            field.set(boundSql, newSql);
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}

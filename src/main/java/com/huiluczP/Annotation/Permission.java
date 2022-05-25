package com.huiluczP.Annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
// 指名数据库查询方法需要和权限挂钩
public @interface Permission {

}

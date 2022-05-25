# mybatis_datascope
Searching sql authentication demo achieved by Mybatis Interceptor.

利用MyBatis拦截器实现的sql查询权限动态修改Demo。</br>
主要情景是不同部门对账户信息有查询限制，希望能在不修改大量原查询接口代码的情况下进行查询权限范围的修改。</br>

主要流程:
1. 对Mapper的对应查询方法加上自定义注解@Permission
2. 拦截器类PermissionInterceptor对MyBatis执行进行拦截。
3. 根据元数据信息反射判断注解存在。
4. 获取配置文件中权限范围进行sql修改。
5. 反射注入新sql完成执行。

具体解析：[MyBatis拦截器实现sql查询权限动态修改](https://blog.csdn.net/qq_41733192/article/details/124972406)

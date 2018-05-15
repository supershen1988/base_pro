基于springside4.0的todoList并将项目升级为springboot项目，可作为后台管理系统的基础框架，功能包含用户管理、角色、权限管理等。

初始化方法：
1. 安装JDK(1.7+)并设置JAVA_HOME环境变量
2. 安装Maven(3.0+)并设置M2_HOME环境变量
3. 初始化mysql数据库,执行src/main/resources下的sql/data.sql和v.1.0.0.sql
4. 修改src/main/resources的application.properties中数据库配置
5. 运行startweb.bat
6. http://localhost:8080/base-admin

用户名：admin 密码：admin

涉及到的技术文档请参考原有springside4 官方wiki ：
https://github.com/springside/springside4/wiki/Home4.0

此项目可以说是springside4的springboot版本，此版本主要做了以下修改：
1. 将原有spring web项目更新为springboot项目；
2. 并将springside4中shiro的演示例子更新为实际项目需要的数据库可配权限；
3. 扩展了shiro标签；
4. 简单实现用户、角色、权限管理及用户登录修改密码。

后续迭代：
1. shiro用户登录密码更改为加密算法（现在是明文密码）；
2. 增加shiro权限控制，实现直接访问url提示“无权限”功能；
3. 做分支项目将spring data jpa 更改为mybatis版本；
4. 先想到这么多吧。

页面效果：

![Alt text](https://github.com/supershen1988/base_pro/blob/master/base-admin/readme/1.png)
![Alt text](https://github.com/supershen1988/base_pro/blob/master/base-admin/readme/2.png)
![Alt text](https://github.com/supershen1988/base_pro/blob/master/base-admin/readme/3.png)
![Alt text](https://github.com/supershen1988/base_pro/blob/master/base-admin/readme/4.png)
![Alt text](https://github.com/supershen1988/base_pro/blob/master/base-admin/readme/5.png)


最后感谢江南白衣兄的springside伴我成长。

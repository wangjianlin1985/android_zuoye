# android_zuoye
# 安卓Android作业考勤管理app设计

系统开发环境: Windows + Myclipse(服务器端) + Eclipse(手机客户端) + mysql数据库

服务器也可以用Eclipse或者idea等工具，客户端也可以采用android studio工具！

系统客户端和服务器端架构技术: 界面层，业务逻辑层，数据层3层分离技术，MVC设计思想！

服务器和客户端数据通信格式：json格式,采用servlet方式

【服务器端采用SSH框架，请自己启动tomcat服务器，hibernate会自动生成数据库表的哈！】

hibernate生成数据库表后，只需要在admin管理员表中加个测试账号密码就可以登录后台了哈！

下面是数据库的字段说明：

学生: 学号,登录密码,姓名,性别,学生照片,出生日期,家长电话,家庭地址

老师: 教师编号,登录密码,姓名,性别,年龄,入职日期,职称,联系电话,教师简介

课程: 课程编号,课程名称,上课老师,上课地点,上课时间,总学时

作业: 作业id,课程,作业标题,作业内容,作业要求,发布日期

请假: 请假id,请假类别,请假原因,请假详情,请假学生,请假时间,审核状态

请假类别: 请假类别id,请假类别名称

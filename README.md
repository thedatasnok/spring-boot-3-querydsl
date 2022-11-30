# Spring Boot 3 QueryDSL

[QueryDSL Issue](https://github.com/querydsl/querydsl/issues/3439)

This project is a reproduction of a bug in QueryDSL 5.0.0 used in conjunction with Spring Boot 3.

The bug is likely caused by going from Hibernate 5.6 to 6.0.

When using the HibernateQueryFactory, the following exception is thrown:

```
Caused by: java.lang.ClassNotFoundException: org.hibernate.type.ByteType
        at java.base/java.net.URLClassLoader.findClass(URLClassLoader.java:445) ~[na:na]
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:587) ~[na:na]
        at org.springframework.boot.loader.LaunchedURLClassLoader.loadClass(LaunchedURLClassLoader.java:149) ~[spring-boot-3-querydsl-0.0.1-SNAPSHOT.jar:na]
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:520) ~[na:na]
        ... 40 common frames omitted
``` 

The exception will be thrown when requesting the endpoint: `http://localhost:8080/user-accounts/hibernate`.
It works as expected when using the JPAQueryFactory, which can be tested by requesting the endpoint: `http://localhost:8080/user-accounts`.

The relevant code is in the [UserAccountController](src/main/java/cool/datasnok/repros/springboot3querydsl/controller/UserAccountController.java) class.

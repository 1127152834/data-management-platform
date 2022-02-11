FROM openjdk:8u212-jre

MAINTAINER MrZhang 1127152834@qq.com

COPY target/data-management-platform-1.0.0.jar /bisicloud/data-management-platform-1.0.0.jar


ENTRYPOINT ["java","-Xmx1024m", "-jar", "/bisicloud/data-management-platform-1.0.0.jar"]
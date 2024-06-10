
FROM amazoncorretto:17
WORKDIR /cflogprocessor
ENV JAVA_APP_JAR cflogprocessor-0.0.1-SNAPSHOT.jar

COPY target/$JAVA_APP_JAR /cflogprocessor
COPY target/classes/application.properties /cflogprocessor

EXPOSE 8080

CMD ["java", "-jar", "cflogprocessor-0.0.1-SNAPSHOT.jar"]
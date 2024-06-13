#Sử dụng thư viện jdk 18
FROM openjdk:18-oracle
EXPOSE 8080
#Coppy file jar từ local lên docker
COPY target/First_project.jar First_project.jar
#Câu lệnh chạy file jar
ENTRYPOINT ["java", "-jar", "/First_project.jar"]
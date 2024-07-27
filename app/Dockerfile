FROM maven:3.9.8-amazoncorretto-21-al2023
ENV TZ="America/New_York"
USER root
WORKDIR /usr/src/app
RUN yum update && yum install ca-certificates wget -y && yum install git -y
RUN git clone https://github.com/pandareen/ci-app.git
RUN cd ci-app/ && ./mvnw clean install
WORKDIR /usr/src/app/ci-app/
ENTRYPOINT ["./mvnw","spring-boot:run"]
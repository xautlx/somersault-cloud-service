FROM apache/skywalking-java-agent:8.9.0-alpine as skywalking

# AdoptOpenJDK 停止发布 OpenJDK 二进制，而 Eclipse Temurin 是它的延伸，提供更好的稳定性
FROM eclipse-temurin:11-jdk

ENV TZ=Asia/Shanghai
ENV LANG=en_US.UTF-8
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

RUN apt-get update
RUN apt-get install -y unzip net-tools iputils-ping telnet iproute2

## 创建目录，并使用它作为工作目录
RUN mkdir -p /app
WORKDIR /app

RUN mkdir -p /app/skywalking
COPY --from=skywalking /skywalking/agent /app/skywalking

RUN mkdir arthas
WORKDIR /app/arthas
ADD https://maven.aliyun.com/repository/public/com/taobao/arthas/arthas-packaging/3.6.9/arthas-packaging-3.6.9-bin.zip /app/arthas
RUN ls -lh /app/arthas
WORKDIR /app

## 设置 JAVA_OPTS 环境变量，可通过 docker run -e "JAVA_OPTS=" 进行覆盖
ENV JAVA_OPTS="-Xms256m -Xmx512m"

COPY docker-entrypoint.sh /app/docker-entrypoint.sh
RUN chmod +x /app/docker-entrypoint.sh

RUN pwd
RUN ls -lh
ENTRYPOINT /app/docker-entrypoint.sh

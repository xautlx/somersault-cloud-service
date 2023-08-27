#!/bin/bash

echo "Entrypoint Log: Startup docker entrypoint shell: "
echo "Entrypoint Log:  - JAVA_OPTS=$JAVA_OPTS"
echo "Entrypoint Log:  - JAVA_AGENT=$JAVA_AGENT"

echo "Entrypoint Log: Startup spring boot jar..."

# 默认参数处理
JAVA_OPTS="${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -Drocketmq.client.logLevel=WARN"

# 预留远程调试参数处理，以便远程问题排查分析
if [[ "${APP_DEBUG}" == "y" ]]; then
  JAVA_OPTS="${JAVA_OPTS} -Xdebug -Xrunjdwp:transport=dt_socket,address=${DEBUG_PORT},server=y,suspend=n"
fi

java -server $JAVA_OPTS $JAVA_AGENT -jar $(find . -iname *.jar)

#常驻容器运行仅用于容器调试
#tail -f /dev/null

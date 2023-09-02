#!/bin/sh

DIRNAME=`pwd`
ulimit -n 65536 >/dev/null 2>&1
ulimit -c unlimited
# resolve links - $0 may be a softlink
PRG="$0"
while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# Get standard environment variables
PRGDIR=`dirname "$PRG"`

EMBED_HOME=`cd ${PRGDIR}/ && pwd`

if [ -f ~/.m2/repository/com/tongweb/tongweb-lic-sdk/tongweb-lic-sdk-4.4.1.2.jar ]; then
  echo 'Tongweb install skipped.'
  exit
fi

mvn install:install-file -DgroupId=com.tongweb -DartifactId=tongweb-embed-core -Dversion=7.0.E.6_P4 -Dfile=${EMBED_HOME}/lib/tongweb-embed-core-7.0.E.6_P4.jar -Dpackaging=jar
mvn install:install-file -DgroupId=com.tongweb -DartifactId=tongweb-embed-el -Dversion=7.0.E.6_P4 -Dfile=${EMBED_HOME}/lib/tongweb-embed-el-7.0.E.6_P4.jar -Dpackaging=jar
mvn install:install-file -DpomFile=${EMBED_HOME}/lib/tongweb-starter2.x.pom -DgroupId=com.tongweb.springboot -DartifactId=tongweb-spring-boot-starter-2.x -Dversion=7.0.E.6_P4 -Dfile=${EMBED_HOME}/lib/tongweb-spring-boot-starter-2.x-7.0.E.6_P4.jar -Dpackaging=jar
mvn install:install-file -DgroupId=com.tongweb -DartifactId=tongweb-dependencies-check -Dversion=7.0.E.6_P4 -Dfile=${EMBED_HOME}/lib/tongweb-dependencies-check-7.0.E.6_P4.jar -Dpackaging=jar
mvn install:install-file  -DgroupId=com.tongweb -DartifactId=tongweb-javax-annotation -Dversion=1.2 -Dfile=${EMBED_HOME}/lib/tongweb-javax-annotation-1.2.jar -Dpackaging=jar
mvn install:install-file -DgroupId=com.tongweb -DartifactId=tongweb-embed-dependencies -Dversion=7.0.E.6_P4 -Dfile=${EMBED_HOME}/lib/tongweb-embed-dependencies.pom -Dpackaging=pom

mvn install:install-file  -DgroupId=com.tongweb -DartifactId=reactor-tongweb-core -Dversion=7.0.E.6_P4 -Dfile=${EMBED_HOME}/lib/reactor-tongweb-core-7.0.E.6_P4.jar -Dpackaging=jar -Denforcer.skip=true
mvn install:install-file  -DgroupId=com.tongweb -DartifactId=reactor-tongweb-http -Dversion=7.0.E.6_P4 -Dfile=${EMBED_HOME}/lib/reactor-tongweb-http-7.0.E.6_P4.jar -Dpackaging=jar -Denforcer.skip=true
mvn install:install-file  -DgroupId=com.tongweb -DpomFile=${EMBED_HOME}/lib/tongweb-reactor.pom -DartifactId=tongweb-spring-boot-reactor-starter -Dversion=7.0.E.6_P4 -Dfile=${EMBED_HOME}/lib/tongweb-spring-boot-reactor-starter-7.0.E.6_P4.jar -Dpackaging=jar

mvn install:install-file  -DgroupId=com.tongweb -DartifactId=tongweb-lic-sdk -Dversion=4.4.1.2 -Dfile=${EMBED_HOME}/lib/tongweb-license-client-4.4.1.2.jar -Dpackaging=jar


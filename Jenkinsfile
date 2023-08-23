#!groovy

pipeline {
    agent any
     parameters {

           booleanParam name:"maven_build",
                        defaultValue: true,
                        description:"是否触发Maven构建项目？"

           booleanParam name:"site_build",
                        defaultValue: false,
                        description:"是否触发生成site站点？"

           string  name: "image_push_registry",
                   description: "构建镜像推送私服地址",
                   defaultValue: "192.168.1.199:5000"

           string  name: "image_push_user",
                   description: "构建镜像推送私服账号",
                   defaultValue: "docker"

           password name: "image_push_password",
                   description: "构建镜像推送私服密码",
                   defaultValue: "RepoP@ssword4Docker"

           gitParameter name: "BRANCH_COMMIT",
                        branchFilter: "origin/(.*)",
                        defaultValue: "master",
                        selectedValue: "DEFAULT",
                        description: "代码的分支",
                        type: "PT_BRANCH_TAG",
                        sortMode: "DESCENDING_SMART",
                        listSize: "5"

           booleanParam name:"hosts_multiple_update",
                        defaultValue: false,
                        description:"默认开发主机更新部署以外是否测试集群环境更新？"
     }

    environment {
        GIT_PROJECT_ADDR="http://192.168.1.199:3000/entdiy.xyz/somersault-cloud-service.git"
    }

    stages {
        stage("git checkout") {
            steps {
                git (url: "${env.GIT_PROJECT_ADDR}",
                     branch: "${BRANCH_COMMIT}",
                     credentialsId: "git_secret_for_jenkins")
            }
        }

        stage ("maven install") {
            when{
                equals actual: "${params.maven_build}" ,expected:"true"
            }
            steps{
                sh "mvn clean install -DskipTests -f pom.xml"
            }
        }

        stage ("maven site") {
            when{
                equals actual: "${params.site_build}" ,expected:"true"
            }
            steps{
                sh "mvn test site:site site:stage -f pom.xml"
            }
        }

        stage ("docker build base-image") {
            steps{
                //已知Dockerfile中引用镜像偶尔会出现403异常，通过提前pull镜像规避此问题
                sh "docker pull eclipse-temurin:11-jdk"
                sh "docker pull apache/skywalking-java-agent:8.9.0-alpine"

                sh "mvn docker:build -f somersault-cloud-dependencies/pom.xml"
            }
        }

        stage ("docker registry login") {
            when{
                not {
                   equals actual: "${params.image_push_user}" ,expected:""
                }
            }
            steps{
                sh "docker login -u ${params.image_push_user} -p ${params.image_push_password} ${params.image_push_registry}"
            }
        }

        //指定平台构建： COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 DOCKER_DEFAULT_PLATFORM=linux/amd64 docker compose build
        //为了兼容在Mac ARM芯片架构系统下构建输出到普通Linux/AMD64机器部署，指定 DOCKER_DEFAULT_PLATFORM=linux/amd64
        //清空失效镜像： docker rmi `docker images | grep "<none>" | awk "{print $3}"
        stage ("docker build images") {
            environment {
                DOCKER_REPO_ADDR="${params.image_push_registry}/"
            }
            steps{
                sh "mvn docker:build -DpushImage -f somersault-cloud-module-infra/somersault-cloud-module-infra-biz/pom.xml"
                sh "mvn docker:build -DpushImage -f somersault-cloud-module-system/somersault-cloud-module-system-biz/pom.xml"
                sh "mvn docker:build -DpushImage -f somersault-cloud-module-bpm/somersault-cloud-module-bpm-biz/pom.xml"
                sh "mvn docker:build -DpushImage -f somersault-cloud-module-report/somersault-cloud-module-report-biz/pom.xml"
                sh "mvn docker:build -DpushImage -f somersault-cloud-module-iot/somersault-cloud-module-iot-biz/pom.xml"
                sh "mvn docker:build -DpushImage -f somersault-cloud-gateway/pom.xml"
                sh "mvn docker:build -DpushImage -f somersault-cloud-monitor/pom.xml"
            }
        }

        stage ("trigger dev server deploy update") {
            steps{
                build wait: false, job: "somersault-cloud-devops",
                      parameters: [
                           booleanParam(name: "microservice_deploy", value: true),
                           booleanParam(name: "frontend_deploy", value: false)
                      ]
            }
        }

        stage ("trigger test servers deploy update") {
            when{
                equals actual: "${params.hosts_multiple_update}" ,expected:"true"
            }
            steps{
                build wait: false, job: "somersault-cloud-devops",
                      parameters: [
                           booleanParam(name: "microservice_deploy", value: true),
                           booleanParam(name: "frontend_deploy", value: false),
                           string(name: 'ansible_hosts', value: 'hosts-multiple')
                      ]
            }
        }
    }
}

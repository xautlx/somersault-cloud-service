#!groovy

def _REVISION_TAG

def defineEnvironment() {
    def branchName = "${env.BRANCH_NAME}"
    if (branchName == "master") {
        return 'staging'
    }
    else {
        return 'test'
    }
}

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

           choice       name: 'web_container_profile',
                        choices: ['tomcat', 'tongweb'],
                        description:"选择运行容器类型"

     }

    environment {
        GIT_PROJECT_ADDR="http://192.168.1.199:3000/entdiy/somersault-cloud-service.git"
    }

    stages {
        stage("git checkout") {
            steps {
                git (url: "${env.GIT_PROJECT_ADDR}",
                     branch: "${BRANCH_COMMIT}",
                     credentialsId: "git_secret_for_jenkins")

                // 显示变量列表信息，便于按需取值
                sh "printenv"

                script {
                    // 默认采用简化定义形式，便于重复开发调试
                    // 可添加提交ID，便于代码追溯定义： _REVISION_TAG = "${BRANCH_COMMIT}-${GIT_COMMIT}"
                    // 可进一步添加时间戳信息便于直观看出代码提交或构建日期时间信息
                    _REVISION_TAG = "${BRANCH_COMMIT}"
                    echo _REVISION_TAG
                }

            }
        }

        stage ("tongweb jar install") {
            when{
                equals actual: "${params.web_container_profile}" ,expected:"tongweb"
            }
            steps{
                sh "chmod +x somersault-cloud-dependencies/tongweb/installMavenJar.sh"
                sh "cd ./somersault-cloud-dependencies/tongweb && ./installMavenJar.sh"
            }
        }

        stage ("maven install") {
            when{
                equals actual: "${params.maven_build}" ,expected:"true"
            }
            steps{
                sh "mvn clean install -DskipTests -P${params.web_container_profile} -f pom.xml"
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

        stage ("docker build base-image") {
            steps{
                dir('somersault-cloud-dependencies/docker') {
                    //已知Dockerfile中引用镜像偶尔会出现403异常，通过提前pull镜像规避此问题
                    //sh "docker pull --platform linux/arm64 eclipse-temurin:11-jdk"
                    //sh "docker pull --platform linux/arm64 apache/skywalking-java-agent:8.9.0-alpine"

                   // https://docs.docker.com/build/building/multi-platform/#qemu
                   sh "docker run --privileged --rm tonistiigi/binfmt --install all"
                    //sh returnStatus: true, script: "docker buildx rm march_container"
                    // https://github.com/moby/buildkit/blob/master/docs/buildkitd.toml.md
                    sh "mkdir -p /etc/buildkit"
                    sh "echo '[registry.\"${image_push_registry}\"]' > /etc/buildkit/buildkitd.toml"
                    sh "echo '  mirrors = [\"${image_push_registry}\"]' >> /etc/buildkit/buildkitd.toml"
                    sh "echo '  http = true' >> /etc/buildkit/buildkitd.toml"
                    sh "echo '  insecure = true' >> /etc/buildkit/buildkitd.toml"
                    // https://docs.docker.com/engine/reference/commandline/buildx_build/
                    // https://github.com/docker/buildx#building-multi-platform-images
                    sh returnStatus: true, script: "docker buildx create --name=march_container --driver=docker-container --use --driver-opt network=host --config /etc/buildkit/buildkitd.toml"
                    sh returnStatus: true, script: "docker buildx inspect --bootstrap"

                    sh "docker buildx build --platform linux/amd64,linux/arm64 -t ${params.image_push_registry}/somersault-cloud/somersault-cloud-service:${_REVISION_TAG} . --push"

                    sh "echo 'FROM  ${params.image_push_registry}/somersault-cloud/somersault-cloud-service:${_REVISION_TAG}' > /tmp/Dockerfile"
                    sh "echo 'COPY target/*.jar /app/.' >> /tmp/Dockerfile"
                    sh "echo 'COPY target/classes/git.properties /app/.' >> /tmp/Dockerfile"
                }
            }
        }

        stage ("docker build images") {
            environment {
                DOCKER_REPO_ADDR="${params.image_push_registry}/"
            }
            steps{
                //TODO 优化为循环方法调用，缩减重复代码
                dir('somersault-cloud-module-infra/somersault-cloud-module-infra-biz') {
                    sh "cp /tmp/Dockerfile ."
                    sh "docker buildx build --platform linux/amd64,linux/arm64 -t ${params.image_push_registry}/somersault-cloud/infra-server:${_REVISION_TAG} . --push"
                }
                dir('somersault-cloud-module-system/somersault-cloud-module-system-biz') {
                    sh "cp /tmp/Dockerfile ."
                    sh "docker buildx build --platform linux/amd64,linux/arm64 -t ${params.image_push_registry}/somersault-cloud/system-server:${_REVISION_TAG} . --push"
                }
                dir('somersault-cloud-module-bpm/somersault-cloud-module-bpm-biz') {
                    sh "cp /tmp/Dockerfile ."
                    sh "docker buildx build --platform linux/amd64,linux/arm64 -t ${params.image_push_registry}/somersault-cloud/bpm-server:${_REVISION_TAG} . --push"
                }
                dir('somersault-cloud-module-report/somersault-cloud-module-report-biz') {
                    sh "cp /tmp/Dockerfile ."
                    sh "docker buildx build --platform linux/amd64,linux/arm64 -t ${params.image_push_registry}/somersault-cloud/report-server:${_REVISION_TAG} . --push"
                }
                dir('somersault-cloud-module-iot/somersault-cloud-module-iot-biz') {
                    sh "cp /tmp/Dockerfile ."
                    sh "docker buildx build --platform linux/amd64,linux/arm64 -t ${params.image_push_registry}/somersault-cloud/iot-server:${_REVISION_TAG} . --push"
                }
                dir('somersault-cloud-gateway') {
                    sh "cp /tmp/Dockerfile ."
                    sh "docker buildx build --platform linux/amd64,linux/arm64 -t ${params.image_push_registry}/somersault-cloud/gateway-server:${_REVISION_TAG} . --push"
                }
                dir('somersault-cloud-monitor') {
                    sh "cp /tmp/Dockerfile ."
                    sh "docker buildx build --platform linux/amd64,linux/arm64 -t ${params.image_push_registry}/somersault-cloud/monitor-server:${_REVISION_TAG} . --push"
                }
                //TODO 移除maven docker构建配置
//                 sh "mvn docker:build -DpushImage -f somersault-cloud-module-infra/somersault-cloud-module-infra-biz/pom.xml"
//                 sh "mvn docker:build -DpushImage -f somersault-cloud-module-system/somersault-cloud-module-system-biz/pom.xml"
//                 sh "mvn docker:build -DpushImage -f somersault-cloud-module-bpm/somersault-cloud-module-bpm-biz/pom.xml"
//                 sh "mvn docker:build -DpushImage -f somersault-cloud-module-report/somersault-cloud-module-report-biz/pom.xml"
//                 sh "mvn docker:build -DpushImage -f somersault-cloud-module-iot/somersault-cloud-module-iot-biz/pom.xml"
//                 sh "mvn docker:build -DpushImage -f somersault-cloud-gateway/pom.xml"
//                 sh "mvn docker:build -DpushImage -f somersault-cloud-monitor/pom.xml"
            }
        }

        stage ("trigger dev server deploy update") {
            steps{
                build wait: false, job: "somersault-cloud-devops",
                      parameters: [
                           string(name: "deploy_target", value: "microservice")
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
                           string(name: "deploy_target", value: "microservice"),
                           string(name: 'ansible_hosts', value: 'hosts-multiple')
                      ]
            }
        }
    }
}

pipeline {
    agent none

    environment {
        DOCKER_IMAGE = 'registry-intl.cn-hongkong.aliyuncs.com/my-link/bankms'
        GIT_BRANCHES  = 'develop'
        REGISTRY_CREDENTIALS_ID = 'aliyun-dockerhub-zhoucheng' // 你需要在 Jenkins 里先配置这个 Credentials


    }
    stages {
        stage('Build and Push Docker Image') {
            agent {
                kubernetes {
                    idleMinutes 3  // how long the pod will live after no jobs have run on it
                    yaml '''
apiVersion: v1
kind: Pod
metadata:
  name: kaniko
spec:
  containers:
    - name: jenkins-slave
      image: zhoucheng45/maven:3.6.3-openjdk-8-git
      workingDir: /home/jenkins/agent
      command: ["sleep", "99999"]
      tty: true
      volumeMounts:
        - name: maven-lib
          mountPath: "/root/.m2"

    - name: kaniko
      image: gcr.io/kaniko-project/executor:v1.9.0-debug
      workingDir: /home/jenkins/agent
      command:
       - /busybox/cat
      tty: true
      volumeMounts:
        - name: kaniko-secret
          mountPath: "/kaniko/.docker"

  volumes:
    - name: maven-lib
      persistentVolumeClaim:
        claimName: maven
    - name: kaniko-secret
      secret:
        secretName: aliyun-regsecret
        items:
          - key: .dockerconfigjson
            path: config.json
  restartPolicy: Never

                    '''
                }
            }
            steps {
                container('jenkins-slave') {
                   sh 'pwd && echo ${GIT_COMMIT}-${GIT_BRANCH}'
                }
                container('jenkins-slave') {
                    // 检出代码
                    checkout scmGit(branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[credentialsId: '14d3b03d-86bf-49ef-a40a-820ab12156f5', url: 'ssh://git@47.242.74.83:222/zhoucheng/bankms.git']])
                }
                container('jenkins-slave') {
                    // 检出代码
                    sh 'mvn clean package -DskipTests '
                }

                container('kaniko') {
                    sh 'pwd && ls -al'
                }
                // 执行 Kaniko 构建过程
                container('kaniko') {
                    withCredentials([usernamePassword(credentialsId: REGISTRY_CREDENTIALS_ID, usernameVariable: 'REGISTRY_USER', passwordVariable: 'REGISTRY_PASSWORD')]) {
                        // Kaniko 命令来构建和推送 Docker 镜像
                        sh """
                        /kaniko/executor --context=dir:///home/jenkins/agent/workspace/${JOB_BASE_NAME} --dockerfile=Dockerfile --destination=${DOCKER_IMAGE}:${GIT_BRANCHES}-${BUILD_NUMBER}
                        """
                    }
                }
            }
        }
    }
}
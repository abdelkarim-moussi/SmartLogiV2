pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = 'dockerhub-pwd'
        IMAGE_NAME = 'smartlogiv2'
        DOCKERHUB_USERNAME = 'abdelkarim25'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage("Inject application.yaml file") {
            steps {
                configFileProvider([
                    configFile(fileId: 'app-config-yaml',
                        targetLocation: 'src/main/resources/application.yaml')
                ])
                echo "Configuration file injected"
            }
        }

        stage('Build & Test') {
            steps {
                sh "chmod +x mvnw"
                sh "./mvnw clean package"
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Fixed: Use $VAR or ${VAR} for Unix environment variables
                    sh "docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} ."
                    sh "docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${IMAGE_NAME}:latest"
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'dockerhub-pwd',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                        sh "echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin"
                        sh "docker push ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${BUILD_NUMBER}"
                        sh "docker push ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:latest"
                    }
                }
            }
        }
    }
}
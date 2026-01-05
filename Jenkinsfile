pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = 'dockerhub-pwd'
        IMAGE_NAME = 'smartlogiv2'  // DockerHub images should be lowercase
        DOCKERHUB_USERNAME = 'abdelkarim25'  // Add your username
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
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
                    // Login to DockerHub using credentials
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

    post {
        always {
            // Logout and cleanup
            sh "docker logout"
        }
    }
}
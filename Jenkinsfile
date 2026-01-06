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

        stage('Build & Test') {
            options {
                        timeout(time: 10, unit: 'MINUTES')  // Add timeout
                    }

            steps {
                withCredentials([
                    string(credentialsId: 'db-url', variable: 'DB_URL'),
                    string(credentialsId: 'db-username', variable: 'DB_USERNAME'),
                    string(credentialsId: 'db-password', variable: 'DB_PASSWORD'),
                    string(credentialsId: 'client-id', variable: 'CLIENT_ID'),
                    string(credentialsId: 'client-secret', variable: 'CLIENT_SECRET'),
                    string(credentialsId: 'issuer-id', variable: 'ISSUER_URI'),
                    string(credentialsId: 'secret-key', variable: 'SECRET_KEY')
                ]) {
                    sh "chmod +x mvnw"
                    sh "./mvnw clean package -Dskiptests"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${BUILD_NUMBER} ."
                    sh "docker tag ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${BUILD_NUMBER} ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:latest"
                }
            }
        }

    }


}
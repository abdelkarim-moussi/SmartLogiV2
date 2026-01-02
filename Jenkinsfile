pipeline {
    agent any

    environment {
        // This ID must match what you created in Jenkins Credentials
        DOCKERHUB_CREDENTIALS = 'dockerhub-pwd'
        IMAGE_NAME = "/CICD"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build & Test') {
            steps {
                // Using bat for Windows
                bat './mvnw clean package'
            }
        }
        stage('Build Docker Image'){
            steps {
                script {
                    // Build the image using the Dockerfile in your project
                    bat "docker build -t %IMAGE_NAME%:%BUILD_NUMBER% ."
                    bat "docker tag %IMAGE_NAME%:%BUILD_NUMBER% %IMAGE_NAME%:latest"
                }
            }
        }
        stage('Push to Docker Hub') {
            steps {
                // This block securely logs you into Docker Hub
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS}", passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                    bat "docker login -u %USER% -p %PASS%"
                    bat "docker push %IMAGE_NAME%:%BUILD_NUMBER%"
                    bat "docker push %IMAGE_NAME%:latest"
                }
            }
        }
    }
}
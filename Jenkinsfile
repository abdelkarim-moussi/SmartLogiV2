pipeline {
    agent any

    environment {
        // This ID must match what you created in Jenkins Credentials
        DOCKERHUB_CREDENTIALS = 'dockerhub-pwd'
        IMAGE_NAME = 'SmartLogiV2'
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
                sh 'mvn clean package'
            }
        }
        stage('Build Docker Image'){
            steps {
                script {
                    // Build the image using the Dockerfile in your project
                    sh "docker build -t %IMAGE_NAME%:%BUILD_NUMBER% ."
                    sh "docker tag %IMAGE_NAME%:%BUILD_NUMBER% %IMAGE_NAME%:latest"
                }
            }
        }

    }
}
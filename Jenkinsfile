pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = ""
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build & Test') {
            steps {
                bat './mvnw clean package'
            }
        }
        stage('Build Docker Image'){
            steps {
                script {
                    bat "docker-compose up"
                }
            }
        }
    }
}
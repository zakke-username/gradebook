pipeline {
    agent any
    environment {
        PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"
        DOCKERHUB_CREDENTIALS_ID = 'f41ec13c-4df1-43d3-b7ea-1ad77d8ff2e0'
        DOCKERHUB_REPO = 'gasdy/gradebook'
        DOCKER_IMAGE_TAG = 'latest'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'docker', url: 'https://github.com/zakke-username/gradebook.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Code coverage') {
            steps {
                bat 'mvn jacoco:report'
            }
        }

        stage('Publish test result') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Publish coverage report') {
            steps {
                jacoco()
            }
        }

        stage('Build Docker Image') {
            steps {
              script {
                  docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
              }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
              script {
                  docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                      docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
                  }
              }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    bat 'docker compose down'
                    bat 'docker compose up -d'
                }
            }
        }
    }
}
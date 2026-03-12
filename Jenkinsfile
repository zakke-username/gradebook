pipeline {
    agent any
//     environment {
//         DOCKER_IMAGE_TAG = 'latest'
//     }
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
    }
}
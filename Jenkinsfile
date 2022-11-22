pipeline {
    agent any
    tools {
        maven 'maven_3_8_6'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/AyushBehl/DevOpsAssignment']]])
                sh 'mvn clean install'
            }
        }
        stage('Build Docker Image'){
            steps{
                script {
                    sh 'docker logout'
                    sh 'docker build -t navyakhurana/devops-0.1 .'
                }
            }
        }
        stage('Push Image to Hub'){
            steps{
                script {
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u navyakhurana -p ${dockerhubpwd} docker.io'
                    }
                    sh 'docker push navyakhurana/devops-0.1'
                }
            }
        }
    }
}
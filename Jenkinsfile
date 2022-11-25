pipeline {
    agent any
    tools {
        maven 'maven_3_8_6'
    }
    stages{
        stage('Build Project'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/AyushBehl/DevOpsAssignment']]])
                sh 'mvn clean install'
            }
        }
        stage('SonarQube Analysis- Backend') {
            steps {
                withSonarQubeEnv('sqdevops') {
                    sh "mvn sonar:sonar -Dsonar.projectKey=devops-project"
                }
            }
        }
        stage('SonarQube Analysis- Frontend'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/AyushBehl/DevOpsAssignment']]])
                dir('ProductWeb'){
                sh 'npm install -g @angular/cli'
                sh 'npm install get-intrinsic'
                sh 'npm run sonar'
                }
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
                        dir('ProductWeb'){
                        sh 'docker build -t navyakhurana/devops-angular-0.1 .'
                    }
                    }
                    sh 'docker push navyakhurana/devops-0.1'
                    sh 'docker push navyakhurana/devops-angular-0.1'
                }
            }
        }
        stage('Deploy to k8s'){
            steps{
                script {
                    sh '''
                    kubectl apply -f deploymentservice.yaml
                    '''
                }
            }
        }
    }
}
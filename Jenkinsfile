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
        // stage('Build Angular Project'){
        //     steps{
        //         checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/AyushBehl/DevOpsAssignment']]])
        //         dir('ProductWeb'){
        //         sh 'npm install -g @angular/cli'
        //         sh 'npm install get-intrinsic'
        //         sh 'npm run sonar'
        //         sh 'npm run build --prod'
        //         sh 'echo Angular Project Build'
        //         }
        //     }
        // }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sqdevops') {
                    sh "mvn sonar:sonar -Dsonar.projectKey=devops-project"
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
                    }
                    sh 'docker push navyakhurana/devops-0.1'
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
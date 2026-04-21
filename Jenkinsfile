
pipeline {
  agent any
  tools {
    jdk 'jdk-21'
    maven 'maven-3.9.9'
  }
  stages {
    stage('Build') {
      steps { sh 'mvn clean package' }
    }
    stage('Docker Build & Push') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
          sh '''
            docker build -t snikhil1729/quiz-app:latest .
            echo $PASS | docker login -u $USER --password-stdin
            docker push snikhil1729/quiz-app:latest
          '''
        }
      }
    }
    stage('Deploy to k3s') {
      steps { sh 'kubectl apply -f k8s/' }
    }
  }
}

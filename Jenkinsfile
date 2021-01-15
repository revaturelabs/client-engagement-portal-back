pipeline {
  agent any
  
  environment{
    JENKINS_NODE_COOKIE = 'dontkillmeplease'
    PORT=9011
  } 
    stages {
  
    stage('Destroy Old Server') {
      steps {
        script {
          try {
            // kill any running instances
            sh 'docker stop cep'
          } catch (all) {
            // if it fails that should mean a server wasn't already running
            echo 'No server was already running'
          }
        }
      }
    }
    stage ('Clean & Package') {
      steps {
        sh 'mvn clean package' 
      }
    }
    /*
      * Tries to remove an existing Docker Container named cep
    */
    stage ('Remove Docker Container') {
      steps {
        script {
          try {
            sh 'docker rm -f cep'
          } catch (all) {
            echo 'Docker Container does not exist'
          }
        }
      }
    }
    /*
      * Deletes Extra images that has no container
    */
    stage ('Delete Docker Image') {
      steps {
        sh 'docker image prune -a -f'
      }
    }
    stage ('Docker Build') {
      steps {
        sh 'docker build -t cep-image .'
      }
    }
    stage ('Docker Run') {
      steps {
        sh 'docker run --env-file=/home/jwilson/dataFiles/env -v /home/jwilson/dataFiles:/env -p 9011:9011 --name cep -d cep-image'
      }
    }
    stage ('Docker Log') {
      steps {
        sh 'nohup docker logs -f cep > /home/jwilson/.jenkins/workspace/CEP-Back/logs/application.log &';
      }
    }
    stage ('Docker Check Containers') {
      steps {
        sh 'docker ps -a'
      }
    }
  }
}
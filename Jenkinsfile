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
                        sh 'kill $(lsof -t -i:$PORT)'
                    } catch (all) {
                        // if it fails that should mean a server wasn't already running
                        echo 'No server was already running'
                    }
                }
            }
        }
          stage('Install maven dependencies'){
            steps{
                //clean install maven
                sh 'mvn install -DskipTests'
            }
        }
        stage ('Clean & Package') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        // stage ('Run Spring App') {
        //     steps {
                
        //     	sh 'nohup java -jar target/Client-Engagement.jar &'
        //        //sh 'disown java -jar /home/ec2-user/.jenkins/workspace/Revature_Client_Engagement_Portal/target/cep-engagement-service-0.0.1-SNAPSHOT.jar &'
        //         //Better user this one if we're unsure of the first one
                 
        //         // sh 'mvn spring-boot:run'  // This one works but cannot be accessed with postman but won't stop running
        //         // sh 'nohup mvn spring-boot:run &' 
        //          //sh 'JENKINS_NODE_COOKIE=dontkillme nohup mvn spring-boot:run > logsfile.txt &'
        //          // it would run if nohup is not included. Not tested though not sure if porrt 8081 is open or what
        //     }
        // }
        stage ('Docker Build') {
            steps {
                sh 'docker build -t tyronev/ce-portal:v1 .'
            }
        }
        stage ('Delete Docker Image') {
            steps {
                sh 'docker rmi \$(docker images -qa -f \'dangling=true\')'
            }
        }
        stage ('Remove Docker Container') {
            steps {
                sh 'docker rm -f cep'
            }
        }
        stage ('Docker Run') {
            steps {
                sh 'docker run -p 9011:9011 --name cep -it -d tyronev/ce-portal:v1'
            }
        }
     }
}
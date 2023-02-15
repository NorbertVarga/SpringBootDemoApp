pipeline {
    agent any
    stages {
        stage('Stage 1') {
            steps {
                echo 'It works! EUREKA!!'
            }
        }
        stage('Build') {
           steps {
              sh 'mvn clean install -DskipTests'
           }
        }
        stage('Deploy') {
           steps {
              sh "scp target/SpringBootDemoApp-0.0.1-SNAPSHOT.jar <http://3.68.199.215/>:/opt/SpringBootDemoApp/"
              sh "sudo java -jar SpringBootDemoApp-0.0.1-SNAPSHOT.jar"
           }
        }
    }
}

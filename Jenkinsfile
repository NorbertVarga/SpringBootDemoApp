pipeline {
    agent any
    stages {

        stage('Stage 1') {
            steps {
                echo 'It works! EUREKA!!!'
            }
        }

        stage('Build') {
           steps {
              sh 'mvn clean install -DskipTests'
           }
        }

        stage('Stop and Clear') {
            steps {
                sh "sudo pkill -f SpringBootDemoApp-0.0.1-SNAPSHOT.jar"
                sh "sudo rm -rf /home/ec2-user/SpringBootDemoApp/*"
            }
        }

        stage('Deploy') {
           steps {
              sh "sudo cp /var/lib/jenkins/workspace/test_develop/target/SpringBootDemoApp-0.0.1-SNAPSHOT.jar /home/ec2-user/SpringBootDemoApp"
              sh "sudo java -jar /home/ec2-user/SpringBootDemoApp/SpringBootDemoApp-0.0.1-SNAPSHOT.jar"
           }
        }
    }
}

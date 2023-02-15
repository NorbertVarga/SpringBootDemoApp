pipeline {
    agent any
    stages {
        stage('Stage 1') {
            steps {
                echo 'It works! Please...'
            }
        }
        stage('Build') {
           steps {
              sh 'mvn clean package -DskipTests'
           }
        }
        stage('Deploy') {
           steps {
              sh "sudo cp /var/lib/jenkins/workspace/test_develop/target/SpringBootDemoApp-0.0.1-SNAPSHOT.jar /SpringBootDemoApp/SpringBootDemoApp-0.0.1-SNAPSHOT.jar"
              sh "sudo java -jar /SpringBootDemoApp/SpringBootDemoApp-0.0.1-SNAPSHOT.jar"
           }
        }
    }
}

pipeline {
    agent any
    stages {
        stage('Stage 1') {
            steps {
                echo 'It works! Im really happyyy'
            }
        }
        stage('Build') {
           steps {
              sh 'mvn clean install -DskipTests'
           }
        }
        stage('Deploy') {
           steps {
              sh "sudo -E cp target/SpringBootDemoApp-0.0.1-SNAPSHOT.jar /SpringBootDemoApp"
              sh "sudo java -jar /SpringBootDemoApp/SpringBootDemoApp-0.0.1-SNAPSHOT.jar"
           }
        }
    }
}

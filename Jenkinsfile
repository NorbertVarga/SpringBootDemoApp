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
              sh 'mvn clean package -DskipTests'
           }
        }

        stage('Stop and Clear') {
            steps {
                sh "sudo pkill -f SpringBootDemoApp-0.0.1-SNAPSHOT.jar"
                sh "sudo rm -rf /home/ec2-user/SpringBootDemoApp/*"
            }
        }

        stage('Copy and Start') {
            steps {
                sh "sudo cp /var/lib/jenkins/workspace/test_develop/target/SpringBootDemoApp-0.0.1-SNAPSHOT.jar /home/ec2-user/SpringBootDemoApp"
                sh "sudo java -jar /home/ec2-user/SpringBootDemoApp/SpringBootDemoApp-0.0.1-SNAPSHOT.jar &" // start the app in the background
            }
        }

        stage('Wait and Verify') {
            steps {
                sh "sleep 120" // wait for the app to start up (adjust the sleep time as needed)
                sh "curl --fail http://localhost:8081/api/users/all || exit 1" // make an HTTP request to the app health endpoint
                script {
                    def response = sh(returnStdout: true, script: "curl http://localhost:8081/api/users/all")
                    echo "Response from http://localhost:8081/api/users/all: ${response}"
                }
            }
        }
    }
}

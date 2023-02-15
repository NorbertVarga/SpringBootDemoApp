pipeline {
    agent any
    stages {
        stage('Stage 1') {
            steps {
                echo 'It not works!'
            }
        }
    }
    options {
        // Monitor changes in the specified branch
        scm {
            git {
                remote {
                    name 'origin'
                    url 'https://github.com/NorbertVarga/SpringBootDemoApp.git'
                }
                branch 'develop'
            }
        }
    }
}
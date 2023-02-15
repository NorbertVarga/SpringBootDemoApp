pipeline {
    agent any
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
    stages {
        stage('Stage 1') {
            steps {
                echo 'It not works!'
            }
        }
    }
}

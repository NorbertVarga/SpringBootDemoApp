pipeline {
    agent any
    triggers {
        githubPush(
            branches: ['develop']
        )
    }
    stages {
        stage('Stage 1') {
            steps {
                echo 'It not works!'
            }
        }
    }
}
pipeline {
    agent any
    triggers {
        scm('refs/heads/develop')
    }
    stages {
        stage('Stage 1') {
            steps {
                echo 'It works!'
            }
        }
    }
}
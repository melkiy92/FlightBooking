pipeline {
    agent any
    stages {
        stage('clean') {
            steps {
                sh "/home/g123_18_1_pavlov/apache-maven/bin/mvn clean"
            }
        }
        stage('test') {
            steps {
                sh "/home/g123_18_1_pavlov/apache-maven/bin/mvn test"
            }
        }
        stage('install') {
            steps {
                sh "/home/g123_18_1_pavlov/apache-maven/bin/mvn install"
            }
        }
        // stage('package') {
        //     steps {
        //         bat "/home/g123_18_1_pavlov/apache-maven/bin/mvn package"
        //     }
        // }      
    }
}

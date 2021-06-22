pipeline {
  agent {
    docker {
      image 'node:12-alpine'
      args '-p 3000:3000'
    }
  }
  stages {
    stage('Build') {
      steps {
        sh 'yarn install'
        sh 'yarn build'
        echo 'Node Build Success!'
      }
    }

    stage('docker build') {
      agent any
      steps {
        dir(path: './shell') {
          sh 'sh build-docker.sh'
        }
      }
    }
  }
}
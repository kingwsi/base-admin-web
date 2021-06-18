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
        echo '打包完成，准备构建docker'
      }
    }

    stage('docker  build') {
      steps {
        sh 'cd ./shell'
        sh 'sh build-docker.sh'
      }
    }

  }
}
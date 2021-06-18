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
    }
}
node{
    stage('docker镜像构建') { 
      dir('./shell'){
        sh 'sh build-docker.sh'
      }
    }
}

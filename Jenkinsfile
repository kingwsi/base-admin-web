pipeline {
  agent none
  stages {
    stage('Build') {
      agent {
        docker {
            image 'node:12-alpine'
            args '-p 3000:3000'
        }
      }
      steps {
        checkout scm
        sh 'yarn install'
        sh 'yarn build'
        echo 'Maven Build Success!'
      }
    }

    stage('Deliver') {
      agent { node { label 'master' } }
      steps {
        sh 'sh ./deliver.sh'
      }
    }
  }
  options {
    skipDefaultCheckout(true)
  }
}

library identifier: 'jenkins-shared-libs@stable', retriever: modernSCM(
        [$class: 'GitSCMSource',
         remote: 'remoteSource',
         credentialsId: 'gitcreds']) _


node() {
  timestamps {
       stage('ACR login') {
               def spClientId = env.SP_CLIENT_ID
               def spClientSecret = env.SP_CLIENT_SECRET
               def spTenantId = env.SP_TENANT_ID
               def registryName = "dockerhublogin"
               sh """#!/usr/bin/env bash
               set +x
               az login --service-principal --username ${spClientId} --password ${spClientSecret} --tenant ${spTenantId}
               """
               sh "az acr login --name ${registryName} --subscription ${utils.getSubscriptionIdFromRegistryName(registryName)}"
       }

       stage('run script') {

           git url: "gitUrl"
           ansiColor('xterm') {
               try {
                   sh "./build.sh ${env.CLUSTER_NAME}"
               } catch(e){
                   echo "failed"
                   throw e
               } finally {
                   publishHTML target: [
                               allowMissing: false,
                               alwaysLinkToLastBuild: false,
                               keepAll: true,
                               reportDir: '**/courgette-report',
                               reportFiles: 'index.html',
                               reportName: 'RCov Report'
                             ]
               }
           }

       }
  }
}


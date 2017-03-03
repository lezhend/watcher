#! groovy

node {
    def errorMessage = "No error"
    try {
        env.DEPLOY_IP="172.30.154.23"
        env.DEPLOY_URL="http://${env.DEPLOY_IP}:8443/FortiCASB"
        env.SCRIPTS = "${env.WORKSPACE}/scripts"
        stage('Preparation') {
            checkout scm
            sh '''$SCRIPTS/prep_local_dev.sh $BUILD_ID
            gulp build --gulpfile=$WORKSPACE/fcasb-java/src/main/webapp/gulpfile.js'''
        }
        stage('Build') {
            errorMessage = "Failed maven build"
            sh '''mvn clean install -f $WORKSPACE/fcasb-java/pom.xml
            mvn clean package -P release -f $WORKSPACE/fcasb-local-aws/pom.xml
            mvn clean package -P platform -f $WORKSPACE/fcasb-platform/pom.xml'''
        }
        stage('Deploy') {
            errorMessage = "Failed to deploy FCASB to server"
            sh '''$SCRIPTS/collect_artifacts.sh
            $SCRIPTS/stop_server.sh $DEPLOY_IP
            $SCRIPTS/deploy_server.sh $DEPLOY_IP
            $SCRIPTS/wait_server.sh $DEPLOY_URL'''
        }
        stage('Test') {
            errorMessage = "Failed sanity test"
            sh "$SCRIPTS/sanity_test.sh $DEPLOY_IP $BUILD_ID"
        }
    } catch (e) {
        currentBuild.result = "FAILED"
        throw e
    } finally {
        notifyBuild(currentBuild.result, errorMessage)
    }
}
def notifyBuild(String buildStatus = 'STARTED', String errorMessage) {
    // build status of null means successful
    buildStatus =  buildStatus ?: 'SUCCESSFUL'

    // Default values
    def colorName = 'RED'
    def colorCode = '#FF0000'
    def subject = "Jenkins Build ${buildStatus}: ${env.JOB_NAME} [${env.BUILD_NUMBER}]"
    def summary = "Build # ${env.BUILD_NUMBER} - ${buildStatus}\n\n"
    def details = ""
    def footnote = "Check console output at ${currentBuild.absoluteUrl} to view the results."
    def recipients = [[$class: 'DevelopersRecipientProvider']]

    // Override default values based on build status
    if (buildStatus == 'STARTED') {
    } else if (buildStatus == 'SUCCESSFUL') {
    } else {
        details = "Error: ${errorMessage}\n\n"
    }

    // Send notifications
    emailext (
        subject: subject,
        body: summary + details + footnote,
        recipientProviders: recipients,
        to: 'dunxingzhang@fortinet.com achow@fortinet.com binxu@fortinet.com lijiang@fortinet.com'
    )
}

#!/usr/bin/env groovy

node {
    notifyBuild("Started")

    git branch: 'master', credentialsId: 'jenkins-ssh', url: 'git@git.leadexsystems.ru:leadex/job-monitor-startup.git'

    makeAll()
}

def makeAll() {
    env.JAVA_HOME="${tool 'jdk8u112'}"
    env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"

    def result = BuildStatus.SUCCESSFUL

    try {
        def guruHome = './Guru'
        def freelancerHome = './Freelancer'
        def upworkHome = './Upwork'
        
        def slackHome = './Slack'
        def nmHome = './NotificationManager'
        
        def tmHome = './TaskManager'
        def backendHome = './Backend'
    
        // Build
        makeLib("./Common", "common")
        
        def tasks = [:]
        tasks["guru"] = { makeAdapter(guruHome, "guru") }
        tasks["freelancer"] = { makeAdapter(freelancerHome, "freelancer") }
        tasks["upwork"] = { makeAdapter(upworkHome, "upwork") }
        tasks["slack"] = { makeAdapter(slackHome, "slack") }
        tasks["notification-manager"] = { makeAdapter(nmHome, "notification-manager") }
        tasks["task-manager"] = { makeAdapter(tmHome, "task-manager") }
        tasks["backend"] = { makeAdapter(backendHome, "backend") }
        
        parallel tasks
        
    } catch (e) {
        result = BuildStatus.FAILED
        throw e
    } finally {
        notifyBuild(result.code)
    }
}

def makeLib(home, name) {
    try {
        buildLib(home, name)
    }
    catch (e) {
        throw e
    }
}

def makeAdapter(home, name) {
    buildAdapter(home, name)
    deployAdapter(home, name)
}

def buildLib(home, name) {
    def label = name.toLowerCase()
    def params = "artifactoryPublish"

    stage("Build ${label}") {
        sh "cd ${home} && ./gradlew build ${params}"
    }
}

def buildAdapter(home, name) {
    def label = name.toLowerCase()
    def params = "" //"--refresh-dependencies"

    stage("Build ${label}") {
        sh "cd ${home} && ./gradlew build ${params}"
    }
}

def deployAdapter(home, name) {
    name = name.toLowerCase();
    def version = "latest"
    def tag = "eu.gcr.io/alert-shape-852/startup-jobmonitor-${name}:${version}"
    
    deployAdapter(home, name, tag)
}

def deployAdapter(home, name, tag) {
    try {
        buildDocker(home, name, tag)
    }
    catch (e) {
        throw e
    }
}

def buildDocker(home, name, tag) {
    def label = name.toLowerCase()
    def dockerfilePath = "${home}/src/main/docker/Dockerfile"
    def k8sPath = "${home}/src/main/k8s/" + name + ".yaml"

    stage("Docker build ${label}") {
        //milestone 1
        sh "cp ${dockerfilePath} ${home}/build/libs/Dockerfile"
		sh 'docker login -e 1234@5678.com -u oauth2accesstoken -p "$(gcloud auth print-access-token)" https://eu.gcr.io'
        sh "docker build -t ${tag} ${home}/build/libs/"
    }

    stage("k8s deploy ${label}") {
        lock(resource: 'google-cloud', inversePrecedence: true) {
            //milestone 2
            sh "gcloud auth activate-service-account --key-file /var/private/gauth.json"
            sh "gcloud docker -- push ${tag}"

            def deploymentName = "jobmonitor-startup-${name}"
            
            try {
                sh "kubectl delete deployment ${deploymentName}"
            }
            catch (e) {
                // Do not worry if deployment doesn't exists
            }

            sh "kubectl apply -f ${k8sPath}"
        }
    }
}

def notifyBuild(message) {
    def subject = "${message}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
    def summary = "${subject} (${env.BUILD_URL})"

    def colorCode = BuildStatus.FAILED.color
    if (message == 'Started') {
        colorCode = '#FFEB3B'
    } else if (message == 'Successful') {
        colorCode = BuildStatus.SUCCESSFUL.color
    }

    // Send notifications
    slackSend (color: colorCode, message: summary, channel: "job-monitor")
}

enum BuildStatus {
    SUCCESSFUL("Successful", "#2ecc71"),
    FAILED("Failed", "#e74c3c")

    final String code;
    final String color;

    public BuildStatus(String code, String color) {
        this.code = code;
        this.color = color;
    }
}

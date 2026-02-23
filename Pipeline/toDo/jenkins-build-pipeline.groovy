pipeline {
    agent { label 'E-commerce' }

    parameters {
        string(
            name: 'VERSION',
            defaultValue: '1.0.0',
            description: 'Enter the version to build'
        )
    }

    environment {
        TRIGGER_TIME = "${new Date().format('yyyy-MM-dd_HH:mm:ss')}"
        IMAGE_NAME = "todo"  // Change this to your Docker image name
    }

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Clean Workspace Direcory'){
            steps {
                cleanWs()
            }
        }
        stage('Print Info') {
            steps {
                echo "Root Directory : ${WORKSPACE}"
                echo "Job triggered at: ${env.TRIGGER_TIME}"
                echo "Building Docker image version: ${params.VERSION}"
            }
        }
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'master-enhanced',
                    url: 'https://github.com/CodeDebugAI/ToDo.git'

                // Run Maven on a Unix agent.
                echo "Root Directory : ${WORKSPACE}"
                echo "Pwd:"
                pwd
                echo "list:"
                ls
                dir('${WORKSPACE}/SourceCode/toDo') {
                    echo "Root Directory : ${WORKSPACE}"
                    echo "pwd:"
                    pwd
                    echo "list:"
                    ls
                    sh 'mvn clean install -U -DskipTests'
                }



                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image and tag it with version and trigger time
                    def imageTag = "${params.VERSION}"
                    echo "Building Docker image: ${env.IMAGE_NAME}:${imageTag}"

                    sh """
                        echo ${WORKSPACE}
                        pwd
                        ls
                        cd ${WORKSPACE}/SourceCode/toDo
                        ls
                        docker build -t ${env.IMAGE_NAME}:${imageTag} .
                    """
                }
            }
        }
        stage('Login to Registry') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'ghcr-token', usernameVariable: 'USER', passwordVariable: 'TOKEN')]) {
                    sh 'echo $TOKEN | docker login ghcr.io -u $USER --password-stdin'
                }
            }
        }
        stage('Clean Workspace Directory') {
            when {
                expression {
                    // run if previous stages succeeded or the build was aborted
                    return currentBuild.currentResult == 'SUCCESS' || currentBuild.currentResult == 'ABORTED'
                }
            }
            steps {
                cleanWs()
            }
        }
    }
}

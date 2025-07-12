pipeline {
    agent any

    tools {
        maven 'Maven-3.9.9'
        jdk 'Java-21'
    }

    environment {
        // Allure configuration
        ALLURE_RESULTS_PATH = 'target/allure-results'
        ALLURE_REPORT_PATH = 'target/allure-report'
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox'],
            description: 'Browser to run tests on'
        )
        choice(
            name: 'TEST_SUITE',
            choices: ['all', 'team-wins', 'top-players', 'performance'],
            description: 'Test suite to execute'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Run browser in headless mode'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "🔄 Checking out source code..."
                }
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    echo "🔨 Building project..."
                }
                sh 'mvn clean compile test-compile'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    echo "🧪 Running NBA Stats automation tests..."

                    // Set test configuration based on parameters
                    def testCommand = "mvn test"

                    // Add browser parameter
                    testCommand += " -Dbrowser=${params.BROWSER}"

                    // Add headless parameter
                    testCommand += " -Dheadless=${params.HEADLESS}"

                    // Add test suite selection
                    switch(params.TEST_SUITE) {
                        case 'team-wins':
                            testCommand += " -Dtest=TeamWinsTest"
                            break
                        case 'top-players':
                            testCommand += " -Dtest=TopPlayersTest"
                            break
                        case 'performance':
                            testCommand += " -Dtest=PerformanceTest"
                            break
                        default:
                            // Run all tests
                            testCommand += " -DsuiteXmlFile=testng.xml"
                    }

                    // Execute tests
                    sh testCommand
                }
            }
            post {
                always {
                    // Archive test results
                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
                    allure commandline: 'allure', results: [[path: 'target/allure-results']]
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                script {
                    echo "📊 Generating Allure test report..."
                    
                    // Ensure the report directory exists
                    sh "mkdir -p ${env.ALLURE_REPORT_PATH}"
                    
                    // Generate Allure report using the configured paths
                    sh "mvn allure:report -Dallure.results.directory=${env.ALLURE_RESULTS_PATH} -Dallure.report.directory=${env.ALLURE_REPORT_PATH}"
                    
                    // Publish Allure report
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: env.ALLURE_RESULTS_PATH]],
                        report: env.ALLURE_REPORT_PATH
                    ])
                    
                    // Archive the Allure report
                    archiveArtifacts artifacts: "${env.ALLURE_REPORT_PATH}/**/*", allowEmptyArchive: true
                }
            }
        }
    }

    post {
        always {
            script {
                echo "🧹 Cleaning up workspace..."
            }

            // Clean up workspace
            cleanWs()
        }

        success {
            script {
                echo "✅ Pipeline completed successfully!"
            }

            // Send success notification
            emailext (
                subject: "✅ NBA Stats Tests - Success",
                body: "All NBA stats automation tests passed successfully.\n\nBuild: ${env.BUILD_URL}",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }

        failure {
            script {
                echo "❌ Pipeline failed!"
            }
        }

        unstable {
            script {
                echo "⚠️ Pipeline completed with test failures!"
            }
        }
    }
}
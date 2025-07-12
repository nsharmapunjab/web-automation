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

                    // Execute tests with error handling
                    try {
                        sh testCommand
                    } catch (Exception e) {
                        echo "⚠️ Tests failed but continuing to generate reports: ${e.getMessage()}"
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
            post {
                always {
                    // Publish test results using simple junit step
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true

                    // Archive allure results
                    archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
                }
            }
        }

        // Remove this stage as Allure generation is now in post section
    }

    post {
        always {
            script {
                echo "🧹 Post-build actions running..."

                // Generate Allure Report (always runs)
                echo "📊 Generating Allure test report..."

                // Ensure the report directory exists
                sh "mkdir -p ${env.ALLURE_REPORT_PATH}"

                // Generate and publish Allure report
                try {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: env.ALLURE_RESULTS_PATH]]
                    ])
                    echo "✅ Allure report generated successfully"
                } catch (Exception e) {
                    echo "⚠️ Allure report generation failed: ${e.getMessage()}"
                }

                // Archive the generated report
                archiveArtifacts artifacts: "${env.ALLURE_REPORT_PATH}/**", allowEmptyArchive: true
            }

            // Publish final test results using simple junit step
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true

            // Archive screenshots if any test failures occurred
            archiveArtifacts artifacts: 'target/screenshots/**', allowEmptyArchive: true
        }

        success {
            script {
                echo "✅ Pipeline completed successfully!"
            }
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
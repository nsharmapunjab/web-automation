pipeline {
    agent any

    tools {
        maven 'Maven-3.9.0'
        jdk 'JDK-11'
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

        stage('Setup Environment') {
            steps {
                script {
                    echo "🛠️ Setting up test environment..."
                }

                // Clean previous test results
                sh 'rm -rf target/allure-results target/allure-report target/surefire-reports'

                // Install Chrome browser for headless testing
                sh '''
                    if ! command -v google-chrome &> /dev/null; then
                        echo "Installing Chrome browser..."
                        wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add -
                        echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list
                        apt-get update
                        apt-get install -y google-chrome-stable
                    fi
                '''

                // Set display for headless mode
                sh 'export DISPLAY=:99'
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
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                script {
                    echo "📊 Generating Allure test report..."
                }

                // Generate Allure report
                sh 'mvn allure:report'

                // Publish Allure report
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: env.ALLURE_RESULTS_PATH]]
                ])
            }
        }

        stage('Archive Artifacts') {
            steps {
                script {
                    echo "📦 Archiving build artifacts..."
                }

                // Archive test reports and logs
                archiveArtifacts artifacts: 'target/allure-results/**, target/allure-report/**, target/surefire-reports/**',
                                 fingerprint: true

                // Archive screenshots if any test failures occurred
                archiveArtifacts artifacts: 'target/screenshots/**',
                                 allowEmptyArchive: true
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

            // Send failure notification
            emailext (
                subject: "❌ NBA Stats Tests - Failed",
                body: "NBA stats automation tests failed.\n\nBuild: ${env.BUILD_URL}\nAllure Report: ${env.BUILD_URL}allure/",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }

        unstable {
            script {
                echo "⚠️ Pipeline completed with test failures!"
            }

            // Send unstable notification
            emailext (
                subject: "⚠️ NBA Stats Tests - Unstable",
                body: "Some NBA stats automation tests failed.\n\nBuild: ${env.BUILD_URL}\nAllure Report: ${env.BUILD_URL}allure/",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
    }
}
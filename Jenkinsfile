pipeline {
    agent any
    environment {
        // App Settings
        parabank_port=8090
        parabank_cov_port=8050

        // Parasoft Licenses
        ls_url="${PARASOFT_LS_URL}"
        ls_user="${PARASOFT_LS_USER}"
        ls_pass="${PARASOFT_LS_PASS}"
        
        // Parasoft DTP Settings
        dtp_url="${PARASOFT_DTP_URL}"
        project_name="Parabank-Jenkins"
        buildId="PBJ-${BUILD_TIMESTAMP}-${BUILD_ID}"
        jtestSessionTag="ParabankJenkins-Jtest"
        soatestSessionTag="ParabankJenkins-SOAtest"
        dtp_publish="${PARASOFT_DTP_PUBLISH}"

        // Parasoft Jtest Settings
        jtestSAConfig="jtest.builtin://Recommended Rules"
        jtestMAConfig="jtest.builtin://Metrics"
        soatestConfig="soatest.user://Example Configuration"
        unitCovImage="Parabank_All;Parabank_UnitTest"

        // Parasoft SOAtest Settings
        soatestCovImage="Parabank_All;Parabank_SOAtest"
    }
    stages {
        stage('Build') {
            steps {
                //cleanWs()
                                
                // build the project


                echo '---> Parsing 10.x unit test reports'
                script {
                    step([$class: 'XUnitPublisher', 
                        thresholds: [failed(failureNewThreshold: '0', failureThreshold: '0')],
                        tools: [[$class: 'ParasoftType', 
                            deleteOutputFiles: true, 
                            failIfNotNew: false, 
                            pattern: '/parabank/target/jtest/ut/*.xml', 
                            skipNoTestFiles: true, 
                            stopProcessingIfError: false
                        ]]
                    ])
                }

                // echo '---> Parsing 10.x static analysis reports'
                // script {
                //     step()
                // }
                // step(recordIssues 
                //     healthy: 5, 
                //     minimumSeverity: 'HIGH', 
                //     qualityGates: [[
                //         threshold: 5, 
                //         type: 'TOTAL_ERROR', 
                //         unstable: 
                //         false
                //     ]], 
                //     skipPublishingChecks: true, 
                //     tools: [
                //         parasoftFindings(
                //             localSettingsPath: './parabank-jenkins/jtest/jtestcli.properties', 
                //             pattern: './parabank/target/jtest/**/*.xml'
                //         )
                //     ], 
                //     unhealthy: 10
                // )
            }
        }
        stage('Deploy') {
            steps {
                // deploy the project
                sh  '''
                    #TODO
                    '''
            }
        }
                
        stage('Test') {
            steps {
                // test the project
                sh  '''
                    # Set Up and write .properties file
                    echo $"
                    parasoft.eula.accepted=true

                    license.network.use.specified.server=true
                    license.network.url=${ls_url}
                    license.network.auth.enabled=true
                    license.network.user=${ls_user}
                    license.network.password=${ls_pass}
                    soatest.license.use_network=true
                    soatest.license.network.edition=custom_edition
                    soatest.license.custom_edition_features=RuleWizard, Command Line, SOA, Web, Server API Enabled, Message Packs, Advanced Test Generation Desktop, Requirements Traceability, API Security Testing
                    
                    report.developer_reports=false
                    report.associations=true
                    report.scontrol=full
                    scope.local=true
                    scope.scontrol=true
                    scope.xmlmap=false

                    application.coverage.enabled=true
                    application.coverage.agent.url=http\\://[TODO]\\:${parabank_cov_port}
                    application.coverage.images=${soatestCovImage}
                    application.coverage.runtime.dir=[TODO]\\runtime_coverage_data
                    application.coverage.binaries.include=com/parasoft/**

                    scontrol.git.exec=git
                    scontrol.rep1.git.branch=master
                    scontrol.rep1.git.url=https://github.com/parasoft/parabank.git
                    scontrol.rep1.type=git

                    build.id="${buildId}"
                    session.tag="${soatestSessionTag}"
                    dtp.url=${dtp_url}
                    dtp.user=${ls_user}
                    dtp.password=${ls_pass}
                    dtp.project=${project_name}" > ./parabank-jenkins/soatest/soatestcli.properties

                    # Debug: Print soatestcli.properties file
                    cat ./parabank-jenkins/soatest/soatestcli.properties
                    '''
            }
        }
        stage('Release') {
            steps {
                // Release the project
                sh  '''
                        
                # Clean up
                # docker stop ${app_name}
                    
                '''
            }
        }
    }
    // post {
    //     // Clean after build
    //     always {
    //         archiveArtifacts artifacts: '**/target/**/*.war, **/target/jtest/**, **/soatest/report/**',
    //             fingerprint: true, 
    //             onlyIfSuccessful: true
            
    //         cleanWs(cleanWhenNotBuilt: false,
    //             deleteDirs: true,
    //             disableDeferredWipeout: false,
    //             notFailBuild: true,
    //             patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
    //                 [pattern: '.propsfile', type: 'EXCLUDE']])
    //     }
    // }
}
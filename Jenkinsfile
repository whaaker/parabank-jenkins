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
                deleteDir()
                                
                // build the project
                sh  '''
                    mkdir parabank-jenkins
                    git clone https://github.com/whaaker/parabank-jenkins.git parabank-jenkins
                    
                    mkdir parabank
                    git clone https://github.com/parasoft/parabank parabank

                    pwd
                    ls -ll

                    # Set Up and write .properties file
                    echo $"
                    parasoft.eula.accepted=true
                    jtest.license.use_network=true
                    jtest.license.network.edition=custom_edition
                    jtest.license.custom_edition_features=Jtest, Static Analysis, Flow Analysis, OWASP Rules, CWE Rules, PCI DSS Rules, DISA STIG Rules, Security Rules, Automation, Desktop Command Line, DTP Publish, Coverage, Unit Test, Unit Test Bulk Creation, Unit Test Tier 1, Unit Test Tier 2, Unit Test Tier 3, Unit Test Tier 4, Unit Test Spring Framework, Change Based Testing
                    license.network.use.specified.server=true
                    license.network.auth.enabled=true
                    license.network.url=${ls_url}
                    license.network.user=${ls_user}
                    license.network.password=${ls_pass}

                    report.associations=false
                    report.coverage.images="${unitCovImage}"
                    report.scontrol=full
                    scope.local=true
                    scope.scontrol=true
                    scope.xmlmap=false
                    
                    scontrol.git.exec=git
                    scontrol.rep1.git.branch=master
                    scontrol.rep1.git.url=https://github.com/parasoft/parabank.git
                    scontrol.rep1.type=git

                    build.id="${buildId}"
                    session.tag="${jtestSessionTag}"
                    dtp.url=${dtp_url}
                    dtp.user=${ls_user}
                    dtp.password=${ls_pass}
                    dtp.project=${project_name}" > ./parabank-jenkins/jtest/jtestcli.properties

                    # Debug: Print jtestcli.properties file
                    cat ./parabank-jenkins/jtest/jtestcli.properties
                    '''
                sh '''
                    # Run Maven build with Jtest tasks via Docker
                    docker run \
                    -u 0:0 \
                    --rm -i \
                    -v "$PWD:$PWD" \
                    -w "$PWD" \
                    --network=demo-net \
                    $(docker build -q ./parabank-jenkins/jtest) /bin/bash -c " \
                    cd parabank; \

                    mvn package jtest:monitor \
                    -s /home/parasoft/.m2/settings.xml \
                    -Dmaven.test.skip=true \
                    -Djtest.settings='../parabank-jenkins/jtest/jtestcli.properties' \
                    -Djtest.showSettings=true \
                    -Dproperty.report.dtp.publish=${dtp_publish}; \
                    "
                    # Unzip monitor.zip
                    mkdir monitor
                    unzip -q ./parabank/target/jtest/monitor/monitor.zip -d .
                    ls -ll
                    ls -la monitor
                    '''
            }
        }
        stage('Deploy') {
            steps {
                // deploy the project
                sh  '''
                    # Run Parabank-baseline docker image with Jtest coverage agent configured
                    docker run \
                    -d \
                    -p 8090:8080 \
                    -p 8050:8050 \
                    -p 9021:9001 \
                    -p 63617:61616 \
                    --env-file ./parabank-jenkins/jtest/monitor.env \
                    -v "/monitor:/home/docker/jtest" \
                    --network=demo-net \
                    --name parabank-baseline \
                    parasoft/parabank:baseline
                    '''
            }
        }
                
        stage('Test') {
            steps {
                // test the project
                sh  '''
                    docker ps -f name=parabank-baseline

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
                // sh  '''

                //     '''
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
    post {
        // Clean after build
        always {
            sh 'docker container rm parabank-baseline'
            sh 'docker image prune -f'

            archiveArtifacts(artifacts: '**/target/**/*.war, **/target/jtest/**, **/soatest/report/**',
                fingerprint: true, 
                onlyIfSuccessful: true
            )
        }
    }
}
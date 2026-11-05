param(
    [Parameter(Mandatory=$true)][string]$MavenRepository,
    [Parameter(Mandatory=$true)][string]$JdkDirectory,
    [Parameter(Mandatory=$true)][string]$H2Jar
)
$ErrorActionPreference = 'Stop'
$repo = $MavenRepository
$repo = (Resolve-Path -LiteralPath $repo).Path
$root = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot '..\..')).Path
$lib = @(
'org/hibernate/hibernate-core/5.4.32.Final/hibernate-core-5.4.32.Final.jar',
'jakarta/persistence/jakarta.persistence-api/2.2.3/jakarta.persistence-api-2.2.3.jar',
'org/hibernate/common/hibernate-commons-annotations/5.1.2.Final/hibernate-commons-annotations-5.1.2.Final.jar',
'org/jboss/logging/jboss-logging/3.4.1.Final/jboss-logging-3.4.1.Final.jar',
'org/javassist/javassist/3.27.0-GA/javassist-3.27.0-GA.jar',
'net/bytebuddy/byte-buddy/1.10.22/byte-buddy-1.10.22.jar',
'antlr/antlr/2.7.7/antlr-2.7.7.jar',
'jakarta/transaction/jakarta.transaction-api/1.3.3/jakarta.transaction-api-1.3.3.jar',
'org/jboss/jandex/2.2.3.Final/jandex-2.2.3.Final.jar',
'com/fasterxml/classmate/1.5.1/classmate-1.5.1.jar',
'javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar',
'org/dom4j/dom4j/2.1.3/dom4j-2.1.3.jar',
'com/fasterxml/jackson/core/jackson-databind/2.12.4/jackson-databind-2.12.4.jar',
'com/fasterxml/jackson/core/jackson-core/2.12.4/jackson-core-2.12.4.jar',
'com/fasterxml/jackson/core/jackson-annotations/2.12.4/jackson-annotations-2.12.4.jar',
'com/domed/transPort/domed-common/1.1/domed-common-1.1.jar',
'org/apache/tomcat/embed/tomcat-embed-core/9.0.50/tomcat-embed-core-9.0.50.jar',
'org/springframework/spring-test/5.3.9/spring-test-5.3.9.jar',
'org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar')
foreach ($spring in @('context','beans','core','tx','web','webmvc','jdbc','orm','aop','expression','jcl')) {
 $lib += "org/springframework/spring-$spring/5.3.9/spring-$spring-5.3.9.jar"
}
$paths = @()
foreach ($jar in $lib) {
 $path = Join-Path $repo $jar
 if (-not (Test-Path -LiteralPath $path)) { throw "Missing cached library: $jar" }
 $paths += $path
}
$paths += (Resolve-Path -LiteralPath $H2Jar).Path
$classes = Join-Path $PSScriptRoot 'test-output'
New-Item -ItemType Directory -Path $classes -Force | Out-Null
$cp = $paths -join ';'
Set-Content -LiteralPath (Join-Path $classes 'classpath.txt') -Value $cp -Encoding UTF8
$foundation = Join-Path $root 'limit_space_api\domed-foundation\src\main\java\com\domed\domain\transportDomain'
$sources = @((Join-Path $foundation 'HyClb.java'), (Join-Path $foundation 'VehicleWorkflowAudit.java'), (Join-Path $foundation 'transportTemp\HyClbTemp.java'))
$manage = Join-Path $root 'limit_space_api\domed-manage-api\src\main\java\com\domed\api'
$sources += (Get-ChildItem -LiteralPath (Join-Path $manage 'service\transportService\vehicleWorkflow') -Filter '*.java').FullName
$sources += (Get-ChildItem -LiteralPath (Join-Path $manage 'controller\transportController\vehicleWorkflow') -Filter '*.java').FullName
& (Join-Path $JdkDirectory 'bin\javac.exe') -encoding UTF-8 -source 1.8 -target 1.8 -cp $cp -d $classes @sources 2>&1 | Tee-Object -FilePath (Join-Path $classes 'vehicle-compile.log')
if ($LASTEXITCODE -ne 0) { throw 'Vehicle compilation failed' }
Write-Output "Compiled $($sources.Count) vehicle source files with Java 8."

& (Join-Path $JdkDirectory 'bin\javac.exe') -encoding UTF-8 -cp "$classes;$cp" -d $classes (Join-Path $PSScriptRoot 'VehicleWorkflowChecks.java')
if ($LASTEXITCODE -ne 0) { throw 'Test compilation failed' }
& (Join-Path $JdkDirectory 'bin\java.exe') '-Dfile.encoding=UTF-8' -cp "$classes;$cp" VehicleWorkflowChecks (Join-Path $root 'tests\fixtures\HY_CLB_reference.sql') (Join-Path $root 'database\VEHICLE_WORKFLOW_REVIEW_REQUIRED.sql') 2>&1 | Tee-Object -FilePath (Join-Path $classes 'vehicle-tests.log')
if ($LASTEXITCODE -ne 0) { throw 'Vehicle persistence checks failed' }
Push-Location -LiteralPath $root
try {
    $env:VEHICLE_PROJECT_ROOT = $root
    & node (Join-Path $PSScriptRoot 'frontend-vehicle-checks.cjs')
    if ($LASTEXITCODE -ne 0) { throw 'Frontend checks failed' }
} finally {
    Remove-Item Env:\VEHICLE_PROJECT_ROOT -ErrorAction SilentlyContinue
    Pop-Location
}

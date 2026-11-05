param(
    [string]$JdkDirectory = $env:JAVA_HOME,
    [string]$MavenCommand = 'mvn',
    [string]$MavenRepository = $env:TRANSPORT_MAVEN_REPOSITORY,
    [switch]$Offline
)
$ErrorActionPreference = 'Stop'
if (-not $JdkDirectory) { throw 'Set JAVA_HOME to JDK 8 or pass -JdkDirectory.' }
$root = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot '..')).Path
$settings = Join-Path $PSScriptRoot 'maven-local-settings.xml'
$arguments = @('-B','-ntp','-s',$settings)
if ($Offline) { $arguments += '-o' }
if ($MavenRepository) { $arguments += "-Dmaven.repo.local=$MavenRepository" }
$previousJava = $env:JAVA_HOME
try {
    $env:JAVA_HOME = $JdkDirectory
    & $MavenCommand @arguments -f (Join-Path $root 'limit_space_api/pom.xml') clean install
    if ($LASTEXITCODE -ne 0) { throw 'Backend reactor build failed' }
    & $MavenCommand @arguments -f (Join-Path $PSScriptRoot 'vehicle-test-server/pom.xml') clean package
    if ($LASTEXITCODE -ne 0) { throw 'Development service build/tests failed' }
    Write-Output 'Build complete. Run: node ./local-development/start-local-test.cjs'
} finally { $env:JAVA_HOME = $previousJava }

$ErrorActionPreference = 'Stop'
$pidFile = Join-Path $PSScriptRoot '.run\pids.json'
if (-not (Test-Path -LiteralPath $pidFile)) { Write-Output 'No local test PID record. Use Ctrl+C in its launcher terminal if it is still running.'; exit 0 }
$state = Get-Content -Raw -LiteralPath $pidFile | ConvertFrom-Json
$expectedRoot = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot '..')).Path
if ($state.root -ne $expectedRoot) { throw 'PID record is for another working copy' }
# The launcher stops only its remembered child objects; no supplied PID is killed.
@{root=[string]$state.root;launcher=[int]$state.launcher} | ConvertTo-Json |
    Set-Content -LiteralPath (Join-Path $PSScriptRoot '.run\stop-request.json') -Encoding UTF8
$deadline = [DateTime]::UtcNow.AddSeconds(10)
while ((Test-Path -LiteralPath $pidFile) -and [DateTime]::UtcNow -lt $deadline) {
    Start-Sleep -Milliseconds 200
}
if (Test-Path -LiteralPath $pidFile) { throw 'Launcher did not acknowledge shutdown. Use Ctrl+C in its original terminal; no unrelated process was stopped.' }
Write-Output 'Development services stopped.'

# 保存状态到 C:\work\last_state.json
$state = @{
    name="winProcesSnap"
    timestamp = (Get-Date).ToString("s")
    WindowProcessList =Get-Process | Where-Object {
      $_.MainWindowTitle -ne "" -and
       $_.Name -notmatch '^\.*$'   # 排除只有点的名称
    } | ForEach-Object {
                 $proc = $_
                 $cmd = Get-CimInstance Win32_Process -Filter "ProcessId = $($proc.Id)"

                 [PSCustomObject]@{
                     Name            = $proc.Name
                     MainWindowTitle = $proc.MainWindowTitle
                     CommandLine     = $cmd.CommandLine
                     ProcessId       = $proc.Id
                 }
             }

}

# covnert to json
$state | ConvertTo-Json -Depth 5 | Out-File "C:\work\last_state.json"

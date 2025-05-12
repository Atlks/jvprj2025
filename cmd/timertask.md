2️⃣ 定时任务（每 5 分钟执行）：
用管理员权限打开 PowerShell：

powershell
复制
编辑
$action = New-ScheduledTaskAction -Execute "powershell.exe" -Argument "-File C:\0prj\OptiBot\cmd\save-state.ps1"


$trigger = New-ScheduledTaskTrigger `
    -Once -At (Get-Date).AddMinutes(1) `
-RepetitionInterval (New-TimeSpan -Minutes 5) `
-RepetitionDuration (New-TimeSpan -Days 365)



Register-ScheduledTask -Action $action -Trigger $trigger -TaskName "SaveWorkState" -Description "定期保存状态" -User "SYSTEM" -RunLevel Highest -Force

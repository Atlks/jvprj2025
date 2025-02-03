




ffmpeg  -i  网络连接警报.mp3  网络连接警报.mp3.wav

ffmpeg  -i "recordings\AwACAgUAAyEFAASS6MGQAAMMZ6A_QlW8yqLrrOG83y3e_CmicJsAAiETAAKaJwFVXU47jatYxnQ2BA.ogg" "recordings\AwACAgUAAyEFAASS6MGQAAMMZ6A_QlW8yqLrrOG83y3e_CmicJsAAiETAAKaJwFVXU47jatYxnQ2BA.wav"


public static string ConvertToWav(string inputFile)
{
string outputFile = Path.ChangeExtension(inputFile, ".wav");

     ProcessStartInfo psi = new()
     {
         FileName = ffmpegPath,
         Arguments = $"-i \"{inputFile}\" \"{outputFile}\"",
         RedirectStandardOutput = true,
         RedirectStandardError = true,
         UseShellExecute = false,
         CreateNoWindow = true
     };
    
     Console.WriteLine($"{ffmpegPath}  {psi.Arguments}");
     using var process = Process.Start(psi);
     process.WaitForExit();

     if (process.ExitCode == 0)
     {
         Console.WriteLine($"🎵 ConvertToWav ok: {outputFile}");
         return outputFile;
     }
     else
     {
         Console.WriteLine($"❌ ConvertToWav fail: {inputFile}");
         return string.Empty;
     }
}


搜索歌词机器人
自动搜索下载mp3机器人
yt-dlp.exe https://www.youtube.com/watch?v=dQw4w9WgXcQ

✅ 使用示例（Windows 命令行）
sh
复制
编辑
yt-dlp.exe https://www.youtube.com/watch?v=dQw4w9WgXcQ
提取 MP3 音频（需要 FFmpeg）：

sh
复制
编辑
yt-dlp.exe -x --audio-format mp3 https://www.youtube.com/watch?v=dQw4w9WgXcQ



C:\Users\attil\IdeaProjects\srchShopInTG\mdsjprj\lib\avClas.cs

     public static string  DownloadSongAsMp3(string songName,string dir)
        {
            var __METHOD__ = "DownloadSongAsMp3";
            dbgCls.PrintCallFunArgs(__METHOD__, dbgCls.func_get_args(  songName, dir));

            try
            {
                var youtube = new YoutubeClient();

                // 搜索视频
                var searchResults = youtube.Search.GetVideosAsync(songName).GetAwaiter().GetResult(); ;
                //Task.Run(async () =>
                //{
                //    await
                //}).aw
                    
                var video = searchResults.FirstOrDefault();

                if (video == null)
                {
                   Print("未找到歌曲！");
                    return "";
                }

                var streamManifest =   youtube.Videos.Streams.GetManifestAsync(video.Id).Result;
                var audioStreams = streamManifest.GetAudioOnlyStreams();
                var streamInfo = audioStreams.OrderByDescending(s => s.Bitrate).FirstOrDefault();

                if (streamInfo == null)
                {
                   Print("未找到音频流！");
                    return "";
                }
                Directory.CreateDirectory(dir);
                // 下载音频流
                var tempFile = Path.GetTempFileName();
              // string filePathTmp = dir + "/" + tempFile;
               Print($"down tmpfile=>{tempFile}");
                youtube.Videos.Streams.DownloadAsync(streamInfo, tempFile).GetAwaiter().GetResult(); ;

                // 转换为 MP3
                // 转换为 MP3
                // 转换为 MP3
                string fname = ConvertToValidFileName2024(songName);
                var outputFilePath = $"{dir}/{fname}.mp3";
               Print($"outputFilePath =>{outputFilePath}");
                  ConvertToMp3(tempFile, outputFilePath);

                // 删除临时文件
              //  File.Delete(tempFile);

               Print($"歌曲已下载并转换为 MP3：{outputFilePath}");
                return outputFilePath;
            }
            catch (Exception ex)
            {
               Print(ex.ToString());
            }
            dbgCls.PrintRet(__METHOD__, 0);
            return "";
        }

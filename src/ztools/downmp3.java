//import com.github.kiulian.downloader.YoutubeDownloader;
//import com.github.kiulian.downloader.model.YoutubeVideo;
//import com.github.kiulian.downloader.model.formats.AudioFormat;
//import com.github.kiulian.downloader.model.search.SearchResult;
//import com.github.kiulian.downloader.model.search.SearchResultVideo;
//import java.io.File;
//import java.io.IOException;
//import java.util.Comparator;
//import java.util.List;
//
//public class MusicDownloader {
//
//    public static String downloadSongAsMp3(String songName, String dir) {
//        final String __METHOD__ = "DownloadSongAsMp3";
//        System.out.println(__METHOD__ + " called with args: " + songName + ", " + dir);
//
//        try {
//            YoutubeDownloader downloader = new YoutubeDownloader();
//
//            // 搜索视频
//            List<SearchResult> searchResults = downloader.searchVideos(songName, 1).items();
//
//            SearchResultVideo video = null;
//            for (SearchResult result : searchResults) {
//                if (result instanceof SearchResultVideo) {
//                    video = (SearchResultVideo) result;
//                    break;
//                }
//            }
//
//            if (video == null) {
//                System.out.println("未找到歌曲！");
//                return "";
//            }
//
//            YoutubeVideo ytVideo = downloader.getVideo(video.videoId());
//            List<AudioFormat> audioFormats = ytVideo.audioFormats();
//
//            // 选择音质最好的音频流
//            AudioFormat bestAudio = audioFormats.stream()
//                    .max(Comparator.comparingInt(AudioFormat::bitrate))
//                    .orElse(null);
//
//            if (bestAudio == null) {
//                System.out.println("未找到音频流！");
//                return "";
//            }
//
//            // 创建目录
//            new File(dir).mkdirs();
//
//            // 下载音频临时文件
//            File tempFile = File.createTempFile("yt_audio", ".webm");
//            System.out.println("down tmpfile => " + tempFile.getAbsolutePath());
//
//            ytVideo.download(bestAudio, tempFile);
//
//            // 转换为 mp3
//            String validFileName = convertToValidFileName(songName);
//            String outputFilePath = dir + File.separator + validFileName + ".mp3";
//            System.out.println("outputFilePath => " + outputFilePath);
//
//            convertToMp3(tempFile.getAbsolutePath(), outputFilePath);
//
//            // 删除临时文件
//            // tempFile.delete();
//
//            System.out.println("歌曲已下载并转换为 MP3：" + outputFilePath);
//            return outputFilePath;
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        System.out.println(__METHOD__ + " return empty");
//        return "";
//    }
//
//    private static String convertToValidFileName(String input) {
//        return input.replaceAll("[\\\\/:*?\"<>|]", "_");
//    }
//
//    private static void convertToMp3(String inputPath, String outputPath) throws IOException, InterruptedException {
//        // 使用 FFmpeg 进行转换（需要 FFmpeg 安装在系统中）
//        ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", inputPath, "-vn", "-ab", "192k", "-ar", "44100", "-y", outputPath);
//        pb.inheritIO();
//        Process process = pb.start();
//        process.waitFor();
//    }
//}

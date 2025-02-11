
espeak "Hello, this is a test." -w output.wav


方法 2：使用 Google Translate TTS + ffmpeg
Google Translate 也支持 TTS，可以下载 MP3，然后用 ffmpeg 处理：

sh
复制
编辑
wget -O speech.mp3 "https://translate.google.com/translate_tts?ie=UTF-8&tl=en&client=tw-ob&q=Hello%20world"
ffmpeg -i speech.mp3 output.wav


✅ 方法 3：Windows 下使用 PowerShell + ffmpeg
如果你在 Windows 上，可以用 PowerShell 的 SAPI.SpVoice 进行语音合成：
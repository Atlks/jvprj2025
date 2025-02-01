for %i in (*.mp3) do ffmpeg -i "%i" "%~ni.wav"

ffmpeg  -i 普通消息普通消息.mp3 普通消息普通消息.mp3.wav
ffmpeg  -i 我的消息我的消息我的消息.mp3  我的消息我的消息我的消息.mp3.wav

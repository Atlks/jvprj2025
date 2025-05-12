
/data/upload_package/optibot/cfg/dbcfg.ini


sudo rm -f app.log
log:   /data/java_work/optibot/app.log

C:\Windows\System32\OpenSSH\scp.exe -i "C:\Users\attil\Downloads\tz_app_amazonlinux.pem" ec2-user@18.136.123.232:/data/java_work/optibot/app.log .

。。。


C:\Windows\System32\OpenSSH\scp.exe -i "C:\Users\attil\Downloads\tz_app_amazonlinux.pem" ec2-user@18.136.123.232:/data/upload_package/optibot/cfg/dbcfg.ini .


cd /data/java_work/optibot/

/usr/lib/jvm/jdk-21.0.6/bin/java -jar optibot.jar -Ddbcfg=/data/upload_package/optibot/cfg/dbcfg.ini



C:\Windows\System32\OpenSSH\scp.exe -i "C:\Users\attil\Downloads\tz_app_amazonlinux.pem" C:\0prj\OptiBot\h2cfg\dbcfg.ini ec2-user@18.136.123.232:/home/ec2-user/

sudo mv /home/ec2-user/dbcfg.ini /data/upload_package/optibot/cfg/


C:\Windows\System32\OpenSSH\scp.exe -i "C:\Users\attil\Downloads\tz_app_amazonlinux.pem" C:\0prj\OptiBot\h2cfg\dbcfg.ini ec2-user@18.136.123.232:/data/upload_package/optibot/cfg/
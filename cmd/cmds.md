
/data/upload_package/optibot/cfg/dbcfg.ini


ssh -i "C:\Users\attil\Downloads\tz_app_amazonlinux.pem" ec2-user@18.136.123.232

C:\Users\Administrator\Downloads

ssh -i "C:\Users\Administrator\Downloads\tz_app_amazonlinux.pem" ec2-user@18.136.123.232

sudo rm -f app.log


。。。

----------down cfg

C:\Windows\System32\OpenSSH\scp.exe -i "C:\Users\attil\Downloads\tz_app_amazonlinux.pem" ec2-user@18.136.123.232:/data/upload_package/optibot/cfg/dbcfg.ini .

C:\Windows\System32\OpenSSH\scp.exe -i "C:\Users\Administrator\Downloads\tz_app_amazonlinux.pem" ec2-user@18.136.123.232:/data/upload_package/optibot/cfg/dbcfg.ini .

cat /data/upload_package/optibot/cfg/dbcfg.ini

-----------down db
C:\Windows\System32\OpenSSH\scp.exe -r -i "C:\Users\attil\Downloads\tz_app_amazonlinux.pem" ec2-user@18.136.123.232:/data/java_work/optibot/data /localsave

sudo rm app.log

、、--------boot

cd /data/java_work/optibot/

sudo /usr/lib/jvm/jdk-21.0.6/bin/java -jar optibot.jar -Ddbcfg=/data/upload_package/optibot/cfg/dbcfg.ini

sudo apt install -y fontconfig libfreetype6 ttf-dejavu
-----------chg cfgs
C:\Windows\System32\OpenSSH\scp.exe -i "C:\Users\attil\Downloads\tz_app_amazonlinux.pem" C:\0prj\OptiBot\cfgSvr\dbcfg.ini ec2-user@18.136.123.232:/home/ec2-user/

C:/Windows/System32/OpenSSH/scp.exe -i  C:\Users\Administrator\Downloads\tz_app_amazonlinux.pem   C:\Users\Administrator\IdeaProjects\OptiBot\cfg\dbcfg.ini ec2-user@18.136.123.232:/home/ec2-user/

C:\Users\Administrator\dbcfg.ini
C:/Windows/System32/OpenSSH/scp.exe -i  C:\Users\Administrator\Downloads\tz_app_amazonlinux.pem  C:\Users\Administrator\dbcfg.ini ec2-user@18.136.123.232:/home/ec2-user/

sudo mv /home/ec2-user/dbcfg.ini /data/upload_package/optibot/cfg/

cat /data/upload_package/optibot/cfg/dbcfg.ini

------------no auth

C:\Windows\System32\OpenSSH\scp.exe -i "C:\Users\attil\Downloads\tz_app_amazonlinux.pem" C:\0prj\OptiBot\h2cfg\dbcfg.ini ec2-user@18.136.123.232:/data/upload_package/optibot/cfg/


-------down log

log:   /data/java_work/optibot/app.log

C:\Windows\System32\OpenSSH\scp.exe -i "C:\Users\attil\Downloads\tz_app_amazonlinux.pem" ec2-user@18.136.123.232:/data/java_work/optibot/app.log .


C:\Windows\System32\OpenSSH\scp.exe -i "C:\Users\Administrator\Downloads\tz_app_amazonlinux.pem" ec2-user@18.136.123.232:/data/java_work/optibot/app.log .



C:/Windows/System32/OpenSSH/scp.exe -i  C:\Users\Administrator\Downloads\tz_app_amazonlinux.pem  C:\Users\Administrator\dbcfg.ini ec2-user@18.136.123.232:/home/ec2-user/
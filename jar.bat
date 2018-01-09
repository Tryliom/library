@echo off
set BIBN=lib.jar
call mvn compiler:compile
call mvn package -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
rmdir /S /Q Doc\server
mkdir Doc\server
mkdir Doc\server\config
cd target
ren "lib-1.5-shaded.jar" "%BIBN%"
cd ..
xcopy /s target\%BIBN% Doc\server\
copy config Doc\server\config
echo java -jar %BIBN% > Doc\server\start.bat
echo pause >> Doc\server\start.bat
del "target\*.jar"
echo Jar created ! Go to Doc\server and execute start.bat !
timeout /t 10>nul
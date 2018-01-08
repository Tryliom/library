call mvn package -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
rmdir /S /Q Doc\server
mkdir Doc\server
mkdir Doc\server\config
xcopy /s target\Bib-1.5.jar Doc\server\
copy config Doc\server\config
echo java -jar Bib-1.5.jar > Doc\server\start.bat
echo pause >> Doc\server\start.bat
del "target\Bib-1.5.jar"
echo Jar created !
pause
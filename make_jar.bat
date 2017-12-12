call mvn package -Dmaven.javadoc.skip=true
rmdir /S /Q Doc\server
mkdir Doc\server
xcopy /s target\Bib-1.5.jar Doc\server\
echo java -jar Bib-1.5.jar > Doc\server\start.bat
pause
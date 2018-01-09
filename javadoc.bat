@echo off
call mvn javadoc:javadoc
xcopy /s target\site\apidocs Doc\JavaDoc\
rmdir /S /Q target\site\apidocs
echo Javadoc created in Doc\JavaDoc !
timeout /t 10>nul
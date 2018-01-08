call mvn javadoc:javadoc
xcopy /s target\site\apidocs Doc\JavaDoc\
rmdir /S /Q target\site\apidocs
pause
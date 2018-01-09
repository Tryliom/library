@echo off
rmdir /S /Q Doc\server
rmdir /S /Q target
rmdir /S /Q Doc\JavaDoc
echo It's clean !
timeout /t 10>nul
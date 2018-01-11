@echo off
for /f "tokens=1,3" %%a in (properties) do set %%a=%%b
echo CREATE DATABASE IF NOT EXISTS library; > Doc\Sql\%name_lib%
echo USE library; >> Doc\Sql\%name_lib%
%mysql%\mysqldump -u root -p library >> Doc\Sql\%name_lib%
echo Backup sql created !
pause
@echo off
for /f "tokens=1,3" %%a in (properties) do set %%a=%%b

if not exist "%mysql%" (
echo Erreur, veuillez modifier le fichier properties afin de trouver le lien vers votre mysql de Wamp. Installer Wamp si cela n'est pas encore fait.
)
if exist "%mysql%" (
echo Taper juste Enter quand il demande le mot de passe si vous ne l'avez pas change
%mysql%\mysql -u root -p library < Doc\Sql\%name_lib%
echo Base de donnee importee.
)
pause
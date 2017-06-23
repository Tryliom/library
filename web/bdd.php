<?php
session_start();
$bdd;
ini_set('magic_quotes_gpc', 1);
try
{
	$bdd = new PDO('mysql:host=localhost:3307;dbname=library;charset=utf8', 'root', '');
}
catch (Exception $e)
{
	echo "erreur bdd";
}
require_once("header.html");

function isValid($bdd, $token) {
	$rep = $bdd->query('SELECT username FROM user WHERE token = "'.$token.'"');
	$b;
	$r = $rep->fetch();
	try {
		$b = !empty($r['username']);
	} catch (Exception $e) {
		$b = false;
	}
	return $b;
}

function setColor($s) {
	$c = substr($s, 2, 1);
	$s = substr($s, 3);
	switch ($c) {
		case 'a':
			return "<div style='color:#3f3;'>$s</div>";
		case 'b':
			return "<div style='color:#aaf;'>$s</div>";		
		case 'c':
			return "<div style='color:#f44;'>$s</div>";
		case 'd':
			return "<div style='color:#f3d;'>$s</div>";
		case 'e':
			return "<div style='color:#3dd;'>$s</div>";
		case 'f':
			return "<div style='color:#111;'>$s</div>";
	}
}

function isPage($s) {
	if (strpos($_SERVER['PHP_SELF'], $s) !== false) {
		return "style='color:#77f;'";
	}
	return "";
}

?>
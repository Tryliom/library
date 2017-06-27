<?php
session_start();
ini_set('magic_quotes_gpc', 1);
require_once("header.html");

function isValid($token) {
	$json_source = file_get_contents('http://localhost:6080/user/verif/member/'.$token);
	$jd= json_decode($json_source);
	try {
		$b = !empty($jd[0]->username);
	} catch (Exception $e) {
		$b = false;
	}
	return $b;
}

function isValidAdmin($token) {
	$json_source = file_get_contents('http://localhost:6080/user/verif/admin/'.$token);
	$jd= json_decode($json_source);
	try {
		$b = !empty($jd[0]->username);
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
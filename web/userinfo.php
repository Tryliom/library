<?php

$json_source = file_get_contents('http://localhost:6080/user/get/token/'.$_SESSION['token']);
$jd= json_decode($json_source);

$id = $jd[0]->id;
$username = $jd[0]->username;
$name = $jd[0]->name;
$lastname = $jd[0]->lastname;
$email = $jd[0]->email;
$tel = $jd[0]->tel;
$level_access = $jd[0]->level_access;
$rank = getTextByLvl($level_access);


function getTextByLvl($level) {
	switch ($level) {
	case 0:
		return "§bMembre";
	case 6:
		return "§cAdmin";
	case 7:
		return "§cSuper Admin";
	}
}
?>
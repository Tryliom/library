<?php

$json_source = file_get_contents($urlhost.'user/get/token/'.$_SESSION['token']);
$jd= json_decode($json_source);

$id = $jd[0]->id;
$username = $jd[0]->username;
$name = $jd[0]->name;
$lastname = $jd[0]->lastname;
$email = $jd[0]->email;
$tel = $jd[0]->tel;
$level_access = $jd[0]->level_access;
$rank = "";
switch ($level_access) {
	case 0:
		$rank="§bMembre";
		break;
	case 7:
		$rank="§cAdmin";
		break;
}
?>
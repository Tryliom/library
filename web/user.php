<head><link href="css\style.css" rel="stylesheet" media="all" type="text/css"></head>
<title>Espace utilisateur</title>

<?php
require_once("bdd.php");
require_once("lib.php");
require_once("need_acc.php");

if (isset($_REQUEST['logout'])) {
	$_SESSION['token'] = null;
	header("Location: index.php");
}

if (isset($_REQUEST['save'])) {
	$email = $_REQUEST['email'];
	$tel = $_REQUEST['tel'];
	$json_source = file_get_contents('http://localhost:6080/user/get/token/'.$_SESSION['token']);
	$jd= json_decode($json_source);
	$id = $jd[0]->id;
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/edit/$email/$tel/$id");
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p style='color:#33ff33'>Changements effectués avec succès !</p>";
	} else {
		echo "<p style='color:#ff3333'>Changements interrompus</p>";
	}
}



$json_source = file_get_contents('http://localhost:6080/user/get/token/'.$_SESSION['token']);
$jd= json_decode($json_source);

$id = $jd[0]->id;
$username = $jd[0]->username;
$name = $jd[0]->name;
$lastname = $jd[0]->lastname;
$email = $jd[0]->email;
$tel = $jd[0]->tel;
$levelAccess = $jd[0]->level_access;
$rank = "";
switch ($levelAccess) {
	case 0:
		$rank="§bMembre";
		break;
	case 7:
		$rank="§cAdmin";
		break;
}
echo "<div>

<form action='' method=post><table id='user' cellspacing='30'>
<h1>Mon compte</h1>
<tr>
	<td>Prénom : </td>
	<td><p id=user>".$name."</p></td>
</tr>
<tr>
	<td>Nom : </td>
	<td><p id=user>".$lastname."</p></td>
</tr>
<tr>
	<td>Pseudo : </td>
	<td><p id=user>".$username."</p></td>
</tr>
<tr>
	<td>Grade : </td>
	<td><p id=user>".setColor($rank)."</p></td>
</tr>
<tr>
	<td>Email : </td>
	<td id=user><input type='email' name='email' value='".$email."' /></td>
</tr>
<tr>
	<td>Numéro de téléphone (Suisse) : </td>
	<td id=user><input type='text' name='tel' value='".$tel."' /></td>
</tr>
<tr>
	<td><input id=button type=submit name=logout value='Se déconnecter'/></td>
	<td><input id=button type=submit name=save value='Sauvegarder'/></td>
</tr>
</table></form>


</div>";


require_once("footer.html");
?>
<head><link href="css\style.css" rel="stylesheet" media="all" type="text/css"></head>
<title>Espace utilisateur</title>

<?php
require_once("bdd.php");

if (isset($_REQUEST['save'])) {
	$email = $_REQUEST['email'];
	$tel = $_REQUEST['tel'];
	$pass = isset($_REQUEST['pass']);
	$s = "";
	$json_source = file_get_contents($urlhost.'user/get/token/'.$_SESSION['token']);
	$jd= json_decode($json_source);
	$id = $jd[0]->id;
	$ch = curl_init();
	$p = $pass && !empty($_REQUEST['pass']);
	if ($p) {
		$pass = sha1($_REQUEST['pass']);
		curl_setopt($ch, CURLOPT_URL,$urlhost."user/change/$pass/$id");
	} else {
		curl_setopt($ch, CURLOPT_URL,$urlhost."user/edit/$email/$tel/$id");
	}
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p style='color:#33ff33'>Changements effectués avec succès ! (".($p ? "Mot de passe, e" : "E")."mail et tél changés)</p>";
	} else {
		echo "<p style='color:#ff3333'>Changements interrompus, raison: $s</p>";
	}
	
}



$json_source = file_get_contents($urlhost.'user/get/token/'.$_SESSION['token']);
$jd= json_decode($json_source);

$id = $jd[0]->id;
$username = $jd[0]->username;
$name = $jd[0]->name;
$lastname = $jd[0]->lastname;
$email = $jd[0]->email;
$tel = $jd[0]->tel;
$levelAccess = $jd[0]->level_access;
$rank = Utils::getLvl($levelAccess, true);
$b =  "style='border-bottom:1px solid rgba(50, 50, 50, 0.2); border-left:1px solid rgba(50, 50, 50, 0.2);'";
echo "<div>

<form action='' method=post><table id='user' cellspacing='10'>
<h1>Mon compte</h1>
<tr>
	<td $b>Pseudo : </td>
	<td><p id=user>".$username."</p></td>
</tr>
<tr>
	<td $b>Prénom : </td>
	<td><p id=user>".$name."</p></td>
</tr>
<tr>
	<td $b>Nom : </td>
	<td><p id=user>".$lastname."</p></td>
</tr>
<tr>
	<td $b>Grade : </td>
	<td><p id=user>".Utils::setColor($rank)."</p></td>
</tr>
<tr>
	<td $b>Email : </td>
	<td id=user><input id='textmin' type='email' name='email' value='".$email."' /></td>
</tr>
<tr>
	<td $b>Numéro de téléphone (Suisse) : </td>
	<td id=user><input id='textmin' type='text' name='tel' value='".$tel."' /></td>
</tr>
<tr>
	<td $b>Nouveau mot de passe : </td>
	<td id=user><input id='textmin' type='text' name='pass' placeholder='Remplir à choix' /></td>
</tr>
<tr>
	<td></td>
	<td><input id='save' title='Sauvegarder' type=submit name=save value=''/></td>
</tr>
</table></form>


</div>";


require_once("footer.html");
?>
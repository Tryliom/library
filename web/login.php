<head><link href="css\style.css" rel="stylesheet" media="all" type="text/css"></head>
<title>Register / Login</title>

<?php
require_once("bdd.php");

if (isset($_SESSION['token']) && Utils::isValid($_SESSION['token']))
	header("Location: user.php");

// Vérification création ou connexion
$w = isset($_REQUEST['login']) ? 1 : 2;



if ($w === 1) {
	if (empty($_REQUEST['username']) || empty($_REQUEST['pass'])) {
		echo "<p style='color:#ff2222'>Erreur lors de la connexion au compte: Champ(s) vide(s)</p>";
		$w = 4;
	} 
	if ($w === 1) {
		// Généré une new token et le garder co
		$token = generateToken(32);
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/member/update/".$_REQUEST['username']."/".sha1($_REQUEST['pass'])."/$token");
		curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		$s = curl_exec ($ch);
		curl_close ($ch);
		if ($s==="true") {
			$_SESSION['token'] = $token;				
			header("Location: user.php");			
		} else {
			echo "<p style='color:#ff2222'>Erreur lors de la connexion au compte: $s</p>";
		}				
	}

}
echo "<table cellspacing='30'><tr><td>";

// Login
echo "<div>

<form action='' method=post><table cellspacing='10'>
<h1>Se connecter</h1>
<tr>
	<td>Pseudo : </td>
	<td><input type=text name=username value='".($w===4 ? getPost("username") : "")."'/></td>
</tr>
<tr>
	<td>Mot de passe : </td>
	<td><input type=password name=pass value='".($w===4 ? getPost("pass") : "")."'/></td>
</tr>
<tr>
	<td></td>
	<td><input id=button type=submit name=login value='Connexion'/></td>

</tr>
</table></form>


</div>";

echo "</td></tr></table>";

require_once("footer.html");


function getPost($str) {
	return isset($_REQUEST[$str]) ? $_REQUEST[$str] : "";
}

function generateToken($length) {
	return bin2hex(random_bytes($length));
}

?>




















<head><link href="css\style.css" rel="stylesheet" media="all" type="text/css"></head>
<title>Register / Login</title>

<?php
require_once("bdd.php");
require_once("lib.php");

if (!$valid)
	return;

// Vérification création ou connexion
$w = isset($_REQUEST['reg']) ? 4 : 0;

if ($w === 0) {
	$w = isset($_REQUEST['login']) ? 1 : 2;
}


if ($w === 4) {
	if (empty($_REQUEST['username']) || empty($_REQUEST['name']) || empty($_REQUEST['lastname']) || empty($_REQUEST['pass']) || empty($_REQUEST['pass2']) || empty($_REQUEST['email']) || empty($_REQUEST['tel'])) {
		echo "<p style='color:#ff2222'>Erreur lors de la création du compte: Champ(s) vide(s)</p>";
		$w = 2;
	} 
	$tel = str_replace(" ", "", $_REQUEST['tel']);
	$tel = str_replace("+41", "0", $tel);
	if (preg_match("(\d{10})", $tel)===0) {
		echo "<p style='color:#ff2222'>Erreur lors de la création du compte: Numéro de téléphone invalide ou non Suisse</p>";
		$w = 2;
	} 
	if (!filter_var($_REQUEST['email'], FILTER_VALIDATE_EMAIL)) {
		echo "<p style='color:#ff2222'>Erreur lors de la création du compte: Email invalide</p>";
		$w = 2;
	}
	if ($_REQUEST['pass'] !== $_REQUEST['pass2']) {
		echo "<p style='color:#ff2222'>Erreur lors de la création du compte: Validation de mot de passe erronée</p>";
		$w = 2;
	}
	if ($w === 4) {
		$reason = "";
		$name = $_REQUEST['name'];
		$lastname = $_REQUEST['lastname'];
		$username = $_REQUEST['username'];
		$email = $_REQUEST['email'];
		$tel = $_REQUEST['tel'];
		$lib_id = $_SESSION['lib'];
		$password = sha1($_REQUEST['pass']);
		$token = $token = generateToken(32);
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/add");
		curl_setopt($ch, CURLOPT_POST, 1);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // N'affiche pas le résultat dans la page
		curl_setopt($ch, CURLOPT_POSTFIELDS,
					"username=$username&name=$name&lastname=$lastname&password=$password&email=$email&tel=$tel&token=$token&library_id=$lib_id");
		$s = curl_exec ($ch);
		curl_close ($ch);
		$reason = $s;
		if ($s==="true") {
			echo "<p style='color:#33ff33'>Compte créé avec succès !<br> Veuillez vous connectez ! $s</p>";
		} else {
			echo "<p style='color:#ff3333'>Erreur avec la création du compte $reason</p>";
		}
	}
} else if ($w === 1) {
	if (empty($_REQUEST['username']) || empty($_REQUEST['pass'])) {
		echo "<p style='color:#ff2222'>Erreur lors de la connexion au compte: Champ(s) vide(s)</p>";
		$w = 4;
	} 
	if ($w === 1) {
		$rep = $bdd->query('SELECT username FROM user WHERE username = "'.$_REQUEST['username'].'" AND password = "'.sha1($_REQUEST['pass']).'"');
		$r = $rep->fetch();
		if ($r['username'] == $_REQUEST['username']) {
			// Généré une new token et le garder co
			$token = generateToken(32);
			$ch = curl_init();
			curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/admin/update/".$_REQUEST['username']."/".sha1($_REQUEST['pass'])."/$token");
			curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
			$s = curl_exec ($ch);
			curl_close ($ch);
			if (!$s) {
				echo "<p style='color:#ff2222'>Erreur lors de la connexion au compte: $s</p>";
			} else {
				$_SESSION['token_admin'] = $token;				
				header("Location: admin_panel.php");
			}
			$bdd->query('UPDATE user SET token = "'.$token.'" WHERE username = "'.$_REQUEST['username'].'" AND password = "'.sha1($_REQUEST['pass']).'"');
			$_SESSION['token'] = $token;
			header("Location: index.php");
		} else {
			echo "<p style='color:#ff2222'>Erreur lors de la connexion au compte: Pseudo ou mot de passe invalide</p>";
		}
	}

}
echo "<table cellspacing='30'><tr><td>";

// Register
echo "<div>

<form action='' method=post><table cellspacing='10'>
<h1>Créer un compte</h1>
<tr>
	<td>Pseudo : </td>
	<td><input type=text name=username value='".getPost("username")."'/></td>
</tr>
<tr>
	<td>Prénom : </td>
	<td><input type=text name=name value='".getPost("name")."'/></td>
</tr>
<tr>
	<td>Nom : </td>
	<td><input type=text name=lastname value='".getPost("lastname")."'/></td>
</tr>
<tr>
	<td>Mot de passe : </td>
	<td><input type=password name=pass value='".getPost("pass")."'/></td>
</tr>
<tr>
	<td>Confirmer le mot de passe : </td>
	<td><input type=password name=pass2 value='".getPost("pass2")."'/></td>
</tr>
<tr>
	<td>Email : </td>
	<td><input type=email name=email value='".getPost("email")."'/></td>
</tr>
<tr>
	<td>Numéro de téléphone (Suisse) : </td>
	<td><input type=text name=tel value='".getPost("tel")."'/></td>
</tr>
<tr>
	<td></td>
	<td><input id=button type='submit' name='reg' value='Créer le compte'/></td>

</tr>
</table></form>

</div>";

echo "</td><td>";

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




















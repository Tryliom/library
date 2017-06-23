<head><link href="css\style.css" rel="stylesheet" media="all" type="text/css"></head>
<title>Admin Panel</title>
<?php
require_once("bdd.php");


if (isset($_SESSION['token_admin']) && isset($_REQUEST['logout'])) {
	$_SESSION['token_admin'] = null;
}


if (isset($_SESSION['token_admin'])) {
	// Vérifier si la token est encore valide
	
	
	echo "<p style='color:#22ff22'>Connecté au panel Administrateur</p>";
	echo "<h1>Prochainement</h1>
		<p>- Ajout de nouvelles librairies</p>
		<p>- Effacement de librairies</p>
		<p>- Gérer les utilisateurs (Créer/enlever des Admin)</p>
	";
	echo "<div>

	<form action='' method=post><table cellspacing='10'>
	<h1>Se déconnecter</h1>
	<tr>
		<td></td>
		<td><input id=button type=submit name=logout value='Déconnexion'/></td>

	</tr>
	</table></form>


	</div>";
	
	
	
	
	
	
	
	
} else {
	if (isset($_REQUEST['login'])) {
		$w = 1;
		if (empty($_REQUEST['username']) || empty($_REQUEST['pass'])) {
			echo "<p style='color:#ff2222'>Erreur lors de la connexion au compte: Champ(s) vide(s)</p>";
			$w = 4;
		} 
		if ($w === 1) {
			// Généré une new token et le garder co
			$token = generateToken(32);
			$ch = curl_init();
			curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/admin/update/".$_REQUEST['username']."/".sha1($_REQUEST['pass'])."/$token");
			curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
			$s = curl_exec ($ch);
			curl_close ($ch);
			if ($s=="true") {
				$_SESSION['token_admin'] = $token;				
				//header("Location: admin_panel.php");
			} else {
				echo "<p style='color:#ff2222'>Erreur lors de la connexion au compte: $s</p>";
			}
		} else
			echo "<p style='color:#ff2222'>Erreur lors de la connexion au compte: Pseudo ou mot de passe vide</p>";
	}

	echo "<div>

	<form action='' method=post><table cellspacing='10'>
	<h1>Se connecter</h1>
	<tr>
		<td>Pseudo : </td>
		<td><input type=text name=username value='".getPost("username")."'/></td>
	</tr>
	<tr>
		<td>Mot de passe : </td>
		<td><input type=password name=pass value='".getPost("pass")."'/></td>
	</tr>
	<tr>
		<td></td>
		<td><input id=button type=submit name=login value='Connexion'/></td>

	</tr>
	</table></form>


	</div>";
}


require_once("footer.html");


function generateToken($length) {
	return bin2hex(random_bytes($length));
}

function getPost($str) {
	return isset($_REQUEST[$str]) ? $_REQUEST[$str] : "";
}

?>
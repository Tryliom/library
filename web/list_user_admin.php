<?php
if (strpos($_SERVER['PHP_SELF'], 'list_book_admin.php') !== false) {
    header("Location: index.php");
}

if (isset($_REQUEST['add'])) {
	$pseudo = $_REQUEST['pseudo'];
	$name = $_REQUEST['name'];
	$lastname = $_REQUEST['lastname'];
	$email = $_REQUEST['email'];
	$tel = $_REQUEST['tel'];
	$level = $_REQUEST['level'];
	$pass = sha1($_REQUEST['password']);
	$lid = $_SESSION['lib'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/add");
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // N'affiche pas le résultat dans la page
	curl_setopt($ch, CURLOPT_POSTFIELDS, "username=$pseudo&name=$name&lastname=$lastname&email=$email&tel=$tel&level_access=$level&library_id=$lid&password=$pass&token=42");
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Livre ajouté !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur $s</p>";
	}
}

if (isset($_REQUEST['update'])) {
	$pseudo = $_REQUEST['pseudo'];
	$name = $_REQUEST['name'];
	$lastname = $_REQUEST['lastname'];
	$email = $_REQUEST['email'];
	$tel = $_REQUEST['tel'];
	$level = $_REQUEST['level'];
	$uid = $_REQUEST['id'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/edit/admin");
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // N'affiche pas le résultat dans la page
	curl_setopt($ch, CURLOPT_POSTFIELDS, "username=$pseudo&name=$name&lastname=$lastname&email=$email&tel=$tel&level_access=$level&id=$uid");
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Utilisateur modifié !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur $s</p>";
	}
}

if (isset($_REQUEST['delete'])) {
	$id = $_REQUEST['id'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/delete/$id");
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Utilisateur supprimé !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur $s</p>";
	}
}

$json_source = file_get_contents('http://localhost:6080/user/get/');
$jd= json_decode($json_source);
$h = "<table id='list' cellspacing='10'><th>Pseudo</th><th>Prénom</th><th>Nom</th><th>Mot de passe</th><th>Email</th><th>Téléphone</th><th>Niveau d'accès</th><th>Options</th><tr>";
$m = "";
for ($i=0;$i<sizeof($jd);$i++) {
	$uid = $jd[$i]->id;
	$pseudo = $jd[$i]->username;
	$name = $jd[$i]->name;
	$lastname = $jd[$i]->lastname;
	$pass = $jd[$i]->password;
	$email = $jd[$i]->email;
	$tel = $jd[$i]->tel;
	$level = $jd[$i]->level_access;
	$lib = $jd[$i]->library_id;

	if ($lib===$_SESSION['lib'])
		$m .= "
		<form method=post>
			<input type='hidden' value='$uid' name='id'/>
			<input type='hidden' value='5' name='$choice'/>
			<td ".setWidth($pseudo).">
				<input id='textmin' type='text' name='pseudo' value='$pseudo' />
			</td>
			<td ".setWidth($name).">
				<input id='textmin' type='text' name='name' value='$name' />
			</td>
			<td ".setWidth($lastname).">
				<input id='textmin' type='text' name='lastname' value='$lastname' />
			</td>
			<td ".setWidth("HIDDEN").">
				<input id='textmin' type='text' name='pass' value='HIDDEN' disabled />
			</td>
			<td ".setWidth($email).">
				<input id='textmin' type='text' name='email' value='$email' />
			</td>
			<td ".setWidth($tel).">
				<input id='textmin' type='text' name='tel' value='$tel' />
			</td>
			<td ".setWidth($level)."))>
				<input id='textmin' type='text' name='level' value='$level' />
				".(setColor(getTextByLvl($level)))."
			</td>
			<td>
				<input id='button' style='width:100%;' type='submit' value='Sauvegarder' name='update' />
				<input id='button' style='width:100%;' type='submit' value='Supprimer' name='delete' />
			</td>
		</form>
	</tr>";
}


$b = "
<tr>
	<form method=post>
		<input type='hidden' value='5' name='$choice'/>
		<td>
			<input id='textmin' type='text' name='pseudo' />
		</td>
		<td>
			<input id='textmin' type='text' name='name' />
		</td>
		<td>
			<input id='textmin' type='text' name='lastname' />
		</td>
		<td>
			<input id='textmin' type='text' name='password' />
		</td>
		<td>
			<input id='textmin' type='text' name='email' />
		</td>
		<td>
			<input id='textmin' type='text' name='tel' />
		</td>
		<td>
			<input id='textmin' type='text' name='level' />
		</td>
		<td>
			<input style='width:100%;' id='button' type='submit' value='Ajouter un nouvel utilisateur' name='add' />
		</td>
	</form>
</tr>";
if ($m==="") 
	echo "<p id='text'>Aucun livres disponibles</p>";
else
	echo "$h $m $b";



function setWidth($s) {
	return "style='width:".(strlen($s)+4)."%;'";
}
?>


























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
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/add");
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // N'affiche pas le résultat dans la page
	curl_setopt($ch, CURLOPT_POSTFIELDS, "username=$pseudo&name=$name&lastname=$lastname&email=$email&tel=$tel&level_access=$level&password=$pass&token=42");
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Utilisateur ajouté !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur, raison: $s</p>";
	}
}

if (isset($_REQUEST['update'])) {
	$pseudo = $_REQUEST['pseudo'];
	$name = $_REQUEST['name'];
	$lastname = $_REQUEST['lastname'];
	// Pas besoin d'hasher le pass
	$pass = $_REQUEST['pass'];
	$email = $_REQUEST['email'];
	$tel = $_REQUEST['tel'];
	$level = $_REQUEST['level'];
	$uid = $_REQUEST['id'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/edit/admin");
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // N'affiche pas le résultat dans la page
	curl_setopt($ch, CURLOPT_POSTFIELDS, "username=$pseudo&name=$name&lastname=$lastname&password=$pass&email=$email&tel=$tel&level_access=$level&id=$uid");
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Utilisateur modifié !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur, raison: $s</p>";
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
$h = "<table id='list' cellspacing='0'><th>Pseudo</th><th>Prénom</th><th>Nom</th><th>Mot de passe</th><th>Email</th><th>Téléphone</th><th>Niveau d'accès</th><th>Options</th>";
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
	$dis = ($jd[$i]->token === $_SESSION['token'] ? "disabled" : $level>=7 ? "disabled" : "");

	$m .= "<tr>
	<form method=post>
		<input type='hidden' value='$uid' name='id'/>
		<input type='hidden' value='5' name='$choice'/>
		<td>
			<input id='textmin' type='text' name='pseudo' value='$pseudo'/>
		</td>
		<td>
			<input id='textmin' type='text' name='name' value='$name'/>
		</td>
		<td>
			<input id='textmin' type='text' name='lastname' value='$lastname'/>
		</td>
		<td>
			<input id='textmin' type='password' name='pass' value=''/>
		</td>
		<td>
			<input id='textmin' type='text' name='email' value='$email'/>
		</td>
		<td>
			<input id='textmin' type='text' name='tel' value='$tel'/>
		</td>
		<td>
			<select style='width:100%;' id='textmin' name='level'>
			";
			$list = Utils::getListRank();
			for ($j=0;$j<sizeof($list);$j++) {
				$d = "";
				if (Utils::getLvl($list[$j], false)==$level)
					$d = "selected=\"selected\"";
				$m .= "<option value='".Utils::getLvl($list[$j], false)."' $d>".$list[$j]."</option>";
			}
			$m .= "
			</select>
		</td>
		<td>
			".($dis!="disabled" ? (Utils::getButtonImage("save", "Sauvegarder", "update").Utils::getButtonImage("delete", "Supprimer", "delete")) : "")."
		</td>
	</form>
</tr>";
}


$b = "
<tr>
	<form method=post>
		<input type='hidden' value='5' name='$choice'/>
		<td>
			<input id='textmin' type='text' name='pseudo' placeholder='Pseudo'/>
		</td>
		<td>
			<input id='textmin' type='text' name='name' placeholder='Prénom'/>
		</td>
		<td>
			<input id='textmin' type='text' name='lastname' placeholder='Nom de famille'/>
		</td>
		<td>
			<input id='textmin' type='password' name='password' placeholder='Mot de passe'/>
		</td>
		<td>
			<input id='textmin' type='text' name='email' placeholder='Email'/>
		</td>
		<td>
			<input id='textmin' type='text' name='tel' placeholder='Téléphone'/>
		</td>
		<td>
			<select style='width:100%;' id='textmin' name='level'>
			";
			$list = Utils::getListRank();
			for ($j=0;$j<sizeof($list);$j++) {
				$d = "";
				if (Utils::getLvl($list[$j], false)==0)
					$d = "selected=\"selected\"";
				$b .= "<option value='".Utils::getLvl($list[$j], false)."' $d>".$list[$j]."</option>";
			}
			$b .= "
			</select>
		</td>
		<td>
			".Utils::getButtonImage("add", "Ajouter un nouvel utilisateur", "add")."
		</td>
	</form>
</tr>";
echo "$h $m $b";
?>


























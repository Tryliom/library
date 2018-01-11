<?php
if (strpos($_SERVER['PHP_SELF'], 'list_lib.php') !== false) {
    header("Location: index.php");
}

$list_idlib = "";

if (isset($_REQUEST['add']) && isset($_REQUEST['user'])) {
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
		echo "<p id='text' style='color:#33ff33'>Utilisateur ajouté !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur $s</p>";
	}
}

if (isset($_REQUEST['update']) && isset($_REQUEST['user'])) {
	$pseudo = $_REQUEST['pseudo'];
	$name = $_REQUEST['name'];
	$lastname = $_REQUEST['lastname'];
	$pass = $_REQUEST['pass'];
	$email = $_REQUEST['email'];
	$tel = $_REQUEST['tel'];
	$level = $_REQUEST['level'];
	$uid = $_REQUEST['id'];
	$lib = $_REQUEST['lib']; //  Notice: Undefined index: lib in C:\wamp64\www\Test\remoteGIT\web\list_lib.php on line 41
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/edit/superadmin");
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // N'affiche pas le résultat dans la page
	curl_setopt($ch, CURLOPT_POSTFIELDS, "username=$pseudo&name=$name&lastname=$lastname&password=$pass&email=$email&tel=$tel&level_access=$level&id=$uid&library_id=$lib");
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Utilisateur modifié !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur, raison: $s</p>";
	}
}

if (isset($_REQUEST['delete']) && isset($_REQUEST['user'])) {
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

if (isset($_REQUEST['lib_list']) && isset($_REQUEST['update'])) {
	$name = $_REQUEST['name'];
	$adress = $_REQUEST['adress'];
	$id = $_REQUEST['id'];
	$ch = curl_init();
	
	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/library/edit");
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // N'affiche pas le résultat dans la page
	curl_setopt($ch, CURLOPT_POSTFIELDS, "name=$name&adress=$adress&library_id=$id");
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Données mises à jour !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur: $s</p>";
	}
}

if (isset($_REQUEST['lib_list']) && isset($_REQUEST['delete'])) {
	$id = $_REQUEST['id'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/library/delete/$id");
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Librairie supprimée !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur</p>";
	}
}

if (isset($_REQUEST['lib_add']) && isset($_REQUEST['add'])) {
	$name = $_REQUEST['name'];
	$adress = $_REQUEST['adress'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/library/add");
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // N'affiche pas le résultat dans la page
	curl_setopt($ch, CURLOPT_POSTFIELDS, "name=$name&adress=$adress");
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Librairie ajoutée !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur $s</p>";
	}
}

if (isset($_REQUEST['book_add']) && isset($_REQUEST['add'])) {
	$title = $_REQUEST['title'];
	$author = $_REQUEST['author'];
	$date = $_REQUEST['date'];
	$desc = $_REQUEST['desc'];
	$edition = $_REQUEST['edition'];
	$editeur = $_REQUEST['editor'];
	$lid = $_REQUEST['lib'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/book/add");
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // N'affiche pas le résultat dans la page
	curl_setopt($ch, CURLOPT_POSTFIELDS, "title=$title&author=$author&date=$date&description=$desc&edition=$edition&editeur=$editeur&library_id=$lid");
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Livre ajouté !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur $s</p>";
	}
}

if (isset($_REQUEST['book_list']) && isset($_REQUEST['update'])) {
	$title = $_REQUEST['title'];
	$author = $_REQUEST['author'];
	$date = $_REQUEST['date'];
	$desc = $_REQUEST['desc'];
	$edition = $_REQUEST['edition'];
	$editeur = $_REQUEST['editor'];
	$lid = $_REQUEST['lib'];
	$id = $_REQUEST['id'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/book/edit");
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // N'affiche pas le résultat dans la page
	curl_setopt($ch, CURLOPT_POSTFIELDS, "title=$title&author=$author&date=$date&description=$desc&edition=$edition&editeur=$editeur&library_id=$lid&id=$id");
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Livre modifié !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur $s</p>";
	}
}

$json_source = file_get_contents('http://localhost:6080/library/get/');
$jd= json_decode($json_source);
	$h = "<h1>Gestion des bibliothèques</h1>
	<table id='list' cellspacing='0'>
		<tr>
			<th>Nom</th>
			<th>Adresse</th>
			<th>Options</th>
		</tr>";
	$m = "";
	for ($i=0;$i<sizeof($jd);$i++) {
		$lid = $jd[$i]->id;
		$adress = $jd[$i]->adress;
		$name = $jd[$i]->name;
		$list_idlib .= $jd[$i]->name."§";
		$m .= "
			<tr>
				<form method='post'>
					<input type='hidden' name='lib_list' />
					<input type='hidden' value='$lid' name='id' />
					<td>
						<input id='textmin' type='text' name='name' value=\"$name\" />
					</td>
					<td>
						<input id='textmin' type='text' name='adress' value=\"$adress\" />
					</td>
					<td> 
						".Utils::getButtonImage("save", "Sauvegarder", "update").Utils::getButtonImage("delete", "Supprimer", "delete")."
					</td>
				</form>
			</tr>";
	}
	$b = "
		<form method=post>	
			<input type='hidden' name='lib_add' />
			<tr>
				<td>
					<input id='textmin' type='text' name='name' />
				</td>
				<td>
					<input id='textmin' type='text' name='adress' />
				</td>
				<td id='textdisp'>
					".Utils::getButtonImage("add", "Ajouter une nouvelle bibliothèque", "add")."
				</td>	
			</tr>
		</form>
	</table>";
		echo "$h $m $b";
	
	$libadmin = -1;
	$choice = "adminlol";
	echo "<h1>Gestion des livres</h1>";
	require_once('page.php');	
	$json_source = file_get_contents('http://localhost:6080/book/get/'.$page.'/-1');
	$jd= json_decode($json_source);
	$h = "<table id='list' cellspacing='0'><th>Titre</th><th>Auteur</th><th>Date</th><th>Édition</th><th>Editeur</th><th>Description</th><th>Bibliothèque</th><th>Options</th>";
	$m = "";
	for ($i=0;$i<sizeof($jd);$i++) {
		$bid = $jd[$i]->id;
		$title = $jd[$i]->title;
		$author = $jd[$i]->author;
		$date = $jd[$i]->date;
		$desc = $jd[$i]->description;
		$edition = $jd[$i]->edition;
		$editor = $jd[$i]->editor;
		$user_id = $jd[$i]->user_id;
		$lib = $jd[$i]->library_id;
		$m .= "<tr>
		<form method=post>
		<input type='hidden' name='book_list' />
		<input type='hidden' value='$bid' name='id'/>
		<td><input id='textmin' type='text' name='title' value=\"$title\" /></td>
		<td><input id='textmin' type='text' name='author' value=\"$author\" /></td>
		<td><input id='textmin' type='text' name='date' value=\"$date\" /></td>
		<td><input id='textmin' type='text' name='edition' value=\"$edition\" /></td>
		<td><input id='textmin' type='text' name='editor' value=\"$editor\" /></td>
		<td><input id='textmin' type='text' name='desc' value=\"$desc\" /></td>
		<td>
		<select style='width:100%;' id='textmin' name='lib'>
		";
		$list_idlib2 = preg_split("[§]", $list_idlib);
		for ($j=0;$j<sizeof($list_idlib2);$j++) {
			$s = "";
			if (Utils::getInfoLibrary($lib, "name")==$list_idlib2[$j])
				$s = "selected";
			if ($list_idlib2[$j]!=="")
				$m .= "<option $s value=\"".Utils::getIdLibrary($list_idlib2[$j])."\">".$list_idlib2[$j]."</option>";
		}
		$m .= "
		</select>
		</td>
		<td id='textdisp'> 
			".Utils::getButtonImage("save", "Sauvegarder", "update").Utils::getButtonImage("delete", "Supprimer", "delete")."
		</td>
		</form></tr>";
	}
	$b = "<tr><form name='book_add' method=post>
	<input type='hidden' name='book_add' />
	<td><input id='textmin' type='text' name='title' /></td>
	<td><input id='textmin' type='text' name='author' /></td>
	<td><input id='textmin' type='text' name='date' /></td>
	<td><input id='textmin' type='text' name='edition' /></td>
	<td><input id='textmin' type='text' name='editor' /></td>
	<td><input id='textmin' type='text' name='desc' /></td>
	<td>
	<select style='width:100%;' id='textmin' name='lib'>
	";
	$list_idlib2 = preg_split("[§]", $list_idlib);
	for ($j=0;$j<sizeof($list_idlib2);$j++) {
		if ($list_idlib2[$j]!=="")
			$b .= "<option value='".Utils::getIdLibrary($list_idlib2[$j])."'>".$list_idlib2[$j]."</option>";
	}
	$b .= "
	</select>
	</td>
	<td id='textdisp'>
		".Utils::getButtonImage("add", "Ajouter un nouveau livre", "add")."
	</td></tr></form></table>";
	echo "$h $m $b";
	
	
	

	$json_source = file_get_contents('http://localhost:6080/user/get/');
	$jd= json_decode($json_source);
	$h = "<h1>Gestion des utilisateurs</h1>
	<table id='list' cellspacing='0'><th>Pseudo</th><th>Prénom</th><th>Nom</th><th>Mot de passe</th><th>Email</th><th>Téléphone</th><th>Niveau d'accès</th><th>Options</th>";
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
		$dis = ($jd[$i]->token === $_SESSION['token'] ? "disabled" : "");
		$m .= "<tr>
			<form method=post>
				<input type='hidden' value='$uid' name='id'/>
				<input type='hidden' name='user'/>
				<td>
					<input id='textmin' type='text' name='pseudo' value='$pseudo' />
				</td>
				<td>
					<input id='textmin' type='text' name='name' value='$name' />
				</td>
				<td>
					<input id='textmin' type='text' name='lastname' value='$lastname' />
				</td>
				<td>
					<input id='textmin' type='password' name='pass' value=''/>
				</td>
				<td>
					<input id='textmin' type='text' name='email' value='$email' />
				</td>
				<td>
					<input id='textmin' type='text' name='tel' value='$tel' />
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
					".($dis!=="disabled" ? Utils::getButtonImage("save", "Sauvegarder", "update").Utils::getButtonImage("delete", "Supprimer", "delete") : (Utils::getButtonImage("save", "Sauvegarder", "update")))."
				</td>
			</form>
		</tr>";
	}


	$b = "
	<tr>
		<form method=post>
			<input type='hidden' name='user'/>
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
				<input id='textmin' type='password' name='password' />
			</td>
			<td>
				<input id='textmin' type='text' name='email' />
			</td>
			<td>
				<input id='textmin' type='text' name='tel' />
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
			<td id='textdisp'>
				".Utils::getButtonImage("add", "Ajouter un nouvel utilisateur", "add")."
			</td>
		</form>
	</tr>";
	if ($m==="") 
		echo "<p id='text'>Aucun utilisateurs disponibles</p>";
	else
		echo "$h $m $b";

	
	
	function setWidth($s) {
		return "style='width:".(strlen($s)+4)."px;'";
	}
?>
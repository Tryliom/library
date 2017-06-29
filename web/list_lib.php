<?php
if (strpos($_SERVER['PHP_SELF'], 'list_lib.php') !== false) {
    header("Location: index.php");
}

if (isset($_REQUEST['lib_list']) && isset($_REQUEST['update'])) {
	$name = $_REQUEST['name'];
	$adress = $_REQUEST['adress'];
	$id = $_REQUEST['id'];
	$ch = curl_init();
	
	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/library/edit/$name/$adress/$id");
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p style='color:#33ff33'>Données mises à jour !</p>";
	} else {
		echo "<p style='color:#ff3333'>Erreur: $s ; ".("http://localhost:6080/library/edit/$name/$adress/$id")."</p>";
	}
}

// WORK
if (isset($_REQUEST['lib_list']) && isset($_REQUEST['delete'])) {
	$id = $_REQUEST['id'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/library/delete/$id");
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p style='color:#33ff33'>Librairie supprimée !</p>";
	} else {
		echo "<p style='color:#ff3333'>Erreur</p>";
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
		echo "<p style='color:#33ff33'>Librairie supprimée !</p>";
	} else {
		echo "<p style='color:#ff3333'>Erreur $s</p>";
	}
}

$json_source = file_get_contents('http://localhost:6080/library/get/');
$jd= json_decode($json_source);
	$h = "<h1>Gestion des librairies</h1>
	<table id='list' cellspacing='10'>
		<tr>
			<th>ID</th>
			<th>Nom</th>
			<th>Adresse</th>
			<th>Options</th>
		</tr>";
	$m = "";
	for ($i=0;$i<sizeof($jd);$i++) {
		$lid = $jd[$i]->id;
		$adress = $jd[$i]->adress;
		$name = $jd[$i]->name;
		$m .= "
			<tr>
				<form method='post'>
					<input type='hidden' name='lib_list' />
					<td ".setWidth($lid).">
						<input id='text' type='text' value='$lid' name='id' />
					</td>
					<td ".setWidth($name).">
						<input id='text' type='text' name='name' value='$name' />
					</td>
					<td ".setWidth($adress).">
						<input id='text' type='text' name='adress' value='$adress' />
					</td>
					<td> 
						<input style='width:100%;' id='button' type='submit' value='Sauvegarder' name='update' />
						<input style='width:100%;' id='button' type='submit' value='Supprimer' name='delete' /> 
					</td>
				</form>
			</tr>";
	}
	$b = "
		<form method=post>	
			<input type='hidden' name='lib_add' />
			<tr>
				<td>
					<input id='text' type='text' name='id' disabled/>
				</td>
				<td>
					<input id='text' type='text' name='name' />
				</td>
				<td>
					<input id='text' type='text' name='adress' />
				</td>
				<td>
					<input style='width:100%;' id='button' type='submit' value='Ajouter une nouvelle librairie' name='add' />
				</td>	
			</tr>
		</form>
	</table>";
		echo "$h $m $b";
	
	
	
	
	
	
	$json_source = file_get_contents('http://localhost:6080/book/get/');
	$jd= json_decode($json_source);
	$h = "<h1>Gestion des livres</h1><table id='list' cellspacing='10'><th>Titre</th><th>Auteur</th><th>Date</th><th>Numéro d'édition</th><th>Editeur</th><th>Description</th><th>Librarie ID</th><th>Options</th><tr>";
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
		$m .= "
		<form name='book_list' method=post>
		<input type='hidden' value='$bid' name='id'/>
		<td ".setWidth($title)."><input id='textmin' type='text' name='title' value='$title' /></td>
		<td ".setWidth($author)."><input id='textmin' type='text' name='author' value='$author' /></td>
		<td ".setWidth($date)."><input id='textmin' type='text' name='date' value='$date' /></td>
		<td ".setWidth($edition)."><input id='textmin' type='text' name='edition' value='$edition' /></td>
		<td ".setWidth($editor)."><input id='textmin' type='text' name='editor' value='$editor' /></td>
		<td ".setWidth($desc)."><input id='textmin' type='text' name='desc' value=\"$desc\" /></td>
		<td ".setWidth($lib)."><input id='textmin' type='text' name='lib' value='$lib' /></td>
		<td> <input style='width:100%;' id='button' type='submit' value='Sauvegarder' name='update' /> <input style='width:100%;' id='button' type='submit' value='Supprimer' name='delete' /> </td>
		</form></tr>";
	}
	$b = "<form name='book_add' method=post><tr>
	<td><input id='textmin' type='text' name='title' /></td>
	<td><input id='textmin' type='text' name='author' /></td>
	<td><input id='textmin' type='text' name='date' /></td>
	<td><input id='textmin' type='text' name='edition' /></td>
	<td><input id='textmin' type='text' name='editor' /></td>
	<td><input id='textmin' type='text' name='desc' /></td>
	<td><input id='textmin' type='text' name='lib' /></td>
	<td><input style='width:100%;' id='button' type='submit' value='Ajouter un nouveau livre' name='add' /></td></tr></form></table>";
		echo "$h $m $b";
	
	
	function setWidth($s) {
		return "style='width:".(strlen($s)+4)."%;'";
	}
?>


























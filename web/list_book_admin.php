<?php
if (strpos($_SERVER['PHP_SELF'], 'list_book_admin.php') !== false) {
    header("Location: index.php");
}

if (isset($_REQUEST['add'])) {
	$title = $_REQUEST['title'];
	$author = $_REQUEST['author'];
	$date = $_REQUEST['date'];
	$desc = $_REQUEST['desc'];
	$edition = $_REQUEST['edition'];
	$editeur = $_REQUEST['editor'];
	$lid = $_SESSION['lib'];
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

if (isset($_REQUEST['update'])) {
	$title = $_REQUEST['title'];
	$author = $_REQUEST['author'];
	$date = $_REQUEST['date'];
	$desc = $_REQUEST['desc'];
	$edition = $_REQUEST['edition'];
	$editeur = $_REQUEST['editor'];
	$lid = $_SESSION['lib'];
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

if (isset($_REQUEST['delete'])) {
	$id = $_REQUEST['id'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/book/delete/$id");
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Livre supprimé !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur $s</p>";
	}
}

require_once('page.php');
$json_source = file_get_contents('http://localhost:6080/book/get/'.$page.'/'.$_SESSION['lib']);
$jd= json_decode($json_source);
$h = "<table id='list' cellspacing='10'><th>Titre</th><th>Auteur</th><th>Date</th><th>Numéro d'édition</th><th>Editeur</th><th>Description</th><th>Emprunté ?</th><th>Option</th>";
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
			<input type='hidden' value='$bid' name='id'/>
			<input type='hidden' value='5' name='$choice'/>
			<input type='hidden' value='$page' name='page'/>
			<td ".setWidth($title)."><input id='textmin' type='text' name='title' value='$title' /></td>
			<td ".setWidth($author)."><input id='textmin' type='text' name='author' value='$author' /></td>
			<td ".setWidth($date)."><input id='textmin' type='text' name='date' value='$date' /></td>
			<td ".setWidth($edition)."><input id='textmin' type='text' name='edition' value='$edition' /></td>
			<td ".setWidth($editor)."><input id='textmin' type='text' name='editor' value='$editor' /></td>
			<td ".setWidth($desc)."><input id='textmin' type='text' name='desc' value=\"$desc\" /></td>
			<td id='textdisp'>
				".($user_id>0 ? "Oui" : "Non")."
			</td>
			<td>
				<input id='button' style='width:100%;' type='submit' value='Sauvegarder' name='update' />
				<input id='button' style='width:100%;' type='submit' value='Supprimer' name='delete' />
			</td>
		</form>
	</tr>";
}


$b = "<tr><form method=post>
	<input type='hidden' value='5' name='$choice'/>
	<td><input id='textmin' type='text' name='title' /></td>
	<td><input id='textmin' type='text' name='author' /></td>
	<td><input id='textmin' type='text' name='date' /></td>
	<td><input id='textmin' type='text' name='edition' /></td>
	<td><input id='textmin' type='text' name='editor' /></td>
	<td><input id='textmin' type='text' name='desc' /></td>
	<td id='textdisp'>
		---
	</td>
	<td><input style='width:100%;' id='button' type='submit' value='Ajouter un nouveau livre' name='add' /></td></tr></form>";
if ($m==="") 
	echo "<p id='text'>Aucun livres disponibles</p>";
else
	echo "$h $m $b";



function setWidth($s) {
	return "style='width:".(strlen($s)+4)."%;'";
}
?>


























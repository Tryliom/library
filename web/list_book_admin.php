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

	curl_setopt($ch, CURLOPT_URL,$urlhost."book/add");
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

	curl_setopt($ch, CURLOPT_URL,$urlhost."book/edit");
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

	curl_setopt($ch, CURLOPT_URL,$urlhost."book/delete/$id");
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
$json_source = file_get_contents($urlhost.'book/get/'.$page.'/'.$_SESSION['lib']);
$jd= json_decode($json_source);
$h = "<table id='list' cellspacing='0'><th>Titre</th><th>Auteur</th><th>Date</th><th>Numéro d'édition</th><th>Editeur</th><th>Description</th><th>Statut</th><th>Option</th>";
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
	$lib = $jd[$i]->library_id; //Utils::getImage("cancel", "Pris") : Utils::getImage("valid", "Libre"))
		$m .= "<tr>
		<form method=post>
			<input type='hidden' value='$bid' name='id'/>
			<input type='hidden' value='5' name='$choice'/>
			<input type='hidden' value='$page' name='page'/>
			<td><input id='textmin' type='text' name='title' value='$title' /></td>
			<td><input id='textmin' type='text' name='author' value='$author' /></td>
			<td><input id='textmin' type='text' name='date' value='$date' /></td>
			<td><input id='textmin' type='text' name='edition' value='$edition' /></td>
			<td><input id='textmin' type='text' name='editor' value='$editor' /></td>
			<td><input id='textmin' type='text' name='desc' value=\"$desc\" /></td>
			<td id='textdisp'>
				".($user_id>0 ? Utils::getImage("cancel", "Pris") : Utils::getImage("valid", "Libre"))."
			</td>
			<td id='textdisp'>
				".Utils::getButtonImage("save", "Sauvegarder", "update").Utils::getButtonImage("delete", "Supprimer", "delete")."
			</td>
		</form>
	</tr>";
}


$b = "<tr><form method=post>
	<input type='hidden' value='5' name='$choice'/>
	<td><input id='textmin' type='text' name='title' placeholder='Titre'/></td>
	<td><input id='textmin' type='text' name='author' placeholder='Auteur'/></td>
	<td><input id='textmin' type='text' name='date' placeholder='Année de parution'/></td>
	<td><input id='textmin' type='text' name='edition' placeholder=\"N° d'édition\"/></td>
	<td><input id='textmin' type='text' name='editor' placeholder='Editeur'/></td>
	<td><input id='textmin' type='text' name='desc' placeholder='Description'/></td>
	<td id='textdisp'>

	</td>
	<td id='textdisp'>
		".Utils::getButtonImage("add", "Ajouter un nouveau livre", "add")."
	</td></tr></form>";
	echo "$h $m $b";
?>
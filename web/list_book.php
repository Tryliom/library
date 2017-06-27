<?php
if (strpos($_SERVER['PHP_SELF'], 'list_book.php') !== false) {
    header("Location: index.php");
}

if (isset($_REQUEST['take'])) {
	$bid = $_REQUEST['id'];
	$uid = $id;
	$lib = $_SESSION['lib'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/take/$bid/$uid/$lib");
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p style='color:#33ff33'>Vous venez d'emprunter un nouveau livre !</p><p style='color:#33ff33'>N'oubliez pas de le rendre dans 2 jours !</p>";
	} else {
		echo "<p style='color:#ff3333'>Erreur</p>";
	}
}

if (isset($_REQUEST['return'])) {
	$bid = $_REQUEST['id'];
	$uid = $id;
	$lib = $_SESSION['lib'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/user/return/$bid/$uid/$lib");
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p style='color:#33ff33'>Vous avez rendu le livre !</p>";
	} else {
		echo "<p style='color:#ff3333'>Erreur $s</p>";
	}
}
$json_source = file_get_contents('http://localhost:6080/book/get/');
$jd= json_decode($json_source);
	$h = "<table id='list' cellspacing='10'><th>Titre</th><th>Auteur</th><th>Date</th><th>Numéro d'édition</th><th>Editeur</th><th>Description</th><th>Option</th><tr>";
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

		$dis = $user_id>0 ? "disabled" : "";
		$name = $user_id>0 ? "Pris" : "Emprunter";
		$n = "take";
		if ($user_id>0 && $user_id==$id) {
			$name = "Rendre";
			$dis = "";
			$n = "return";
		}

		if ($lib===$_SESSION['lib'])
			$m .= "
			<form method=post>
			<input type='hidden' value='$bid' name='id'/>
			<input type='hidden' value='5' name='$choice'/>
			<td>$title</td><td>$author</td><td>$date</td><td>$edition</td><td>$editor</td><td>$desc</td>
			<td><input id='button' type='submit' value='$name' name='$n' $dis /></td>
			</form></tr>";
	}
	$b = "</table>";
	if ($m==="") 
		echo "<p>Aucun livres disponibles</p>";
	else
		echo "$h $m $b";
?>


























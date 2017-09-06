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
		echo "<p id='text' style='color:#33ff33'>Vous venez d'emprunter un nouveau livre !</p><p id='text' style='color:#33ff33'>N'oubliez pas de le rendre dans 2 jours !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur</p>";
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

if (isset($_POST['page']))
	$page = $_POST['page'];
else
	$page = 1;

echo '<style>
	.pagination {
	display: inline-block;
	}

	.pagination input {
	color: black;
	background-color:#ddd;
	float: left;
	padding: 8px 16px;
	text-decoration: none;
	border-radius: 5px;
	transition: background-color .3s;
	}
	.pagination input:hover {
	color: black;
	background-color:red;
	float: left;
	padding: 8px 16px;
	text-decoration: none;
	border-radius: 5px;
	}
	</style>

	<div class="pagination">';
	echo "<form method=post><table><tr><input type='hidden' value='5' name='$choice'/>";
	for ($i = ($page-4)<=1 ? $page : $page-4;$i<$page+10;$i++)
		echo "<td><input type='submit' value='$i' name='page'/></td>";
	echo "</tr></table></form>";
	echo '</div>';
	$json_source = file_get_contents('http://localhost:6080/book/get/'.$page);
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
			<input type='hidden' value='$page' name='page'/>
			<td id='textdisp'>$title</td><td id='textdisp'>$author</td><td id='textdisp'>$date</td><td id='textdisp'>$edition</td><td id='textdisp'>$editor</td><td id='textdisp'>$desc</td>
			<td><input id='button' style='width:100%;' type='submit' value='$name' name='$n' $dis /></td>
			</form></tr>";
	}
	$b = "</table>";
	if ($m==="") 
		echo "<p id='text'>Aucun livres disponibles</p>";
	else
		echo "$h $m $b";
?>


























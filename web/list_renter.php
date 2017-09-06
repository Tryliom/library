<?php
if (strpos($_SERVER['PHP_SELF'], 'list_renter.php') !== false) {
    header("Location: index.php");
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
		echo "<p id='text' style='color:#33ff33'>Vous avez rendu le livre !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur $s</p>";
	}
}

$h = "<table id='list' cellspacing='10'><th>Titre</th><th>Auteur</th><th>Date</th><th>Numéro d'édition</th><th>Editeur</th><th>Description</th><th>Option</th><tr>";
$m = "";
$json_source = file_get_contents('http://localhost:6080/book/get/1');
$jd= json_decode($json_source);
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

	if ($user_id>0 && $user_id==$id) {
		$name = "Rendre";
		$dis = "";
		$n = "return";

		if ($lib===$_SESSION['lib'])
			$m .= "
			<form method=post>
			<input type='hidden' value='$bid' name='id'/>
			<input type='hidden' value='5' name='$choice'/>
			<td id='textdisp'>$title</td><td id='textdisp'>$author</td><td id='textdisp'>$date</td><td id='textdisp'>$edition</td><td id='textdisp'>$editor</td><td id='textdisp'>$desc</td>
			<td><input id='button' style='width:100%;' type='submit' value='$name' name='$n' $dis /></td>
			</form></tr>";
		
	}
}

$b =  "</table>";

if ($m==="") 
		echo "<p id='text'>Aucun livres empruntés</p>";
	else
		echo "$h $m $b";

?>

























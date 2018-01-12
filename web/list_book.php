<?php
if (strpos($_SERVER['PHP_SELF'], 'list_book.php') !== false) {
    header("Location: index.php");
}


if (isset($_REQUEST['take'])) {
	$bid = $_REQUEST['id'];
	$uid = $id;
	$lib = $_SESSION['lib'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,$urlhost."user/take/$bid/$uid/$lib");
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Vous venez de demander pour emprunter un livre !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur, raison: $s</p>";
	}
}

if (isset($_REQUEST['reserve'])) {
	$bid = $_REQUEST['id'];
	$uid = $id;
	$lib = $_SESSION['lib'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,$urlhost."user/take/$bid/$uid/$lib");
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Vous venez d'emprunter un nouveau livre !</p><p id='text' style='color:#33ff33'>N'oubliez pas de le rendre dans 2 jours !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur, raison: $s</p>";
	}
}

require_once('page.php');
$json_source = file_get_contents($urlhost.'book/get/'.$page.'/'.$_SESSION['lib']);
$jd= json_decode($json_source);
$h = "<table id='list' cellspacing='0'><th>Titre</th><th>Auteur</th><th>Date</th><th>Numéro d'édition</th><th>Editeur</th><th>Description</th><th>Option</th><tr>";
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
	
	$nm="";
	$status = 0;
	$json_source2 = file_get_contents($urlhost.'renter/user/get/'.$id.'/'.$bid);		
	$jd2= json_decode($json_source2);
	for ($j = 0;$j<sizeof($jd2);$j++) {
		$status = $jd2[$j]->status;
	}
	if ($status == 3)
		continue;
	
	$dis = $user_id>0 ? "disabled" : "";
	$name = $user_id>0 ? "Pris par ".$nm : "Demander un emprunt";
	$n = "take";
	if ($user_id>0 && $user_id==$id) {
		continue;
	}
	$m .= "
	<form method=post>
	<input type='hidden' value='$bid' name='id'/>
	<input type='hidden' value='5' name='$choice'/>
	<input type='hidden' value='$page' name='page'/>
	<td>$title</td><td>$author</td><td>$date</td><td>$edition</td><td>$editor</td><td>$desc</td>
	<td>
		<input id='button' type='submit' value='$name' name='$n' $dis />";
	if ($user_id>0 && $user_id!=$id) {
		$json_source2 = file_get_contents($urlhost.'renter/getall/'.$bid);		
		$jd2= json_decode($json_source2);
		$date = "";
		for ($j = 0;$j<sizeof($jd2);$j++) {
			if ($jd2[$j]->user_id == $user_id) {
				$date = $jd2[$j]->max_return_date;
			}
		}
		$m .= "<input id='button' style='width:100%;' type='submit' value='Réserver le ".$date."' name='reserve'/>";
	}
	$m .="</td>
	</form></tr>";
}
$b = "</table>";
if ($m==="") 
	echo "<p id='text' style='color:white;'>Aucun livres disponibles</p>";
else
	echo "$h $m $b";
?>

























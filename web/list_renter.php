<?php
if (strpos($_SERVER['PHP_SELF'], 'list_renter.php') !== false) {
    header("Location: index.php");
}

if (isset($_REQUEST['cancel'])) {
	$rid = $_REQUEST['renter_id'];
	$url = "http://localhost:6080/renter/user/cancel/$rid";

	$s = file_get_contents($url);
	
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>La demande a bien été annulée !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur, raison: $s</p>";
	}
}

if (isset($_REQUEST['renew'])) {
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

$h = "<table id='list' cellspacing='10'><th>Titre</th><th>Auteur</th><th>Date</th><th>Numéro d'édition</th><th>Editeur</th><th>Description</th><th>État</th><th>Option</th><tr>";
$m = "";

$json_source = file_get_contents('http://localhost:6080/renter/get/1/'.$_SESSION['lib']);
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

	$name = "";
	$status = 0;
	$n = "renew";
	$rid = "";
	$json_source2 = file_get_contents('http://localhost:6080/renter/user/get/'.$id.'/'.$bid);		
	$jd2= json_decode($json_source2);
	if (sizeof($jd2) == 0)
		continue;
	for ($j = 0;$j<sizeof($jd2);$j++) {
		$status = $jd2[$j]->status;
		$rid = $jd2[$j]->id;
	}
	if ($status == 3) {
		$name = "En attente de validation";
		$n = "cancel";
	}
	if ($status == 2)
		$name = "Pris";
	if ($status == 1)
		$name = "Pris et renouvelé";	
	if ($status == 0)
		$name = "Rendu";
	$dis = "";
	$m .= "<tr>
	<form method=post>
	<input type='hidden' value='$rid' name='renter_id'/>
	<input type='hidden' value='$bid' name='id'/>
	<input type='hidden' value='5' name='$choice'/>
	<td id='textdisp'>$title</td><td id='textdisp'>$author</td><td id='textdisp'>$date</td><td id='textdisp'>$edition</td><td id='textdisp'>$editor</td><td id='textdisp'>$desc</td>
	<td id='textdisp'>$name</td><td>";
		if ($status == 2)
			$m .= "<input id='button' style='width:100%;' type='submit' value='Renouveler' name='$n' $dis />";
		else
			$m .= "<input id='button' style='width:100%;' type='submit' value='Annuler' name='$n' $dis />";
	$m .= "</td>
	</form></tr>";
}

$b =  "</table>";

if ($m==="") 
		echo "<p id='text'>Aucun livres empruntés</p>";
	else
		echo "$h $m $b";

?>

























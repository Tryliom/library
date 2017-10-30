<?php
if (strpos($_SERVER['PHP_SELF'], 'list_renter.php') !== false) {
    header("Location: index.php");
}

if (isset($_REQUEST['accept'])) {
	$rid = $_REQUEST['id'];
	$ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,"http://localhost:6080/admin/valid/$rid");
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$s = curl_exec ($ch);
	curl_close ($ch);
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>Demande d'emprunt validée !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur, raison: $s</p>";
	}
}
if (isset($_REQUEST['cancel'])) {
	$rid = $_REQUEST['id'];
	$url = "http://localhost:6080/renter/cancel/$rid";

	$s = file_get_contents($url);
	
	if ($s==="true") {
		echo "<p id='text' style='color:#33ff33'>La demande a bien été annulée !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur, raison: $s</p>";
	}
}
$h = "<table id='list' cellspacing='0'><th>Titre</th><th>Numéro d'édition</th><th>Utilisateur</th><th>Option</th>";
$m = "";
$json_source = file_get_contents('http://localhost:6080/renter/get/tovalid/1/'.$_SESSION['lib']);
$jd= json_decode($json_source);
for ($i=0;$i<sizeof($jd);$i++) {
	$bid = $jd[$i]->book_id;
	$uid = $jd[$i]->user_id;
	$rid = $jd[$i]->id;
	$title = Utils::getInfoBook($bid, "title");
	$edition = Utils::getInfoBook($bid, "edition");
	$username = Utils::getInfoUser($uid, "name")." ".Utils::getInfoUser($uid, "lastname");
	
	if ($uid>0)
		$m .= "<tr>
			<form method='post'>
			<input type='hidden' value='$rid' name='id'/>
			<input type='hidden' value='5' name='$choice'/>
			<td>$title</td>
			<td>$edition</td>
			<td>$username</td>
			<td>
				".Utils::getButtonImage("valid", "Accepter", "accept").Utils::getButtonImage("cancel", "Refuser", "cancel")."
			</td>
			</form>
			</tr>";
}

$b =  "</table>";

if ($m==="") 
	echo "<p id='text'>Aucun livres empruntés</p>";
else
	echo "$h $m $b";

?>

























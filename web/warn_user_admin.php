<?php
if (strpos($_SERVER['PHP_SELF'], 'list_renter.php') !== false) {
    header("Location: index.php");
}

if (isset($_REQUEST['send_email'])) {
	// Envoyer le mail
	$b = true; // mail($_REQUEST['email'], "Librarie", "Vous devez rendre un livre, vous avez dépasser le délais maximum. Vous risquez une amende s'il n'est pas rendu dans les délais...");
	if ($b) {
		echo "<p id='text' style='color:#33ff33'>Email envoyé !</p>";
	} else {
		echo "<p id='text' style='color:#ff3333'>Erreur</p>";
	}	
}



$m = "";
$h = "<table id='list' cellspacing='10'><th>Titre du livre</th><th>Pseudo de l'utilisateur</th><th>Délai dépassé</th><th>Options</th><tr>";
$json_source = file_get_contents('http://localhost:6080/renter/get/'.$_SESSION['lib']);
$jd= json_decode($json_source);
for ($i=0;$i<sizeof($jd);$i++) {
	if (sizeof($jd)===0)
		break;
	$uid = $jd[$i]->user_id;
	$bid = $jd[$i]->book_id;
	$time = $jd[$i]->max_return_date;
	
	// Faire $time - NOW()
	$time = date("j\j H\h i\m\i\\n s\s\\e\c", $time);

	$json_source2 = file_get_contents('http://localhost:6080/user/get/id/'.$uid);
	$jd2= json_decode($json_source2);
	if (sizeof($jd2)!==0) {
		$pseudo = $jd2[0]->username;
		$email = $jd2[0]->email;
		$json_source3 = file_get_contents('http://localhost:6080/book/getid/'.$bid);
		$jd3= json_decode($json_source3);
		if (sizeof($jd3)===0)
			continue;
		$title = $jd3[0]->title;		
		$m .= "
			<form method=post>
			<input type='hidden' value='$email' name='email'>
			<input type='hidden' name='$choice'>
			<td id='textdisp'>$title</td><td id='textdisp'>$pseudo</td><td id='textdisp'>$time</td>
			<td><input style='width:100%;' id='button' type='submit' value=\"Envoyer un e-mail d'alerte\" name='send_email'/></td>
			</form></tr>";
	}
}
$b =  "</table>";

if ($m==="") 
	echo "<p id='text'>Aucunes alertes</p>";
else
	echo "$h $m $b";

function time_elapsed($secs){
    $bit = array(
        'y' => $secs / 31556926 % 12,
        'w' => $secs / 604800 % 52,
        'd' => $secs / 86400 % 7,
        'h' => $secs / 3600 % 24,
        'm' => $secs / 60 % 60,
        's' => $secs % 60
        );
        
    foreach($bit as $k => $v)
        if($v > 0)$ret[] = $v . $k;
        
    return join(' ', $ret);
    }

?>

























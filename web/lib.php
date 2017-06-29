<?php
$valid = true;
if (!isset($_SESSION['lib'])) {
	if (isset($_REQUEST['choose'])) {
		$_SESSION['lib'] = $_REQUEST['id'];
		header("Location: index.php");
	} else {
		echo "<h1>Choisir la librairie</h1>";
		$json_source = file_get_contents('http://localhost:6080/library/get/');
		$jd= json_decode($json_source);
	for ($i=0;$i<sizeof($jd);$i++) {
		$lid = $jd[$i]->id;
		$adress = $jd[$i]->adress;
		$name = $jd[$i]->name;
			if ($i!=0) {
				echo "<hr style='display:inline-block; width:60%;' />";
			}
			echo "<form method='POST' action='index.php'><p>Nom: ".$name."</p><p>Adresse: ".$adress."</p><br><input name='id' type='hidden' value='".$lid."'><input id='button' name='choose' type='submit' value='Choisir'></form><br>";
		}
		$valid = false;
	}
}

?>
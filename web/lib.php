<?php
$valid = true;
if (!isset($_SESSION['lib'])) {
	if (isset($_REQUEST['choose'])) {
		$_SESSION['lib'] = $_REQUEST['id'];
		header("Location: index.php");
	} else {
		echo "<h1>Choisir la librairie</h1>";
		$reponse = $bdd->query('SELECT * FROM library');
		$i = 0;
		while ($data = $reponse->fetch()) {
			$i++;
			if ($i!=1) {
				echo "<hr style='display:inline-block; width:60%;' />";
			}
			echo "<form method='POST' action='index.php'><p>Nom: ".$data['name']."</p><p>Adresse: ".$data['adress']."</p><br><input name='id' type='hidden' value='".$data['id']."'><input id='button' name='choose' type='submit' value='Choisir'></form><br>";
		}
		$valid = false;
	}
}

?>
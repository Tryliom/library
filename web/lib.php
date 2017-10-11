<?php
$valid = true;
if (!isset($_SESSION['lib'])) {
	if (isset($_REQUEST['choose'])) {
		$_SESSION['lib'] = $_REQUEST['id'];
		header("Location: login.php");
	} else {
		echo "<h1>Choisir la librairie</h1><table id='user' style='width:30%;'>";
		$json_source = file_get_contents('http://localhost:6080/library/get');
		$jd= json_decode($json_source);
		for ($i=0;$i<sizeof($jd);$i++) {
			$data = $jd[$i];
			echo "<tr><td><form method='POST' action='index.php'><p><b>".$data->name."</b></p><input name='id' type='hidden' value='".$data->id."'></td><td><input id='button' style='width:100%;' name='choose' type='submit' value='Choisir'></form></td></tr>";
		}
		echo "</table>";
		$valid = false;
	}
}

?>
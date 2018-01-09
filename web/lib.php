<?php
$valid = false;
if (isset($_REQUEST['choose'])) {
	$_SESSION['lib'] = $_REQUEST['id'];
}
echo "<h1>Bibliothèques disponibles</h1><table id='user' style='width:30%;'>";
$json_source = file_get_contents('http://localhost:6080/library/get');
$jd= json_decode($json_source);
for ($i=0;$i<sizeof($jd);$i++) {
	$data = $jd[$i];
	$n = "choose";
	$v = "Choisir [".$data->name."]";
	$did = true;
	$wd = "height:30px;";
	if (isset($_SESSION['lib']) && $data->id == $_SESSION['lib']) {
		$n = "disconnect";
		$v = "Quitter [".$data->name."]";
		$did = false;
		$valid = true;
		$wd = "height:40px;";
	}
	echo "<tr><td><form method='POST' action='index.php'>";
	if ($did)
		echo "<input name='id' type='hidden' value='".$data->id."'>"; 
	echo "</td><td><input id='button' style='width:100%; $wd' 
	name='".$n."' type='submit' value='".$v."'></form></td></tr>";
}
echo "</table>";

?>
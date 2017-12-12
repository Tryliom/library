<head><link href="css\style.css" rel="stylesheet" media="all" type="text/css"></head>
<title>Library Home</title>

<?php
require_once("bdd.php");
if (isset($_SESSION['lib']) && isset($_REQUEST['disconnect'])) {
	$_SESSION['lib'] = null;
	header("Location: index.php");
}
require_once("lib.php");

if (!$valid)
	return;

echo "<h1>Accueil de la librairie</h1>";

echo "<form method=post><input id='button' type='submit' name='disconnect' value='Se déconnecter de la librairie actuelle' /></form>";


require_once("footer.html");
?>
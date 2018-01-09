<head><link href="css\style.css" rel="stylesheet" media="all" type="text/css"></head>
<title>Library Home</title>

<?php
require_once("bdd.php");
if (isset($_SESSION['lib']) && isset($_REQUEST['disconnect'])) {
	$_SESSION['lib'] = null;
	header("Location: index.php");
}

echo "<h1>Accueil de la bibliothèque</h1>";

require_once("lib.php");


require_once("footer.html");
?>
<head><link href="css\style.css" rel="stylesheet" media="all" type="text/css"></head>
<title>Admin Panel</title>
<?php
require_once("bdd.php");


if (isset($_SESSION['token']) && isset($_REQUEST['logout'])) {
	$_SESSION['token'] = null;
}


if (isset($_SESSION['token']) && Utils::isValidSA($_SESSION['token'])) {
	echo "<h1 style='color:#f77;'>Panel administrateur</h1>";
	require_once("list_lib.php");
} else {
	header("Location: index.php");
}

require_once("footer.php");


function generateToken($length) {
	return bin2hex(random_bytes($length));
}

function getPost($str) {
	return isset($_REQUEST[$str]) ? $_REQUEST[$str] : "";
}

?>
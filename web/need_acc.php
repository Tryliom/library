<?php
if (isset($_SESSION['token']) && isValid($bdd, $_SESSION['token']) && isset($_SESSION['lib']))
	;
else
	header("Location: index.php");

?>
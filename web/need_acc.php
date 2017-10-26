<?php
if (isset($_SESSION['token']) && Utils::isValid($_SESSION['token']) && isset($_SESSION['lib']))
	;
else
	header("Location: login.php");

?>
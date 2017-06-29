<?php
if (isset($_SESSION['token']) && isValid($_SESSION['token']) && isset($_SESSION['lib']))
	;
else
	header("Location: index.php");

?>
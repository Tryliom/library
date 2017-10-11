<head><link href="css\style.css" rel="stylesheet" media="all" type="text/css"></head>
<title>Library Home</title>

<?php
require_once("bdd.php");
require_once("lib.php");
require_once("need_acc.php");
require_once("userinfo.php");
switch ($level_access) {
	case 0:
		require_once("member_lib.php");
		break;
	case 6:
		require_once("admin_lib.php");
		break;
	case 7:
		require_once("admin_lib.php");
		break;
}
require_once("footer.html");
?>
<body>
<div id=all>
<nav>
	<?php	
	if (isset($_REQUEST['logout'])) {
		$_SESSION['token'] = null;
		header("Location: login.php");
	}
	$user_id;
	if (isset($_SESSION['token']) && Utils::isValid($_SESSION['token'])) {
		$json_source = file_get_contents($urlhost.'user/get/token/'.$_SESSION['token']);
		$jd= json_decode($json_source);
		$us = "user_admin_32";
		$user_id = $jd[0]->id;
		$level = $jd[0]->level_access;
		global $us;
		if ($level<6)		
			$us = "user";
		else
			$us = "user_admin";
	}

	echo "
	<a href='index.php' title='Home'>
		<img height='64' width='64' src='images/home.png'>
	</a>
	";
	if (isset($_SESSION['token']) && Utils::isValid($_SESSION['token'])) {
		echo "
		<a href='user.php' title='Espace utilisateur'>
			<img height='64' width='64' src='images/".$us."_64.png'>
		</a>
		";
	} else {
		echo "
		<a href='login.php' title='Login'>
			<img height='64' width='64' src='images/user_64.png'>
		</a>
		";
	}

	if (isset($_SESSION['lib']) && isset($_SESSION['token']) && Utils::isValid($_SESSION['token'])) {
		echo "
		<a href='library.php' title='Bibliothèque'>
			<img height='64' width='64' src='images/library.png'>
		</a>
		";
	}
	
	if (isset($_SESSION['token']) && Utils::isValid($_SESSION['token'])) {
		echo "
		<form style='display:inline; float:right;' action='' method=post>
			<input id='logout' title='Se déconnecter' type=submit name=logout value=''/>
		</form>
		<a style='float:right;' href='user.php' title='Espace utilisateur'>
			<img height='32' width='32' src='images/".$us."_32.png'>
		</a>
				<p style='color:#3A9D23; font-size:32px; float:right; margin:0 auto;'>
			".strtoupper(Utils::getInfoUser($user_id, "username"))."
		</p>
		";
		/* if (Utils::hasAlert($user_id))
		echo "
			".Utils::getImage("alert", "Vous devez rendre 1 ou plusieurs livres !")."
		";
		*/
	}
	if (isset($_SESSION['token']) && Utils::isValidSA($_SESSION['token'])) {
		echo "
		<a href='admin_panel.php' title='Panel administrateur'>
			<img height='64' width='64' src='images/admin.png'>
		</a>
		";
	}
	echo "</nav>";
?>

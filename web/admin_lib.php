<?php
if (strpos($_SERVER['PHP_SELF'], 'admin_lib.php') !== false) {
    header("Location: index.php");
}

$choice = "";
		
if (isset($_REQUEST['list_book'])) {
	$choice = "list_book";
}
if (isset($_REQUEST['list_renter'])) {
	$choice = "list_renter";
}
if (isset($_REQUEST['list_user'])) {
	$choice = "list_user";
}
if (isset($_REQUEST['warn_user'])) {
	$choice = "warn_user";
}



echo "
<div>
<h1 style='color:#f77;'>Mode Admin</h1>
<table cellspacing='10'><tr style='width:100%;'><td>
<form method='post'><table id='user' cellspacing=15><h1>Gestion des livres</h1>
<input type='hidden' name='admin' />
<tr>
	<td><input id='bookmanager".($choice=='list_book' ? '_selected' : '')."' type='submit' name='list_book' value='Liste des livres' /></td>
	<td><input id='bookmanager".($choice=='list_renter' ? '_selected' : '')."' type='submit' name='list_renter' value='Liste des livres empruntés' /></td>
</tr>

</table></form>

</td><td>

<form method='post'><table id='user' cellspacing=15><h1>Gestion des utilisateurs</h1>

<tr>
	<td><input id='bookmanager".($choice=='list_user' ? '_selected' : '')."' type='submit' name='list_user' value='Liste des utilisateurs' /></td>
	<td><input id='bookmanager".($choice=='warn_user' ? '_selected' : '')."' type='submit' name='warn_user' value='Alertes des utilisateurs' /></td>
</tr>

</table></form>

</td></tr></table>
</div>
";

if ($choice !== "")
require_once($choice."_admin.php");


?>
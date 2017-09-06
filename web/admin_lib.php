<?php
if (strpos($_SERVER['PHP_SELF'], 'admin_lib.php') !== false) {
    header("Location: index.php");
}
echo "
<div>
<h1 style='color:#f77;'>Mode Admin</h1>
<form name='lg' method='post'><table>
<tr>
	<td><input id='button' type='submit' name='logout' value='Se déconnecter' /></td>
</tr>

</table></form>
<table cellspacing='30'><tr><td>
<form name='book' method='post'><table id='user' cellspacing=15><h1>Gestion des livres</h1>

<tr>
	<td><input id='bookmanager' type='submit' name='list_book' value='Liste des livres' /></td>
	<td><input id='bookmanager' type='submit' name='list_renter' value='Liste des livres empruntés' /></td>
</tr>

</table></form>

</td><td>

<form name='book' method='post'><table id='user' cellspacing=15><h1>Gestion des utilisateurs</h1>

<tr>
	<td><input id='bookmanager' type='submit' name='list_user' value='Liste des utilisateurs' /></td>
	<td><input id='bookmanager' type='submit' name='warn_user' value='Alertes des utilisateurs' /></td>
</tr>

</table></form>

</td></tr></table>
</div>
";


if (isset($_POST['list_book'])) {
	$choice="list_book_admin";
}
if (isset($_POST['list_renter'])) {
	$choice="list_renter_admin";
}

if (isset($_POST['list_user'])) {
	$choice="list_user_admin";
}
if (isset($_POST['warn_user'])) {
	$choice="warn_user_admin";
}

if (!empty($choice))
	require_once($choice.".php");

?>
<?php
if (strpos($_SERVER['PHP_SELF'], 'admin_lib.php') !== false) {
    header("Location: index.php");
}
echo "
<div>
<h1 style='color:#f77;'>Mode Admin</h1>
<p>Tous les boutons doivent encore être codés</p>
<table cellspacing='30'><tr><td>
<form name='book' method='post'><table id='user' cellspacing=15><h1>Gestion des livres</h1>

<tr>
	<td><input id='bookmanager' type='submit' name='list_book' value='Liste des livres' /></td>
	<td><input id='bookmanager' type='submit' name='list_renter' value='Liste des livres empruntés' /></td>
</tr>
<tr>
	<td><input id='bookmanager' type='submit' name='create_book' value='Créer un livre' /></td>
	<td><input id='bookmanager' type='submit' name='delete_book' value='Supprimer un livre' /></td>
	<td><input id='bookmanager' type='submit' name='edit_book' value='Editer un livre' /></td>
</tr>

</table></form>

</td><td>

<form name='book' method='post'><table id='user' cellspacing=15><h1>Gestion des utilisateurs</h1>

<tr>
	<td><input id='bookmanager' type='submit' name='list_user' value='Liste des utilisateurs' /></td>
	<td><input id='bookmanager' type='submit' name='warn_user' value='Alertes des utilisateurs' /></td>
</tr>
<tr>
	<td><input id='bookmanager' type='submit' name='create_user' value='Créer un utilisateur' /></td>
	<td><input id='bookmanager' type='submit' name='delete_user' value='Supprimer un utilisateur' /></td>
	<td><input id='bookmanager' type='submit' name='edit_user' value='Editer un utilisateur' /></td>
</tr>

</table></form>

</td></tr></table>
</div>
";
?>
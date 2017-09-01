<?php
if (strpos($_SERVER['PHP_SELF'], 'member_lib.php') !== false) {
    header("Location: index.php");
}

echo "
<div>
<form name='book' method='post'><table id='user' cellspacing=15><h1>Gestion des livres</h1>

<tr>
	<td><input id='bookmanager' type='submit' name='list_book' value='Liste des livres' /></td>
	<td><input id='bookmanager' type='submit' name='list_renter' value='Liste des livres empruntés' /></td>
</tr>
<tr>
	<td><input id='bookmanager' type='submit' name='take_book' value='Emprunter un livre' /></td>
	<td><input id='bookmanager' type='submit' name='return_book' value='Rendre un livre' /></td>
</tr>

</table></form>
</div>
";

if (isset($_POST['book']))
	require_once($_POST['book'].".php");
?>
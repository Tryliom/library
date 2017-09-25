<?php
if (strpos($_SERVER['PHP_SELF'], 'member_lib.php') !== false) {
    header("Location: index.php");
}

echo "
<div>
<form name='book' method='post'><table id='user' cellspacing=15><caption style='font-size:40px;'>Gestion des livres</caption>

<tr>
	<td style='width:50%;'><input id='bookmanager' type='submit' name='list_book' value='Liste des livres' /></td>
	<td style='width:50%;'><input id='bookmanager' type='submit' name='list_renter' value='Liste des livres empruntés' /></td>
</tr>

</table></form>
</div>
";



if (isset($_POST['list_book'])) {
	$choice = "list_book";
}
if (isset($_POST['list_renter'])) {
	$choice = "list_renter";
}
if (isset($choice))
	require_once($choice.".php");
?>
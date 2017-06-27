<?php
if (strpos($_SERVER['PHP_SELF'], 'member_lib.php') !== false) {
    header("Location: index.php");
}

$choice = "";
		
if (isset($_REQUEST['list_book'])) {
	$choice = "list_book";
}
if (isset($_REQUEST['list_renter'])) {
	$choice = "list_renter";
}
if (isset($_REQUEST['take_book'])) {
	$choice = "take_book";
}
if (isset($_REQUEST['return_book'])) {
	$choice = "return_book";
}

echo "
<div>
<form name='book' method='post'><table id='user' cellspacing=15><h1>Gestion des livres</h1>
<tr>
	<td><input id='bookmanager".($choice=='list_book' ? '_selected' : '')."' type='submit' name='list_book' value='Liste des livres' /></td>
	<td><input id='bookmanager".($choice=='list_renter' ? '_selected' : '')."' type='submit' name='list_renter' value='Liste des livres empruntés' /></td>
</tr>

</table></form>
</div>
";

if ($choice !== "")
require_once($choice.".php");


















































?>
<?php
if (strpos($_SERVER['PHP_SELF'], 'list_book.php') !== false) {
    header("Location: index.php");
}

echo "<table id='list' cellspacing='10'><th>Titre</th><th>Auteur</th><th>Date</th><th>Numéro d'édition</th><th>Editeur</th><th>Description</th><th>Commander</th><tr>";
$json_source = file_get_contents('http://localhost:6080/book/get/');
$jd= json_decode($json_source);
for ($i=0;$i<sizeof($jd);$i++) {
$id = $jd[$i]->id;
$title = $jd[$i]->title;
$author = $jd[$i]->author;
$date = $jd[$i]->date;
$desc = $jd[$i]->description;
$edition = $jd[$i]->edition;
$editor = $jd[$i]->editor;
$user_id = $jd[$i]->user_id;
$lib = $jd[$i]->library_id;

if ($lib===$_SESSION['lib'])
	echo "
	<input type=hidden value=$id name=id/>
	<td>$title</td><td>$author</td><td>$date</td><td>$edition</td><td>$editor</td><td>$desc</td>
	<td><input id='button' type='submit' value='Emprunter' name='take'/></td>
	</tr>";
}

echo "</table>";
?>
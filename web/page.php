<?php
$page = 1;
if (isset($_POST['page']))
	$page = $_POST['page'];
if (!isset($libadmin))
	$libadmin=$_SESSION['lib'];
echo '<style>
	.pagination {
	display: inline-block;
	}

	.pagination input {
	color: black;
	background-color:#ddd;
	float: left;
	padding: 4px 8px;
	text-decoration: none;
	border-radius: 5px;
	transition: background-color .3s;
	}
	.pagination input:hover {
	color: black;
	background-color:#898989;
	float: left;
	padding: 4px 8px;
	text-decoration: none;
	border-radius: 5px;
	}
	</style>

	<div class="pagination">';
	$jd = array();
	$json_source = "";
	$j = 1;	
	for ($j=1;true;$j++) {
		$json_source = file_get_contents($urlhost.'book/get/'.$j.'/'.$libadmin);
		$jd= json_decode($json_source);
		if (sizeof($jd)===0 || $j>($page+6))			
			break;
	}
	$j -=1;
	echo "<form method=post><table><tr><input type='hidden' value='5' name='$choice'/>";
	$sup = $j>($page+6) ? ($page+6) : 
		($j==$page ? $page+1 : $page-($page-$j-1));
	$min = 0;
	$i = 6;
	if (($page-$i)<=0)
		for (;($page-$i)<=0;$i--) {}
	$min = $page-$i;
	for ($i = $min;$i<$sup;$i++)
		echo "<td><input ".($i==$page ? "style='background-color:#898989;'" : "")." type='submit' value='$i' name='page'/></td>";
	echo "</tr></table></form>";
	echo '</div>';	
?>
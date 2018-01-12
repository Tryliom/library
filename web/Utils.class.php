<?php

class Utils {
	public static function getUrl() {
		return "http://localhost:6080/remoteGIT/";
	}

	public static function isValid($token) {
		$b=false;
		$json_source = file_get_contents(Utils::getUrl().'user/verif/member/'.$token);
		$jd= json_decode($json_source);
		$name = empty($jd) ? "" : $jd[0]->username;
		if (empty($name)) {
			$b=false;
		} else
			$b=true;
		return $b;
	}

	public static function isValidSA($token) {
		$b=false;
		$json_source = file_get_contents(Utils::getUrl().'user/verif/admin/'.$token);
		$jd= json_decode($json_source);
		$name = empty($jd) ? "" : $jd[0]->username;
		if (empty($name)) {
			$b=false;
		} else
			$b=true;
		return $b;
	}


	public static function setColor($s) {
		$c = substr($s, 2, 1);
		$s = substr($s, 3);
		switch ($c) {
			case 'a':
				return "<div style='color:#3f3;'>[$s]</div>";
			case 'b':
				return "<div style='color:#aaf;'>[$s]</div>";		
			case 'c':
				return "<div style='color:#f44;'>[$s]</div>";
			case 'd':
				return "<div style='color:#f3d;'>[$s]</div>";
			case 'e':
				return "<div style='color:#3dd;'>[$s]</div>";
			case 'f':
				return "<div style='color:#111;'>[$s]</div>";
			case '6':
				return "<div style='color:#FF8C00;'>[$s]</div>";
		}
	}

	public static function isPage($s) {
		if (strpos($_SERVER['PHP_SELF'], $s) !== false) {
			return "style='color:#77f;'";
		}
		return "";
	}

	public static function getLvl($lvl, $nb) {
		if ($nb==true) {
			switch ($lvl) {
				case 7: return "§cSuperAdmin";
				case 6: return "§6Admin";
				case 0: return "§bMembre";
			}
		} else {
			switch ($lvl) {
				case "§cSuperAdmin": return 7;
				case "§6Admin": return 6;
				case "§bMembre": return 0;
				case "SuperAdmin": return 7;
				case "Admin": return 6;
				case "Membre": return 0;
			}
		}
	}

	public static function getListRank() {
		return array("SuperAdmin", "Admin", "Membre");
	}

	public static function getInfoBook($id, $info) {
		$json_source = file_get_contents(Utils::getUrl().'book/getid/'.$id);
		$jd= json_decode($json_source);
		if (sizeof($jd)===0)
			return "Not found";
		if ($info=="id")
			return $jd[0]->id;
		if ($info=="title")
			return $jd[0]->title;
		if ($info=="author")
			return $jd[0]->author;
		if ($info=="date")
			return $jd[0]->date;
		if ($info=="edition")
			return $jd[0]->edition;
		if ($info=="editor")
			return $jd[0]->editor;
		if ($info=="desc")
			return $jd[0]->description;
		if ($info=="user_id")
			return $jd[0]->user_id;
		if ($info=="library_id")
			return $jd[0]->library_id;
	}

	public static function getInfoUser($id, $info) {
		$json_source = file_get_contents(Utils::getUrl().'user/get/id/'.$id);
		$jd= json_decode($json_source);
		if (sizeof($jd)===0)
			return "Not found";
		if ($info=="id")
			return $jd[0]->id;
		if ($info=="username")
			return $jd[0]->username;
		if ($info=="name")
			return $jd[0]->name;
		if ($info=="lastname")
			return $jd[0]->lastname;
		if ($info=="password")
			return $jd[0]->password;
		if ($info=="email")
			return $jd[0]->email;
		if ($info=="tel")
			return $jd[0]->tel;
		if ($info=="token")
			return $jd[0]->token;
		if ($info=="level_access")
			return $jd[0]->level_access;
	}

	public static function getInfoLibrary($id, $info) {
		$json_source = file_get_contents(Utils::getUrl().'library/get/id/'.$id);
		$jd= json_decode($json_source);
		if (sizeof($jd)===0)
			return "Not found";
		if ($info=="id")
			return $jd[0]->id;
		if ($info=="name")
			return $jd[0]->name;
		if ($info=="adress")
			return $jd[0]->adress;
	}

	public static function getIdLibrary($name) {
		$json_source = file_get_contents(Utils::getUrl().'library/get/name/'.str_replace("+", "%20", urlencode($name)));
		$jd= json_decode($json_source);
		if (sizeof($jd)===0) {
			return "Not found";			
		}
		return $jd[0]->id;
	}


	public static function getButtonImage($src, $title, $name) {
		return "
		<input id='$src' type='submit' value='' title='$title' name='$name' />
		";
	}

	public static function getImage($src, $title, $size = 16) {
		return "
		<img src='images/$src.png' style='margin: 0 0 0 ".($size)."px;' height='".$size."px' width='".$size."px' alt='$title'/>
		";
	}

	public static function hasAlert($user_id) {
		$s = file_get_contents(Utils::getUrl().'renter/hasalert/'.$user_id);
		if ($s==="true")
			return true;
		else
			return false;		
	}
}
?>
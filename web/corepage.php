<?PHP
class general_data {
	public function get_root()
	{
		return "http://127.0.0.1/enstall/";
	}
	
	public function get_organization()
	{
		return "Example Code";
	}
	
	public function get_version()
	{
		return "0.1";
	}
	
}

class php_draw {
	public static function page_header()
	{
		if ($_SERVER['HTTP_USER_AGENT'] == "Enstall")
	 	{
		}else{
	 		//random browser, load enxtended header	
	 		echo "<div id='page_header'>
	 				<h2><a style='color: black; text-decoration: none;' href='" . general_data::get_root() . "/index.php'>Enstall</a></h2><h5 style='margin: 0 0 0 10;'>A simplied installer, because class is hard enough</h5>
	 			</div>";
	 	}
	}
	
	public static function page_contentarea_open()
	{
		echo "<div id='page_contentarea'>";
		php_draw::page_sidebar();
	}
	
	public static function page_contentarea_close()
	{
		echo "</div>";
	}
	
	public static function page_sidebar()
	{
		echo "<div id='page_sidebar'>
				<span id='sidebar_username'>" . Enstall_Authentication::get_username() . "</span>
				<hr>
				<div id='sidebar_settings'>My Settings</div>
				<hr>
				<div id='sidebar_recent'>My Recent Packages</div>
			</div>";
	}

	public static function page_footer()
	{
		echo "<div id='page_footer'>
				<hr>
				<div id='page_footer_link'>
					<a href='" . general_data::get_root() . "admin/add_package.php'>Add New Package</a>
					<p><a href='" . general_data::get_root() . "packages.php'>Packages</a></p>
				</div>
			</div>";
	}

}

class Enstall_Authentication {
	public static function isAuthenticated()
	{
		//No authentication here
		return true;
	}
	
	public static function get_username()
	{
		//No authentication here
		if (Enstall_Authentication::isAuthenticated())
		{
			return "Test User";
		}else{
			return "Login";
		}
		
	}
}

class database_helper {
	public static function db_connect()
	{
		mysql_connect("localhost", "Enstall", "hGSuQUBKhEe9Sq6K") or die("Could Not Connect To MYSQL");
		mysql_select_db("Enstall") or die ("Could Not Connect to DATABASE");
	}
	
	public static function db_disconnect()
	{
		mysql_close();
	}
	
	public static function db_get_packages_alp() 
	{
		$result_array = array();
		$query = "SELECT * FROM `packages` ORDER BY `Package_Title` ASC";
		$result = mysql_query($query);
		if ($result)
		{
			while ($row = mysql_fetch_array($result))
			{
				$temp_array = array();
				array_push($temp_array, $row['index']);					//0
				array_push($temp_array, $row['Package_Title']);			//1
				array_push($temp_array, $row['Package_Description']);	//2
				array_push($temp_array, $row['Package_Basic']);			//3
				array_push($temp_array, $row['Package_Advanced']);		//4
				array_push($temp_array, $row['Package_Version']);		//5
				array_push($temp_array, $row['Software_Version']);		//6
				array_push($temp_array, $row['Package_Icon']);			//7
				
				array_push($result_array, $temp_array);
			}
		}
		return $result_array;
	}
	
	public static function db_get_package($package_id) 
	{
		$result_array = array();
		$query = "SELECT * FROM `packages` WHERE `index`='$package_id'";
		$result = mysql_query($query);
		if ($result)
		{
			while ($row = mysql_fetch_array($result))
			{
				$temp_array = array();
				array_push($temp_array, $row['index']);					//0
				array_push($temp_array, $row['Package_Title']);			//1
				array_push($temp_array, $row['Package_Description']);	//2
				array_push($temp_array, $row['Package_Basic']);			//3
				array_push($temp_array, $row['Package_Advanced']);		//4
				array_push($temp_array, $row['Package_Version']);		//5
				array_push($temp_array, $row['Software_Version']);		//6
				array_push($temp_array, $row['Package_Icon']);			//7
				
				$result_array = $temp_array;
			}
		}
		return $result_array;
	}
}

?>
<?PHP

class general_data {
	
	public function get_organization()
	{
		return "Example Enstall";
	}
	
	public function get_version()
	{
		return "0.1";
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
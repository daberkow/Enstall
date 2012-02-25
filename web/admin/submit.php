<?PHP
	if(isset($_REQUEST['Package_Title']) && isset($_REQUEST['Package_Description']) && isset($_REQUEST['Package_Basic_Script']) && isset($_REQUEST['Package_Advanced_Script']) && isset($_REQUEST['Package_Icon']))
	{
		include('../corepage.php');
		database_helper::db_connect();
		
		$query = "INSERT INTO `Enstall`.`packages` (`index`, `Package_Title` , `Package_Description` , `Package_Basic`, `Package_Advanced`, `Package_Version`, `Package_Icon`) VALUES (NULL , '" . $_REQUEST['Package_Title'] . "' , '" . $_REQUEST['Package_Description'] . "' , '" . $_REQUEST['Package_Basic_Script'] . "' , '" . $_REQUEST['Package_Advanced_Script'] . "', 1, '" . $_REQUEST['Package_Icon'] . "')";
		
		$result = mysql_query($query);
		
		if ($result)
		{
			//inserted
			
			header("location: " . general_data::get_root() . "packages.php?id=" . mysql_insert_id());
		}else{
			//ITEM NEED ADDED
			//this should kick you back with options in place
		}
		
		database_helper::db_disconnect();
	}
?>
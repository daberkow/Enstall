<?PHP
	if(isset($_REQUEST['Pacakge_Title']) && isset($_REQUEST['Package_Description']) && isset($_REQUEST['Package_Basic_Script']) && isset($_REQUEST['Package_Advanced_Script']))
	{
		include('../corepage.php');
		database_helper::db_connect();
		
		$query = "INSERT INTO `Enstall`.`packages` (`index`, `Package_Title` , `Package_Description` , `Package_Basic`, `Package_Advanced`, `Package_Version`) VALUES (NULL , '" . $_REQUEST['Pacakge_Title'] . "' , '" . $_REQUEST['Package_Description'] . "' , '" . $_REQUEST['Package_Basic_Script'] . "' , '" . $_REQUEST['Package_Advanced_Script'] . "', 1)";
		
		$result = mysql_query($query);
		
		if ($result)
		{
			//inserted
			
			header($root_folder . "packages.php?id=" . mysql_insert_id());
		}else{
			//ITEM NEED ADDED
			//this should kick you back with options in place
		}
		
		database_helper::db_disconnect();
	}
?>
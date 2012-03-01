<?PHP
	if (isset($_REQUEST['id']))
	{
		include("./corepage.php");
		database_helper::db_connect();
		
		header("Content-type: text/xml; ");
		header("Content-Disposition: attachment; filename=\"ETUE" . $_REQUEST['id'] . ".xml\""); 
		
		$package_details = database_helper::db_get_package($_REQUEST['id']);
		if (count($package_details) == 1)
		{
			echo "<ETUE-Config>\n";
			
			echo "	<Organization>" . general_data::get_organization() . "</Organization>\n";
			
			echo "	<Version-of-File>" . $package_details[0][5] . "</Version-of-File>\n";
			
			echo "	<ETUE-Version-Created-In>" . general_data::get_version() . "</ETUE-Version-Created-In>\n";
		
			echo "	<Software>\n";
			
			echo "		<Package>\n";
			
			echo "			<UID>" . $package_details[0][0] . "</UID>\n";
			
			echo "			<Title>" . $package_details[0][1] . "</Title>\n";
			
			echo "			<Description>" . $package_details[0][2] . "</Description>\n";
			
			echo "			<Basic>\n";
			
			echo "				<script>" . $package_details[0][3] . "</script>\n";
			
			echo "			</Basic>\n";
			
			echo "			<Advanced>\n";
			
			echo "				<script>" . $package_details[0][4] . "</script>\n";
			
			echo "			</Advanced>\n";
			
			echo "		</Package>\n";
			
			echo "	</Software>\n";
			
			echo "</ETUE-Config>\n";
		}else{
			echo "Package Retrieval Error";
		}
		
	}else{
		echo "Error in package";
	}

?>
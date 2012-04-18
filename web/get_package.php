<?PHP
	if (isset($_REQUEST['id']))
	{
		include("./core.php");
		database_helper::db_connect();
		
		header("Content-type: text/xml; ");
		//header("Content-Disposition: attachment; filename=\"ETUE" . $_REQUEST['id'] . ".xml\""); 
		
		$package_details = database_helper::db_get_package($_REQUEST['id']);
		//print_r($package_details);
		if (count($package_details) == 8)
		{
		
			$xml = new SimpleXMLElement('<xml/>');

			$doc = $xml->addChild('ETUE-Config');
			$doc->addChild("Organization", general_data::get_organization());
			$doc->addChild("Version-of-File", $package_details[5]);
			$doc->addChild("ETUE-Version-Created-In", general_data::get_version());
			$software = $doc->addChild('Software');
			$package = $software->addChild('Package');
			$package->addChild("UID", $package_details[0]);
			$package->addChild("Title", $package_details[1]);
			$package->addChild("Description", $package_details[2]);
			$basic = $package->addChild('Basic');
			$basic->addChild('script', $package_details[3]);
			$advanced = $package->addChild('Advanced');
			$advanced->addChild('script', $package_details[4]);
			
			print($xml->asXML());

		}else{
			echo "Package Retrieval Error";
		}
		
	}else{
		echo "Error in package";
	}

?>
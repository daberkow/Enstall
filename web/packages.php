<!-- if $_REQUEST['id'] has a item then just pull that up, or pull up a list of them -->

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="./style.css"/>
	</head>
	<body>
		<div id='main_page'>
		<?PHP 
			include("./corepage.php");
			
			php_draw::page_header();
			php_draw::page_contentarea_open();
			
			if(isset($_REQUEST['id']))
			{
			
			}else{
				database_helper::db_connect();
				$array_data = database_helper::db_get_packages_alp();
				print_r($array_data);
				database_helper::db_disconnect();
			}
			
			php_draw::page_contentarea_close();
			php_draw::page_footer();
		?>
		</div>
	</body>
</html>
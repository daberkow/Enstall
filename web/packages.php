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
				//select a individual package
				database_helper::db_connect();
				$row_item = database_helper::db_get_package($_REQUEST['id']);
				
				
				echo "<div id='package_table'>
							<div class='icon'>
								<a href='" . general_data::get_root()  . "/packages.php?id=$row_item[0]'><img src='" . general_data::get_root() . "$row_item[7]' alt='$row_item[1] Icon' height='100' width='100' /></a>
							</div>
							<div class='package_text' style='height: 200px;'>
									<p class='package_title'><a href='" . general_data::get_root() . "/packages.php?id=$row_item[0]'> $row_item[1] </a></p>
									<p > $row_item[2] </p>
							</div>
									
							<div class='screen_shots'>
								Screen Shots
								<!-- Put in code to upload/call screen shots-->
							</div>
					</div>";
			
			}else{
				//no package sepecified, just load hte whole thing
				database_helper::db_connect();
				$array_data = database_helper::db_get_packages_alp();
				echo "<h3>Pacakages</h3>";
				
				foreach ($array_data as $row_item)
				{
					echo "<div id='package_line'>
							<table id='package_table'> <!-- I KNOW TABLES IM SORRY -->
								<td>
									<a href='" . general_data::get_root()  . "/packages.php?id=$row_item[0]'><img src='" . general_data::get_root() . "$row_item[7]' alt='$row_item[1] Icon' height='50' width='50' /></a>
								</td>
								<td>
									<table id='package_words' style='width: 600px;'>
										<tr>
											<td id='package_title'><a href='" . general_data::get_root() . "/packages.php?id=$row_item[0]'> $row_item[1] </a></td>
										</tr>
										<tr>
											<td id='package_description'> $row_item[2] </td>
										</tr>
									</table>
								</td>
								<td style=''>
									<a href='" . general_data::get_root()  . "/get_package.php?id=$row_item[0]'>Install</a>
								</td>
							</table>
							</div>";
				}
				//print_r($array_data);
				database_helper::db_disconnect();
			}
			
			php_draw::page_contentarea_close();
			php_draw::page_footer();
		?>
		</div>
	</body>
</html>
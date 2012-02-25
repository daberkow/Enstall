<?PHP

//Until I descide to make a front page redirect to the front page
	header('location: ./packages.php');

?>

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
			php_draw::page_contentarea_close();
			php_draw::page_footer();
		?>
		</div>
	</body>
</html>
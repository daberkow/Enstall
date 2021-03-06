<!DOCTYPE html>

<html lang="en">
	<head>
		<link rel="stylesheet" href="./bootstrap/css/bootstrap.css">
		<script type="text/javascript" src="https://www.google.com/jsapi">
		</script>
		<script>
			google.load("jquery", "1.7.1");
		</script>
		<script src="./bootstrap/js/bootstrap.js"></script>
		<title>Enstall Packages</title>
	</head>
	<body>
	<div class="navbar navbar-fixed-top">
	    <div class="navbar-inner">
	      <div class="container">
	        <a class="brand" href="#">Enstall</a>
	        <div class="nav-collapse">
	          <ul class="nav">
	            <li><a href="index.php">Home</a></li>
	            <li class="active"><a href="packages.php">Packages</a></li>
	            <li><a href="http://rcos.rpi.edu/">RCOS</a></li>
	            <li><a href="http://enstall.wordpress.com/">Blog</a></li>
	            <li><a href="https://github.com/daberkow/Enstall">Source</a></li>
	          </ul>
	        </div><!-- /.nav-collapse -->
	      </div>
	    </div><!-- /navbar-inner -->
	  </div><!-- /navbar -->
	  <hr>
	  
	  <div class="row show-grid">
	  		<div class="span12">
	  			<div class="row">
	  				<div class="span4 offset1"><h2>Packages</h2></div>
	  				<div class='span3 offset2'><a class='btn' href='add_package.php'>Add a New Package</a></div>
	  			</div>
	  		</div>
	  		<div class="span10 offset1">
	  		<?PHP
	  			include("./core.php");
	  			database_helper::db_connect();
				$array_data = database_helper::db_get_packages_alp();
				foreach ($array_data as $row_item)
				{
					echo "<hr>";
					echo "	<div class='row'>
								<div class='span4 offset1'><h4><a href='package.php?id=$row_item[0]'>$row_item[1]</a></h4></div>
								<div class='span2 offset7'><a class='btn' href='get_package.php?id=$row_item[0]'>Install</a></div>
							</div>
							<div class='row'>
								<div class='span6 offset2'>$row_item[2]</div>
							</div>";
				}
				database_helper::db_disconnect();
	  		?>
	  </div>
	</body>
</html>
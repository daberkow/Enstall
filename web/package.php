<?PHP
	if(!isset($_REQUEST['id']))
	{
	  	header("location: index.php");
	}
?>

<!DOCTYPE html>

<html lang="en">
	<head>
		<link rel="stylesheet" type="text/css" href="./style.css"/>
		<link rel="stylesheet" href="./bootstrap/css/bootstrap.css">
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>
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
	  		<div class="span8 offset1">
	  			<div class="row-fluid">
	  				<div class="span6"><h2>Package</h2></div>
	  			</div>
	  		</div>
	  		<div class="span10 offset1">
	  		<?PHP
	  			include("./core.php");
	  			//select a individual package
				database_helper::db_connect();
				$row_item = database_helper::db_get_package($_REQUEST['id']);
				
				echo "
					<div class='span8 offset1'>
			  			<div class='row-fluid'>
			  				<div class='span6'><h3>$row_item[1]</h3></div>
			  			</div>
			  			<div class='row'>
			  				<div class='span3'>$row_item[2]</div>
			  			</div>
			  			
			  			<div class='row-fluid'>
			  				<div class='span3 offset2'>Basic Script</div>
			  				<div class='span6 offset1'><pre>$row_item[3]</pre></div>
			  			</div>
			  			<div class='row-fluid'>
			  				<div class='span3 offset2'>Advanced Script</div>
			  				<div class='span6 offset1'><pre>$row_item[4]</pre></div>

			  			</div>
			  			<div class='row'>
			  				<div class='span2 offset5'><a class='btn' href='get_package.php?id=$row_item[0]'>Install</a></div>
			  			</div>
			  		</div>";
	  		?>
	  		</div>
	  </div>
	</body>
</html>
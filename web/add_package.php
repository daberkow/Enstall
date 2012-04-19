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
	  
	  
	  <!--		<div id='label'>Description: </div>\
						<div id='label'>Basic Script: <textarea name='Package_Basic_Script' id='Package_Basic_Script'></textarea></div>\
						<div id='label'>Advanced Script: <textarea name='Package_Advanced_Script' id='Package_Advanced_Script'></textarea></div>\
						<input TYPE='submit' id='cmdSubmit' VALUE='Submit' style='margin-left: 80%; width: 100px;'/> -->
	  
	  <form action='./submit.php' method='post'>
		  <div class="row show-grid">
		  		<div class="span8 offset1">
		  			<div class="row-fluid">
		  				<div class="span4"><h2>Add Package</h2></div>
		  			</div>
		  		</div>
		  		<div class="span10 offset1">
		  			<div class="row-fluid">
		  				<div class="span3"><h4>Package Title</h4></div>
		  				<div class="span8"><input style='width: 400px;' class='input_box' type='text' name='Package_Title' id='Package_Title'/></div>
		  			</div>
		  			<div class="row-fluid">
		  				<div class="span3"><h4>Package Description</h4></div>
		  				<div class="span8"><textarea style='height: 100px; width: 400px;' name='Package_Description' id='Package_Description'></textarea></div>
		  			</div>
		  			<div class="row-fluid">
		  				<div class="span3"><h4>Basic Package Script</h4></div>
		  				<div class="span8"><textarea style='height: 150px; width: 400px;' name='Package_Basic_Script' id='Package_Basic_Script'></textarea></div>
		  			</div>
		  			<div class="row-fluid">
		  				<div class="span3"><h4>Advanced Package Script</h4></div>
		  				<div class="span8"><textarea style='height: 150px; width: 400px;' name='Package_Advanced_Script' id='Package_Advanced_Script'></textarea></div>
		  			</div>
		  			<div class="row">
		  				<div class="span3 offset6"><input TYPE='submit' id='cmdSubmit' VALUE='Submit' style='width: 100px;'/></div>
		  			</div>
		  		</div>
		  </div>
	  </form>
	</body>
</html>
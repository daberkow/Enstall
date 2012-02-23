<?PHP 
	include("../corepage.php");
	
	if (!Enstall_Authentication::isAuthenticated())
	{
		header("location: ../index.php");
	}
?>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../style.css"/>
		<link rel="stylesheet" type="text/css" href="./style.css"/>
		<script src="../jquery-1.6.2.min.js"></script>
		<script>
			function add_controls() {
				var data = "";
				switch( $("#Package_Type").val() )
				{
					case "2":
						//software pacakge
						data = "<div id='label' style='height: 25px;'>Title: <input class='input_box' type='text' name='Pacakge_Title' id='Pacakge_Title'/></div>\
						<div id='label'>Description: <textarea name='Package_Description' id='Package_Description'></textarea></div>\
						<div id='label'>Basic Script: <textarea name='Package_Basic_Script' id='Package_Basic_Script'></textarea></div>\
						<div id='label'>Advanced Script: <textarea name='Package_Advanced_Script' id='Package_Advanced_Script'></textarea></div>\
						<input TYPE='submit' id='cmdSubmit' VALUE='Submit' style='margin-left: 80%; width: 100px;'/>";
						break;
					case "3":
						data = "";
						break;
					default:
						data = "";
				}
				
				$("#options").html(data);

			}
		</script>
	</head>
	<body onload="add_controls()">
		<div id='main_page'>
			<?PHP 
			
				php_draw::page_header();
				php_draw::page_contentarea_open();
			?>
			<h3>Add a Package</h3>
			<div id='formz'>
				<form action='./submit.php' method='post'>
					<div id='selection_label' style='margin: 0 10 10 0; display: inline;'> Package Type: </div> 
					<select name='Package_Type' id='Package_Type' onchange="add_controls()">
						<option value=0 selected='selected'>Select a Package</option>
						<option value=1>----------------</option>
						<option value=2>Software Package</option>
						<!--<option value=3>Printer</option>-->
					</select>
				<div id="options" style='margin: 10 0 0 0;'></div>
				</form>
			</div>
			<?PHP	
				
				php_draw::page_contentarea_close();				
				php_draw::page_footer();
				
				
			?>
		</div>
	</body>
</html>
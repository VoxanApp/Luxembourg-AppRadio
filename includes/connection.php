<?php
    error_reporting(0);
    ob_start();
    session_start();
    $is_demo_user = false; 	
 	
 	header("Content-Type: text/html;charset=UTF-8");
	
	DEFINE ('DB_USER', "u814602478_radio");
	DEFINE ('DB_PASSWORD', "Demo@#123");
	DEFINE ('DB_HOST', "localhost"); //host name depends on server
	DEFINE ('DB_NAME', "u814602478_radio");

	
	$mysqli =mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);
	
    	$conn = new mysqli(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);

	if ($mysqli->connect_errno) 
	{
    	echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
	}

	mysqli_query($mysqli,"SET NAMES 'utf8'");
	
	//Settings
	$setting_qry="SELECT * FROM tbl_settings where id='1'";
	$setting_result=mysqli_query($mysqli,$setting_qry);
    	$settings_details=mysqli_fetch_assoc($setting_result);
    
    	define("FCM_SERVER_KEY",$settings_details['fcm_server_key']);
 
?> 
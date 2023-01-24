<?php 
 
    //getting database connection
    require_once 'includes/connection.php';
  	include("includes/function.php");
  	include("includes/data.php");
  	$settings = getById("tbl_settings","1");
    
    $imagepath = getBaseUrl();
    
    //array to show the response
    $response = array();
    $key_get = $_GET['key'];
    $getChannelsByCat = $_GET['getChannelsByCat'];
    $slider = $_GET['slider'];
    $byid = $_GET['byid'];
    $get_latest = $_GET['latest'];
    $get_mostview = $_GET['mostview'];
    
    if(isset($_GET['do']) == "verify"){
    $code = $_GET['pcode'];
    $code = trim($code);
    verify_envato_purchase_code($code);
    }
    
            
    
  if($key_get == $settings['api_key']){  
    
    if(isset($_GET['apicall'])){
		
		switch($_GET['apicall']){
		    
		    case 'settings':
            $query = "SELECT `ad_status`, `inter_clicks`, `ad_network`, `admob_small_banner`, `admob_medium_banner`, `admob_inter`, `admob_native`, `applovin_small_banner`, `applovin_medium_banner`, `applovin_inter`, `applovin_native` FROM `tbl_settings` WHERE 1";
            
            $stmt = $mysqli->prepare($query);
            $stmt->execute();
            $stmt->bind_result($ad_status, $inter_clicks, $ad_network, $admob_small_banner, $admob_medium_banner, $admob_inter, $admob_native, $applovin_small_banner, $applovin_medium_banner, $applovin_inter, $applovin_native);
                
            while($stmt->fetch()){
            $data = array(); 
            $data['ad_status'] = $ad_status; 
            $data['inter_clicks'] = $inter_clicks; 
            $data['ad_network'] = $ad_network; 
            $data['admob_small_banner'] = $admob_small_banner; 
            $data['admob_medium_banner'] = $admob_medium_banner; 
            $data['admob_inter'] = $admob_inter; 
            $data['admob_native'] = $admob_native; 
            $data['applovin_small_banner'] = $applovin_small_banner; 
            $data['applovin_medium_banner'] = $applovin_medium_banner; 
            $data['applovin_inter'] = $applovin_inter; 
            $data['applovin_native'] = $applovin_native; 
            array_push($response, $data);
            }
                
            break;
            
        case 'category':
                if(isset($get_latest)){ 
                    $query = "SELECT
             cat_id, category_name, category_image FROM tbl_category WHERE status = '1' ORDER BY cat_id DESC LIMIT $get_latest";
                }
                else{
                    $query = "SELECT
             cat_id, category_name, category_image FROM tbl_category WHERE status = '1' ORDER BY cat_id";
                }
                $stmt = $mysqli->prepare($query);
                $stmt->execute();
                $stmt->bind_result($cat_id, $category_name,$category_image);
                
                while($stmt->fetch()){
                    $data = array(); 
                    $data['cat_id'] = $cat_id; 
                    $data['category_name'] = $category_name; 
                    $data['category_image'] = $imagepath.'images/'.$category_image; 
                    array_push($response, $data);
                }
                
            break;
            
            case 'channels':
           
            if(isset($get_latest)){
                $query = "SELECT
             id, cid, channel_name, frequency, description, channel_icon, channel_banner, category_name, source_url, views, channel_status, slider_status
            FROM tbl_channels
                LEFT JOIN
                    tbl_category c
                        ON cid = c.cat_id WHERE
                channel_status = '1' ORDER BY id DESC LIMIT $get_latest";
                
            } else if(isset($get_mostview)){
                $query = "SELECT
             id, cid, channel_name, frequency, description, channel_icon, channel_banner, category_name, source_url, views, channel_status, slider_status
            FROM tbl_channels
                LEFT JOIN
                    tbl_category c
                        ON cid = c.cat_id WHERE
                channel_status = '1' ORDER BY views DESC LIMIT $get_mostview";
                
            } else if(isset($byid)){
                $query = "SELECT
             id, cid, channel_name, frequency, description, channel_icon, channel_banner, category_name, source_url, views, channel_status, slider_status
            FROM tbl_channels
                LEFT JOIN
                    tbl_category c
                        ON cid = c.cat_id WHERE
                FIND_IN_SET(`id`, '$byid') AND channel_status = '1'";
                
            } else if(isset($slider)){
                $query = "SELECT
             id, cid, channel_name, frequency, description, channel_icon, channel_banner, category_name, source_url, views, channel_status, slider_status
            FROM tbl_channels
                LEFT JOIN
                    tbl_category c
                        ON cid = c.cat_id WHERE
                slider_status = '1' AND channel_status = '1' ORDER BY id DESC LIMIT $slider";
                
            } else if(isset($getChannelsByCat)){
                $query = "SELECT
             id, cid, channel_name, frequency, description, channel_icon, channel_banner, category_name, source_url, views, channel_status, slider_status
            FROM tbl_channels
                LEFT JOIN
                    tbl_category c
                        ON cid = c.cat_id WHERE
                c.cat_id = ".$getChannelsByCat." AND channel_status = '1' ORDER BY id";
            }
                
                $stmt = $mysqli->prepare($query);
                $stmt->execute();
                $stmt->bind_result($id, $cid, $channel_name, $frequency, $description, $channel_icon, $channel_banner, $category_name, $source_url, $views, $channel_status, $slider_status);
                
                while($stmt->fetch()){
                    $data = array(); 
                    $data['id'] = $id; 
                    $data['cid'] = $cid; 
                    $data['channel_name'] = $channel_name; 
                    $data['frequency'] = $frequency; 
                    $data['description'] = $description; 
                    $data['channel_icon'] = $imagepath.'images/'.$channel_icon; 
                    $data['channel_banner'] = $imagepath.'images/'.$channel_banner; 
                    $data['category_name'] = $category_name;
                    $data['source_url'] = $source_url;
                    $data['views'] = $views;
                    $data['channel_status'] = $channel_status;
                    $data['slider_status'] = $slider_status;
                    array_push($response, $data);
                }
            break;
            
            case 'addToken' :
        if($_SERVER['REQUEST_METHOD']=='POST'){
            $token = $_POST['token'];
            $new_token = $_POST['new_token'];
            
            $query = "SELECT token FROM tbl_users WHERE token = '".$token."'";
      
            $result = mysqli_query($mysqli, $query);
            $row = mysqli_num_rows($result);

            if ($row > 0)
            {
            $Sql_Query = "UPDATE `tbl_users` SET `token` = '".$new_token."' WHERE `tbl_users`.`token` = '".$token."'";
			$message = 'Token Updated Successfully';
            } else {
            $Sql_Query = "INSERT INTO tbl_users (token) VALUES ('".$new_token."')";
            $message = 'New Token Added Successfully';
            }

                if(mysqli_query($mysqli,$Sql_Query))
                { 
                $data['error'] = false; 
                $data['message'] = $message;
                }
                else
                {
                $data['error'] = true; 
				$data['message'] = 'Token Update Failed';
                }
                
                }
                array_push($response, $data);
                mysqli_close($mysqli);
                break;
            
            case 'updateViews' :
        if($_SERVER['REQUEST_METHOD']=='POST'){
            $viewsget = getById("tbl_channels",$_POST['id']);
            $views = $viewsget['views'];
            $views++;
            $id = $_POST['id'];
 
            $Sql_Query = "UPDATE tbl_channels SET views = '$views' WHERE id = $id";
            
            if(mysqli_query($mysqli,$Sql_Query))
                { 
                $data['error'] = false; 
                $data['message'] = 'Record Updated Successfully';
                }
                else
                {
                $data['error'] = true; 
				$data['message'] = 'View Update Failed';
                }
                
                }
                array_push($response, $data);
                mysqli_close($mysqli);
                break;
                
                case 'report' :
        if($_SERVER['REQUEST_METHOD']=='POST'){
            $id = $_POST['id'];
            $radio_id = $_POST['radio_id'];
            $radio_name = $_POST['radio_name'];
            $report = $_POST['report'];
            
            
            $Sql_Query = "INSERT INTO tbl_report (id, radio_id, radio_name, report) VALUES ('".$id."', '".$radio_id."', '".$radio_name."', '".$report."')";

                if(mysqli_query($mysqli,$Sql_Query))
                { 
                $data['error'] = false; 
                $data['message'] = 'Record Added';
                }
                else
                {
                $data['error'] = true; 
				$data['message'] = 'Record Add Failed';
                }
                
                }
                array_push($response, $data);
                mysqli_close($mysqli);
                break;
            
				default: 
				$response['error'] = true; 
				$response['message'] = 'Invalid Operation Called';
		}
		
	}
	
 }
        else{
            	$response['error'] = true; 
				$response['message'] = 'Invalid Api Key';
        }
 
    function getBaseURL2(){
        $url  = isset($_SERVER['HTTPS']) ? 'https://' : 'http://';
        $url .= $_SERVER['SERVER_NAME'];
        $url .= $_SERVER['REQUEST_URI'];
        return dirname($url) . '/';
    }
    
 if(isset($_GET['apicall'])){
    header('Content-Type: application/json; charset=utf-8');
    echo $response= str_replace('\\/', '/', json_encode($response,JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
    }
    
?>
   
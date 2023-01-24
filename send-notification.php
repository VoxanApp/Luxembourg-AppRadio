<?php
include("includes/header.php");

  require("includes/function.php");
  require("alert/alert.php");
  
  $data_qry="SELECT * FROM tbl_category ORDER BY category_name";
  $data_result=mysqli_query($mysqli,$data_qry);
  
  $data_qry2="SELECT * FROM tbl_channels ORDER BY channel_name";
  $data_result2=mysqli_query($mysqli,$data_qry2);
  
  $token_query = "SELECT token FROM tbl_users";
  $token_result = $mysqli->query($token_query);

    $reg_tokens = array();
    while($row = $token_result->fetch_array())
    {
    $reg_tokens[] = $row['token']; 
    }
    
if(isset($_POST['submit'])) {

if(!$is_demo_user){      
      if($_POST['external_link']!=""){
        if(!$is_demo_user){
            $external_link = $_POST['external_link'];
        } else { 
            $external_link = 'https://andromob.co.in/about';
        }
     } else {
        $external_link = false;
     } 
  
  if($_POST['cat_id']!="") {
         $cat_id = $_POST['cat_id'];
     } else {
        $cat_id = false;
     } 
     
     if($_POST['channel_id']!="") {
         $channel_id = $_POST['channel_id'];
     } else {
        $channel_id = false;
     } 
     
  $url = 'https://fcm.googleapis.com/fcm/send';
  
  $headers = array (
    'Authorization:key=' . FCM_SERVER_KEY,
    'Content-Type:application/json'
  );
  
  if($_FILES['big_picture']['name']!="")
    {   

        $big_picture=rand(0,99999)."_".$_FILES['big_picture']['name'];
        $tpath2='images/notification/'.$big_picture;
        move_uploaded_file($_FILES["big_picture"]["tmp_name"], $tpath2);
 
        $protocol = ((!empty($_SERVER['HTTPS']) && $_SERVER['HTTPS'] != 'off') || $_SERVER['SERVER_PORT'] == 443 || $_SERVER['HTTP_X_FORWARDED_PORT'] == 443) ? "https://" : "http://";

        $file_path = $protocol.$_SERVER['SERVER_NAME'] . dirname($_SERVER['REQUEST_URI']).'/images/notification/'.$big_picture;
          
       $dataPayload = [
    'title' => $_POST['notification_title'],
    'message' => $_POST['notification_msg'],
    "channel_id"=>$channel_id,
    "cat_id"=>$cat_id,
    "external_link"=>$external_link,
    "big_picture" => $file_path,
  ];

  // Create the api body
  $apiBody = [
    'channelId' => "notify",
    'android_channel_id' => "notify",
    'channel_id' => "notify", 
    'priority'=>'high',
    'data' => $dataPayload,
    //'to' => '/topics/mytargettopic'
	'registration_ids' 	=> $reg_tokens,
    /*'to' => 'abcd'*/
  ];

  // Initialize curl with the prepared headers and body
  $ch = curl_init();
  curl_setopt ($ch, CURLOPT_URL, $url);
  curl_setopt ($ch, CURLOPT_POST, true);
  curl_setopt ($ch, CURLOPT_HTTPHEADER, $headers);
  curl_setopt ($ch, CURLOPT_RETURNTRANSFER, true);
  curl_setopt ($ch, CURLOPT_POSTFIELDS, json_encode($apiBody,JSON_UNESCAPED_SLASHES));

  // Execute call and save result
  $result = curl_exec($ch);
  print( json_encode($apiBody,JSON_UNESCAPED_SLASHES));
  curl_close($ch);
  
    }
    else
    {
        $dataPayload = [
    'title' => $_POST['notification_title'],
    'message' => $_POST['notification_msg'],
    "channel_id"=>$channel_id,
    "cat_id"=>$cat_id,
    "external_link"=>$external_link,
  ];

  // Create the api body
  $apiBody = [
    'channelId' => "notify",
    'android_channel_id' => "notify",
    'channel_id' => "notify",  
    'priority'=>'high',
    'data' => $dataPayload,
    //'to' => '/topics/mytargettopic'
	'registration_ids' 	=> $reg_tokens,
    /*'to' => 'abcd'*/
  ];

  // Initialize curl with the prepared headers and body
  $ch = curl_init();
  curl_setopt ($ch, CURLOPT_URL, $url);
  curl_setopt ($ch, CURLOPT_POST, true);
  curl_setopt ($ch, CURLOPT_HTTPHEADER, $headers);
  curl_setopt ($ch, CURLOPT_RETURNTRANSFER, true);
  curl_setopt ($ch, CURLOPT_POSTFIELDS, json_encode($apiBody,JSON_UNESCAPED_SLASHES));

  // Execute call and save result
  $result = curl_exec($ch);
  print( json_encode($apiBody));
  curl_close($ch);

  }
  $_SESSION['msg']="17";
  header( "Location:send-notification");
  exit; 
} else{
  $_SESSION['msg']="20";
  header( "Location:send-notification");
  exit; 
 }

    
}
  
?>

<div class="main-content">
        <section class="section">
          <div class="section-body">
            
            <div class="row">
             <div class="col-12">
                <div class="card">
                  <div class="card-header">
                    	<legend class="hidden-first">Send Push Notification</legend>
                  </div>
                  <div class="card-body">
                      
                <div class="col-md-12 col-sm-12">
                <?php if(isset($_SESSION['msg'])){?> 
                 <div class="alert alert-primary alert-dismissible" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                  <?php echo $alert_msg[$_SESSION['msg']] ; ?></a> </div>
                <?php unset($_SESSION['msg']);}?> 
              </div>
				 <form action="" name="postform" method="post" class="form form-horizontal" enctype="multipart/form-data">
					<fieldset>
							
						<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Titile :-</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="notification_title" id="notification_title" value="" required/>
                      </div>
                    </div>
                    
                    
                    	<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Message :-</label>
                      <div class="col-sm-12 col-md-7">
                        <textarea class="form-control" style="height: 140px;" name="notification_msg" id="notification_msg" required></textarea>
                      </div>
                    </div>
                    
                    	<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Image :- (Optional) </label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="file" name="big_picture" value="fileupload" id="fileupload"/>
                      </div>
                    </div>
                    
                    <div class="link_block">
                        
                    	<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">External Link :- (Optional)</label>
                      <div class="col-sm-12 col-md-7">
                         <input class="form-control" placeholder="https://andromob.co.in" type="text" name="external_link" id="external_link" value=""/>
                      </div>
                    </div>
                    
                    <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Open Radio Channel (Optional)</label>
                      <div class="col-sm-12 col-md-7">
                          <select name="channel_id" class="form-control select2">
                        <option value="">--Select Radio Channel--</option>
                        	<?php
          									while($data_row2=mysqli_fetch_array($data_result2))
          									{
          							?>       
          							<option value="<?php echo $data_row2['id'];?>"><?php echo $data_row2['channel_name'];?></option>	          							 
          							<?php
          								}
          							?> 
                      </select>
                      <p style="margin-top:5px;">(To open directly Radio Channel when click on notification)</p>
                      </div>
                    </div>
                    
                    	<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Open Category (Optional)</label>
                      <div class="col-sm-12 col-md-7">
                          <select name="cat_id" class="form-control select2">
                        <option value="">--Select Category--</option>
                        	<?php
          									while($data_row=mysqli_fetch_array($data_result))
          									{
          							?>       
          							<option value="<?php echo $data_row['cat_id'];?>"><?php echo $data_row['category_name'];?></option>	          							 
          							<?php
          								}
          							?> 
                      </select>
                      <p style="margin-top:5px;">(To open directly selected category when click on notification)</p>
                      </div>
                    </div>
                    
                    </div>
							
					<div class="card-footer text-right">
					<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3"></label>
                      <div class="col-sm-12 col-md-7">
                        <button type="submit" name="submit" class="btn btn-lg btn-primary">Send</button>
                      </div>
                    </div>
                     </div>
					</form>
					
					</div>
					</div>
					</div>
					</div>
					</div>
					</section>
        
<?php include("includes/footer.php");?>       
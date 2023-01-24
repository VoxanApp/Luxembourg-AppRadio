<?php include("includes/header.php");

  require("includes/function.php");
  require("alert/alert.php");

  require_once("thumbnail_images.class.php");
  $qry="SELECT * FROM tbl_settings where id='1'";
  $result=mysqli_query($mysqli,$qry);
  $settings_row=mysqli_fetch_assoc($result);
  $tab = $_GET['tab'];
  
   if(isset($_POST['admin_submit']))
   {
    if(!$is_demo_user){  
    $app_logo=rand(0,99999)."_".$_FILES['app_logo']['name'];
    $pic1=$_FILES['app_logo']['tmp_name'];
    $tpath1='images/'.$app_logo;      
    copy($pic1,$tpath1);
    
    if($_FILES['app_logo']['name']!="")
    {
    $data = array(
    'app_name'  =>  $_POST['app_name'],
    'name'  =>  $_POST['name'],
    'username'  =>  $_POST['username'],
    'password'  =>  $_POST['password'],
    'api_key'  =>  $_POST['api_key'],
    'app_logo'  =>  $app_logo
    );
    }
    else
    {
    $data = array(
    'app_name'  =>  $_POST['app_name'],
    'name'  =>  $_POST['name'],
    'username'  =>  $_POST['username'],
    'password'  =>  $_POST['password'],
    'api_key'  =>  $_POST['api_key']
    );
    } 

    $settings_edit=Update('tbl_settings', $data, "WHERE id = '1'");
    $_SESSION['msg']="11";
    header( "Location:settings?tab=admin");
    exit;
 
  }
  else{
        $_SESSION['msg']="20";
        header( "Location:settings?tab=admin");
        exit; 
    }
   }
   
   
    if(isset($_POST['notification_submit'])) {
      if(!$is_demo_user){

          $data = array(
                'fcm_server_key' => $_POST['fcm_server_key'],
                  );


          $settings_edit=Update('tbl_settings', $data, "WHERE id = '1'");

          $_SESSION['msg']="11";
          header( "Location:settings?tab=notification");
          exit;
      }
      else{
          $_SESSION['msg']="20";
          header( "Location:settings?tab=notification");
          exit;
      }
 
  } 
  
  
  if(isset($_POST['ads_submit'])) {
      if(!$is_demo_user){

          $data = array(
                'ad_status' => $_POST['ad_status'],
                'inter_clicks' => $_POST['inter_clicks'],
                'ad_network' => $_POST['ad_network'],
                'admob_small_banner' => $_POST['admob_small_banner'],
                'admob_medium_banner' => $_POST['admob_medium_banner'],
                'admob_inter' => $_POST['admob_inter'],
                'admob_native' => $_POST['admob_native'],
                'applovin_small_banner' => $_POST['applovin_small_banner'],
                'applovin_medium_banner' => $_POST['applovin_medium_banner'],
                'applovin_inter' => $_POST['applovin_inter'],
                'applovin_native' => $_POST['applovin_native']
                  );


          $settings_edit=Update('tbl_settings', $data, "WHERE id = '1'");

          $_SESSION['msg']="11";
          header( "Location:settings?tab=ads");
          exit;
      }
      else{
          $_SESSION['msg']="20";
          header( "Location:settings?tab=ads");
          exit;
      }
 
  } 
  
    
?>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>

<script type="text/javascript">
$(document).ready(function(e) {
           $("#ad_network").change(function(){
          
           var type=$("#ad_network").val();
              
             if(type=="admob")
              {
                 
                 $("#admob_display").show();
                 $("#applovin_display").hide();
              }
              else if(type=="applovin")
              {   
                
                 $("#applovin_display").show();
                 $("#admob_display").hide();
 
              }
              
         });
        });
        
</script>
<div class="main-content">
        <section class="section">
          <div class="section-body">
            
            <div class="row">
             <div class="col-12">
                <div class="card">
                  <div class="card-header">
                    	<legend class="hidden-first">Settings</legend>
                  </div>
                  <div class="card-body">
                <div class="col-md-12 col-sm-12">
                <?php if(isset($_SESSION['msg'])){?> 
                 <div class="alert alert-primary alert-dismissible" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                  <?php echo $alert_msg[$_SESSION['msg']] ; ?></a> </div>
                <?php unset($_SESSION['msg']);}?> 
              </div>
              
                 <div class="card-body">
                    <ul class="nav nav-tabs" id="myTab2" role="tablist">
                      <li class="nav-item">
                        <a class="nav-link <?php if($tab == "admin") {?>active<?php }else{?><?php }?>" id="home-tab2" data-toggle="tab" href="#admin" role="tab"
                          aria-controls="home" aria-selected="true">Admin</a>
                      </li>
                     
                      <li class="nav-item">
                        <a class="nav-link <?php if($tab == "notification") {?>active<?php }else{?><?php }?>" id="contact-tab2" data-toggle="tab" href="#notification" role="tab"
                          aria-controls="contact" aria-selected="false">Notification</a>
                      </li>
                     
                      <li class="nav-item">
                        <a class="nav-link <?php if($tab == "ads") {?>active<?php }else{?><?php }?>" id="contact-tab2" data-toggle="tab" href="#ads" role="tab"
                          aria-controls="contact" aria-selected="false">Ads Settings</a>
                      </li>
                     
                    </ul>
                    <div class="tab-content tab-bordered" id="myTab3Content">
                      <div class="tab-pane fade <?php if($tab == "admin") {?>show active<?php }else{?><?php }?>" id="admin" role="tabpanel" aria-labelledby="home-tab2">
                        <form action="" name="settings" method="post" class="form form-horizontal" enctype="multipart/form-data">
					<fieldset>
						<input  type="hidden" name="id" value="<?php echo $_GET['id'];?>" />
						<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">App Name</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="app_name" id="app_name" value="<?php echo $settings_row['app_name'];?>" />
                      </div>
                    </div>
				
                    <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Name</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="name" id="name" value="<?php echo $settings_row['name'];?>" />
                      </div>
                    </div>
                    
                    <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">username</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="username" id="username" value="<?php echo $settings_row['username'];?>" />
                      </div>
                    </div>
                    
                     <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Password</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="password" name="password" id="password" value="<?php echo $settings_row['password'];?>" />
                      </div>
                    </div>
                    
                    <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Api Key</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="password" name="api_key" id="api_key" value="<?php echo $settings_row['api_key'];?>" />
                      </div>
                    </div>
                    
                     <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">App Icon</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="file" name="app_logo" value="fileupload" id="fileupload"/>
                      </div>
                    </div>
                    
                     <?php if($settings_row['app_logo']!="") {?>
                            <div class="form-group row mb-4">
                           <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Icon Preview</label>
                      <div class="col-sm-12 col-md-7">
                            <img style="max-width: 60%; object-fit: cover;" src="images/<?php echo $settings_row['app_logo'];?>" alt="">
                    </div>
                    </div>
                    
                    <div class="card-footer text-right">
					<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3"></label>
                      <div class="col-sm-12 col-md-7">
                        <button type="submit" name="admin_submit" class="btn btn-lg btn-primary">Submit</button>
                      </div>
                    </div>
                     </div>
                          <?php } ?>
							
                     </fieldset>
					</form>
                      </div>
                      <div class="tab-pane fade <?php if($tab == "notification") {?>show active<?php }else{?><?php }?>" id="notification" role="tabpanel" aria-labelledby="profile-tab2">
                       <form action="" name="settings" method="post" class="form form-horizontal" enctype="multipart/form-data">
					<fieldset>
						<input  type="hidden" name="id" value="<?php echo $_GET['id'];?>" />
					
                    <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">FCM Server Key</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="password" name="fcm_server_key" id="fcm_server_key" value="<?php echo $settings_row['fcm_server_key'];?>" />
                      </div>
                    </div>
                    
                    
                    <div class="card-footer text-right">
					<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3"></label>
                      <div class="col-sm-12 col-md-7">
                        <button type="submit" name="notification_submit" class="btn btn-lg btn-primary">Submit</button>
                      </div>
                    </div>
                     </div>
							
                     </fieldset>
					</form>
                      </div>
                    
                    
                      <div class="tab-pane fade <?php if($tab == "ads") {?>show active<?php }else{?><?php }?>" id="ads" role="tabpanel" aria-labelledby="profile-tab2">
                       <form action="" name="settings" method="post" class="form form-horizontal" enctype="multipart/form-data">
					<fieldset>
						
						<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Ad Status</label>
                       <div class="col-sm-12 col-md-7">
                          <select name="ad_status" id="ad_status" class="form-control select2" style="width: 100%;" required>
                            <option value="">--Select--</option>
                             <option value="on" <?php if($settings_row['ad_status'] == "on") {?>selected<?php }else{?><?php }?> >ON</option>
                            <option value="off" <?php if($settings_row['ad_status'] == "off") {?>selected<?php }else{?><?php }?> >OFF</option>
                      </select>
                      </div>
                    </div>
						
                    <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Interstitial Ad Interval</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="inter_clicks" id="inter_clicks" value="<?php echo $settings_row['inter_clicks'];?>" />
                      </div>
                    </div>
                  	
						<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Select Ad Network Type</label>
                       <div class="col-sm-12 col-md-7">
                          <select name="ad_network" id="ad_network" class="form-control select2" style="width: 100%;" required>
                            <option value="">--Select Type--</option>
                             <option value="admob" <?php if($settings_row['ad_network'] == "admob") {?>selected<?php }else{?><?php }?> >Admob OR Admob + Facebook </option>
                            <option value="applovin" <?php if($settings_row['ad_network'] == "applovin") {?>selected<?php }else{?><?php }?> >AppLovin's Max</option>
                      </select>
                      </div>
                    </div>
                    
                     <div id="admob_display" <?php if($settings_row['ad_network'] == "admob") {?><?php }else{?>style="display:none;"<?php }?> >
                         
                      <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Admob Banner Id</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="admob_small_banner" id="admob_small_banner" value="<?php echo $settings_row['admob_small_banner'];?>" />
                      </div>
                    </div>
                    
                    <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Admob Medium Banner Id</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="admob_medium_banner" id="admob_medium_banner" value="<?php echo $settings_row['admob_medium_banner'];?>" />
                      </div>
                    </div>
                    
                    <div class="form-group row mb-4">  
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Admob Interstitial Id</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="admob_inter" id="admob_inter" value="<?php echo $settings_row['admob_inter'];?>" />
                      </div>
                      </div>
                      
                      <div class="form-group row mb-4">  
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Admob Native Id</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="admob_native" id="admob_native" value="<?php echo $settings_row['admob_native'];?>" />
                      </div>
                      </div>
                      
                    </div>
                    
                    
                    <div id="applovin_display" <?php if($settings_row['ad_network'] == "applovin") {?><?php }else{?>style="display:none;"<?php }?>>
                         
                      <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">AppLovin Small Banner Id</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="applovin_small_banner" id="applovin_small_banner" value="<?php echo $settings_row['applovin_small_banner'];?>" />
                      </div>
                    </div>
                    
                    <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">AppLovin Medium Banner Id</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="applovin_medium_banner" id="applovin_medium_banner" value="<?php echo $settings_row['applovin_medium_banner'];?>" />
                      </div>
                    </div>
                    
                    <div class="form-group row mb-4">  
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">AppLovin Interstitial Id</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="applovin_inter" id="applovin_inter" value="<?php echo $settings_row['applovin_inter'];?>" />
                      </div>
                      </div>
                      
                      <div class="form-group row mb-4">  
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">AppLovin Native Id</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="applovin_native" id="applovin_native" value="<?php echo $settings_row['applovin_native'];?>" />
                      </div>
                      </div>
                      
                    </div>
                    
                    <div class="card-footer text-right">
					<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3"></label>
                      <div class="col-sm-12 col-md-7">
                        <button type="submit" name="ads_submit" class="btn btn-lg btn-primary">Submit</button>
                      </div>
                    </div>
                     </div>
							
                     </fieldset>
					</form>
                      </div>
                    
                    </div>
                  </div>
				 
					
					</div>
					</div>
					</div>
					</div>
					</div>
					</section>
        
<?php include("includes/footer.php");?>       

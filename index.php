<?php
    include("includes/connection.php");
	include("includes/data.php");
	require("alert/alert.php");
	$settings = getById("tbl_settings","1");
	if (file_exists('install/index.php')) {
    	header("location:install/");
    	die();
    	}
    
	if(isset($_SESSION['admin_name']))
	{
		header("Location:home");
		return false;
        exit();
	}
?>
<!DOCTYPE html>
<html lang="en">
<head>
	 <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><?php echo $settings['app_name']?></title>
  <!-- General CSS Files -->
  <link rel="stylesheet" href="assets/css/app.min.css">
  <link rel="stylesheet" href="assets/bundles/chocolat/dist/css/chocolat.css">
  <!-- Template CSS -->
  <link rel="stylesheet" href="assets/css/style.css">
  <link rel="stylesheet" href="assets/css/components.css">
  <!-- Custom style CSS -->
  <link rel="stylesheet" href="assets/css/custom.css">
  <link rel='shortcut icon' type='image/x-icon' href='assets/img/favicon.ico' />
		
</head>

<style>
    
.centerit {
    right: 50%;
    bottom: 50%;
    transform: translate(50%,50%);
    position: absolute;
}
</style>

<body>
    
        <div class="col-sm-6 col-md-4 col-md-offset-4 centerit">
	    <div class="card" id="sample-login">
                  <form action="login" method="post" name="login">
                    <div class="card-header"style="text-align:center; display:block;font-size: x-large; color: #1b2c33;">
                      <h3">Login</h3>
                    </div>
                    <div class="card-body pb-0">
                    <div class="col-md-12 col-sm-12">
                <?php if(isset($_SESSION['msg'])){?> 
                 <div class="alert alert-primary alert-dismissible" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                  <?php echo $alert_msg[$_SESSION['msg']] ; ?></a> </div>
                <?php unset($_SESSION['msg']);}?> 
              </div>
                      <h3 style="text-align:center;"><?php echo $settings['app_name']?></h3>
                      <div class="form-group">
                        <label>Username</label>
                        <div class="input-group">
                          <div class="input-group-prepend">
                            <div class="input-group-text">
                              <i class="fas fa-envelope"></i>
                            </div>
                          </div>
                         <input type="text" class="form-control" placeholder="Username" name="username" required autofocus>
                        </div>
                      </div>
                      <div class="form-group">
                        <label>Password</label>
                        <div class="input-group">
                          <div class="input-group-prepend">
                            <div class="input-group-text">
                              <i class="fas fa-lock"></i>
                            </div>
                          </div>
                          <input type="password" class="form-control" placeholder="Password" name="password" required>
                        </div>
                      </div>
                    <div class="card-footer text-right">
                      <button type="submit" class="btn btn-primary">Login</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
            </div>
	
	<script src="assets/js/app.min.js"></script>
  <!-- JS Libraies -->
  <script src="assets/bundles/prism/prism.js"></script>
  <!-- Page Specific JS File -->
  <!-- Template JS File -->
  <script src="assets/js/scripts.js"></script>
  <!-- Custom JS File -->
  <script src="assets/js/custom.js"></script>
</body>
</html>
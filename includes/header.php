<?php
    include("includes/connection.php");
	include("includes/session_check.php");
	$currentURL = $_SERVER["SCRIPT_NAME"];
    $parts = Explode('/', $currentURL);
    $currentURL = $parts[count($parts) - 1];  
    	
    include("includes/data.php");
    $settings = getById("tbl_settings","1");?>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no" name="viewport">
  <title><?php echo $settings['app_name']?></title>
  <!-- General CSS Files -->
  <link rel="stylesheet" href="assets/css/app.min.css">
  <link rel="stylesheet" href="assets/bundles/datatables/datatables.min.css">
  <link rel="stylesheet" href="assets/bundles/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
   <link rel="stylesheet" href="assets/bundles/bootstrap-daterangepicker/daterangepicker.css">
  <link rel="stylesheet" href="assets/bundles/bootstrap-colorpicker/dist/css/bootstrap-colorpicker.min.css">
  <link rel="stylesheet" href="assets/bundles/select2/dist/css/select2.min.css">
  <link rel="stylesheet" href="assets/bundles/jquery-selectric/selectric.css">
  <link rel="stylesheet" href="assets/bundles/bootstrap-timepicker/css/bootstrap-timepicker.min.css">
  <link rel="stylesheet" href="assets/bundles/bootstrap-tagsinput/dist/bootstrap-tagsinput.css">
   <link rel="stylesheet" href="assets/bundles/summernote/summernote-bs4.css">
  <link rel="stylesheet" href="assets/bundles/codemirror/lib/codemirror.css">
  <link rel="stylesheet" href="assets/bundles/codemirror/theme/duotone-dark.css">
  <link rel="stylesheet" href="assets/bundles/chocolat/dist/css/chocolat.css">
  <link rel="stylesheet" href="assets/bundles/pretty-checkbox/pretty-checkbox.min.css">
  <!-- Template CSS -->
  <link rel="stylesheet" href="assets/css/style.css">
  <link rel="stylesheet" href="assets/css/components.css">
  <!-- Custom style CSS -->
  <link rel="stylesheet" href="assets/css/custom.css">
</head>

<style type="text/css">
    .imgGallery img {
      padding: 8px;
      max-width: 100px;
    }    
</style>

<body>
   <div id="app">
    <div class="main-wrapper main-wrapper-1">
      <div class="navbar-bg"></div>
      <nav class="navbar navbar-expand-lg main-navbar sticky">
        <div class="form-inline mr-auto">
          <ul class="navbar-nav mr-3">
            <li><a href="#" data-toggle="sidebar" class="nav-link nav-link-lg
									collapse-btn"> <i data-feather="align-justify"></i></a></li>
            
            <li>
            </li>
          </ul>
        </div>
        <ul class="navbar-nav navbar-right">
         
          <li class="dropdown"><a href="#" data-toggle="dropdown"
              class="nav-link dropdown-toggle nav-link-lg nav-link-user"> <img alt="image" src="assets/img/user2.png"
                class="user-img-radious-style"> <span class="d-sm-none d-lg-inline-block"></span></a>
            <div class="dropdown-menu dropdown-menu-right pullDown">
              <div class="dropdown-title">Hello <?php echo $settings['name']?></div>
              <a href="settings?tab=admin" class="dropdown-item has-icon"> <i class="fas fa-cog"></i>
                Settings
              </a>
              <div class="dropdown-divider"></div>
              <a href="logout" class="dropdown-item has-icon text-danger"> <i class="fas fa-sign-out-alt"></i>
                Logout
              </a>
            </div>
          </li>
        </ul>
      </nav>
      <div class="main-sidebar sidebar-style-2">
        <aside id="sidebar-wrapper">
          <div class="sidebar-brand">
             
            <a href="index"> 
             <img alt="image" src="images/<?php echo $settings['app_logo']?>" class="header-logo"/> 
             <br>
            <span
                class="logo-name"><?php echo $settings['app_name']?></span>
            </a>
          </div>
          <ul class="sidebar-menu">
            <li <?php if($currentURL=="home.php"){?>class="active"<?php }?>> <a href="home" class="nav-link"><i class="fa fa-home" aria-hidden="true"></i><span>Dashboard</span></a>
        </li>
            
            <li <?php if($currentURL=="manage-category.php" or $currentURL=="add-category.php"){?>class="active"<?php }?>> <a href="manage-category" class="nav-link"><i class="fa fa-sitemap" aria-hidden="true"></i><span>Categories</span></a>
        </li>
        
        
        <li <?php if($currentURL=="manage-channels.php" or $currentURL=="add-channel.php"){?>class="active"<?php }?>> <a href="manage-channels" class="nav-link"><i class="fa fa-microphone" aria-hidden="true"></i><span>Radio Channels</span></a>
        </li>
        
         <li <?php if($currentURL=="manage-slider.php" or $currentURL=="add-slider.php"){?>class="active"<?php }?>> <a href="manage-slider" class="nav-link"><i class="fa fa-sliders-h" aria-hidden="true"></i><span>Home Slider</span></a>
        </li>
        
        <li <?php if($currentURL=="manage-reports.php"){?>class="active"<?php }?>> <a href="manage-reports" class="nav-link"><i class="fa fa-flag" aria-hidden="true"></i><span>Reports</span></a>
        </li>
        
         <li <?php if($currentURL=="send-notification.php"){?>class="active"<?php }?>> <a href="send-notification" class="nav-link"><i class="fa fa-bell" aria-hidden="true"></i><span>Send Notification</span></a>
        </li>
        
        <li <?php if($currentURL=="settings.php"){?>class="active"<?php }?>> <a href="settings?tab=admin" class="nav-link"><i class="fa fa-cog" aria-hidden="true"></i><span>Settings</span></a>
        </li>
          </ul>
        </aside>
      </div>
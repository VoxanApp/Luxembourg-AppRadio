<?php 

    include("includes/header.php");
	require("includes/function.php");
	require("alert/alert.php");

	require_once("thumbnail_images.class.php");
	
	$cat_qry="SELECT * FROM tbl_category ORDER BY category_name";
    $cat_result=mysqli_query($mysqli,$cat_qry);
	
	$img_path = 'images';
    if (!is_dir($img_path)) {
        mkdir($img_path, 0755, true);
    }
	
	if(isset($_POST['submit']) and isset($_GET['add']))
	{
	   
      if(!$is_demo_user){
        $channel_icon=rand(0,99999)."_".$_FILES['channel_icon']['name'];
              $pic1=$_FILES['channel_icon']['tmp_name'];
              $tpath1='images/'.$channel_icon;
              copy($pic1,$tpath1);
              
             if($_FILES['channel_icon']['name']!="")
              {
                $data = array(
                'cid'  =>  $_POST['cid'],
                'channel_name'  =>  $_POST['channel_name'],
                'frequency'  =>  $_POST['frequency'],
                'description'  =>  addslashes(trim($_POST['description'])),
                'channel_icon'  =>  $channel_icon,
                'source_url' =>  $_POST['source_url']
              );
              } else {
                $data = array(
                'cid'  =>  $_POST['cid'],
                'channel_name'  =>  $_POST['channel_name'],
                'frequency'  =>  $_POST['frequency'],
                'description'  =>  addslashes(trim($_POST['description'])),
                'source_url' =>  $_POST['source_url']
              );  
              }

          $qry = Insert('tbl_channels',$data);      

          $_SESSION['msg']="10";
          header( "Location:add-channel.php?add=yes");
          exit;
      }
      else{
          $_SESSION['msg']="20";
          header( "Location:add-channel.php?add=yes");
          exit;
      }	
		
	}
	
	if(isset($_GET['id']))
	{
			 
			$qry="SELECT * FROM tbl_channels where id='".$_GET['id']."'";
			$result=mysqli_query($mysqli,$qry);
			$row=mysqli_fetch_assoc($result);

	}
	if(isset($_POST['submit']) and isset($_POST['id']))
	{
		  if(!$is_demo_user){
    
              $channel_icon=rand(0,99999)."_".$_FILES['channel_icon']['name'];
              $pic1=$_FILES['channel_icon']['tmp_name'];
              $tpath1='images/'.$channel_icon;
              copy($pic1,$tpath1);
              
              if($_FILES['channel_icon']['name']!="")
              {
                $data = array(
                'cid'  =>  $_POST['cid'],
                'channel_name'  =>  $_POST['channel_name'],
                'frequency'  =>  $_POST['frequency'],
                'description'  =>  addslashes(trim($_POST['description'])),
                'channel_icon'  =>  $channel_icon,
                'source_url' =>  $_POST['source_url']
              );
              } else {
                $data = array(
                'cid'  =>  $_POST['cid'],
                'channel_name'  =>  $_POST['channel_name'],
                'frequency'  =>  $_POST['frequency'],
                'description'  =>  addslashes(trim($_POST['description'])),
                'source_url' =>  $_POST['source_url']
              );  
              }
		      
              $data_edit=Update('tbl_channels', $data, "WHERE id = '".$_POST['id']."'");


          $_SESSION['msg']="11"; 
          header( "Location:add-channel.php?id=".$_POST['id']);
          exit;
      }
      else{
          $_SESSION['msg']="20"; 
          header( "Location:add-channel.php?id=".$_POST['id']);
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
                    	<legend class="hidden-first"><?php if(isset($_GET['id'])){?>Edit<?php }else{?>Add<?php }?> Channel</legend>
                  </div>
                  <div class="card-body">
                      
                <div class="col-md-12 col-sm-12">
                <?php if(isset($_SESSION['msg'])){?> 
                 <div class="alert alert-primary alert-dismissible" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                  <?php echo $alert_msg[$_SESSION['msg']] ; ?></a> </div>
                <?php unset($_SESSION['msg']);}?> 
              </div>
              
				 <form action="" name="addeditdata" method="post" class="form form-horizontal" enctype="multipart/form-data">
						<input  type="hidden" name="id" value="<?php echo $_GET['id'];?>" />
						
					<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Select Category</label>
                      <div class="col-sm-12 col-md-7">
                          <select name="cid" id="cid" class="form-control select2" required>
                        <option value="">--Select Category--</option>
                        	<?php
          									while($cat_row=mysqli_fetch_array($cat_result))
          									{
          							?>       
          							<option value="<?php echo $cat_row['cat_id'];?>" <?php if($cat_row['cat_id']==$row['cid']){?>selected<?php }?>><?php echo $cat_row['category_name'];?></option>	          							 
          							<?php
          								}
          							?> 
                      </select>
                      </div>
                    </div>
                    
                    
                    <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Radio Name</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="channel_name" id="channel_name" value="<?php if(isset($_GET['id'])){echo $row['channel_name'];}?>" required/>
                      </div>
                    </div>
                    
                    <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Radio Frequency</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="frequency" id="frequency" value="<?php if(isset($_GET['id'])){echo $row['frequency'];}?>" required/>
                      </div>
                    </div>
                    
                    <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Radio Description</label>
                      <div class="col-sm-12 col-md-7">
                        <textarea class="form-control" style="height: 156px;" name="description" id="description" required><?php echo stripslashes($row['description']);?></textarea>
                      </div>
                    </div>
                    
                     <div class="form-group row mb-4">
                      <label style="transform: translate(0%, 30%);" class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Radio Icon</label>
                       <div class="col-md-5" style="transform: translate(0%, 40%);margin-bottom: 35px;">
                    <div class="custom-file">
                      <input type="file" name="channel_icon" value="fileupload" id="fileupload" class="custom-file-input" id="customFile" onchange="this.nextElementSibling.innerText = this.files[0].name" 
                    <?php if(isset($_GET['id'])){?><?php }else{?>required<?php }?>>
                      <label class="custom-file-label" for="customFile">Choose File</label>
                       <p style="margin-top:5px;">(Recommended resolution: 300 x 300 or 400 x 400 pixels)</p>
                    </div>
                    
                     </div>
                     <div class="col-md-2">
                    
                     <?php if(isset($_GET['id']) and $row['channel_icon']!="") {?>
                            <img id="imgPreview" style="max-width: 100%; object-fit: cover;margin-top: 10px;" src="images/<?php echo $row['channel_icon'];?> ">  
                  
                          <?php  } else { ?>
                          
                          <img id="imgPreview" style="max-width: 100%; object-fit: cover;margin-top: 10px;" src="assets/img/add.png">  
                          <?php } ?>
                          
                      </div>
                      </div>
                      
                    <div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Source Url</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="source_url" id="source_url" value="<?php if(isset($_GET['id'])){echo $row['source_url'];}?>" required/>
                      </div>
                    </div>
                          
                      
					<div class="card-footer text-right">
					<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3"></label>
                      <div class="col-sm-12 col-md-7">
                        <button type="submit" name="submit" class="btn btn-lg btn-primary">Submit</button>
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
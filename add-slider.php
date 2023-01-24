<?php include("includes/header.php");

  require("includes/function.php");
  require("alert/alert.php");

  require_once("thumbnail_images.class.php");
  $data_qry="SELECT * FROM tbl_channels WHERE slider_status = 0 ORDER BY channel_name";
  $data_result=mysqli_query($mysqli,$data_qry);
  $selectOption = $_POST['Option'];
  

   
  if(isset($_POST['submit']) and isset($_GET['add']))
  {
      
      if(!$is_demo_user){
          
        mysqli_query($mysqli, "UPDATE `tbl_channels` SET  `slider_status` = '1'  WHERE id = '".$selectOption."' ");

        $_SESSION['msg']="10";
        header( "Location:add-slider?add=yes");
        exit;

      }
      else{
        $_SESSION['msg']="20";
        header( "Location:add-slider?add=yes");
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
                    	<legend class="hidden-first">Add Item To Slider</legend>
                  </div>
                  <div class="card-body">
                      
                <div class="col-md-12 col-sm-12">
                <?php if(isset($_SESSION['msg'])){?> 
                 <div class="alert alert-primary alert-dismissible" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                  <?php echo $alert_msg[$_SESSION['msg']] ; ?></a> </div>
                <?php unset($_SESSION['msg']);}?> 
              </div>
				 <form action="" name="addedit" method="post" class="form form-horizontal" enctype="multipart/form-data">
					<fieldset>
						
							<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Select Item</label>
                      <div class="col-sm-12 col-md-7">
                          <select name="Option" class="form-control select2">
                        <option value="">--Select Item--</option>
                        	<?php
          									while($data_row=mysqli_fetch_array($data_result))
          									{
          							?>       
          							<option value="<?php echo $data_row['id'];?>"><?php echo $data_row['channel_name'];?></option>	          							 
          							<?php
          								}
          							?> 
                      </select>
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

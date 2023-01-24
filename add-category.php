<?php include("includes/header.php");

  require("includes/function.php");
  require("alert/alert.php");
  require_once("thumbnail_images.class.php");
   
  if(isset($_POST['submit']) and isset($_GET['add']))
  {
      
      if(!$is_demo_user){
        $category_image=rand(0,99999)."_".$_FILES['category_image']['name'];

        //Main Image
        $tpath1='images/'.$category_image;        
        $pic1=$_FILES["category_image"]["tmp_name"];
        copy($pic1,$tpath1);


        $data = array( 
        'category_name'  =>  $_POST['category_name'],
        'category_image'  =>  $category_image
        );    

        $qry = Insert('tbl_category',$data);  

        $_SESSION['msg']="10";
        header( "Location:add-category?add=yes");
        exit;

      }
      else{
        $_SESSION['msg']="20";
        header( "Location:add-category?add=yes");
        exit;
      }
      
  }
  
  if(isset($_GET['id']))
  {
       
      $qry="SELECT * FROM tbl_category where cat_id='".$_GET['id']."'";
      $result=mysqli_query($mysqli,$qry);
      $row=mysqli_fetch_assoc($result);

  }
  if(isset($_POST['submit']) and isset($_POST['cat_id']))
  {
    
    if(!$is_demo_user){
        if($_FILES['category_image']['name']!="")
        {    
            $img_res=mysqli_query($mysqli,'SELECT * FROM tbl_category WHERE cat_id='.$_GET['id'].'');
            $img_res_row=mysqli_fetch_assoc($img_res);


            if($img_res_row['category_image']!="")
            {
              unlink('images/'.$img_res_row['category_image']);
            }

           $category_image=rand(0,99999)."_".$_FILES['category_image']['name'];
           $tpath1='images/'.$category_image;        
           $pic1=$_FILES["category_image"]["tmp_name"];
           copy($pic1,$tpath1);

            $data = array(
              'category_name'  =>  $_POST['category_name'],
              'category_image'  =>  $category_image
            );

            $category_edit=Update('tbl_category', $data, "WHERE cat_id = '".$_POST['cat_id']."'");

        }
        else
        {

            $data = array(
              'category_name'  =>  $_POST['category_name']
            );  

           $category_edit=Update('tbl_category', $data, "WHERE cat_id = '".$_POST['cat_id']."'");

        }
        $_SESSION['msg']="11"; 
        header( "Location:add-category?id=".$_POST['cat_id']);
        exit;
    }
    else{
        $_SESSION['msg']="20"; 
      header( "Location:add-category?id=".$_POST['cat_id']);
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
                    	<legend class="hidden-first"><?php if(isset($_GET['id'])){?>Edit<?php }else{?>Add<?php }?> Category</legend>
                  </div>
                  <div class="card-body">
                      
                <div class="col-md-12 col-sm-12">
                <?php if(isset($_SESSION['msg'])){?> 
                 <div class="alert alert-primary alert-dismissible" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                  <?php echo $alert_msg[$_SESSION['msg']] ; ?></a> </div>
                <?php unset($_SESSION['msg']);}?> 
              </div>
				 <form action="" name="addeditcategory" method="post" class="form form-horizontal" enctype="multipart/form-data">
					<fieldset>
						<input  type="hidden" name="cat_id" value="<?php echo $_GET['id'];?>" />
						<div class="form-group row mb-4">
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Category Name</label>
                      <div class="col-sm-12 col-md-7">
                        <input class="form-control" type="text" name="category_name" id="category_name" value="<?php if(isset($_GET['id'])){echo $row['category_name'];}?>" required/>
                      </div>
                    </div>
                    
			
                    <div class="form-group row mb-4">
                        
                        
                      <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Category Image</label>
                       <div class="col-md-7">
                          <div class="custom-file">
                      <input type="file" name="category_image" value="fileupload" id="fileupload" class="custom-file-input" id="customFile" onchange="this.nextElementSibling.innerText = this.files[0].name">
                      <label class="custom-file-label" for="customFile">Choose File</label>
                    </div>
                    <p style="margin-top:5px;">(Recommended resolution: 1080 x 566 pixels or Landscape Image)</p>
                    </div>
                   
                    </div>
                    
                        <?php if(isset($_GET['id']) and $row['category_image']!="") {?>
                            <div class="form-group row mb-4">
                           <label class="col-form-label text-md-right col-12 col-md-3 col-lg-3">Image Preview</label>
                      <div class="col-sm-12 col-md-7">
                            <img style="max-width: 60%; object-fit: cover;" src="images/<?php echo $row['category_image'];?>" alt="">
                    </div>
                    </div>
                  </div><br>
                          <?php } ?>
                    
							
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

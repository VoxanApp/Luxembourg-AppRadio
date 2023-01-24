<?php
    include "includes/header.php";
	require("alert/alert.php");
	require("includes/function.php");
			
			
      $tableName="tbl_channels";   
      $targetpage = "manage-slider"; 
      $limit = 9; 

      $query = "SELECT COUNT(*) as num FROM $tableName WHERE $tableName.slider_status = 1";
      $total_pages = mysqli_fetch_array(mysqli_query($mysqli,$query));
      $total_pages = $total_pages['num'];

      $stages = 3;
      $page=0;
      if(isset($_GET['page'])){
      $page = mysqli_real_escape_string($mysqli,$_GET['page']);
      }
      if($page){
      $start = ($page - 1) * $limit; 
      }else{
      $start = 0; 
      } 

      $qry="SELECT * FROM $tableName
      LEFT OUTER JOIN tbl_category c ON c.cat_id = $tableName.cid
      WHERE $tableName.slider_status = 1
      ORDER BY $tableName.id DESC LIMIT $start, $limit";

      $result=mysqli_query($mysqli,$qry); 
      
				
				?>
				
		<div class="main-content">
        <section class="section">
          <div class="section-body">
            <div class="row">
              <div class="col-12">
                <div class="card">
                  <div class="card-header">
                    <h4>Manage Slider</h4>
                    <div class="card-header-action">
                    	 <a href="add-slider?add=yes" class="btn btn-primary">
                        Add New Item
                      </a>
                </div>
                  </div>
                 
                  <div class="card-body">
                      
                      <div class="col-md-12 col-sm-12">
                <?php if(isset($_SESSION['msg'])){?> 
                 <div class="alert alert-primary alert-dismissible" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                  <?php echo $alert_msg[$_SESSION['msg']] ; ?></a> </div>
                <?php unset($_SESSION['msg']);}?> 
              </div>
              
              <section class="section">
          <div class="section-body">
            <div class="row">
                 <?php 
                $i=0;
                while($fetched_data=mysqli_fetch_array($result))
                {         
              ?>
              
					 <div class="col-12 col-md-6 col-lg-4">
					<div class="card card-primary" style="margin: 10px;">
                  <div class="card-header">
                    <h4><?php echo $fetched_data['channel_name'];?></h4>
                    
                    <div class="card-header-action">
                      <div class="dropdown">
                        <a href="#" data-toggle="dropdown" class="btn btn-warning dropdown-toggle">Options</a>
                        <div class="dropdown-menu">
                            
                             <?php if($fetched_data['channel_status']!="1"){?>
                             <a href="save?table=channel&act=enable&id=<?php echo $fetched_data['id']?>&loc=slider" onclick="return confirm('Are you sure want to Enable?')" style="margin-left: 15px;" class="btn btn-sm btn-danger">Disabled</a>
		                    <?php }else{?>
		                    <a href="save?table=channel&act=disable&id=<?php echo $fetched_data['id']?>&loc=slider" onclick="return confirm('Are you sure want to Disable?')" style="margin-left: 15px;" class="btn btn-sm btn-success">Enabled</a>
		                         <?php }?>
		                         
		                         <div class="dropdown-divider"></div>
		                         
		                    <?php if($fetched_data['slider_status']!="1"){?>
                            <a href="save?table=channel&act=slider-e&id=<?php echo $fetched_data['id']?>&loc=slider" class="dropdown-item has-icon"><i class="far fa fa-sliders-h"></i> Add To Slider</a>
		                    <?php }else{?>
		                    <a href="save?table=channel&act=slider-d&id=<?php echo $fetched_data['id']?>&loc=slider" class="dropdown-item has-icon"><i class="far fa fa-sliders-h"></i> Remove From Slider</a>
		                         <?php }?>
                        </div>
                      </div>
                    </div>
                  </div>
                  
                  <div class="card-body align-center">
                    <div class="chocolat-parent">
                      <a href="images/<?php echo $fetched_data['channel_icon'];?>" class="chocolat-image" title="Preview">
                        <div data-crop-image="160">
                         <img alt="image" src="images/<?php echo $fetched_data['channel_icon'];?>" style="max-width: 100%; max-height: 100%;" class="img-fluid">
                        </div>
                      </a>
                    </div>
                  </div>
                  
                </div>
                  </div>
              	<?php
            
            $i++;
              }
        ?>     
              	</div>
                  </div>
                </div>
                  </div>
                </div>
              </div>
            </div>
             <div class="col-md-12 col-xs-12">
            <div class="pagination_item_block">
              <nav>
                <?php if(!isset($_POST["data_search"]))
                { include("pagination.php");}?>
              </nav>
            </div>
          </div>
        </section>
        </div>
       
					<?php include "includes/footer.php";?>
				
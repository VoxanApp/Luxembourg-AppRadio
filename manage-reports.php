<?php
    include "includes/header.php";
	require("alert/alert.php");
	$qry="select *
    FROM tbl_report";  
               
    $result=mysqli_query($mysqli,$qry);
?>
				
		<div class="main-content">
        <section class="section">
          <div class="section-body">
            <div class="row">
              <div class="col-12">
                <div class="card">
                  <div class="card-header">
                    <h4>Manage Reports</h4>
                  </div>
                 
                  <div class="card-body">
                      
                      <div class="col-md-12 col-sm-12">
                <?php if(isset($_SESSION['msg'])){?> 
                 <div class="alert alert-primary alert-dismissible" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                  <?php echo $alert_msg[$_SESSION['msg']] ; ?></a> </div>
                <?php unset($_SESSION['msg']);}?> 
              </div>
              
                    <div class="table-responsive">
				<table id="sorted" class="table table-striped table-bordered">
			<thead>
			<tr>
            <th>Id</th>
			<th>Radio ID</th>
			<th>Radio Name</th>
			<th>Report</th>
            <th class="no-sorted">Actions</th>
			</tr>
			</thead>
                <?php	
                $i=0;
        		while($row=mysqli_fetch_array($result))
        		{ ?>
			<tr>
		<td><?php echo $row['id']?></td>
		<td><?php echo $row['radio_id']?></td>
		<td><?php echo $row['radio_name']?></td>
		<td><?php echo $row['report']?></td>


						<td>
						    <a href="save.php?act=delete&id=<?php echo $row['id']?>&table=report" onclick="return navConfirm(this.href);" class="btn btn-icon icon-left btn-danger btn-sm"><i class="fas fa-times"></i> Delete</a>
						    </div>
                </td>
						</tr>
					 <?php
						    $i++;
				     	  }
				        ?> 
					</table>
					 </div>
                  </div>
                </div>
              </div>
            </div>
        </section>
        </div>
					<?php include "includes/footer.php";?>
				
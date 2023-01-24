<?php
		include("includes/connection.php");

		$table = $_POST['table'];
		$table_get = $_GET['table'];
		$act = $_POST['act'];
		$act_get = $_GET['act'];
		$id = $_POST['id'];
		$id_get = $_GET['id'];
		$status_get = $_GET['status'];

			if(!$is_demo_user){
				if($table == "category" || $table_get == "category"){
					$category_name = mysqli_real_escape_string($mysqli,$_POST["category_name"]);
                        $category_image = mysqli_real_escape_string($mysqli,$_POST["category_image"]);
					if ($act_get == "delete"){
						mysqli_query($mysqli, "DELETE FROM `tbl_category` WHERE cat_id = '".$id_get."' ");
						$_SESSION['msg']="12";
					}
					if ($act_get == "enable"){
					mysqli_query($mysqli, "UPDATE `tbl_category` SET  `status` = '1'  WHERE `cat_id` = '".$id_get."' ");
					$_SESSION['msg']="11";
			        } else if ($act_get == "disable"){
					mysqli_query($mysqli, "UPDATE `tbl_category` SET `status` = '0'  WHERE `cat_id` = '".$id_get."' ");
					$_SESSION['msg']="11";
			        }
					header("location:"."manage-category");
				}
				
				else if($table == "channel" || $table_get == "channel"){
				    if ($act_get == "delete"){
						mysqli_query($mysqli, "DELETE FROM `tbl_channels` WHERE id = '".$id_get."' ");
						$_SESSION['msg']="12";
					}
					if ($act_get == "enable"){
					mysqli_query($mysqli, "UPDATE `tbl_channels` SET  `channel_status` = '1'  WHERE `id` = '".$id_get."' ");
			        } else if ($act_get == "disable"){
					mysqli_query($mysqli, "UPDATE `tbl_channels` SET `channel_status` = '0'  WHERE `id` = '".$id_get."' ");
			        } else if ($act_get == "slider-e"){
					mysqli_query($mysqli, "UPDATE `tbl_channels` SET `slider_status` = '1'  WHERE `id` = '".$id_get."' ");
			        } else if ($act_get == "slider-d"){
					mysqli_query($mysqli, "UPDATE `tbl_channels` SET `slider_status` = '0'  WHERE `id` = '".$id_get."' ");
			        }
					$_SESSION['msg']="11";
			    	if(isset($_GET['loc']) == "slider" )
					{
					header("location:"."manage-slider");
				}
				else{
				    header("location:"."manage-channels");
				}
				}
				
				else if($table == "report" || $table_get == "report"){
				    if ($act_get == "delete"){
						mysqli_query($mysqli, "DELETE FROM `tbl_report` WHERE id = '".$id_get."' ");
						$_SESSION['msg']="12";
					}
				    header("location:"."manage-reports");
				}

			  }else{
			      if($table == "channel"|| $table_get == "channel"){
			        	if(isset($_GET['loc']) == "slider" )
					{
					header("location:"."manage-slider");
				}
				else{
				    header("location:"."manage-channels");
				}
			  } 
			      
			      else if($table == "category" || $table_get == "category"){
			        header("location:"."manage-category");
			      } 
			      
			      else if($table == "report" || $table_get == "report"){
			        header("location:"."manage-reports");
			      } 
			      
			        $_SESSION['msg']="20";
				}
			
				
				?>
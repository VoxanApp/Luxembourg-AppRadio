<?php
    include "includes/header.php";
	include "includes/function.php";
	$img_path = 'images';
    if (!is_dir($img_path)) {
        mkdir($img_path, 0755, true);
    }	
		
		?>
		
		 <div class="main-content">
        <section class="section">
          <div class="section-body">
            <div class="row">
                
              
              <div class="col-12 col-md-6 col-lg-4">
                <div class="card card-primary">
                     <div class="card-header">
                    <h4>Category</h4>
                    <div class="card-header-action">
                        <a href="add-category?add=yes" class="btn btn-primary">
                         Add New
                      </a>
                      <a href="manage-category" class="btn btn-primary">
                        View All
                      </a>
                    </div>
                  </div>
              <div class="card-statistic-4">
                  <div class="align-items-center justify-content-between">
                    <div class="row ">
                      <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 pr-0 pt-3">
                        <div class="card-content">
                          <h2 class="mb-3  text-warning font-50"><?=thousandsNumberFormat(counting("tbl_category", "id"))?></h2>
                        </div>
                      </div>
                      <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 pl-0">
                        <span class="fa-stack fa-3x fa-lg fa-pull-right ">
                            <i class="fa fa-circle fa-stack-2x"></i>
                             <i class="fa fa-sitemap fa-stack-1x fa-inverse"></i>
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
                </div>
                </div>
                
             <div class="col-12 col-md-6 col-lg-4">
                <div class="card card-primary">
                     <div class="card-header">
                    <h4>Channels</h4>
                    <div class="card-header-action">
                        <a href="add-channel?add=yes" class="btn btn-primary">
                         Add New
                      </a>
                      <a href="manage-channels" class="btn btn-primary">
                        View All
                      </a>
                    </div>
                  </div>
              <div class="card-statistic-4">
                  <div class="align-items-center justify-content-between">
                    <div class="row ">
                      <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 pr-0 pt-3">
                        <div class="card-content">
                          <h2 class="mb-3  text-warning font-50"><?=thousandsNumberFormat(counting("tbl_channels", "id"))?></h2>
                        </div>
                      </div>
                      <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 pl-0">
                          <span class="fa-stack fa-3x fa-lg fa-pull-right ">
                            <i class="fa fa-circle fa-stack-2x"></i>
                             <i class="fa fa-microphone fa-stack-1x fa-inverse"></i>
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
                </div>
                </div>
              
             <div class="col-12 col-md-6 col-lg-4">
                <div class="card card-primary">
                     <div class="card-header">
                    <h4>Home Slider</h4>
                    <div class="card-header-action">
                      <a href="manage-slider" class="btn btn-primary">
                        View All
                      </a>
                    </div>
                  </div>
              <div class="card-statistic-4">
                  <div class="align-items-center justify-content-between">
                    <div class="row ">
                      <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 pr-0 pt-3">
                        <div class="card-content">
                          <h2 class="mb-3  text-warning font-50"><?=thousandsNumberFormat(countingSlider("tbl_channels", "id"))?></h2>
                        </div>
                      </div>
                      <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 pl-0">
                          <span class="fa-stack fa-3x fa-lg fa-pull-right ">
                            <i class="fa fa-circle fa-stack-2x"></i>
                             <i class="fa fa-sliders-h fa-stack-1x fa-inverse"></i>
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
                </div>
                </div>
                
                <div class="col-12 col-md-6 col-lg-4">
                <div class="card card-primary">
                     <div class="card-header">
                    <h4>Reports</h4>
                    <div class="card-header-action">
                      <a href="manage-reports" class="btn btn-primary">
                        View All
                      </a>
                    </div>
                  </div>
              <div class="card-statistic-4">
                  <div class="align-items-center justify-content-between">
                    <div class="row ">
                      <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 pr-0 pt-3">
                        <div class="card-content">
                          <h2 class="mb-3  text-warning font-50"><?=thousandsNumberFormat(counting("tbl_report", "id"))?></h2>
                        </div>
                      </div>
                      <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 pl-0">
                          <span class="fa-stack fa-3x fa-lg fa-pull-right ">
                            <i class="fa fa-circle fa-stack-2x"></i>
                             <i class="fa fa-flag fa-stack-1x fa-inverse"></i>
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
                </div>
                </div>
                
                <div class="col-12 col-md-6 col-lg-4">
                <div class="card card-primary">
                     <div class="card-header">
                    <h4>App Downloads</h4>
                  </div>
              <div class="card-statistic-4">
                  <div class="align-items-center justify-content-between">
                    <div class="row ">
                      <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 pr-0 pt-3">
                        <div class="card-content">
                          <h2 class="mb-3  text-warning font-50"><?= thousandsNumberFormat(counting("tbl_users", "id"))?></h2>
                        </div>
                      </div>
                      <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 pl-0">
                          <span class="fa-stack fa-3x fa-lg fa-pull-right style="color:1b2c33;">
                            <i class="fa fa-circle fa-stack-2x"></i>
                             <i class="fa fa-download fa-stack-1x fa-inverse"></i>
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
                </div>
                </div>
              
              
              
            </div>
        </section>
			<?php include "includes/footer.php";?>
			
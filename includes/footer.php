
				</div>


				<!-- jQuery Version 1.11.1 -->
			<script src="assets/js/app.min.js"></script>
  <!-- JS Libraies -->
  <script src="assets/bundles/datatables/datatables.min.js"></script>
  <script src="assets/bundles/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
  <script src="assets/bundles/jquery-ui/jquery-ui.min.js"></script>
  <script src="assets/bundles/cleave-js/dist/cleave.min.js"></script>
  <script src="assets/bundles/cleave-js/dist/addons/cleave-phone.us.js"></script>
  <script src="assets/bundles/jquery-pwstrength/jquery.pwstrength.min.js"></script>
  <script src="assets/bundles/bootstrap-daterangepicker/daterangepicker.js"></script>
  <script src="assets/bundles/bootstrap-colorpicker/dist/js/bootstrap-colorpicker.min.js"></script>
  <script src="assets/bundles/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>
  <script src="assets/bundles/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
  <script src="assets/bundles/select2/dist/js/select2.full.min.js"></script>
  <script src="assets/bundles/jquery-selectric/jquery.selectric.min.js"></script>
  <script src="assets/bundles/summernote/summernote-bs4.js"></script>
  <script src="assets/bundles/codemirror/lib/codemirror.js"></script>
  <script src="assets/bundles/codemirror/mode/javascript/javascript.js"></script>
  <script src="assets/bundles/sweetalert/sweetalert.min.js"></script>
  <script src="assets/js/page/sweetalert.js"></script>
  <script src="assets/bundles/chocolat/dist/js/jquery.chocolat.min.js"></script>
  <!-- Page Specific JS File -->
  <script src="assets/js/page/forms-advanced-forms.js"></script>
  <!-- Page Specific JS File -->
  <script src="assets/js/page/datatables.js"></script>
  <!-- Template JS File -->
  <script src="assets/js/scripts.js"></script>
  <!-- Custom JS File -->
  <script src="assets/js/custom.js"></script>
  <script src="assets/bundles/apexcharts/apexcharts.min.js"></script>
  <script src="assets/js/page/index.js"></script>
 <script type="text/javascript">

          $(".select2").change(function(e){

            var _val=$(this).val();
            if(_val!=0){
              $(this).parents('.link_block').find("input").attr("disabled","disabled");
              $(this).parents('.link_block').find(".select2").attr("disabled","disabled");
              $(this).removeAttr("disabled");

              $("input[name='type']").val($(this).data("type"));
              $("input[name='title']").val($(this).children("option").filter(":selected").text());

            }
            else{
              $(this).parents('.link_block').find(".select2").removeAttr("disabled");
              $(this).parents('.link_block').find("input").removeAttr("disabled");
              $("input[name='type']").val('');
              $("input[name='title']").val('');
            }

          });

          function fileValidation(){
            var fileInput = document.getElementById('fileupload');
            var filePath = fileInput.value;
            var allowedExtensions = /(\.png|.PNG|.jpg|.JPG)$/i;
            if(!allowedExtensions.exec(filePath)){
              alert('Please upload file having extension .png, .jpg, .PNG, .JPG only.');
              fileInput.value = '';
              return false;
            }else{
        //image preview
        if (fileInput.files && fileInput.files[0]) {
          var reader = new FileReader();
          reader.onload = function(e) {
            document.getElementById('uploadPreview').innerHTML = '<img src="'+e.target.result+'" style="width:150px;height:90px"/>';
          };
          reader.readAsDataURL(fileInput.files[0]);
        }
      }
    }
  </script>
				<script type="text/javascript">
					 $(document).ready(function () {

					 	$("#sidebarCollapse, #sidebarExtend").on("click", function () {
							$("#sidebar").toggleClass("active");
						});

						$("#sorted").DataTable( {
							"bStateSave": true,
							"sPaginationType": "full_numbers"
						});
					 });
					 
    $(document).ready(()=>{
      $('#fileupload').change(function(){
        const file = this.files[0];
        console.log(file);
        if (file){
          let reader = new FileReader();
          reader.onload = function(event){
            console.log(event.target.result);
            $('#imgPreview').attr('src', event.target.result);
          }
          reader.readAsDataURL(file);
        }
      });
    });
    
    $(document).ready(()=>{
      $('#fileupload2').change(function(){
        const file = this.files[0];
        console.log(file);
        if (file){
          let reader = new FileReader();
          reader.onload = function(event){
            console.log(event.target.result);
            $('#imgPreview2').attr('src', event.target.result);
          }
          reader.readAsDataURL(file);
        }
      });
    });
    
				</script>

				<script type="text/javascript">
				function navConfirm(loc) {
					if (confirm("Are you sure?")) {
						window.location.href = loc;
					}
					return false;
				}
				</script>
				
		<footer class="main-footer">
        <div class="footer-left">
          Copyright © <?php echo date('Y');?> <a href="http://www.andromob.co.in" target="_blank">AndroMOB</a>. All Rights Reserved.
        </div>
        <div class="footer-right">
        </div>
      </footer>

				
			</body>
			</html>
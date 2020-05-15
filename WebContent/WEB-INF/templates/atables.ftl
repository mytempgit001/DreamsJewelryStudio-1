<#import "parts/aleftsidebar.ftl" as sidebar>
<@sidebar.page>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">
</@sidebar.page>
        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">
          		${tableName}
       	  </h1>
          	<div class="dropdown mb-4">
          	Click here if you want to 
               <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                 Dropdown
               </button>
               <div class="dropdown-menu animated--fade-in" aria-labelledby="dropdownMenuButton">
                 <a class="dropdown-item" href="http://localhost:8080/admin/modifying?table=${tableName}&operation=INSERT">INSERT</a>
                 <a class="dropdown-item" href="http://localhost:8080/admin/modifying?table=${tableName}&operation=UPDATE">UPDATE</a>
                 <a class="dropdown-item" href="http://localhost:8080/admin/modifying?table=${tableName}&operation=DELETE">DELETE</a>
               </div>
               entries.
             </div>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
            	<h6 class="m-0 font-weight-bold text-primary">Set parameters</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <#if fields??>
	                      <#list fields as f>
	                    	  <th>${f}</th>
	                      </#list>
                      </#if>
                    </tr>
                  </thead>
                  <tbody>
                  		<#if items??>
                    		<#list items as str>
                    			<tr>
                    			<#list str as i>
	                    	 	 	<td style="white-space: nowrap; overflow: hidden; text-overflow:ellipsis">${i}</td>
	                    	  	</#list>
	                    	  	</tr>
	                      	</#list>
                    	</#if>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; Your Website 2019</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="login.html">Logout</a>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="http://localhost:8080/static/admin/vendor/jquery/jquery.min.js"></script>
  <script src="http://localhost:8080/static/admin/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="http://localhost:8080/static/admin/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="http://localhost:8080/static/admin/js/sb-admin-2.min.js"></script>

  <!-- Page level plugins -->
  <script src="http://localhost:8080/static/admin/vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="http://localhost:8080/static/admin/vendor/datatables/dataTables.bootstrap4.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="http://localhost:8080/static/admin/js/demo/datatables-demo.js"></script>

</body>

</html>
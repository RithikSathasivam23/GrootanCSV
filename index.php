<?php include 'connection.php';?> 
<!DOCTYPE html>
<html>
 <head>
  <title>Grootan</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="styles.css">
 </head>
 <body>
  <div class="container">
   <br />
   <h3 align="center">Upload Your CSV Files</h3>
   <br />
   
  <form class="myclass" method="post" action="http://192.168.43.36:7001/uploadcsv" enctype="multipart/form-data">
    <div class="col-md-3">
     <br />
     <label>Select CSV File</label>
    </div>  
                <div class="col-md-4">  
                    <input type="file" name="files" accept=".csv" style="margin-top:15px;" />
                </div>  
                <div class="col-md-5">  
                    <button type="submit" style="margin-top:10px;" class="btn btn-info">Upload</button>
                   
                </div>  
                <div style="clear:both"></div>
   </form>
   <div class="col-md-8">  
   <form action="delete.php" method="post">
    <input type="submit"  style="margin-top:10px;" class="btn btn-danger deletebtn" name="deletebtn" value="Drop">
    </form>
    </div>
   <br />
   <br />
   <div id="csv_file_data"></div>
    
  </div>
  <script src="sweetalert2.all.min.js"></script>
     <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10.15.5/dist/sweetalert2.all.min.js"></script>
     <?php
            if(isset($_SESSION['status']) && $_SESSION['status'] !='')
            {
              ?>
              <script>
                Swal.fire({
                position: 'center',
                icon: "<?php echo $_SESSION["status_code"];?>",
                title: "<?php echo $_SESSION["status"];?>",
                showConfirmButton:false,
                timer: 2500,
                showClass: {
                    popup: 'animate_animated animate_fadeInDown'
                  },
                  hideClass: {
                    popup: 'animate_animated animate_fadeOutUp'
                  }
              });
                </script>
                <?php
                unset($_SESSION['status']);
            }
            ?>
 </body>
</html>


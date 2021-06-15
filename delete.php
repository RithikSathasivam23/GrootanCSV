<?php include 'connection.php';?> 
<?php
         
if(isset($_POST['deletebtn'])){
    $query = "DROP TABLE csvtable";
    $query_run = mysqli_query($conn, $query);
    
    if($query_run){
         
        $_SESSION['status']="Table Deleted";
        $_SESSION['status_code'] ="success";
        header('Location:index.php');
    }
    else{
        $_SESSION['status']="!OOPS SOME THING WENT WORNG";
        $_SESSION['status_code'] ="error";
        header('Location:index.php');

    }

}
?>
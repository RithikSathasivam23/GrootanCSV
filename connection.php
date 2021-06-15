<?php
session_start();

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "csvdb1";

// Create connection
$conn = mysqli_connect($servername, $username, $password,$dbname);
?>
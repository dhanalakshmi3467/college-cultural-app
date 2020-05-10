<?php 
$db_name = "college";
$username = "root";
$password = "";
$server_name = "localhost";
$conn = new mysqli($server_name, $username, $password, $db_name);
 
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>
 
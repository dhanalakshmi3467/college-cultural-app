<?php
$servername="localhost"; 
$mysql_user="root";
$mysql_pass="";
$dbname="college";
$conn=mysqli_connect($servername, $mysql_user, $mysql_pass, $dbname);
if(!$conn){
	echo("database connection failed");
}

if($_SERVER['REQUEST_METHOD']=='POST'){
	$reg_num=$_POST['register_number'];
    $user=$_POST['username'];
	$pass=$_POST['password'];
	$email=$_POST['email'];
	$admin=$_POST['admin'];
	$email_query="SELECT `email` FROM `users` WHERE username='$email'";
	
	$query="INSERT INTO `users`(`register_number`,`email`,`username`,`password`,`admin`) VALUES('$reg_num','$email','$user','$pass','$admin')";
	if(mysqli_query($conn, $query)){
		echo("registration succesfully completed");
	}else {
		printf("error in registration %s", $conn->error);
	}
}else{
	echo("error in request method");	
}

?>
 
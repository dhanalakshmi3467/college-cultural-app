<?php
require "conn.php";
$user_name = $_POST["user_name"];
$user_pass = $_POST["password"];

$stmt = $conn->prepare("select uuid, register_number, email, username, admin from users where username='$user_name' and password='$user_pass';");
if (!$stmt->execute()){
	$user['exists'] = False;
	$user['message'] = "Error: (" . $stmt->errno . ") " . $stmt->error;
}else{
	$stmt->store_result();
	if($stmt->num_rows > 0){
		$stmt->bind_result($uuid, $register_number, $email, $username, $admin);
		$user['exists'] = True;
		while($stmt->fetch()){
			$user['uuid'] = $uuid;
			$user['registerNumber'] = $register_number;
			$user['email'] = $email;
			$user['username'] = $username;
			if ($admin == 1){
				$user['admin'] = True;
			}else {
				$user['admin'] = False;
			}
			
		}
	}else{
		$user['exists'] = False;
		$user['message'] = "username/password is incorrect";
	}
	echo json_encode($user,JSON_PRETTY_PRINT);
}

/*
$mysql_qry = "select uuid, register_number, email, username, admin from users where username='$user_name' and password='$user_pass';";
$result = mysqli_query($conn,$mysql_qry);
if(mysqli_num_rows($result) > 0)
{
	$admin = $result->fetch_object()->admin; 
	if($admin == 1){
		echo "admin";
	}else {
		echo "user";
	}
	$result->bind_result($uuid, $register_number, email, username, admin);
	
	while($result->fetch()){
		$user['uuid'] = $uuid;
		$user['register_number'] = $register_number;
		$user['email'] = $email;
		$user['username'] = $username;
		$user['admin'] = $admin;
	}
	echo json_encode($user);
}
else {
		echo "Login failed. Please provide valid credentials";
}*/
?>


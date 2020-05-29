<?php
require "conn.php";
$email = $_POST["email"];
$user_pass = $_POST["password"];

$stmt = $conn->prepare("select uuid, register_number, email, username, admin from users where email='$email' and password='$user_pass';");
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

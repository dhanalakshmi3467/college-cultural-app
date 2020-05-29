<?php 
 
	$type = $_GET['type'];
	$email = $_GET['email'];
	
	/*
	
	function clean($string) {
		//$string = str_replace(' ', '-', $string); // Replaces all spaces with hyphens.
		return preg_replace('/[^A-Za-z0-9\-]/', ' ', $string); // Removes special chars.
	}
	*/

	//database constants
	require "conn.php";
	
	//Checking if any error occured while connecting
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}
	
	//creating a query
	//$stmt = $conn->prepare("SELECT id, title, shortdesc, price, image, date, time  FROM events where type ='$type';");
	$stmt = $conn->prepare("SELECT e.id, e.title, e.shortdesc, e.date, e.time, e.price, e.image, e.type, e.created_by, e.approved FROM events as e INNER JOIN users as u ON e.created_by = u.uuid AND e.approved = 1 AND type ='$type' UNION SELECT e.id, e.title, e.shortdesc, e.date, e.time, e.price, e.image, e.type, e.created_by, e.approved FROM events as e INNER JOIN users as u ON e.created_by = u.uuid AND u.email ='$email' AND e.approved = 0  AND type ='$type'");

	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($id, $title, $shortdesc, $date, $time, $price, $image, $type, $created_by, $approved);
	
	
	$events = array(); 
	
	//traversing through all the result 
	while($stmt->fetch()){
		$temp = array();
		$temp['id'] = $id; 
		$temp['title'] = $title; 
		$temp['description'] = $shortdesc; 
		$temp['price'] = $price; 
		$temp['imageUrl'] = $image; 
		$temp['date'] = $date; 
		$temp['time'] = $time; 
		$temp['type'] = $type; 
		$temp['created_by'] = $created_by; 
		if($approved == 1){
			$temp['approved'] = True; 
		}else{
			$temp['approved'] = False; 
		}
		
		array_push($events, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($events,JSON_PRETTY_PRINT);
	?>
<?php 
define('DB_HOST', 'localhost');
	define('DB_USER', 'root');
	define('DB_PASS', '');
	define('DB_NAME', 'college');
	
	//connecting to database and getting the connection object
	$conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
	
	//Checking if any error occured while connecting
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}
	

if (isset($_GET['id'])) {
$id = $_GET['id'];
//$queryl = mysqli_query("select * from recycle where id=$id", $conn);
	$stmt = $conn->prepare("select * from recycle where id=$id");

	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($id, $title, $shortdesc, $price, $image);
	
	$products = array(); 
	
	//traversing through all the result 
	/*while($stmt->fetch()){
		$temp = array();
		$temp['id'] = $id; 
		$temp['title'] = $title; 
		$temp['shortdesc'] = $shortdesc; 
		$temp['rating'] = $rating; 
		$temp['price'] = $price; 
		$temp['image'] = $image; 
		array_push($products, $temp);
	}*/
	$temp = array();
	while($stmt->fetch()){
		$temp['id'] = $id; 
		$temp['title'] = $title; 
		$temp['shortdesc'] = $shortdesc; 
		$temp['price'] = $price; 
		$temp['image'] = $image; 
		//array_push($products, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($temp);
}
	?>
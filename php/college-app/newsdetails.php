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
	$stmt = $conn->prepare("SELECT id, title, news_by, info, date, image from campus_news where id=$id");
	$stmt->execute();
	//binding results to the query; 
	$stmt->bind_result($id, $title, $news_by, $info, $date, $image);
	
	$products = array(); 
	
	$temp = array();
	while($stmt->fetch()){
		$temp['id'] = $id; 
		$temp['title'] = $title; 
		$temp['news_by'] = $news_by; 
	    $temp['info'] = $info; 
		$temp['date'] = $date;
		$temp['image'] = $image;
	
		//array_push($products, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($temp,JSON_PRETTY_PRINT);
}
/* define('DB_HOST', 'localhost');
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
	$stmt = $conn->prepare("select * from campus_news where id=$id");

	$stmt->execute();
	
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
	//binding results to the query 
	$stmt->bind_result($id, $title, $news_by,  $info, $date, $image);
	
	$products = array(); 
	
	$temp = array();
	while($stmt->fetch()){
		$temp['id'] = $id; 
		$temp['title'] = $title; 
		$temp['news_by'] = $news_by; 
		$temp['info'] = $info; 
		$temp['date'] = $date;
		$temp['image'] = $image; 
		
	}
	
	 
	echo json_encode($temp);
}*/
	?>




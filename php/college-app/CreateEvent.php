<?php
	 
	 require "conn.php";
	 
     $title = $_POST["title"];
     $description = $_POST["description"];
     $price = $_POST["price"];
     //$image = $_POST["image"];
	 $date = $_POST["date"];
	 $time = $_POST["time"];
	 $type = $_POST['type'];
	 $created_by = $_POST['created_by'];
	 
	 function getApproved($userId,$conn) {
		 $query = "SELECT * FROM users WHERE uuid = $userId AND admin = True";
		 $result = mysqli_query($conn,$query );
		 if(mysqli_num_rows($result) == 1){
			 return 1;
		 }
		 return 0;
	 }
	 $approved = getApproved($created_by,$conn);
     
     $sql = "INSERT INTO `events`(`title`,`shortdesc`,`price`,`type`,`date`,`time`,`created_by`,`approved`) VALUES ('$title','$description','$price','$type','$date','$time',$created_by,$approved)";
	 
     
	 //echo "$title $description $price $type ";
	 
     $result = mysqli_query($conn,$sql);
     
     if($result){
         echo "event created";  
     }
     else{
		 echo("Nata Error : " . $conn -> error);
     }
     mysqli_close($conn);
	 
?>
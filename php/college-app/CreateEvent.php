<?php

    $connection = mysqli_connect("localhost","root","","college");
    
     $title = $_POST["title"];
     $description = $_POST["description"];
     $price = $_POST["price"];
     //$image = $_POST["image"];
	 $date = $_POST["date"];
	 $time = $_POST["time"];
	 $type = $_POST['type'];
     
     $sql = "INSERT INTO `events`(`title`,`shortdesc`,`price`,`type`,`date`,`time`) VALUES ('$title','$description','$price','$type','$date','$time')";
     
	 //echo "$title $description $price $type ";
	 
     $result = mysqli_query($connection,$sql);
     
	 //echo "result $result";
     if($result){
         echo "event created";  
     }
     else{
         echo "PUTTI ERROR: $connection->connect_error";
     }
     mysqli_close($connection);

?>
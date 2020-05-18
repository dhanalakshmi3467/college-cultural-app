<?php

    $connection = mysqli_connect("localhost","root","","college");
     $id = $_POST["id"];
     $title = $_POST["title"];
     $shortdesc = $_POST["shortdesc"];
     $price = $_POST["price"];
     $date = $_POST["date"];
	 $time = $_POST["time"];
	 
	 
     
   $sql = "UPDATE events SET  title = '$title', shortdesc = '$shortdesc', price = '$price', date = '$date', time = '$time' WHERE id = '$id' ";
     
     $result = mysqli_query($connection,$sql);
     
     if(mysqli_affected_rows($connection)){
         echo "Data Updated";
        
     }
     else{
         echo "Failed";
     }
     mysqli_close($connection);
     
        
?>
<?php

    $connection = mysqli_connect("localhost","root","","college");
    
     $id = $_POST["id"];
     
     $deleteBookedEvent = "DELETE FROM events_booked WHERE event_id='$id'";
     
     $resultDeleteBooked = mysqli_query($connection,$deleteBookedEvent);
	 
	 
     $sql = "DELETE FROM events WHERE id='$id'";
     
     $result = mysqli_query($connection,$sql);
     
     if(mysqli_affected_rows($connection)){
         echo "Data Deleted";
        
     }
     else{
         echo "Failed";
     }
     mysqli_close($connection);
     


?>
<?php

require "conn.php";

$userId = $_GET["userId"];
$eventId = $_GET["eventId"];
$sql = "SELECT * FROM `events_booked` WHERE `user_id` = '$userId' AND `event_id` = '$eventId'";

if(mysqli_num_rows(mysqli_query($conn, $sql)) == 1 ){
	$response['booked'] = True;
	$response['message'] = "Event is already booked";
}else {
	$response['booked'] = False;
	$response['message'] = "Event is not booked";
}
echo json_encode($response,JSON_PRETTY_PRINT);

?>

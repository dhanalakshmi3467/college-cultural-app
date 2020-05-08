<?php

require "conn.php";

$userId = $_POST["userId"];
$eventId = $_POST["eventId"];

$sql = "INSERT INTO `events_booked`(`user_id`,`event_id`) VALUES ('$userId','$eventId')";
if(mysqli_query($conn, $sql)){
	$response['booked'] = True;
	$response['message'] = "Event is succesfully registered";
}else {
	$response['booked'] = False;
	$response['message'] = "Unable to book an event: $conn->error. Please contact admin";
}
echo json_encode($response,JSON_PRETTY_PRINT);

?>
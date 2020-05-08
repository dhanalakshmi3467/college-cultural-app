<?php

require "conn.php";

$userId = $_POST["userId"];
$eventId = $_POST["eventId"];
$sql = "DELETE FROM `events_booked` WHERE `user_id` = '$userId' AND `event_id` = '$eventId'";
$result =mysqli_query($conn,$sql);
if(mysqli_affected_rows($conn)){
	$response['booked'] = True;
	$response['message'] = "Event is successfully unregistered";
}else {
	$response['booked'] = False;
	$response['message'] = "Failed to unregister an event: $conn->error. Please contact admin";
}
echo mysqli_error($conn);
echo json_encode($response,JSON_PRETTY_PRINT);

?>

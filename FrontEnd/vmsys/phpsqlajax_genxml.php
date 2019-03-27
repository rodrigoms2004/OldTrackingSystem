<?php
require("phpsqlajax_dbinfo.php");

// Start XML file, create parent node
$dom = new DOMDocument("1.0");
$node = $dom->createElement("markers");
$parnode = $dom->appendChild($node);

// Opens a connection to a MySQL server
$connection = mysql_connect($db_host, $username, $password);
 
if(!$connection) { die('Not connected : ' . mysql_error()); }

// Set the active MySQL database
$db_selected = mysql_select_db($database, $connection);
if(!$db_selected) { die('Can\'t use db : ' . mysqlerror()); }

// Select all the rows in the markers table
$query = "SELECT * FROM monitor WHERE 1";
$result = mysql_query($query);
if(!$result) { die('Invalid query: ' . mysql_error()); }

header("Content-type: text/xml");

// Iterate through the rows, adding XML nodes for each

while ($row = @mysql_fetch_assoc($result)) {
	// ADD TO XML DOCUMENT NODE
	$node = $dom->createElement("marker");
	$newnode = $parnode->appendChild($node);
	$newnode->setAttribute("latitude", $row['latitude']);
	$newnode->setAttribute("longitude", $row['longitude']);
	$newnode->setAttribute("timestp", $row['timestp']);
	$newnode->setAttribute("RSSI", $row['RSSI']);
}	// end while

echo $dom->saveXML();

?>

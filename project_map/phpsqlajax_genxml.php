<?php
require("phpsqlajax_dbinfo.php");

// Start XML file, create parent node
$doc = domxml_new_doc("1.0");
$node = $doc->create_element("markers");
$parnode = $doc->append_child($node);

// Opens a connection to a MySQL server
$connection=mysql_connect (localhost, $username, $password);
if (!$connection) {
  die('Not connected : ' . mysql_error());
}

// Set the active MySQL database
$db_selected = mysql_select_db($database, $connection);
if (!$db_selected) {
  die ('Can\'t use db : ' . mysql_error());
}

//Gets the information from GET data
$user = $_GET['user'];
$farm = $_GET['farm']
$sheep = $_GET['sheep'];


$split = preg_split('/[,]+/', $sheep);
$len = count($split);

if($sheep = 'all') //A query for gettig all the the sheeps latest positions
{
  $query = $sheep;
}
else
{
  
  if($len == 0)
  {
    die('Invalid url data: ' . mysql_error());
  }
  else if ($len == 1 { //One sheep selected. Get all the the positions for that sheep
    $query =;
  }
  else //Some sheeps
  {

  }
}

$result = mysql_query($query);
if (!$result) {
  die('Invalid query: ' . mysql_error());
}

header("Content-type: text/xml");

// Iterate through the rows, adding XML nodes for each
while ($row = @mysql_fetch_assoc($result)){
  // ADD TO XML DOCUMENT NODE
  $node = $doc->create_element("marker");
  $newnode = $parnode->append_child($node);

  $newnode->set_attribute("name", $row['name']);
  $newnode->set_attribute("lat", $row['lat']);
  $newnode->set_attribute("lng", $row['lng']);
}

$xmlfile = $doc->dump_mem();
echo $xmlfile;

?>
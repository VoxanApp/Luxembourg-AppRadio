<?php
session_start();
unset($_SESSION["adminuser"]);
session_destroy();
header("location:"."home");
?>

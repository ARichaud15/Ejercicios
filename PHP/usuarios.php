<?php

    //Se obtiene los datos ingresados en los campos de texto del HTML y guardados en variable para ser utilizados posteriormente 
    $nombre = $_POST['txtNombre'];
	$direccion = $_POST['txtDireccion'];
	$telefono = $_POST['txtTelefono'];
	$correo = $_POST['txtCorreo'];
	$password = $_POST['txtContraseña'];
	
    //Se crea una conexion hacia la DB (Direccion, Usuario, Contraseña, Nombre de la DB)
    $conexion = new mysqli ( '127.0.0.1', 'root', '', 'db_usuarios');

    $query = " INSERT INTO usuarios (`Nombre`, `Direccion`, `Telefono`, `Correo`, `Password`) VALUES ( '$nombre' , '$direccion' , '$telefono', '$correo', '$password'); ";
?>

<html lang="es">
    <head> 
        <title> Registro de usuarios </title>
        
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="keywords" content="" />
        <meta http-equiv="refresh" content="1; url=http://localhost/PHP/index.html">
    </head>
    
    <body>
        <?php
            //Se verifica que el query haya realizado correctamente la insercion de los datos hacia la DB 
            if ($conexion->query($query) === TRUE) 
                echo "<br> <p align = 'center'> <h2> Usuario registrado correctamente </h2> </p>";
            else 
                echo "<p align = 'center'> <h2> UPS! <br> a ocurrido un error </h2> </p>"; 
    
            //Cerramos la conexion de la DB
            $conexion -> close();
        ?>
    </body>
</html>


   
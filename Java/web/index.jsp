<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head> 
        <title> Registro de usuarios </title>
        
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>

    <body>
        <h1> Registro de usuarios </h1>
        
        <%
            if(request.getAttribute("error") != null )
            {   
                out.println("<p> <h2>" + request.getAttribute("error") + " </h2> </p>");
            }
        %>

        <form method = "POST" action = "registro_usuarios">
            <table border = "0">
                <tr>
                    <td> Nombre: </td>
                    <td align = "center"><input type = "text" name = "txtNombre" size = "30" maxlength = "30"  /></td>
                </tr>
                        
                <tr>
                    <td> Direccion: </td>
                    <td align = "center"><input type = "text" name = "txtDireccion" size = "30" maxlength = "100"  /></td>
                </tr>
                        
                <tr>
                    <td> Telefono: </td>
                    <td align = "center"><input type = "number" name = "txtTelefono" size = "30" maxlength = "10"  /></td>
                </tr>
                        
                <tr>
                    <td> Correo: </td>
                    <td align = "center"><input type = "email" name = "txtCorreo" size = "30" maxlength = "50"  /></td>
                </tr>
                        
                <tr>
                    <td> Contraseña: </td>
                    <td align = "center"><input type = "text" name = "txtPassword" size = "30" maxlength = "16"  /></td>
                </tr>

                <tr>
                    <td colspan = "2" align = "center"><input type = "submit" value = "Registrar"  /></td>
                </tr>
            </table>
        </form>
    </body>
</html>

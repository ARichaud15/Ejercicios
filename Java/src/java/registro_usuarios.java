import java.io.IOException;
import java.io.PrintWriter;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class registro_usuarios extends HttpServlet 
{
    private Statement statment = null;
    private Connection conexion = null;
    
    // Definimos el driver y la url
    String Driver = "com.mysql.jdbc.Driver";
    String URL = "jdbc:mysql://localhost:3306/db_usuarios";
    String user = "root";
    String password = "";

    //Al iniciar el servlet es la primera clase que inicia
    //Esta clase se encarga de realizar la conexion con la DB, ademas
    //de configurar los parametros para realizar con exito la conexion 
    public void init(ServletConfig config) 
    {
        //Se implementa un Try-Catch que ayuda gestionar los errores que puedan 
        //ocurrir en el proceso de conexion de la DB
        try 
        {
            Class.forName(Driver).newInstance();
            conexion = DriverManager.getConnection(URL, user, password);
            statment = conexion.createStatement();
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(registro_usuarios.class.getName()).log(Level.SEVERE,
                "No se pudo cargar el driver de la base de datos", ex);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(registro_usuarios.class.getName()).log(Level.SEVERE,
                "No se pudo obtener la conexión a la base de datos", ex);
        } 
        catch (InstantiationException ex) 
        {
            Logger.getLogger(registro_usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IllegalAccessException ex) 
        {
            Logger.getLogger(registro_usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    //Esta clase se encarga de recibir los datos del formulario por el metodo POST
    //ademas de la insercion de los datos en la DB 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        ServletContext contexto = request.getServletContext();
        ResultSet resultSet = null;
        String query = null;
        
        //Se obtiene los datos ingresados en los campos de texto del HTML y guardados en variable para ser utilizados posteriormente 
        String Nombre = request.getParameter("txtNombre");
        String Direccion = request.getParameter("txtDireccion");
        String Telefono = request.getParameter("txtTelefono");
        String Correo = request.getParameter("txtCorreo");
        String Password = request.getParameter("txtPassword");
        
        //Se implementa un Try-Catch que ayuda gestionar los errores que puedan 
        //ocurrir en el proceso de insercion de la DB
        try 
        {
            query = "INSERT INTO usuarios (Nombre, Direccion, Telefono, Correo, Password) VALUES ('"+ Nombre + "','" + Direccion + "','" + Telefono + "','" + Correo + "','" + Password +"')";
            synchronized(statment)
            {
                statment.executeUpdate(query);
            }
        } 
        catch (SQLException ex) 
        {
            gestionarErrorEnConsultaSQL(ex,  request, response);
        }
        finally //se encarga de siempre cerrar la conexion de la DB
        {
            if (resultSet != null) 
            {
                try 
                {
                    resultSet.close();
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(registro_usuarios.class.getName()).log(Level.SEVERE,
                        "No se pudo cerrar el Resulset", ex);
                }
            }
        }
        
        //Se envia un mensaje que indica que los datos fueron insertados correctamente
        request.setAttribute("error", "Usuario registrado correctamente");
        //Se declara la pagina a la cual se desea regresar una vez terminado la insercion
        RequestDispatcher FormularioJSP = contexto.getRequestDispatcher("/index.jsp");
        //una vez insertados los datos, se redireccina de nuevo al formulario
        FormularioJSP.forward(request, response);
    }
    
    //Esta clase se encarga de gestionar los errores que puedan 
    //ocurrir en el proceso de insercion hacia la DB
    private void gestionarErrorEnConsultaSQL(SQLException ex, HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException 
    {
        ServletContext contexto  = request.getServletContext();
        Logger.getLogger(registro_usuarios.class.getName()).log(Level.SEVERE, "No se pudo realizar la insercion en la base de datos", ex);
        
        //Se envia un mensaje que indica que hubo un error al insertar los datos
        request.setAttribute("error", "UPS! a ocurrido un error");
        //Se declara la pagina a la cual se desea regresar
        RequestDispatcher paginaError = contexto.getRequestDispatcher("/index.jsp");
        paginaError.forward(request, response);
    }
}

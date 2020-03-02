from flask import Flask, render_template, request, flash, redirect, url_for
from flask_mysqldb import MySQL

app = Flask(__name__)
app.secret_key = b'_5#y2L"F4Q8z\n\xec]/' #Se crea una key que ayuda a mantener la integridad de los flash y de los datos enviados atravez del formulario 

#Configuracion de los datos para realizar la conexion hacia la DB
app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = ''
app.config['MYSQL_DB'] = 'db_usuarios'

mysql = MySQL(app)

#Se crea una ruta de acceso hacia el formulario HTML   
@app.route("/", methods=['GET', 'POST'])
#Se crea un manejador de solicitudes que se encargar de controlar las peticiones del formulario
def usuarios():
    #Valida que el formulario envie datos por medio del metodo POST
    if request.method == "POST":
        #Se obtiene los datos ingresados en los campos de texto del HTML y guardados en variable para ser utilizados posteriormente 
        Nombre = request.form["txtNombre"]
        Direccion = request.form["txtDireccion"]
        Telefono = request.form["txtTelefono"]
        Correo = request.form["txtCorreo"]
        Password = request.form["txtContraseña"]

        pool = mysql.connection.cursor() #Se crea un pool conexion hacia la DB
        pool.execute("INSERT INTO usuarios (Nombre, Direccion, Telefono, Correo, Password) VALUES ('"+ Nombre + "','" + Direccion + "','" + Telefono + "','" + Correo + "','" + Password +"')" )
        mysql.connection.commit() #Se encagar de realizar la insercio  en la DB
        pool.close() #Cierra la conexion de la DB
         
        flash("Usuario registrado correctamente") #Se crea un mensaje para mantener informado al usuario
        return redirect(url_for('usuarios')) #Se redirecciona nuevamente al manejador de solicutdes para poder realizar mas inserciones a la DB si es necesariio
    
    return render_template("index.html") #Se encarga de cargar la pagina HTML cada vez que se ingresa a la ruta

#Verifica que se ejecute el programa principal
if __name__ == "__main__":
    #Se ejecuta la aplicacion en el puerto 3000 y se habilita el modo debug
    app.run(port = 3000, debug = True)
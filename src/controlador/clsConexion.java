/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author 57315
 */

public class clsConexion {

    // Configuracion de la conexion a la base de datos
    private String url = "";
    public Connection con = null;
    private Statement stmt = null;//instruciones 
    private ResultSet rs = null;//resultados de las consultas sql
//Constructor sin parmetros

    public clsConexion() {
        url = "jdbc:sqlite:C:\\bases_de_datos\\Farmacia.db";
        try {//if 
// Realizar la conexion
            con = DriverManager.getConnection(url);// reconozca driver de datos 
            if (con != null) {
                DatabaseMetaData meta = con.getMetaData();
              //  JOptionPane.showMessageDialog(null, "Base de datos conectada " + meta.getDriverName());
            }
        } catch (SQLException ex) {//else 
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
//Retornar la conexin

    public Connection getConnection() {
        return con;
    }
//Cerrar la conexin

    public void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(clsConexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cerrarConexion() {
        closeConnection(con);
    }

    //reactiva las transacciones automáticas para que el motor 
    //funcione como mejor lo pueda hacer cuando ejecutemos una sentencia SQL
    public boolean setAutoCommitBD(boolean parametro) {
        try {
            con.setAutoCommit(parametro);
        } catch (SQLException sqlex) {
            System.out.println("Error al configurar el autoCommit " + sqlex.getMessage());
            return false;
        }
        return true;
    }
//Una transacción SQL finaliza con un COMMIT , 
//para aceptar todos los cambios que la transacción 
//ha realizado en la base de datos

    public boolean commitBD() {
        try {
            con.commit();
            return true;
        } catch (SQLException sqlex) {
            System.out.println("Error al hacer commit " + sqlex.getMessage());
            return false;
        }
    }

//Una transacción SQL finaliza con un ROLLBACK  , 
//para deshacer los cambios que la transacción 
//ha realizado en la base de datos
    public boolean rollbackBD() {
        try {
            con.rollback();
            return true;
        } catch (SQLException sqlex) {
            System.out.println("Error al hacer rollback " + sqlex.getMessage());
            return false;
        }
    }
// Metodos que realiza una operacin como UPDATE, DELETE, CREATE TABLE, entre otras
// y devuelve TRUE si la operacin fue existosa
// Metodo que realiza un INSERT y devuelve TRUE si la operacin fue existosa
    
    
    public boolean insertarBD(String sentencia) {
        try {
            stmt = con.createStatement();
            stmt.execute(sentencia);
        } catch (SQLException | RuntimeException sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex);
            return false;
        }
        return true;//retorna positivo el correcto
    }

    // Metodo que devuelve un ResultSet de una consulta (tratamiento de SELECT)
    public ResultSet consultarBD(String sentencia) {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sentencia);
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return rs;
    }
    public boolean actualizarBD(String sentencia) {
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sentencia);
        } catch (SQLException | RuntimeException sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex);
            return false;
        }
        return true;
    }

    public boolean borrarBD(String sentencia) {
        try {
            stmt = con.createStatement();
            stmt.execute(sentencia);
        } catch (SQLException | RuntimeException sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex);
            return false;
        }
        return true;
    }

}

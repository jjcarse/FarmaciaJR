/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.clsConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 57315
 */
public class Producto {
    private String nombre;
    private String id;
    private double temperatura;
    private double valorBase;
    
    //constructor

    public Producto(String nombre, String id, double temperatura, double valorBase) {
        this.nombre = nombre;
        this.id = id;
        this.temperatura = temperatura;
        this.valorBase = valorBase;
    }

    public Producto() {
    }
    //Encapsulacion

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }
    
    public double calcularCostoDeAlmacenamiento(){
        double costo=0;
        if(getTemperatura()> 21){
             ProductoNoRefrigerado  p = new ProductoNoRefrigerado();
             p.setValorBase(valorBase);
             costo=p.calcularCostoDeAlmacenamiento();
        }if(getTemperatura()< 21){
            ProductoRefrigerado  p = new ProductoRefrigerado();
             p.setValorBase(valorBase);
             costo=p.calcularCostoDeAlmacenamiento();        
        }
        return costo;
    }
    
    

    @Override
    public String toString() {
        return "Producto{" + "nombre=" + nombre + ", id=" + id + ", temperatura=" + temperatura + ", valorBase=" + valorBase + '}';  
    }
    
    //metodos
    public boolean guardarProducto() {
        clsConexion conexion = new clsConexion();
        String sql = "INSERT INTO Producto(id,nombre,temperatura,valorBase, costo)"
                + "VALUES("+this.id+",'" + this.nombre + "'," + this.temperatura + "," + this.valorBase + "," + calcularCostoDeAlmacenamiento() + ");";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.insertarBD(sql)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }
//Listar productos
    public List<Producto> listarProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        clsConexion conexion = new clsConexion();
        String sql = "select * from producto;";
        try {
            ResultSet rs = conexion.consultarBD(sql);
            Producto p;
            while (rs.next()) {
                p = new Producto();
                p.setId(rs.getString(1));              
                p.setNombre(rs.getString(2));
                p.setTemperatura(rs.getDouble(3));
                p.setValorBase(rs.getDouble(4));
                p.calcularCostoDeAlmacenamiento();
                listaProductos.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
        return listaProductos;
}
    public  List<Producto> Buscar() {
        
        List<Producto> Buscar = new ArrayList<>();
        clsConexion conexion = new clsConexion();
        String sql = "SELECT * FROM producto WHERE id=" + this.id +";";
        try {
            ResultSet rs = conexion.consultarBD(sql);
            Producto p;
            while (rs.next()) {
                p = new Producto();
                p.setId(rs.getString(1));              
                p.setNombre(rs.getString(2));
                p.setTemperatura(rs.getDouble(3));
                p.setValorBase(rs.getDouble(4));
                p.calcularCostoDeAlmacenamiento();
                Buscar.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
            
        } finally {
            conexion.cerrarConexion();
        }
        return Buscar;
}
    public boolean actualizarProducto(){
        clsConexion conexion = new clsConexion();
        String sentencia = "UPDATE producto SET "
                         + "id = '" + this.id
                         + "',nombre = '" + this.nombre
                         + "', temperatura = " + this.temperatura
                         + ", valorBase = " + this.valorBase
                         + ", costo= " + calcularCostoDeAlmacenamiento()
                         + " WHERE id ='"+ this.id +"';";
        System.out.println(sentencia);
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.actualizarBD(sentencia)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }
    
    public boolean eliminarProducto(){
        clsConexion conexion =new clsConexion();
        String sql = "DELETE FROM producto WHERE id=" + this.id +";";
                if(conexion.setAutoCommitBD(false)){
                    if (conexion.actualizarBD(sql)){
                        conexion.commitBD();
                        conexion.cerrarConexion();
                        return true;
                    }else{
                        conexion.rollbackBD();
                        conexion.cerrarConexion();
                        return false;
                    }
                }else{
                    conexion.cerrarConexion();
                    return false;
                }  
    }
}
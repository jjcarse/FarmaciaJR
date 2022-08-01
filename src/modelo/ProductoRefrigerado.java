/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author 57315
 */
public class ProductoRefrigerado  extends Producto{
    public ProductoRefrigerado(String nombre, String id, double temperatura, double valorBase) {
        super(nombre, id, temperatura, valorBase);
    }
 
    public ProductoRefrigerado() {
        
    }
    @Override
    public double calcularCostoDeAlmacenamiento(){
        return getValorBase()*120/100;
    }
    
}

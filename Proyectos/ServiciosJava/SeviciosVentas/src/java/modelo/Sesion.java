/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;

/**
 *
 * @author raymundo
 */
public class Sesion {
    private static final long TIEMPO_VIDA = 1800000L;
    private String id;
    private int idEmpleado;
    private Date horaAccion;
    
   public Sesion(String id, int idEmpleado,Date horaAccion){
       this.id = id;
       this.idEmpleado = idEmpleado;
       this.horaAccion = horaAccion;
   }
   
   public void actualizarTiempo(){
       horaAccion = new Date();
   }
    
}

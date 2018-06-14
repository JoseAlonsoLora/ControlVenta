/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author raymundo
 */
@Embeddable
public class VentaHasArticuloPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Venta_idVenta")
    private int ventaidVenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Articulo_idArticulo")
    private int articuloidArticulo;

    public VentaHasArticuloPK() {
    }

    public VentaHasArticuloPK(int ventaidVenta, int articuloidArticulo) {
        this.ventaidVenta = ventaidVenta;
        this.articuloidArticulo = articuloidArticulo;
    }

    public int getVentaidVenta() {
        return ventaidVenta;
    }

    public void setVentaidVenta(int ventaidVenta) {
        this.ventaidVenta = ventaidVenta;
    }

    public int getArticuloidArticulo() {
        return articuloidArticulo;
    }

    public void setArticuloidArticulo(int articuloidArticulo) {
        this.articuloidArticulo = articuloidArticulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) ventaidVenta;
        hash += (int) articuloidArticulo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentaHasArticuloPK)) {
            return false;
        }
        VentaHasArticuloPK other = (VentaHasArticuloPK) object;
        if (this.ventaidVenta != other.ventaidVenta) {
            return false;
        }
        if (this.articuloidArticulo != other.articuloidArticulo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.VentaHasArticuloPK[ ventaidVenta=" + ventaidVenta + ", articuloidArticulo=" + articuloidArticulo + " ]";
    }
    
}

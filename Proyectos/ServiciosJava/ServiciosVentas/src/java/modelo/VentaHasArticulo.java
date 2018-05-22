/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author raymundo
 */
@Entity
@Table(name = "venta_has_articulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentaHasArticulo.findAll", query = "SELECT v FROM VentaHasArticulo v")
    , @NamedQuery(name = "VentaHasArticulo.findByVentaidVenta", query = "SELECT v FROM VentaHasArticulo v WHERE v.ventaHasArticuloPK.ventaidVenta = :ventaidVenta")
    , @NamedQuery(name = "VentaHasArticulo.findByArticuloidArticulo", query = "SELECT v FROM VentaHasArticulo v WHERE v.ventaHasArticuloPK.articuloidArticulo = :articuloidArticulo")
    , @NamedQuery(name = "VentaHasArticulo.findByCantidad", query = "SELECT v FROM VentaHasArticulo v WHERE v.cantidad = :cantidad")
    , @NamedQuery(name = "VentaHasArticulo.findBySubtotal", query = "SELECT v FROM VentaHasArticulo v WHERE v.subtotal = :subtotal")})
public class VentaHasArticulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VentaHasArticuloPK ventaHasArticuloPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "subtotal")
    private double subtotal;
    @JoinColumn(name = "Articulo_idArticulo", referencedColumnName = "idArticulo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Articulo articulo;
    @JoinColumn(name = "Venta_idVenta", referencedColumnName = "idVenta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Venta venta;

    public VentaHasArticulo() {
    }

    public VentaHasArticulo(VentaHasArticuloPK ventaHasArticuloPK) {
        this.ventaHasArticuloPK = ventaHasArticuloPK;
    }

    public VentaHasArticulo(VentaHasArticuloPK ventaHasArticuloPK, int cantidad, double subtotal) {
        this.ventaHasArticuloPK = ventaHasArticuloPK;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public VentaHasArticulo(int ventaidVenta, int articuloidArticulo) {
        this.ventaHasArticuloPK = new VentaHasArticuloPK(ventaidVenta, articuloidArticulo);
    }

    public VentaHasArticuloPK getVentaHasArticuloPK() {
        return ventaHasArticuloPK;
    }

    public void setVentaHasArticuloPK(VentaHasArticuloPK ventaHasArticuloPK) {
        this.ventaHasArticuloPK = ventaHasArticuloPK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ventaHasArticuloPK != null ? ventaHasArticuloPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentaHasArticulo)) {
            return false;
        }
        VentaHasArticulo other = (VentaHasArticulo) object;
        if ((this.ventaHasArticuloPK == null && other.ventaHasArticuloPK != null) || (this.ventaHasArticuloPK != null && !this.ventaHasArticuloPK.equals(other.ventaHasArticuloPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.VentaHasArticulo[ ventaHasArticuloPK=" + ventaHasArticuloPK + " ]";
    }
    
}

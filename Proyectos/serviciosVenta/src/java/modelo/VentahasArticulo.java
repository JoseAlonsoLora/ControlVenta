/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "Venta_has_Articulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentahasArticulo.findAll", query = "SELECT v FROM VentahasArticulo v")
    , @NamedQuery(name = "VentahasArticulo.findByCantidad", query = "SELECT v FROM VentahasArticulo v WHERE v.cantidad = :cantidad")
    , @NamedQuery(name = "VentahasArticulo.findBySubtotal", query = "SELECT v FROM VentahasArticulo v WHERE v.subtotal = :subtotal")
    , @NamedQuery(name = "VentahasArticulo.findByVentaidVenta", query = "SELECT v FROM VentahasArticulo v WHERE v.ventaidVenta = :ventaidVenta")})
public class VentahasArticulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "subtotal")
    private double subtotal;
    @Id
    @Basic(optional = false)
    @Column(name = "Venta_idVenta")
    private Integer ventaidVenta;
    @JoinColumn(name = "Articulo_idArticulo", referencedColumnName = "idArticulo")
    @ManyToOne(optional = false)
    private Articulo articuloidArticulo;
    @JoinColumn(name = "Venta_idVenta", referencedColumnName = "idVenta", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Venta venta;

    public VentahasArticulo() {
    }

    public VentahasArticulo(Integer ventaidVenta) {
        this.ventaidVenta = ventaidVenta;
    }

    public VentahasArticulo(Integer ventaidVenta, int cantidad, double subtotal) {
        this.ventaidVenta = ventaidVenta;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
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

    public Integer getVentaidVenta() {
        return ventaidVenta;
    }

    public void setVentaidVenta(Integer ventaidVenta) {
        this.ventaidVenta = ventaidVenta;
    }

    public Articulo getArticuloidArticulo() {
        return articuloidArticulo;
    }

    public void setArticuloidArticulo(Articulo articuloidArticulo) {
        this.articuloidArticulo = articuloidArticulo;
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
        hash += (ventaidVenta != null ? ventaidVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentahasArticulo)) {
            return false;
        }
        VentahasArticulo other = (VentahasArticulo) object;
        if ((this.ventaidVenta == null && other.ventaidVenta != null) || (this.ventaidVenta != null && !this.ventaidVenta.equals(other.ventaidVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.VentahasArticulo[ ventaidVenta=" + ventaidVenta + " ]";
    }
    
}

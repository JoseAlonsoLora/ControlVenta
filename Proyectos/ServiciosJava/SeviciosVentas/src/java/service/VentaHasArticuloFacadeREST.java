/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import modelo.VentaHasArticulo;
import modelo.VentaHasArticuloPK;

/**
 *
 * @author raymundo
 */
@Stateless
@Path("modelo.ventahasarticulo")
public class VentaHasArticuloFacadeREST extends AbstractFacade<VentaHasArticulo> {

    @PersistenceContext(unitName = "SeviciosVentasPU")
    private EntityManager em;

    private VentaHasArticuloPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;ventaidVenta=ventaidVentaValue;articuloidArticulo=articuloidArticuloValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        modelo.VentaHasArticuloPK key = new modelo.VentaHasArticuloPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> ventaidVenta = map.get("ventaidVenta");
        if (ventaidVenta != null && !ventaidVenta.isEmpty()) {
            key.setVentaidVenta(new java.lang.Integer(ventaidVenta.get(0)));
        }
        java.util.List<String> articuloidArticulo = map.get("articuloidArticulo");
        if (articuloidArticulo != null && !articuloidArticulo.isEmpty()) {
            key.setArticuloidArticulo(new java.lang.Integer(articuloidArticulo.get(0)));
        }
        return key;
    }

    public VentaHasArticuloFacadeREST() {
        super(VentaHasArticulo.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(VentaHasArticulo entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, VentaHasArticulo entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        modelo.VentaHasArticuloPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public VentaHasArticulo find(@PathParam("id") PathSegment id) {
        modelo.VentaHasArticuloPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<VentaHasArticulo> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<VentaHasArticulo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

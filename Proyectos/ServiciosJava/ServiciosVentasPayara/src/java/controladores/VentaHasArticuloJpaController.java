/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import controladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Articulo;
import modelo.Venta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.VentaHasArticulo;

/**
 *
 * @author alonso
 */
public class VentaHasArticuloJpaController implements Serializable {

    public VentaHasArticuloJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentaHasArticulo ventaHasArticulo) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Venta ventaOrphanCheck = ventaHasArticulo.getVenta();
        if (ventaOrphanCheck != null) {
            VentaHasArticulo oldVentaHasArticuloOfVenta = ventaOrphanCheck.getVentaHasArticulo();
            if (oldVentaHasArticuloOfVenta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Venta " + ventaOrphanCheck + " already has an item of type VentaHasArticulo whose venta column cannot be null. Please make another selection for the venta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Articulo articuloidArticulo = ventaHasArticulo.getArticuloidArticulo();
            if (articuloidArticulo != null) {
                articuloidArticulo = em.getReference(articuloidArticulo.getClass(), articuloidArticulo.getIdArticulo());
                ventaHasArticulo.setArticuloidArticulo(articuloidArticulo);
            }
            Venta venta = ventaHasArticulo.getVenta();
            if (venta != null) {
                venta = em.getReference(venta.getClass(), venta.getIdVenta());
                ventaHasArticulo.setVenta(venta);
            }
            em.persist(ventaHasArticulo);
            if (articuloidArticulo != null) {
                articuloidArticulo.getVentaHasArticuloList().add(ventaHasArticulo);
                articuloidArticulo = em.merge(articuloidArticulo);
            }
            if (venta != null) {
                venta.setVentaHasArticulo(ventaHasArticulo);
                venta = em.merge(venta);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findVentaHasArticulo(ventaHasArticulo.getVentaidVenta()) != null) {
                throw new PreexistingEntityException("VentaHasArticulo " + ventaHasArticulo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentaHasArticulo ventaHasArticulo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            VentaHasArticulo persistentVentaHasArticulo = em.find(VentaHasArticulo.class, ventaHasArticulo.getVentaidVenta());
            Articulo articuloidArticuloOld = persistentVentaHasArticulo.getArticuloidArticulo();
            Articulo articuloidArticuloNew = ventaHasArticulo.getArticuloidArticulo();
            Venta ventaOld = persistentVentaHasArticulo.getVenta();
            Venta ventaNew = ventaHasArticulo.getVenta();
            List<String> illegalOrphanMessages = null;
            if (ventaNew != null && !ventaNew.equals(ventaOld)) {
                VentaHasArticulo oldVentaHasArticuloOfVenta = ventaNew.getVentaHasArticulo();
                if (oldVentaHasArticuloOfVenta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Venta " + ventaNew + " already has an item of type VentaHasArticulo whose venta column cannot be null. Please make another selection for the venta field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (articuloidArticuloNew != null) {
                articuloidArticuloNew = em.getReference(articuloidArticuloNew.getClass(), articuloidArticuloNew.getIdArticulo());
                ventaHasArticulo.setArticuloidArticulo(articuloidArticuloNew);
            }
            if (ventaNew != null) {
                ventaNew = em.getReference(ventaNew.getClass(), ventaNew.getIdVenta());
                ventaHasArticulo.setVenta(ventaNew);
            }
            ventaHasArticulo = em.merge(ventaHasArticulo);
            if (articuloidArticuloOld != null && !articuloidArticuloOld.equals(articuloidArticuloNew)) {
                articuloidArticuloOld.getVentaHasArticuloList().remove(ventaHasArticulo);
                articuloidArticuloOld = em.merge(articuloidArticuloOld);
            }
            if (articuloidArticuloNew != null && !articuloidArticuloNew.equals(articuloidArticuloOld)) {
                articuloidArticuloNew.getVentaHasArticuloList().add(ventaHasArticulo);
                articuloidArticuloNew = em.merge(articuloidArticuloNew);
            }
            if (ventaOld != null && !ventaOld.equals(ventaNew)) {
                ventaOld.setVentaHasArticulo(null);
                ventaOld = em.merge(ventaOld);
            }
            if (ventaNew != null && !ventaNew.equals(ventaOld)) {
                ventaNew.setVentaHasArticulo(ventaHasArticulo);
                ventaNew = em.merge(ventaNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventaHasArticulo.getVentaidVenta();
                if (findVentaHasArticulo(id) == null) {
                    throw new NonexistentEntityException("The ventaHasArticulo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            VentaHasArticulo ventaHasArticulo;
            try {
                ventaHasArticulo = em.getReference(VentaHasArticulo.class, id);
                ventaHasArticulo.getVentaidVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventaHasArticulo with id " + id + " no longer exists.", enfe);
            }
            Articulo articuloidArticulo = ventaHasArticulo.getArticuloidArticulo();
            if (articuloidArticulo != null) {
                articuloidArticulo.getVentaHasArticuloList().remove(ventaHasArticulo);
                articuloidArticulo = em.merge(articuloidArticulo);
            }
            Venta venta = ventaHasArticulo.getVenta();
            if (venta != null) {
                venta.setVentaHasArticulo(null);
                venta = em.merge(venta);
            }
            em.remove(ventaHasArticulo);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentaHasArticulo> findVentaHasArticuloEntities() {
        return findVentaHasArticuloEntities(true, -1, -1);
    }

    public List<VentaHasArticulo> findVentaHasArticuloEntities(int maxResults, int firstResult) {
        return findVentaHasArticuloEntities(false, maxResults, firstResult);
    }

    private List<VentaHasArticulo> findVentaHasArticuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentaHasArticulo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public VentaHasArticulo findVentaHasArticulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentaHasArticulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaHasArticuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentaHasArticulo> rt = cq.from(VentaHasArticulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.VentaHasArticulo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.Articulo;

/**
 *
 * @author alonso
 */
public class ArticuloJpaController implements Serializable {

    public ArticuloJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articulo articulo) throws RollbackFailureException, Exception {
        if (articulo.getVentaHasArticuloList() == null) {
            articulo.setVentaHasArticuloList(new ArrayList<VentaHasArticulo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<VentaHasArticulo> attachedVentaHasArticuloList = new ArrayList<VentaHasArticulo>();
            for (VentaHasArticulo ventaHasArticuloListVentaHasArticuloToAttach : articulo.getVentaHasArticuloList()) {
                ventaHasArticuloListVentaHasArticuloToAttach = em.getReference(ventaHasArticuloListVentaHasArticuloToAttach.getClass(), ventaHasArticuloListVentaHasArticuloToAttach.getVentaidVenta());
                attachedVentaHasArticuloList.add(ventaHasArticuloListVentaHasArticuloToAttach);
            }
            articulo.setVentaHasArticuloList(attachedVentaHasArticuloList);
            em.persist(articulo);
            for (VentaHasArticulo ventaHasArticuloListVentaHasArticulo : articulo.getVentaHasArticuloList()) {
                Articulo oldArticuloidArticuloOfVentaHasArticuloListVentaHasArticulo = ventaHasArticuloListVentaHasArticulo.getArticuloidArticulo();
                ventaHasArticuloListVentaHasArticulo.setArticuloidArticulo(articulo);
                ventaHasArticuloListVentaHasArticulo = em.merge(ventaHasArticuloListVentaHasArticulo);
                if (oldArticuloidArticuloOfVentaHasArticuloListVentaHasArticulo != null) {
                    oldArticuloidArticuloOfVentaHasArticuloListVentaHasArticulo.getVentaHasArticuloList().remove(ventaHasArticuloListVentaHasArticulo);
                    oldArticuloidArticuloOfVentaHasArticuloListVentaHasArticulo = em.merge(oldArticuloidArticuloOfVentaHasArticuloListVentaHasArticulo);
                }
            }
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

    public void edit(Articulo articulo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Articulo persistentArticulo = em.find(Articulo.class, articulo.getIdArticulo());
            List<VentaHasArticulo> ventaHasArticuloListOld = persistentArticulo.getVentaHasArticuloList();
            List<VentaHasArticulo> ventaHasArticuloListNew = articulo.getVentaHasArticuloList();
            List<String> illegalOrphanMessages = null;
            for (VentaHasArticulo ventaHasArticuloListOldVentaHasArticulo : ventaHasArticuloListOld) {
                if (!ventaHasArticuloListNew.contains(ventaHasArticuloListOldVentaHasArticulo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VentaHasArticulo " + ventaHasArticuloListOldVentaHasArticulo + " since its articuloidArticulo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<VentaHasArticulo> attachedVentaHasArticuloListNew = new ArrayList<VentaHasArticulo>();
            for (VentaHasArticulo ventaHasArticuloListNewVentaHasArticuloToAttach : ventaHasArticuloListNew) {
                ventaHasArticuloListNewVentaHasArticuloToAttach = em.getReference(ventaHasArticuloListNewVentaHasArticuloToAttach.getClass(), ventaHasArticuloListNewVentaHasArticuloToAttach.getVentaidVenta());
                attachedVentaHasArticuloListNew.add(ventaHasArticuloListNewVentaHasArticuloToAttach);
            }
            ventaHasArticuloListNew = attachedVentaHasArticuloListNew;
            articulo.setVentaHasArticuloList(ventaHasArticuloListNew);
            articulo = em.merge(articulo);
            for (VentaHasArticulo ventaHasArticuloListNewVentaHasArticulo : ventaHasArticuloListNew) {
                if (!ventaHasArticuloListOld.contains(ventaHasArticuloListNewVentaHasArticulo)) {
                    Articulo oldArticuloidArticuloOfVentaHasArticuloListNewVentaHasArticulo = ventaHasArticuloListNewVentaHasArticulo.getArticuloidArticulo();
                    ventaHasArticuloListNewVentaHasArticulo.setArticuloidArticulo(articulo);
                    ventaHasArticuloListNewVentaHasArticulo = em.merge(ventaHasArticuloListNewVentaHasArticulo);
                    if (oldArticuloidArticuloOfVentaHasArticuloListNewVentaHasArticulo != null && !oldArticuloidArticuloOfVentaHasArticuloListNewVentaHasArticulo.equals(articulo)) {
                        oldArticuloidArticuloOfVentaHasArticuloListNewVentaHasArticulo.getVentaHasArticuloList().remove(ventaHasArticuloListNewVentaHasArticulo);
                        oldArticuloidArticuloOfVentaHasArticuloListNewVentaHasArticulo = em.merge(oldArticuloidArticuloOfVentaHasArticuloListNewVentaHasArticulo);
                    }
                }
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
                Integer id = articulo.getIdArticulo();
                if (findArticulo(id) == null) {
                    throw new NonexistentEntityException("The articulo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Articulo articulo;
            try {
                articulo = em.getReference(Articulo.class, id);
                articulo.getIdArticulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<VentaHasArticulo> ventaHasArticuloListOrphanCheck = articulo.getVentaHasArticuloList();
            for (VentaHasArticulo ventaHasArticuloListOrphanCheckVentaHasArticulo : ventaHasArticuloListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articulo (" + articulo + ") cannot be destroyed since the VentaHasArticulo " + ventaHasArticuloListOrphanCheckVentaHasArticulo + " in its ventaHasArticuloList field has a non-nullable articuloidArticulo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(articulo);
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

    public List<Articulo> findArticuloEntities() {
        return findArticuloEntities(true, -1, -1);
    }

    public List<Articulo> findArticuloEntities(int maxResults, int firstResult) {
        return findArticuloEntities(false, maxResults, firstResult);
    }

    private List<Articulo> findArticuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Articulo.class));
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

    public Articulo findArticulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articulo> rt = cq.from(Articulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

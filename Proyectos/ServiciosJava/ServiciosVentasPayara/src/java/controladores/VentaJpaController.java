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
import modelo.Cliente;
import modelo.Empleado;
import modelo.VentaHasArticulo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.Venta;

/**
 *
 * @author alonso
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta venta) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente clienteidCliente = venta.getClienteidCliente();
            if (clienteidCliente != null) {
                clienteidCliente = em.getReference(clienteidCliente.getClass(), clienteidCliente.getIdCliente());
                venta.setClienteidCliente(clienteidCliente);
            }
            Empleado empleadoidEmpleado = venta.getEmpleadoidEmpleado();
            if (empleadoidEmpleado != null) {
                empleadoidEmpleado = em.getReference(empleadoidEmpleado.getClass(), empleadoidEmpleado.getIdEmpleado());
                venta.setEmpleadoidEmpleado(empleadoidEmpleado);
            }
            VentaHasArticulo ventaHasArticulo = venta.getVentaHasArticulo();
            if (ventaHasArticulo != null) {
                ventaHasArticulo = em.getReference(ventaHasArticulo.getClass(), ventaHasArticulo.getVentaidVenta());
                venta.setVentaHasArticulo(ventaHasArticulo);
            }
            em.persist(venta);
            if (clienteidCliente != null) {
                clienteidCliente.getVentaList().add(venta);
                clienteidCliente = em.merge(clienteidCliente);
            }
            if (empleadoidEmpleado != null) {
                empleadoidEmpleado.getVentaList().add(venta);
                empleadoidEmpleado = em.merge(empleadoidEmpleado);
            }
            if (ventaHasArticulo != null) {
                Venta oldVentaOfVentaHasArticulo = ventaHasArticulo.getVenta();
                if (oldVentaOfVentaHasArticulo != null) {
                    oldVentaOfVentaHasArticulo.setVentaHasArticulo(null);
                    oldVentaOfVentaHasArticulo = em.merge(oldVentaOfVentaHasArticulo);
                }
                ventaHasArticulo.setVenta(venta);
                ventaHasArticulo = em.merge(ventaHasArticulo);
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

    public void edit(Venta venta) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Venta persistentVenta = em.find(Venta.class, venta.getIdVenta());
            Cliente clienteidClienteOld = persistentVenta.getClienteidCliente();
            Cliente clienteidClienteNew = venta.getClienteidCliente();
            Empleado empleadoidEmpleadoOld = persistentVenta.getEmpleadoidEmpleado();
            Empleado empleadoidEmpleadoNew = venta.getEmpleadoidEmpleado();
            VentaHasArticulo ventaHasArticuloOld = persistentVenta.getVentaHasArticulo();
            VentaHasArticulo ventaHasArticuloNew = venta.getVentaHasArticulo();
            List<String> illegalOrphanMessages = null;
            if (ventaHasArticuloOld != null && !ventaHasArticuloOld.equals(ventaHasArticuloNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain VentaHasArticulo " + ventaHasArticuloOld + " since its venta field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteidClienteNew != null) {
                clienteidClienteNew = em.getReference(clienteidClienteNew.getClass(), clienteidClienteNew.getIdCliente());
                venta.setClienteidCliente(clienteidClienteNew);
            }
            if (empleadoidEmpleadoNew != null) {
                empleadoidEmpleadoNew = em.getReference(empleadoidEmpleadoNew.getClass(), empleadoidEmpleadoNew.getIdEmpleado());
                venta.setEmpleadoidEmpleado(empleadoidEmpleadoNew);
            }
            if (ventaHasArticuloNew != null) {
                ventaHasArticuloNew = em.getReference(ventaHasArticuloNew.getClass(), ventaHasArticuloNew.getVentaidVenta());
                venta.setVentaHasArticulo(ventaHasArticuloNew);
            }
            venta = em.merge(venta);
            if (clienteidClienteOld != null && !clienteidClienteOld.equals(clienteidClienteNew)) {
                clienteidClienteOld.getVentaList().remove(venta);
                clienteidClienteOld = em.merge(clienteidClienteOld);
            }
            if (clienteidClienteNew != null && !clienteidClienteNew.equals(clienteidClienteOld)) {
                clienteidClienteNew.getVentaList().add(venta);
                clienteidClienteNew = em.merge(clienteidClienteNew);
            }
            if (empleadoidEmpleadoOld != null && !empleadoidEmpleadoOld.equals(empleadoidEmpleadoNew)) {
                empleadoidEmpleadoOld.getVentaList().remove(venta);
                empleadoidEmpleadoOld = em.merge(empleadoidEmpleadoOld);
            }
            if (empleadoidEmpleadoNew != null && !empleadoidEmpleadoNew.equals(empleadoidEmpleadoOld)) {
                empleadoidEmpleadoNew.getVentaList().add(venta);
                empleadoidEmpleadoNew = em.merge(empleadoidEmpleadoNew);
            }
            if (ventaHasArticuloNew != null && !ventaHasArticuloNew.equals(ventaHasArticuloOld)) {
                Venta oldVentaOfVentaHasArticulo = ventaHasArticuloNew.getVenta();
                if (oldVentaOfVentaHasArticulo != null) {
                    oldVentaOfVentaHasArticulo.setVentaHasArticulo(null);
                    oldVentaOfVentaHasArticulo = em.merge(oldVentaOfVentaHasArticulo);
                }
                ventaHasArticuloNew.setVenta(venta);
                ventaHasArticuloNew = em.merge(ventaHasArticuloNew);
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
                Integer id = venta.getIdVenta();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
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
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getIdVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            VentaHasArticulo ventaHasArticuloOrphanCheck = venta.getVentaHasArticulo();
            if (ventaHasArticuloOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Venta (" + venta + ") cannot be destroyed since the VentaHasArticulo " + ventaHasArticuloOrphanCheck + " in its ventaHasArticulo field has a non-nullable venta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente clienteidCliente = venta.getClienteidCliente();
            if (clienteidCliente != null) {
                clienteidCliente.getVentaList().remove(venta);
                clienteidCliente = em.merge(clienteidCliente);
            }
            Empleado empleadoidEmpleado = venta.getEmpleadoidEmpleado();
            if (empleadoidEmpleado != null) {
                empleadoidEmpleado.getVentaList().remove(venta);
                empleadoidEmpleado = em.merge(empleadoidEmpleado);
            }
            em.remove(venta);
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

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
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

    public Venta findVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

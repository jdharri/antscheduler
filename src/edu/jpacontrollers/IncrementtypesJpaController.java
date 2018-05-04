/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jpacontrollers;

import edu.exceptions.NonexistentEntityException;
import edu.exceptions.PreexistingEntityException;
import edu.model.Incrementtypes;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author JoelHarris
 */
public class IncrementtypesJpaController implements Serializable {

    public IncrementtypesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Incrementtypes incrementtypes) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(incrementtypes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIncrementtypes(incrementtypes.getIncrementTypeId()) != null) {
                throw new PreexistingEntityException("Incrementtypes " + incrementtypes +
                        " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Incrementtypes incrementtypes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            incrementtypes = em.merge(incrementtypes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = incrementtypes.getIncrementTypeId();
                if (findIncrementtypes(id) == null) {
                    throw new NonexistentEntityException("The incrementtypes with id " + id +
                            " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incrementtypes incrementtypes;
            try {
                incrementtypes = em.getReference(Incrementtypes.class, id);
                incrementtypes.getIncrementTypeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The incrementtypes with id " + id +
                        " no longer exists.", enfe);
            }
            em.remove(incrementtypes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Incrementtypes> findIncrementtypesEntities() {
        return findIncrementtypesEntities(true, -1, -1);
    }

    public List<Incrementtypes> findIncrementtypesEntities(int maxResults, int firstResult) {
        return findIncrementtypesEntities(false, maxResults, firstResult);
    }

    private List<Incrementtypes> findIncrementtypesEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Incrementtypes.class));
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

    public Incrementtypes findIncrementtypes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Incrementtypes.class, id);
        } finally {
            em.close();
        }
    }

    public int getIncrementtypesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Incrementtypes> rt = cq.from(Incrementtypes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}


package edu.jpacontrollers;

import edu.exceptions.NonexistentEntityException;
import edu.exceptions.PreexistingEntityException;
import edu.model.Reminder;
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
public class ReminderJpaController implements Serializable {

    public ReminderJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reminder reminder) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(reminder);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReminder(reminder.getReminderId()) != null) {
                throw new PreexistingEntityException("Reminder " + reminder + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reminder reminder) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            reminder = em.merge(reminder);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reminder.getReminderId();
                if (findReminder(id) == null) {
                    throw new NonexistentEntityException("The reminder with id " + id +
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
            Reminder reminder;
            try {
                reminder = em.getReference(Reminder.class, id);
                reminder.getReminderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reminder with id " + id +
                        " no longer exists.", enfe);
            }
            em.remove(reminder);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reminder> findReminderEntities() {
        return findReminderEntities(true, -1, -1);
    }

    public List<Reminder> findReminderEntities(int maxResults, int firstResult) {
        return findReminderEntities(false, maxResults, firstResult);
    }

    private List<Reminder> findReminderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reminder.class));
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

    public Reminder findReminder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reminder.class, id);
        } finally {
            em.close();
        }
    }

    public int getReminderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reminder> rt = cq.from(Reminder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

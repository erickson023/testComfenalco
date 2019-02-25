/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Premio;
import modelo.Participante;
import modelo.PptePremio;

/**
 *
 * @author lenovo
 */
public class PptePremioJpaController implements Serializable {

    public PptePremioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PptePremio pptePremio) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Premio codPremio = pptePremio.getCodPremio();
            if (codPremio != null) {
                codPremio = em.getReference(codPremio.getClass(), codPremio.getCodPremio());
                pptePremio.setCodPremio(codPremio);
            }
            Participante numdocPart = pptePremio.getNumdocPart();
            if (numdocPart != null) {
                numdocPart = em.getReference(numdocPart.getClass(), numdocPart.getNumdocPart());
                pptePremio.setNumdocPart(numdocPart);
            }
            em.persist(pptePremio);
            if (codPremio != null) {
                codPremio.getPptePremioList().add(pptePremio);
                codPremio = em.merge(codPremio);
            }
            if (numdocPart != null) {
                numdocPart.getPptePremioList().add(pptePremio);
                numdocPart = em.merge(numdocPart);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPptePremio(pptePremio.getNumSorteo()) != null) {
                throw new PreexistingEntityException("PptePremio " + pptePremio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PptePremio pptePremio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PptePremio persistentPptePremio = em.find(PptePremio.class, pptePremio.getNumSorteo());
            Premio codPremioOld = persistentPptePremio.getCodPremio();
            Premio codPremioNew = pptePremio.getCodPremio();
            Participante numdocPartOld = persistentPptePremio.getNumdocPart();
            Participante numdocPartNew = pptePremio.getNumdocPart();
            if (codPremioNew != null) {
                codPremioNew = em.getReference(codPremioNew.getClass(), codPremioNew.getCodPremio());
                pptePremio.setCodPremio(codPremioNew);
            }
            if (numdocPartNew != null) {
                numdocPartNew = em.getReference(numdocPartNew.getClass(), numdocPartNew.getNumdocPart());
                pptePremio.setNumdocPart(numdocPartNew);
            }
            pptePremio = em.merge(pptePremio);
            if (codPremioOld != null && !codPremioOld.equals(codPremioNew)) {
                codPremioOld.getPptePremioList().remove(pptePremio);
                codPremioOld = em.merge(codPremioOld);
            }
            if (codPremioNew != null && !codPremioNew.equals(codPremioOld)) {
                codPremioNew.getPptePremioList().add(pptePremio);
                codPremioNew = em.merge(codPremioNew);
            }
            if (numdocPartOld != null && !numdocPartOld.equals(numdocPartNew)) {
                numdocPartOld.getPptePremioList().remove(pptePremio);
                numdocPartOld = em.merge(numdocPartOld);
            }
            if (numdocPartNew != null && !numdocPartNew.equals(numdocPartOld)) {
                numdocPartNew.getPptePremioList().add(pptePremio);
                numdocPartNew = em.merge(numdocPartNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pptePremio.getNumSorteo();
                if (findPptePremio(id) == null) {
                    throw new NonexistentEntityException("The pptePremio with id " + id + " no longer exists.");
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
            PptePremio pptePremio;
            try {
                pptePremio = em.getReference(PptePremio.class, id);
                pptePremio.getNumSorteo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pptePremio with id " + id + " no longer exists.", enfe);
            }
            Premio codPremio = pptePremio.getCodPremio();
            if (codPremio != null) {
                codPremio.getPptePremioList().remove(pptePremio);
                codPremio = em.merge(codPremio);
            }
            Participante numdocPart = pptePremio.getNumdocPart();
            if (numdocPart != null) {
                numdocPart.getPptePremioList().remove(pptePremio);
                numdocPart = em.merge(numdocPart);
            }
            em.remove(pptePremio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PptePremio> findPptePremioEntities() {
        return findPptePremioEntities(true, -1, -1);
    }

    public List<PptePremio> findPptePremioEntities(int maxResults, int firstResult) {
        return findPptePremioEntities(false, maxResults, firstResult);
    }

    private List<PptePremio> findPptePremioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PptePremio.class));
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

    public PptePremio findPptePremio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PptePremio.class, id);
        } finally {
            em.close();
        }
    }

    public int getPptePremioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PptePremio> rt = cq.from(PptePremio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public PptePremio getMaxNumSorteo(){
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("PptePremio.findByMaxNumSorteo").setMaxResults(1);
        PptePremio p= (PptePremio) q.getSingleResult();
        return p;
    }
    
    public List<PptePremio> getPptePremioOrderByDesc(){
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("PptePremio.findAll");
        return q.getResultList();
    }
    
}

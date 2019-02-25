/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.PptePremio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Participante;

/**
 *
 * @author lenovo
 */
public class ParticipanteJpaController implements Serializable {

    public ParticipanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Participante participante) throws PreexistingEntityException, Exception {
        if (participante.getPptePremioList() == null) {
            participante.setPptePremioList(new ArrayList<PptePremio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PptePremio> attachedPptePremioList = new ArrayList<PptePremio>();
            for (PptePremio pptePremioListPptePremioToAttach : participante.getPptePremioList()) {
                pptePremioListPptePremioToAttach = em.getReference(pptePremioListPptePremioToAttach.getClass(), pptePremioListPptePremioToAttach.getNumSorteo());
                attachedPptePremioList.add(pptePremioListPptePremioToAttach);
            }
            participante.setPptePremioList(attachedPptePremioList);
            em.persist(participante);
            for (PptePremio pptePremioListPptePremio : participante.getPptePremioList()) {
                Participante oldNumdocPartOfPptePremioListPptePremio = pptePremioListPptePremio.getNumdocPart();
                pptePremioListPptePremio.setNumdocPart(participante);
                pptePremioListPptePremio = em.merge(pptePremioListPptePremio);
                if (oldNumdocPartOfPptePremioListPptePremio != null) {
                    oldNumdocPartOfPptePremioListPptePremio.getPptePremioList().remove(pptePremioListPptePremio);
                    oldNumdocPartOfPptePremioListPptePremio = em.merge(oldNumdocPartOfPptePremioListPptePremio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParticipante(participante.getNumdocPart()) != null) {
                throw new PreexistingEntityException("Participante " + participante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Participante participante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Participante persistentParticipante = em.find(Participante.class, participante.getNumdocPart());
            List<PptePremio> pptePremioListOld = persistentParticipante.getPptePremioList();
            List<PptePremio> pptePremioListNew = participante.getPptePremioList();
            List<String> illegalOrphanMessages = null;
            for (PptePremio pptePremioListOldPptePremio : pptePremioListOld) {
                if (!pptePremioListNew.contains(pptePremioListOldPptePremio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PptePremio " + pptePremioListOldPptePremio + " since its numdocPart field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PptePremio> attachedPptePremioListNew = new ArrayList<PptePremio>();
            for (PptePremio pptePremioListNewPptePremioToAttach : pptePremioListNew) {
                pptePremioListNewPptePremioToAttach = em.getReference(pptePremioListNewPptePremioToAttach.getClass(), pptePremioListNewPptePremioToAttach.getNumSorteo());
                attachedPptePremioListNew.add(pptePremioListNewPptePremioToAttach);
            }
            pptePremioListNew = attachedPptePremioListNew;
            participante.setPptePremioList(pptePremioListNew);
            participante = em.merge(participante);
            for (PptePremio pptePremioListNewPptePremio : pptePremioListNew) {
                if (!pptePremioListOld.contains(pptePremioListNewPptePremio)) {
                    Participante oldNumdocPartOfPptePremioListNewPptePremio = pptePremioListNewPptePremio.getNumdocPart();
                    pptePremioListNewPptePremio.setNumdocPart(participante);
                    pptePremioListNewPptePremio = em.merge(pptePremioListNewPptePremio);
                    if (oldNumdocPartOfPptePremioListNewPptePremio != null && !oldNumdocPartOfPptePremioListNewPptePremio.equals(participante)) {
                        oldNumdocPartOfPptePremioListNewPptePremio.getPptePremioList().remove(pptePremioListNewPptePremio);
                        oldNumdocPartOfPptePremioListNewPptePremio = em.merge(oldNumdocPartOfPptePremioListNewPptePremio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = participante.getNumdocPart();
                if (findParticipante(id) == null) {
                    throw new NonexistentEntityException("The participante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Participante participante;
            try {
                participante = em.getReference(Participante.class, id);
                participante.getNumdocPart();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The participante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PptePremio> pptePremioListOrphanCheck = participante.getPptePremioList();
            for (PptePremio pptePremioListOrphanCheckPptePremio : pptePremioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participante (" + participante + ") cannot be destroyed since the PptePremio " + pptePremioListOrphanCheckPptePremio + " in its pptePremioList field has a non-nullable numdocPart field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(participante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Participante> findParticipanteEntities() {
        return findParticipanteEntities(true, -1, -1);
    }

    public List<Participante> findParticipanteEntities(int maxResults, int firstResult) {
        return findParticipanteEntities(false, maxResults, firstResult);
    }

    private List<Participante> findParticipanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Participante.class));
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

    public Participante findParticipante(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Participante.class, id);
        } finally {
            em.close();
        }
    }

    public int getParticipanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Participante> rt = cq.from(Participante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

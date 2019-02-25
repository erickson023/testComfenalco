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
import modelo.Premio;

/**
 *
 * @author lenovo
 */
public class PremioJpaController implements Serializable {

    public PremioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Premio premio) throws PreexistingEntityException, Exception {
        if (premio.getPptePremioList() == null) {
            premio.setPptePremioList(new ArrayList<PptePremio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PptePremio> attachedPptePremioList = new ArrayList<PptePremio>();
            for (PptePremio pptePremioListPptePremioToAttach : premio.getPptePremioList()) {
                pptePremioListPptePremioToAttach = em.getReference(pptePremioListPptePremioToAttach.getClass(), pptePremioListPptePremioToAttach.getNumSorteo());
                attachedPptePremioList.add(pptePremioListPptePremioToAttach);
            }
            premio.setPptePremioList(attachedPptePremioList);
            em.persist(premio);
            for (PptePremio pptePremioListPptePremio : premio.getPptePremioList()) {
                Premio oldCodPremioOfPptePremioListPptePremio = pptePremioListPptePremio.getCodPremio();
                pptePremioListPptePremio.setCodPremio(premio);
                pptePremioListPptePremio = em.merge(pptePremioListPptePremio);
                if (oldCodPremioOfPptePremioListPptePremio != null) {
                    oldCodPremioOfPptePremioListPptePremio.getPptePremioList().remove(pptePremioListPptePremio);
                    oldCodPremioOfPptePremioListPptePremio = em.merge(oldCodPremioOfPptePremioListPptePremio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPremio(premio.getCodPremio()) != null) {
                throw new PreexistingEntityException("Premio " + premio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Premio premio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Premio persistentPremio = em.find(Premio.class, premio.getCodPremio());
            List<PptePremio> pptePremioListOld = persistentPremio.getPptePremioList();
            List<PptePremio> pptePremioListNew = premio.getPptePremioList();
            List<String> illegalOrphanMessages = null;
            for (PptePremio pptePremioListOldPptePremio : pptePremioListOld) {
//                if (!pptePremioListNew.contains(pptePremioListOldPptePremio)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain PptePremio " + pptePremioListOldPptePremio + " since its codPremio field is not nullable.");
//                }
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
            premio.setPptePremioList(pptePremioListNew);
            premio = em.merge(premio);
            for (PptePremio pptePremioListNewPptePremio : pptePremioListNew) {
                if (!pptePremioListOld.contains(pptePremioListNewPptePremio)) {
                    Premio oldCodPremioOfPptePremioListNewPptePremio = pptePremioListNewPptePremio.getCodPremio();
                    pptePremioListNewPptePremio.setCodPremio(premio);
                    pptePremioListNewPptePremio = em.merge(pptePremioListNewPptePremio);
                    if (oldCodPremioOfPptePremioListNewPptePremio != null && !oldCodPremioOfPptePremioListNewPptePremio.equals(premio)) {
                        oldCodPremioOfPptePremioListNewPptePremio.getPptePremioList().remove(pptePremioListNewPptePremio);
                        oldCodPremioOfPptePremioListNewPptePremio = em.merge(oldCodPremioOfPptePremioListNewPptePremio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = premio.getCodPremio();
                if (findPremio(id) == null) {
                    throw new NonexistentEntityException("The premio with id " + id + " no longer exists.");
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
            Premio premio;
            try {
                premio = em.getReference(Premio.class, id);
                premio.getCodPremio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The premio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PptePremio> pptePremioListOrphanCheck = premio.getPptePremioList();
            for (PptePremio pptePremioListOrphanCheckPptePremio : pptePremioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Premio (" + premio + ") cannot be destroyed since the PptePremio " + pptePremioListOrphanCheckPptePremio + " in its pptePremioList field has a non-nullable codPremio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(premio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Premio> findPremioEntities() {
        return findPremioEntities(true, -1, -1);
    }

    public List<Premio> findPremioEntities(int maxResults, int firstResult) {
        return findPremioEntities(false, maxResults, firstResult);
    }

    private List<Premio> findPremioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Premio.class));
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

    public Premio findPremio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Premio.class, id);
        } finally {
            em.close();
        }
    }

    public int getPremioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Premio> rt = cq.from(Premio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public List<Premio> premiosEntregar(){
        EntityManager em = getEntityManager();
        Query consulta = em.createNamedQuery("Premio.findByCantExt");
        return consulta.getResultList();
    }
    
}

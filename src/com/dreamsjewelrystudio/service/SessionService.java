package com.dreamsjewelrystudio.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Session;
import com.dreamsjewelrystudio.repository.SessionRepository;

@Service
public class SessionService extends CRUDService<Session>{
	
	public SessionService() {
		super(Session.class);
	}

	@Autowired private SessionRepository sessRepository;
	@Autowired private EntityManagerFactory emf;
	
	private String HQL_SELECT_BY_ID = "SELECT DISTINCT s FROM Session s WHERE s.token = :sessionToken";
	
	public void deleteItemBySession(String token, long itemID) {
		if(token == null || token.length()== 0 ) return;
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.createNativeQuery("DELETE i FROM item as i "
					+ "INNER JOIN session as sess ON sess.sessID = i.sessID "
					+ "WHERE sess.token = ?1 AND i.itemID = ?2")
				.setParameter(1, token)
				.setParameter(2, itemID)
				.executeUpdate();
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
		em.close();
	}
	
	public Session createNewSessionWithItems(Session session) {
		return sessRepository.saveAndFlush(session);
	}
	
	public Session findSessionByToken(String token) {
		return sessRepository.findSessionByToken(token);
	}
	
	public List<Session> findAll(){
		return sessRepository.findAll();
	}
	
	public Session findSessionItemPrsByToken(String sessionToken) {
		Session sess = null;
		try {
			EntityManager em = emf.createEntityManager();
			sess = em.createQuery(HQL_SELECT_BY_ID, Session.class)
					.setParameter("sessionToken", sessionToken)
					.setHint("javax.persistence.fetchgraph", em.createEntityGraph("sess-itms-prs"))
					.getSingleResult(); 
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sess;
	}
	
	public Session findSessionItemPrsPrdByToken(String sessionToken) {
		Session sess = null;
		try {
			EntityManager em = emf.createEntityManager();
			sess = em.createQuery(HQL_SELECT_BY_ID, Session.class)
					.setParameter("sessionToken", sessionToken)
					.setHint("javax.persistence.fetchgraph", em.createEntityGraph("sess-itms-prd"))
					.getSingleResult(); 
			sess = em.createQuery("SELECT DISTINCT s "
					+ "FROM Session s "
					+ "WHERE s in :sess", Session.class)
					.setParameter("sess", sess)
					.setHint("javax.persistence.fetchgraph", em.createEntityGraph("sess-itms-prs"))
					.getSingleResult(); 
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sess;
	}

	@Override
	public void insert(Session entity) {
		sessRepository.saveAndFlush(entity);
	}

	@Override
	public void delete(Session entity) {
		sessRepository.delete(entity);
	}
}

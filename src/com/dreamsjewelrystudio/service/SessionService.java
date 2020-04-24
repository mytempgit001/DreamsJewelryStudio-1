package com.dreamsjewelrystudio.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Item;
import com.dreamsjewelrystudio.models.Session;
import com.dreamsjewelrystudio.repository.SessionRepository;

@Service
public class SessionService {
	
	@Autowired private SessionRepository sessRepository;
	@Autowired private EntityManagerFactory emf;
	@Autowired private JdbcTemplate jdbcTemplate;
	
	public Session findSessionByToken(String token) {
		return sessRepository.getSessionByToken(token);
	}
	
	public Session createNewSessionWithItems(Session session) {
		return sessRepository.saveAndFlush(session);
	}
	
	public List<Session> findAll(){
		return sessRepository.findAll();
	}
	
	public Session findSessionItemProductByToken(String token) {
		String SQL_SELECT = "SELECT s.*, i.*, p.*, prs.*, pimg.* FROM session as s" +
				" INNER JOIN item as i ON i.sessID = s.sessID" +
				" INNER JOIN product as p ON i.productID = p.product_id" +
				" INNER JOIN product_price_size as prs ON p.product_id = prs.product_id" +
				" INNER JOIN product_images as pimg ON p.product_id = pimg.product_id" +
				" WHERE s.token = :token AND i.price_id = prs.price_id";
		
		EntityManager em = emf.createEntityManager();
		org.hibernate.Session session = (org.hibernate.Session) em.getDelegate();
		List<?> rows = session.createSQLQuery(SQL_SELECT)
				.addEntity("s", Session.class)
				.addJoin("i", "s.items")
				.addJoin("p", "i.product")
				.addJoin("prs", "p.price")
				.addJoin("pimg", "p.images")
				.setParameter("token", token)
				.getResultList();
		session.close();
		
		Session sess = null;
		for(Object row : rows) {
			if(row instanceof Object[]) {
				Object[] objs = (Object[]) row;
				if(objs[0] instanceof Session) {
					sess = (Session) objs[0];
				}
			}
		}
		return sess;
	}
	public Session findSessionItemProductByToken1(String token) {
		List<Session> list = jdbcTemplate.query("SELECT * FROM session as s "
				+ "INNER JOIN item as i ON s.sessID = i.sessID "
				+ "WHERE s.token = '?'",
				new Object[] {token},
				new RowMapper<Session>() {
					@Override
					public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
						Session sess = new Session();
						sess.setSessID(rs.getLong("sessID"));
						sess.setLastUsed(rs.getString("lastUsed"));
						sess.setToken(rs.getString("token"));
						
						if(sess.getItems() == null) sess.setItems(new ArrayList<>());
						Item item = new Item();
						item.setItemID(rs.getLong("itemID"));
						item.setPrice(rs.getFloat("price"));
						item.setPrice_id(rs.getLong("price_id"));
						item.setProductID(rs.getLong("productID"));
						item.setQuantity(rs.getInt("quantity"));
						item.setSessID(rs.getLong("sessID"));
						item.setSession(sess);
						item.setSize(rs.getString("size"));
						sess.getItems().add(item);
						return sess;
					}	
				});
		return new HashSet<>(list).iterator().next();
	}
}

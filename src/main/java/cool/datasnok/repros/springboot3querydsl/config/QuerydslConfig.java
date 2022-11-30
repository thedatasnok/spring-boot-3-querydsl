package cool.datasnok.repros.springboot3querydsl.config;

import org.hibernate.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
public class QuerydslConfig {
  
  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public EntityManager getEntityManager() {
    return this.entityManager;
  }

  @Bean
  public Session getHibernateSession() {
    return this.entityManager.unwrap(Session.class);
  }

  @Bean
  public HibernateQueryFactory getHibernateQueryFactory() {
    return new HibernateQueryFactory(this.getHibernateSession());
  }

  @Bean
  public JPAQueryFactory getJpaQueryFactory() {
    return new JPAQueryFactory(this.entityManager);
  }

}

package cool.datasnok.repros.springboot3querydsl.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import cool.datasnok.repros.springboot3querydsl.model.QUserAccount;
import cool.datasnok.repros.springboot3querydsl.projection.UserAccountProjection;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-accounts")
@RequiredArgsConstructor
public class UserAccountController implements ApplicationListener<ContextRefreshedEvent> {
  private final HibernateQueryFactory queryFactory;
  private final JPAQueryFactory jpaQueryFactory;

  private QUserAccount qUserAccount = QUserAccount.userAccount;

  @GetMapping
  public List<UserAccountProjection> getUserAccounts() {
    // return this.queryFactory.from(qUserAccount)
    return this.jpaQueryFactory.from(qUserAccount)
      .select(
        Projections.constructor(
          UserAccountProjection.class,
          qUserAccount.id,
          qUserAccount.firstName.concat(" ").concat(qUserAccount.lastName),
          qUserAccount.username,
          qUserAccount.email
        )
      )
      .fetch();
  }

  @GetMapping("/hibernate")
  public List<UserAccountProjection> getUserAccountsHibernate() {
    // this fails with java.lang.ClassNotFoundException: org.hibernate.type.ByteType
    return this.queryFactory.from(qUserAccount)
      .select(
        Projections.constructor(
          UserAccountProjection.class,
          qUserAccount.id,
          qUserAccount.firstName.concat(" ").concat(qUserAccount.lastName),
          qUserAccount.username,
          qUserAccount.email
        )
      )
      .fetch();
  }

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {
    this.jpaQueryFactory.insert(qUserAccount)
      .columns(
        qUserAccount.id, 
        qUserAccount.firstName, 
        qUserAccount.lastName, 
        qUserAccount.username, 
        qUserAccount.email,
        qUserAccount.createdAt,
        qUserAccount.updatedAt
      )
      .values(
        UUID.randomUUID(),
        "John",
        "Doe",
        "johndoe",
        "johndoe@invalid.email",
        new Date(),
        new Date()
      )
      .execute();
  }

}

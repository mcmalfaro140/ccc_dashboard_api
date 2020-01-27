package com.ccc.api.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ccc.api.model.Users;
import com.ccc.api.model.dao.UsersDao;

@Repository
public class UsersDaoImpl implements UsersDao{
	@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Users getUsers(Integer userid) {
        return entityManager.find(Users.class, userid);
    }

    @Override
    public List<Users> getUsers() {
        return entityManager.createQuery("from Users", Users.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Users saveUsers(Users users) {
        return entityManager.merge(users);
    }

}

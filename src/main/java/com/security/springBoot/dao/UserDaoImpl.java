package com.security.springBoot.dao;

import org.springframework.stereotype.Repository;
import com.security.springBoot.models.Role;
import com.security.springBoot.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUserByEmail(String email) {
        User user = entityManager.createQuery("select user from User user join fetch user.roles where user.email = :userEmail", User.class).setParameter("userEmail", email).getSingleResult();
        return user;
    }

    @Override
    public User getUserByName(String name) {
        User user = entityManager.createQuery("select user from User user join fetch user.roles where user.name = :username", User.class).setParameter("username", name).getSingleResult();
        return user;
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);

    }

    @Override
    public void deleteUser(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public List<User> userList() {
        List<User> users = entityManager.createQuery("select distinct user from User user join fetch user.roles").getResultList();
        return users;

    }

    @Override
    public User getUserById(long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public List<Role> roles() {
        return entityManager.createQuery("select role from Role role").getResultList();
    }

    @Override
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

}


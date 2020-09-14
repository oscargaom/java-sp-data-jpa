package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;

import org.springframework.stereotype.Repository;

@Repository("clienteDaoJpa")
public class ClienteDaoImpl implements IClienteDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Cliente> findAll() {

        return entityManager.createQuery("from Cliente").getResultList();
    }

    @Override
    public void save(Cliente cliente) {

        // En caso de que ya exista el cliente con merge se actualiza y con persist se
        // crea
        if (cliente.getId() != null && cliente.getId() > 0) {
            entityManager.merge(cliente);
        } else {
            entityManager.persist(cliente);
        }
    }

    @Override
    public Cliente findOne(Long id) {
        return entityManager.find(Cliente.class, id);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(findOne(id));
    }
}

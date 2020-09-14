package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.service;

import java.util.List;

import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.dao.IClienteRepository;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("clienteServiceJpa")
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteRepository clienteRespository;

    @Override
    @Transactional
    public void delete(Long id) {
        clienteRespository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteRespository.findAll();
    }

    @Override
    @Transactional
    public Cliente findOne(Long id) {
        return clienteRespository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        clienteRespository.save(cliente);
    }

    @Override
    @Transactional
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteRespository.findAll(pageable);
    }
}

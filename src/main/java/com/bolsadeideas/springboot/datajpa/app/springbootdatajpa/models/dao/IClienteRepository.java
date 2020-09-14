package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.dao;

import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;

import org.springframework.data.repository.PagingAndSortingRepository;

/*  Cuando se utiliza CrudRepository ya no es necesario agregar ninguna anotación
    ya que internamente SpringBoot ya lo maneja en el contenedor y si podemos hacer
    referencia a esta a través de Autowired y va a ser recnocida por StringBoot.
    PagingAndSortingRepository extiende de CrudRepository por lo que el 
    comportamiento de la interfaz IClienteRepository seguirá siendo la misma.
*/
// public interface IClienteRepository extends CrudRepository<Cliente, Long> {
public interface IClienteRepository extends PagingAndSortingRepository<Cliente, Long> {

}

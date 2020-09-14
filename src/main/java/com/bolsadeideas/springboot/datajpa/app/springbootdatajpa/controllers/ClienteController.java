package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.controllers;

import java.util.Map;

import javax.validation.Valid;

import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.service.IClienteService;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.util.paginator.PageRender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    private static final String MODEL_TITULO = "titulo";

    @Autowired
    @Qualifier("clienteServiceJpa")
    private IClienteService clienteService;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

        Pageable pageable = PageRequest.of(page, 5);

        Page<Cliente> clientes = clienteService.findAll(pageable);

        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

        model.addAttribute(MODEL_TITULO, "Listado de clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);

        return "listar";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String crear(Map<String, Object> model, RedirectAttributes flash) {
        Cliente cliente = new Cliente();

        model.put(MODEL_TITULO, "Formulario de Clientes");
        /*
         * Se guarda en la sesión el cliente gracias a la
         * anotación @SessionAttribute("cliente") que se realiza en el controlador por
         * eso deben tener el mismo nombre al momento de que se agrega al modelo
         */
        model.put("cliente", cliente);
        flash.addFlashAttribute("success", "El usuario se ha creado correctamente");
        return "form";
    }

    /*
     * Con la anotación @Valid hacemos posible que se validen las anotaciones
     * definidas en el objeto cliente y por medio de BindingResult obtenemos el
     * resultado de la validación hecha al objeto. Es muy importante que
     * BindingResult vaya seguido del objeto que se va a validar (@Valid Cliente
     * cliente, BindingResult result), es decir, no podríamos definir el método de
     * la siguiente forma: (@Valid Cliente cliente, Model model, BindingResult
     * result)
     */
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus session,
            RedirectAttributes flash) {

        if (result.hasErrors()) {
            /*
             * En caso que de exista alguna falla en la validación se pasa de manera
             * automática el objeto cliente a la vista form siempre y cuando tanto la vista
             * como el objeto tengan el mismo nombre, ya que de lo contrartio se debe
             * especificar con la anotación ModelAttributte("NOMBRE") en la definición del
             * método .... guardar(@Valid @ModelAttribute("NOMBRE") Cliente cliente,
             * BindingResult result) {
             */
            model.addAttribute(MODEL_TITULO, "Formulario del Cliente");
            flash.addFlashAttribute("error", "Los datos introducidos no son válidos");
            return "form";
        }

        clienteService.save(cliente);
        /*
         * Hasta este punto es válido el objeto cliente en la sesión, ya que en la
         * siguiente línea se marca el punto en que su ciclo ha concluido
         */
        session.setComplete();
        flash.addFlashAttribute("success", "El cliente se ha actualizado correctamente");
        return "redirect:listar";
    }

    /*
     * RedirectAttributes nos permite envíar mensajes a la vista pero estos terminan
     * su ciclo de vida en el momento que se realiza el redirect en la vista, por lo
     * que son ideales para mostrar como mensajes de exito o error al usuario.
     */
    @RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(name = "id") Long identificador, Map<String, Object> model,
            RedirectAttributes flash) {

        Cliente cliente = null;

        if (identificador > 0) {
            cliente = clienteService.findOne(identificador);

            if (cliente == null) {
                flash.addFlashAttribute("error", "El usuario indicado no existe");
                return "redirect:/listar";
            }

            model.put(MODEL_TITULO, "Editar cliente");
            // Se continúa persistiendo el objeto cliente en la sesión.
            model.put("cliente", cliente);
            return "form";

        } else {
            flash.addFlashAttribute("error", "El identificador no puede ser cero");
            return "redirect:/listar";
        }
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(name = "id") Long identificador, RedirectAttributes flash) {

        if (identificador > 0) {
            try {
                clienteService.delete(identificador);
                flash.addFlashAttribute("success", "El usuario se ha eliminado correctamente");
            } catch (Exception e) {
                flash.addFlashAttribute("error", "El usuario indicado no existe");
            } finally {
                return "redirect:/listar";
            }

        } else {
            flash.addFlashAttribute("error", "El identificador no puede ser cero");
            return "redirect:/listar";
        }

    }
}

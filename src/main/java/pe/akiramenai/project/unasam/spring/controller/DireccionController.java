package pe.akiramenai.project.unasam.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.akiramenai.project.unasam.spring.model.Sesion;
import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;
import pe.akiramenai.project.unasam.spring.serviceimpl.JpaUserDetailsService;


@Controller
@RequestMapping("/monicovid")
public class DireccionController {

	@Autowired
	JpaUserDetailsService jpa;

	@Autowired
	IUsuarioService uService;
	
	private String mensaje;
	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		
		return "bienvenido";
	}
	
	@RequestMapping("/index")
	public String irIndex(Model model, RedirectAttributes objRedir) {
		if(mensaje!=null)
			if(!mensaje.equalsIgnoreCase(""))
				objRedir.addFlashAttribute("successmessage", mensaje);
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("recuperado", uService.obtenerObjetoUsuario().getRecuperado());
		return "monicovidBienvenido";
	}
	
	@RequestMapping("/recuperarContrasenia")
	public String irRecuperarContrasenia(Model model) {

		model.addAttribute("sesion", new Sesion());
		
		return "recuperarContrasenia";
	}
	
	@RequestMapping("/CentrosSaludCercanos")
	public String google() {
		
		return "monicovidCentroSalud";
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	@RequestMapping("/login")
	public String irLogin() {
		
		return "login";
	}
	@RequestMapping("/error404")
	public String irError() {
		
		return "error404";
	}

	@RequestMapping("/logout")
	 public String irLogOut() {
			
		return "logout";
	} 
		
}

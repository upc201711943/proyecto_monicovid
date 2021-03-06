package pe.akiramenai.project.unasam.spring.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.model.Reporte;
import pe.akiramenai.project.unasam.spring.service.IReporteService;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;
import pe.akiramenai.project.unasam.spring.serviceimpl.OxigenoServiceImpl;
import pe.akiramenai.project.unasam.spring.serviceimpl.TemperaturaServiceImpl;

@Controller
@RequestMapping("/medico")
public class MedicoController {

	@Autowired
	private IUsuarioService uService;
	
	@Autowired
	private IReporteService rService;
	
	@Autowired
	private SesionController sController;
	
	private List<Reporte> listReportes;
	
	@Autowired
	private TemperaturaServiceImpl tServiceImpl;
	@Autowired
	private OxigenoServiceImpl oServiceImpl;
	
	private Usuario pacienteBuscado;

	@RequestMapping("/verReportes")
	public String verReportes(Model model)
	{
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("listaReportes", null);
		model.addAttribute("mensaje", null);
		return "monicovidMedicoVerReportes";
	}
	//test
	@RequestMapping("/verReportesTest")
	public String verReportesTest(Model model)
	{
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("listaReportes", null);
		return "monicovidTest";
	}
	
	
	@RequestMapping("/verReportesPaciente")
	public String verReportesPaciente(Model model)
	{
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("listaReportes", listReportes);
		return "monicovidMedicoVerReportes";
	}

	@RequestMapping("/verDatosPaciente")
	public String verDatosPaciente(Model model)
	{
		model.addAttribute("usuario", pacienteBuscado);
		return "monicovidMedicoVerPacienteDatos";
	}

	
	@RequestMapping("/buscar")
	public String buscar(@ModelAttribute Usuario objUsuario, BindingResult binRes, Model model)
			throws ParseException
	{
		if(binRes.hasErrors()) {
			System.out.println("Tengo errores");
			return "monicovidMedicoVerReportes";
		
		}
		else {
				List<Reporte> reportes=rService.buscarporPacienteDNIOrdenado(objUsuario.getDni());
				if(uService.buscarUsuario(objUsuario.getDni()).size()>0)
				{
					pacienteBuscado= uService.buscarUsuario(objUsuario.getDni()).get(0);
					tServiceImpl.setPacienteBuscado(pacienteBuscado.getUsername());
					oServiceImpl.setPacienteBuscado(pacienteBuscado.getUsername());
					
					boolean flag=false;
					
					if(reportes.size()>0)
						flag =true;
					
					if(flag) {
						
						model.addAttribute("listaReportes", reportes);
						listReportes = reportes;
						return "redirect:/medico/verReportesPaciente";}
					else {
						model.addAttribute("mensaje", "No se han encontrado resultados");
						return "redirect:/medico/verReportesPaciente";
					}
				}
				else {
					model.addAttribute("mensaje", "No se han encontrado resultados");
					listReportes = null;
					return "redirect:/medico/verReportesPaciente";
				}
			}		
	}	
	
	
	
	@RequestMapping("/lista")
	public String lista(Model model)
	{
		model.addAttribute("listaUsuarios", uService.listar());
		model.addAttribute("texto", "Lista de todos los Usuarios");
		sController.setMensaje(null);
		return "adminListUsuarios";
	}
	
}

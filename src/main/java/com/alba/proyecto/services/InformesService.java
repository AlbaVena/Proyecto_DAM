package com.alba.proyecto.services;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Image;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alba.proyecto.modelo.DatoGrafico;
import com.alba.proyecto.modelo.Estudiante;
import com.alba.proyecto.modelo.FCT;
import com.alba.proyecto.modelo.FctDTO;
import com.alba.proyecto.repositorios.EstudianteRepository;
import com.alba.proyecto.repositorios.FCTRepository;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import utils.Transformador;

/**
 * servicio para la generación de informes
 */

/**
 * Clase ServicioInformes.
 * 
 * Servicio encargado de generar los informes PDF de la aplicación
 * usando JasperReports. Los archivos se guardan en reportes_generados/.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Service
public class InformesService {
	
	@Autowired
	private FCTRepository fctRepository;

	/**
	 * Gewnera un PDF estadistico con un g´rafico mostrando estudiantes con y sin FE asignada.
	 * @param numEstudiantes
	 * @param numEmpresas
	 * @param numFEs
	 * @return Ruta absoluta del PDF generado, o null si hay error.
	 */
	public String generarInformeEstadistico(long numEstudiantes, long numEmpresas, long numFEs) {

	    try {
	        InputStream plantilla = getClass().getResourceAsStream("/reportes/informeEstadistico.jasper");

	        // calcular estudiantes con y sin FE
	        long conFE = fctRepository.contarEstudiantesConFE();
	        long sinFE = numEstudiantes - conFE;

	        // construir la lista para el gráfico
	        List<DatoGrafico> datos = new ArrayList<DatoGrafico>();
	        datos.add(new DatoGrafico("Con FE asignada", conFE));
	        datos.add(new DatoGrafico("Sin FE asignada", sinFE));

	        // parámetros
	        Map<String, Object> parametros = new HashMap<String, Object>();
	        //para el logo:
	        InputStream imgStream = getClass().getResourceAsStream("/images/LogoGestiona1_redondo.png");
	        Image logo = ImageIO.read(imgStream);
	        parametros.put("logo", logo);
	        
	        parametros.put("numEstudiantes", numEstudiantes);
	        parametros.put("numEmpresas", numEmpresas);
	        parametros.put("numFEs", numFEs);
	        parametros.put("fecha", LocalDate.now().toString());

	        // datasource con los datos del gráfico
	        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datos);

	        JasperPrint jasperPrint = JasperFillManager.fillReport(plantilla, parametros, dataSource);
	        
	        String userHome = System.getProperty("user.home");//el home del usuario actual
	        

	        //ruta del escritorio del usuario actual (desktop funciona siempre)
	        String rutaCarpeta = userHome + File.separator + "Desktop" + File.separator + "reportes_generados";
	        File carpeta = new File(rutaCarpeta);

	        if (!carpeta.exists()) {
	            carpeta.mkdirs();
	        }
	        String rutaSalida = rutaCarpeta + File.separator + "informe_estadistico_" + LocalDate.now() + ".pdf";

	        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaSalida);

	        return new File(rutaSalida).getAbsolutePath();

	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	/**
	 * Genera un listado en PDF con las FEs recibidas como parámetro.
	 * 
	 * @param fcts Lista de FCTs a incluir en el listado.
	 * @return Ruta absoluta del PDF generado, o null si hay error.
	 */
	public String generarListadoFEs(List<FCT> fcts) {

	    try {
	        InputStream plantilla = getClass().getResourceAsStream("/reportes/listadoFEs.jasper");

	        // construir lista de DTOs
	        List<FctDTO> datos = new ArrayList<FctDTO>();
	        for (FCT fct : fcts) {
	            String estudiante = fct.getEstudiante() != null ? fct.getEstudiante().getNombreCompleto() : "—";
	            String empresa = fct.getTutor() != null && fct.getTutor().getEmpresa() != null
	                    ? fct.getTutor().getEmpresa().getNombre() : "—";
	            String tutor = fct.getTutor() != null ? fct.getTutor().getNombreCompleto() : "—";
	            String periodo = fct.getPeriodo() != null ? fct.getPeriodo().toString() : "—";
	            String fechaInicio = fct.getFechaInicio() != null ? fct.getFechaInicio().toString() : "—";
	            String fechaFin = fct.getFechaFin() != null ? fct.getFechaFin().toString() : "—";

	            datos.add(new FctDTO(estudiante, empresa, tutor, periodo, fechaInicio, fechaFin));
	        }

	        // parámetros
	        Map<String, Object> parametros = new HashMap<String, Object>();
	        InputStream imgStream = getClass().getResourceAsStream("/images/LogoGestiona1_redondo.png");
	        Image logo = ImageIO.read(imgStream);
	        parametros.put("logo", logo);
	        parametros.put("fecha", LocalDate.now().toString());

	        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datos);

	        JasperPrint jasperPrint = JasperFillManager.fillReport(plantilla, parametros, dataSource);

	        String userHome = System.getProperty("user.home");
	        String rutaCarpeta = userHome + File.separator + "Desktop" + File.separator + "reportes_generados";
	        File carpeta = new File(rutaCarpeta);

	        if (!carpeta.exists()) {
	            carpeta.mkdirs();
	        }

	        String rutaSalida = rutaCarpeta + File.separator + "listado_fes_" + LocalDate.now() + ".pdf";
	        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaSalida);

	        return new File(rutaSalida).getAbsolutePath();

	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	/**
	 * Genera la ficha en PDF de un estudiante concreto con sus datos
	 * personales y los de su FE asignada si la tiene.
	 * 
	 * @param estudiante Estudiante del que se genera la ficha.
	 * @return Ruta absoluta del PDF generado, o null si hay error.
	 */
	public String generarFichaEstudiante(Estudiante estudiante) {
	    try {
	        InputStream plantilla = getClass().getResourceAsStream("/reportes/fichaEstudiante.jasper");

	        Map<String, Object> parametros = new HashMap<String, Object>();
	        InputStream imgStream = getClass().getResourceAsStream("/images/LogoGestiona1_redondo.png");
	        Image logo = ImageIO.read(imgStream);
	        parametros.put("logo", logo);
	        parametros.put("nombre", estudiante.getNombre());
	        parametros.put("apellidos", estudiante.getApellidos());
	        parametros.put("email", estudiante.getEmail() != null ? estudiante.getEmail() : "—");
	        parametros.put("telefono", estudiante.getTelefono() != null ? estudiante.getTelefono() : "—");
	        parametros.put("usuario", estudiante.getUsuario());
	        parametros.put("nss", estudiante.getnSS() != null ? estudiante.getnSS() : "—");
	        parametros.put("curso", estudiante.getCurso() != null ? estudiante.getCurso().toString() : "—");
	        parametros.put("fecha", LocalDate.now().toString());

	        // datos de la FE si tiene
	        if (estudiante.getFcts() != null && !estudiante.getFcts().isEmpty()) {
	            FCT fct = estudiante.getFcts().iterator().next();
	            parametros.put("empresa", fct.getTutor() != null && fct.getTutor().getEmpresa() != null
	                    ? fct.getTutor().getEmpresa().getNombre() : "—");
	            parametros.put("tutorEmpresa", fct.getTutor() != null
	                    ? fct.getTutor().getNombreCompleto() : "—");
	            parametros.put("fechaInicio", fct.getFechaInicio() != null
	                    ? Transformador.transformarFechaAString(fct.getFechaInicio()) : "—");
	            parametros.put("fechaFin", fct.getFechaFin() != null
	                    ? Transformador.transformarFechaAString(fct.getFechaFin()) : "—");
	            parametros.put("periodo", fct.getPeriodo() != null
	                    ? fct.getPeriodo().toString() : "—");
	        } else {
	            parametros.put("empresa", "Sin FE asignada");
	            parametros.put("tutorEmpresa", "—");
	            parametros.put("fechaInicio", "—");
	            parametros.put("fechaFin", "—");
	            parametros.put("periodo", "—");
	        }

	        JREmptyDataSource dataSource =
	                new JREmptyDataSource(1);

	        JasperPrint jasperPrint =
	                JasperFillManager.fillReport(plantilla, parametros, dataSource);

	        String userHome = System.getProperty("user.home");
	        String rutaCarpeta = userHome + File.separator + "Desktop" + File.separator + "reportes_generados";
	        File carpeta = new File(rutaCarpeta);

	        if (!carpeta.exists()) {
	            carpeta.mkdirs();
	        }

	        String rutaSalida = rutaCarpeta + File.separator + "ficha_" + estudiante.getNombreCompleto() + "_" + LocalDate.now() + ".pdf";
	        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaSalida);

	        return new File(rutaSalida).getAbsolutePath();

	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	
	
	
	

}

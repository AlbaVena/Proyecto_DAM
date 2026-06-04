package com.alba.proyecto.services;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * servicio para la generación de informes
 */
@Service
public class ServicioInformes {
	
	 /**
     * Genera el informe estadístico del departamento en PDF.
     * El archivo se guarda en la carpeta "informes" del directorio raíz del proyecto.
     *
     * @param numEstudiantes número de estudiantes registrados
     * @param numEmpresas    número de empresas registradas
     * @param numFEs         número de FEs registradas
     * @return ruta absoluta del PDF generado, o null si hay error
     */
    public String generarInformeEstadistico(long numEstudiantes, long numEmpresas, long numFEs) {

        try {
            // cargar el .jasper desde resources
            InputStream plantilla = getClass().getResourceAsStream("/reportes/informeEstadistico.jasper");

            // parámetros del informe
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("numEstudiantes", numEstudiantes);
            parametros.put("numEmpresas", numEmpresas);
            parametros.put("numFEs", numFEs);
            parametros.put("fecha", LocalDate.now().toString());

            // datasource vacío (el informe solo usa parámetros, no filas)
            List<Object> listaVacia = new ArrayList<Object>();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaVacia);

            // rellenar el informe
            JasperPrint jasperPrint = JasperFillManager.fillReport(plantilla, parametros, dataSource);

            // crear carpeta de salida si no existe
            File carpeta = new File("reportes_generados");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            // exportar a PDF
            String rutaSalida = "informes" + File.separator + "informe_estadistico_" + LocalDate.now() + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, rutaSalida);

            return new File(rutaSalida).getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	

}

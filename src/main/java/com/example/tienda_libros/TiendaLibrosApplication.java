package com.example.tienda_libros;

import com.example.tienda_libros.presentacion.LibroForm;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class TiendaLibrosApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext contextoSpring =
                new SpringApplicationBuilder(TiendaLibrosApplication.class)
                        .headless(false)
                        .web(WebApplicationType.NONE).run(args);


        //CODIGO PARA CARGAR EL FORMULARIO
        EventQueue.invokeLater(() ->{
            //OBTENEMOS EL OBJETO FOMR A TRAVES DE SPRING
            LibroForm
                    libroForm = contextoSpring.getBean(LibroForm.class);
            libroForm.setVisible(true);
        });
    }

}

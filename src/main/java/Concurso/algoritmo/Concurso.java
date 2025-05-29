package Concurso.algoritmo;

import TP2.EmailsService;
import TP2.EnviarMails;
import TP2.Registrar;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Concurso {
    private String nombre;
    private LocalDate fechaLimite;
    private LocalDate fechaInicio;
    private List<Participante> participantes;
    private Registrar registro;
    private EmailsService enviarEmail;


    public Concurso(String nombre, LocalDate fechaLimite, LocalDate fechaInicio, Registrar fakeRegistrador, EmailsService EmailService) {
        this.nombre = nombre;
        this.fechaLimite = fechaLimite;
        this.fechaInicio = fechaInicio;
        this.participantes = new ArrayList<>();
        this.registro = fakeRegistrador;
        this.enviarEmail = EmailService;

    }

    // Inscribe a un participante si la fecha está dentro del rango
    public void inscribir(Participante participante, LocalDate seInscribio) {
        if (seInscribio.isBefore(fechaInicio) || seInscribio.isAfter(fechaLimite)) {
            throw new RuntimeException("Inscripción fuera del rango permitido");
        }
        if (seInscribio.equals(fechaInicio)) {
            participante.sumaPuntos(10);
        }

        participantes.add(participante);
        participante.inscribio(this);


        registrarInscripcion(seInscribio, participante);
        enviarEmail(participante.toString());
    }


    private void registrarInscripcion(LocalDate fecha, Participante participante) {

        String FechaDniConcurso = fecha + "||" + participante.getDni() + "||" + this.nombre;
        this.registro.registrarTXT(FechaDniConcurso);
     this.registro.registrarJDBC(FechaDniConcurso);
    }

    public boolean estaInscripto(Participante participante) {
        return participantes.contains(participante);
    }

    public void participantesDelConcurso() {
        if (participantes.isEmpty()) {
            System.out.println("No hay participantes inscritos en el concurso.");
        } else {
            System.out.println("Lista de participantes inscritos en " + nombre + ":");
            participantes.forEach(System.out::println);
        }
    }

    public String getNombre() {
        return this.nombre;
    }

    public boolean tienePuntos(Participante participante, int i) {
        return participante.tienePuntos(i);
    }

    public Collection<Object> getInscriptos() {
        Collection<Object> inscriptos = new ArrayList<>();
        for (Participante participante : participantes) {
            inscriptos.add(participante);
        }
        return inscriptos;
    }

    public void registrarTXT(String participante) {
      registro.registrarTXT(participante);
    }


    public void registrarJDBC(String participante) {
     registro.registrarJDBC(participante);
    }


    public void enviarEmail(String nombreParticipante) {


        // Simulamos una dirección de email basada en el nombre
        String email = nombreParticipante.toLowerCase().replace(" ", ".") + "@mailtrap.io";
        String asunto = "Confirmación de inscripción en el concurso";
        String mensaje = "Hola " + nombreParticipante + ",\n\n" +
                "Te confirmamos que te has inscrito exitosamente al concurso: " + this.nombre +
                ".\n\n¡Buena suerte!\n\n-- Organización del Concurso";

        String mensaje2 = mensaje + email + ", " + asunto + ", " ;
        enviarEmail.enviarEmail( nombreParticipante , asunto, mensaje);
    }
}



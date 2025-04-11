package Concurso.algoritmo;

import TP2.EnviarMails;
import TP2.FakeRegistrador;
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
import java.util.List;

public class Concurso implements Registrar{
    private String nombre;
    private LocalDate fechaLimite;
    private LocalDate fechaInicio;
    private List<Participante> participantes;
    private FakeRegistrador registroFake;


    public Concurso(String nombre, LocalDate fechaLimite, LocalDate fechaInicio, FakeRegistrador fakeRegistrador) {
        this.nombre = nombre;
        this.fechaLimite = fechaLimite;
        this.fechaInicio = fechaInicio;
        this.participantes = new ArrayList<>();
        this.registroFake = fakeRegistrador;

    }

    // Inscribe a un participante si la fecha está dentro del rango
    public void inscribir(Participante participante, LocalDate seInscribio) {
        if (seInscribio.isAfter(fechaLimite)) {
            throw new IllegalArgumentException("La inscripción está fuera de fecha.");
        }
        if (estaInscripto(participante)) {
            throw new IllegalArgumentException("El participante ya estaba inscripto.");
        }

        participantes.add(participante);
        participante.inscribio(this);

        // Otorga 10 puntos si es el primer día
        if (seInscribio.equals(fechaInicio)) {
            participante.sumaPuntos(10);
        }

        registrarInscripcion(seInscribio, participante);
        enviarEmail(participante.toString());
    }


    private void registrarInscripcion(LocalDate fecha, Participante participante) {

        String FechaDniConcurso = fecha + "||" + participante.getDni() + "||" + this.nombre;
        this.registroFake.registrarTXT(FechaDniConcurso);
     this.registroFake.registrarJDBC(FechaDniConcurso);
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

    @Override
    public void registrarTXT(String participante) {
        String rutaArchivo = "inscripciones.txt";

        try (FileWriter fw = new FileWriter(rutaArchivo, true);
             PrintWriter pw = new PrintWriter(fw)) {

            String registro = participante;

            pw.println(registro);
            System.out.println("Inscripción registrada correctamente en el archivo.");

        } catch (IOException e) {
            System.err.println("Error al registrar la inscripción: " + e.getMessage());
        }
    }

@Override
public void registrarJDBC(String participante) {
    String sql = "INSERT INTO inscripciones (dni, fecha, concurso) VALUES (?, ?, ?)";

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:concurso.db");
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
int dni = Integer.parseInt(participante.split(", ")[1]);
        String[] datos = participante.split(", ");

        pstmt.setInt(1, Integer.parseInt(datos[1])); // DNI
        pstmt.setString(2, datos[0]); // Fecha
        pstmt.setString(3, datos[2]); // Concurso

        pstmt.executeUpdate();

        System.out.println("Inscripción registrada en SQLite.");

    } catch (SQLException e) {
        System.err.println("Error al registrar inscripción: " + e.getMessage());
    }
}

@Override
    public void enviarEmail(String nombreParticipante) {
        EnviarMails enviarMails = new EnviarMails();

        // Simulamos una dirección de email basada en el nombre
        String email = nombreParticipante.toLowerCase().replace(" ", ".") + "@mailtrap.io";
        String asunto = "Confirmación de inscripción en el concurso";
        String mensaje = "Hola " + nombreParticipante + ",\n\n" +
                "Te confirmamos que te has inscrito exitosamente al concurso: " + this.nombre +
                ".\n\n¡Buena suerte!\n\n-- Organización del Concurso";

        String mensajeFake = mensaje + email + ", " + asunto + ", " ;
        this.registroFake.enviarEmail(mensajeFake);
    }
public boolean tienePuntos(Participante participante, int i) {
    return participante.tienePuntos(i);
}
}



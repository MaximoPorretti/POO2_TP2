package tp2;

import Concurso.algoritmo.Concurso;
import Concurso.algoritmo.Participante;
import TP2.EmailsService;
import TP2.Registrar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class ConcursoTest {

    private Registrar registrador = new FakeRegistrador();
    private EmailsService enviarEmail = new FakeMails();

    @BeforeEach
    public void setup() {
        registrador = new FakeRegistrador();
        enviarEmail = new FakeMails();
    }



    @Test
    public void test01TXT() {
        Concurso concurso = new Concurso("Hackathonito",
                LocalDate.of(2024, 4, 23),
                LocalDate.of(2024, 3, 1), registrador, enviarEmail);

        Participante juan = new Participante("Juan", 5456346);
        concurso.inscribir(juan, LocalDate.of(2024, 3, 18));

        String resultado = "2024-03-18";
        // Verifica que el registro se haya guardado correctamente en formato TXT
        assertEquals(resultado,registrador.registrarTXT("2024-03-18"));
    }

    @Test
    public void test02JDBC() {
        Concurso concurso = new Concurso("Deep Learning",
                LocalDate.of(2024, 4, 23),
                LocalDate.of(2024, 3, 13), registrador, enviarEmail);

        Participante pedro = new Participante("Pedro", 5443623);
        concurso.inscribir(pedro, LocalDate.of(2024, 3, 13));

        String resultado = "2024-03-18";

        // Verifica que el registro se haya guardado correctamente en formato JDBC
        assertEquals(resultado,registrador. registrarJDBC("2024-03-13"));
    }


    @Test
    public void test04_inscribirVariosParticipantes() {
        Concurso concurso = new Concurso("Hackathonaso",
                LocalDate.of(2024, 4, 23),
                LocalDate.of(2024, 3, 13), registrador, enviarEmail);

        Participante ana = new Participante("Ana", 234345);
        Participante luis = new Participante("Luis", 2343454);

        concurso.inscribir(ana, LocalDate.of(2024, 3, 14));
        concurso.inscribir(luis, LocalDate.of(2024, 3, 15));

        // Verifica que ambos participantes se hayan inscrito correctamente|
        assertTrue(concurso.estaInscripto(ana));
        assertTrue(concurso.estaInscripto(luis));
        // Verifica que el registro se haya guardado correctamente en formato TXT
        assertEquals("2024-03-14", registrador.registrarTXT("2024-03-14"));

    }

    @Test
    public void test05_inscripcionUltimoDia() {
        Concurso concurso = new Concurso("Hackathonaaaco",
                LocalDate.of(2024, 3, 20),
                LocalDate.of(2024, 3, 13), registrador, enviarEmail);

        Participante maximo = new Participante("Maximo", 32456424);
        concurso.inscribir(maximo, LocalDate.of(2024, 3, 20));

        // Verifica que el participante se haya inscrito correctamente en el último día permitido
        assertTrue(concurso.estaInscripto(maximo));
        // Verifica que el registro se haya guardado correctamente en formato TXT
        assertEquals("2024-03-20", registrador.registrarTXT("2024-03-20"));

    }
    @Test
    public void test06_envioDeEmail() {
EmailsService enviarEmail = new FakeMails();

       String mensaje =enviarEmail.enviarEmail("maria", "Inscripción exitosa al concurso Hackathonito", "Hola Maria, te has inscrito exitosamente al concurso Hackathonito. ¡Buena suerte!");

        assertEquals("maria"+ "Inscripción exitosa al concurso Hackathonito"+ "Hola Maria, te has inscrito exitosamente al concurso Hackathonito. ¡Buena suerte!", mensaje);
    }

    @Test
    void participanteSeInscribeCorrectamente() {
        Concurso concurso = new Concurso("Futbol 5", LocalDate.of(2025, 4, 10), LocalDate.of(2025, 4, 2),registrador, enviarEmail);
        Participante p = new Participante("Ana", 12345678);

        concurso.inscribir(p, LocalDate.of(2025, 4, 3));

        assertEquals(0, p.getPuntos());
        assertTrue(concurso.getInscriptos().contains(p));
    }

    @Test
    void participanteSeInscribeYGanaPuntos() {
        Concurso concurso = new Concurso("Fiesta de la manzana", LocalDate.of(2025, 4, 10), LocalDate.of(2025, 4, 1), registrador, enviarEmail);
        Participante p = new Participante("Luis", 87654321);

        concurso.inscribir(p, LocalDate.of(2025, 4, 1));

        assertEquals(10, p.getPuntos());
    }

    @Test
    void participanteSeInscribeFueraDeRangoYLanzaExcepcion() {
        Concurso concurso = new Concurso("Feria del libro", LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 10), registrador, enviarEmail);
        Participante p = new Participante("Sofi", 23456789);

        Exception e = assertThrows(RuntimeException.class, () ->
                concurso.inscribir(p, LocalDate.of(2025, 3, 31)));

        assertEquals("Inscripción fuera del rango permitido", e.getMessage());
    }

}



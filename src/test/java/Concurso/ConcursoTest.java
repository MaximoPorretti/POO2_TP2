package Concurso;

import Concurso.algoritmo.Concurso;
import Concurso.algoritmo.Participante;
import TP2.FakeRegistrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ConcursoTest {

    private FakeRegistrador registrador;

    @BeforeEach
    public void setup() {
        registrador = new FakeRegistrador();
    }



    @Test
    public void test01TXT() {
        Concurso concurso = new Concurso("Hackathonito",
                LocalDate.of(2024, 4, 23),
                LocalDate.of(2024, 3, 1), registrador);

        Participante juan = new Participante("Juan", 5456346);
        concurso.inscribir(juan, LocalDate.of(2024, 3, 18));

        assertTrue(registrador.startWithTXT("2024-03-18"));
    }

    @Test
    public void test02JDBC() {
        Concurso concurso = new Concurso("Deep Learning",
                LocalDate.of(2024, 4, 23),
                LocalDate.of(2024, 3, 13), registrador);

        Participante pedro = new Participante("Pedro", 5443623);
        concurso.inscribir(pedro, LocalDate.of(2024, 3, 13));

        assertTrue(registrador.startWithJDBC("2024-03-13"));
    }

    @Test
    public void test03fueraDeFecha() {
        Concurso concurso = new Concurso("Hackathonacho",
                LocalDate.of(2024, 4, 23),
                LocalDate.of(2024, 3, 13), registrador);

        Participante paco = new Participante("Paco", 45634673);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            concurso.inscribir(paco, LocalDate.of(2024, 5, 11));
        });

        assertEquals("La inscripción está fuera de fecha.", exception.getMessage());
    }

    @Test
    public void test04_inscribirVariosParticipantes() {
        Concurso concurso = new Concurso("Hackathonaso",
                LocalDate.of(2024, 4, 23),
                LocalDate.of(2024, 3, 13), registrador);

        Participante ana = new Participante("Ana", 234345);
        Participante luis = new Participante("Luis", 2343454);

        concurso.inscribir(ana, LocalDate.of(2024, 3, 14));
        concurso.inscribir(luis, LocalDate.of(2024, 3, 15));

        assertTrue(registrador.startWithTXT("2024-03-14") || registrador.startWithTXT("2024-03-15"));
    }

    @Test
    public void test05_inscripcionUltimoDia() {
        Concurso concurso = new Concurso("Hackathonaaaco",
                LocalDate.of(2024, 3, 20),
                LocalDate.of(2024, 3, 13), registrador);

        Participante maximo = new Participante("Maximo", 32456424);
        concurso.inscribir(maximo, LocalDate.of(2024, 3, 20));

        assertTrue(registrador.startWithTXT("2024-03-20"));
    }
    @Test
    public void test06_envioDeEmail() {
        Concurso concurso = new Concurso("HackEmail",
                LocalDate.of(2024, 4, 30),
                LocalDate.of(2024, 4, 1), registrador);

        Participante maria = new Participante("Maria", 12345678);
        concurso.inscribir(maria, LocalDate.of(2024, 4, 5));

        assertTrue( registrador.startWithEmail("Hola Maria (DNI: 12345678)"));
    }


}



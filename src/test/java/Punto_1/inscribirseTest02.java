import Punto_1.algoritmo.Concurso;
import Punto_1.algoritmo.Inscripcion;
import Punto_1.algoritmo.Participante;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class InscribirseTest02 {
    public void test01() {
        @Test
        //Un participante se inscribe en un concurso

        Participante participante = new Participante("pepe", 3404042);

        Concurso concurso = new Concurso(
                "Concurso de baile",
                LocalDateTime.of(2026, 12, 12, 3, 0), // Fecha límite
                LocalDateTime.of(2025, 5, 30, 2, 0) );   // Fecha de inicio

        Inscripcion inscripcion = new Inscripcion(  LocalDateTime.of(2025, 5, 30, 2, 0) ,participante, concurso);
        // el usuario procede a inscribirse
        inscripcion.inscribir(participante, concurso);



    }

}
package Concurso.algoritmo;



import TP2.FakeRegistrador;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {


        Concurso concurso = new Concurso("Maratón de Programación",
                LocalDate.of(2025, 4, 1),  // Fecha límite
                LocalDate.of(2025, 3, 25), // Fecha de inicio
                new FakeRegistrador() );


        Participante participante1 = new Participante("Juan Pérez", 12345678);
        Participante participante2 = new Participante("María López", 87654321);


        try {
            concurso.inscribir(participante1, LocalDate.of(2025, 3, 25));
            concurso.inscribir(participante2, LocalDate.of(2025, 3, 27));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Mostrar los participantes inscritos en el concurso
        System.out.println("Participantes inscritos:");
        concurso.participantesDelConcurso();
    }
}

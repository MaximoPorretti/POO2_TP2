

 package TP2;

    public class FakeMails implements EmailsService {

        public String enviarEmail(String destinatario, String asunto, String mensaje) {
            System.out.print(destinatario);
            System.out.println( asunto);
            System.out.println( mensaje);
            return destinatario + asunto+  mensaje;
        }
    }


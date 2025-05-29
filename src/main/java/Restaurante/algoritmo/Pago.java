package Restaurante.algoritmo;

import TP2.EmailsService;
import TP2.EnviarMails;
import TP2.Registrar;

import java.time.LocalDate;
import java.util.List;

// Clase Pago
public class Pago implements Registrar {
    private int total;
    private String tipoTarjeta;
    private int propina;
    private List<Pedido> pedidos;
    private Registrar registro;
    private EmailsService enviarEmail;


    public Pago(int total, String tipoTarjeta, int propina, List<Pedido> pedidos, Registrar registro , EmailsService enviarEmail) {
        this.total = total;
        this.tipoTarjeta = tipoTarjeta;
        this.propina = propina;
        this.pedidos = pedidos;
        this.registro = registro;
        this.enviarEmail = enviarEmail;
    }

    public int calcularTotal() {
        int totalPlatos = pedidos.stream()
                .filter(p -> p.TipoDeProducto().equals("Bebida"))
                .mapToInt(Pedido::obtenerCosto).sum();

        int totalBebidas = pedidos.stream()
                .filter(p -> p.TipoDeProducto().equals("Bebida"))
                .mapToInt(Pedido::obtenerCosto).sum();

        int descuento = switch (tipoTarjeta) {
            case "Visa" -> (int) (totalBebidas * 0.03);
            case "Mastercard" -> (int) (totalPlatos * 0.02);
            case "Comarca Plus" -> (int) ((totalPlatos + totalBebidas) * 0.02);
            default -> 0;
        };

        int totalConDescuento = total - descuento;
        int montoPropina = totalConDescuento * propina / 100;
        int montoTotal = totalConDescuento + montoPropina;
        String fechaMonto = LocalDate.now() + " || " + montoTotal;

       this.registro.registrarTXT(fechaMonto);
        this.registro.registrarJDBC( fechaMonto);
        enviarEmail(fechaMonto);
        return montoTotal;
    }


 public short registrarTXT(String fechaMonto) {
    registro.registrarTXT( fechaMonto);
     return 0;
 }

    public short registrarJDBC(String fechaMonto) {
 registro.registrarJDBC(fechaMonto);
        return 0;
    }


    public void enviarEmail(String datos) {
        EnviarMails enviarMails = new EnviarMails();

        // Simulamos una dirección de email basada en el nombre
        String email = datos.toLowerCase().replace(" ", ".") + "@mailtrap.io";
        String asunto = "Confirmación de inscripción en el concurso";
        String mensaje = "Se ah realizado una transaccion el dia " + datos + ",\n\n" +
             "El pago a impactado con exito!!\n\n" +
                "Gracias por su preferencia.\n\n" +
                "Saludos cordiales,\n" +
                "El equipo del restaurante";

        enviarMails.enviarEmail( email, asunto, mensaje);
    }


}

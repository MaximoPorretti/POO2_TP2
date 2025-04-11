package Restaurante.algoritmo;

import TP2.EnviarMails;
import TP2.FakeRegistrador;
import TP2.Registrar;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

// Clase Pago
public class Pago implements Registrar {
    private int total;
    private String tipoTarjeta;
    private int propina;
    private List<Pedido> pedidos;
    private FakeRegistrador registroFake;



    public Pago(int total, String tipoTarjeta, int propina, List<Pedido> pedidos, FakeRegistrador registroFake) {
        this.total = total;
        this.tipoTarjeta = tipoTarjeta;
        this.propina = propina;
        this.pedidos = pedidos;
        this.registroFake = registroFake;
    }

    public int calcularTotal() {
        int totalPlatos = pedidos.stream()
                .filter(p -> p.getProductoTipo().equals("Bebida"))
                .mapToInt(Pedido::obtenerCosto).sum();

        int totalBebidas = pedidos.stream()
                .filter(p -> p.getProductoTipo().equals("Bebida"))
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

       this.registroFake.registrarTXT(fechaMonto);
        this.registroFake.registrarJDBC( fechaMonto);
        enviarEmail(fechaMonto);
        return montoTotal;
    }


@Override
 public void registrarTXT(String fechaMonto) {
     String rutaArchivo = "transacciones.txt";

     // Dividir el String en un arreglo
     String[] datos = fechaMonto.split(", ");
     String registro = datos[0] + " || $" + datos[1];

     try (FileWriter fw = new FileWriter(rutaArchivo, true)) {
         fw.write(registro + "\n");
         System.out.println("Transacción registrada en archivo.");
     } catch (IOException e) {
         System.err.println("Error al escribir en archivo: " + e.getMessage());
     }
 }
    @Override
    public void registrarJDBC(String fechaMonto) {
        LocalDate fecha = LocalDate.now();//fecha de hoy

        String sql = "INSERT INTO transacciones (fecha, monto) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:restaurante.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String[] datos = fechaMonto.split(", ");
            pstmt.setString(1, datos[0]);
            pstmt.setInt(2, Integer.parseInt(datos[1])); // Monto total
            pstmt.executeUpdate();

            System.out.println("Transacción registrada en SQLite.");

        } catch (SQLException e) {
            System.err.println("Error al registrar transacción: " + e.getMessage());
        }
    }

    @Override
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
String mensajeFake=mensaje+email+asunto;
        this.registroFake.enviarEmail(mensajeFake);
    }


}

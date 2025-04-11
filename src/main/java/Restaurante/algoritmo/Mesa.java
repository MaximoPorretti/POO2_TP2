package Restaurante.algoritmo;

import TP2.FakeRegistrador;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private int numeroMesa;
    private List<Pedido> pedidosRealizados = new ArrayList<>();
    private FakeRegistrador registrador;
    // Campo para registrar

    public Mesa(int numeroMesa, FakeRegistrador registrador) {
        this.numeroMesa = numeroMesa;
        this.registrador = registrador;

    }

    public void realizarPedido(String nombreComida, String nombreBebida, int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que 0");

        Producto comida = Menu.buscarProducto(nombreComida);
        Producto bebida = Menu.buscarProducto(nombreBebida);

        pedidosRealizados.add(new Pedido(comida, cantidad));
        pedidosRealizados.add(new Pedido(bebida, cantidad));
    }

    public int pagar(String tipoTarjeta, int propina) {


        //  Se calcula el total
     int total = pedidosRealizados.stream().mapToInt(Pedido::obtenerCosto).sum();

        //  Pasamos el registro
        Pago pago = new Pago(total, tipoTarjeta, propina, pedidosRealizados, this.registrador);
        return pago.calcularTotal();
    }
}

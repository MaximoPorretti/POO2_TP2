package Restaurante.algoritmo;

// Clase Pedido
public class Pedido {
    private Producto producto;
    private int cantidad;

    public Pedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int obtenerCosto() {
        return producto.obtenerPrecio() * cantidad;
    }

    public String TipoDeProducto() {
        return producto.getTipo();
    }
}

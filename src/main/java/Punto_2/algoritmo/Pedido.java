package Punto_2.algoritmo;

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

    public Producto getProducto() {
        return producto;
    }
}

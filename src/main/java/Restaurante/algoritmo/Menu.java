
package Restaurante.algoritmo;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private static List<Producto> productos = new ArrayList<>();

    public static void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public static Producto buscarProducto(String nombre) {
        return productos.stream()
                .filter(producto -> producto.getNombre().equals(nombre))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("El producto no existe en el menú."));
    }
}
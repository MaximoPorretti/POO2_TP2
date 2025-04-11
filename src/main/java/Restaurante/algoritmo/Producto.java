package Restaurante.algoritmo;

// Clase base Producto
public class Producto {
    protected String tipo;
    protected String nombre;
    protected int precio;

    public Producto( String tipo,String nombre, int precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
    }


    public int obtenerPrecio() {
        return precio;
    }
    public String getTipo() {
        return tipo;
    }

    public Object getNombre() {
        return nombre;
    }
}


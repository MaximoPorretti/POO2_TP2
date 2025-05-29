package Restaurante;

import Concurso.algoritmo.Concurso;
import Concurso.algoritmo.Participante;
import Restaurante.algoritmo.*;
import TP2.FakeRegistrador;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RestauranteTest {
    private Mesa mesa;

    @Test
    public void testCostoConTarjetaVisa() {
        Menu.agregarProducto(new Producto("Comida","Pizza", 200));
        Menu.agregarProducto(new Producto("Bebida","Coca-Cola", 50));

        mesa = new Mesa(1, new FakeRegistrador());
        mesa.realizarPedido("Pizza", "Coca-Cola", 2);
        int costoTotal = mesa.pagar("Visa", 3);
        assertEquals(511, costoTotal, "El total con Visa no coincide");
    }

    @Test
    public void testCostoConTarjetaMastercard() {
        Menu.agregarProducto(new Producto("Comida","Hamburguesa", 150 ));
        Menu.agregarProducto(new Producto("Bebida","Agua", 30));

        mesa = new Mesa(6, new FakeRegistrador());
        mesa.realizarPedido("Hamburguesa", "Agua", 1);
        int costoTotal = mesa.pagar("Mastercard", 3);
        assertEquals(185, costoTotal, "El total con Mastercard no coincide");
    }

    @Test
    public void testCostoConTarjetaComarcaPlus() {
        Menu.agregarProducto(new Producto("Comida","Pizza", 200));
        Menu.agregarProducto(new Producto("Comida","Hamburguesa", 150));
        Menu.agregarProducto(new Producto("Bebida","Coca-Cola", 50));
        Menu.agregarProducto(new Producto("Bebida","Agua", 30));

        mesa = new Mesa(1, new FakeRegistrador());
        mesa.realizarPedido("Pizza", "Coca-Cola", 2);
        mesa.realizarPedido("Hamburguesa", "Agua", 1);
        int costoTotal = mesa.pagar("Comarca Plus", 2);
        assertEquals(688, costoTotal, "El total con Comarca Plus no coincide");
    }

    @Test
    public void testCostoConTarjetaViedma() {
        Menu.agregarProducto(new Producto("Cena","Pizza", 200));
        Menu.agregarProducto(new Producto("Bebida","Coca-Cola", 50));

        mesa = new Mesa(1, new FakeRegistrador());
        mesa.realizarPedido("Pizza", "Coca-Cola", 2);
        int costoTotal = mesa.pagar("Viedma", 5);
        assertEquals(525, costoTotal);
    }

    @Test
    public void testPedidoInvalidoProductoNoExistente() {
        Menu.agregarProducto(new Producto("Cena","Pizza", 200));
        Menu.agregarProducto(new Producto("Bebida","Coca-Cola", 50));

        mesa = new Mesa(1, new FakeRegistrador());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mesa.realizarPedido("Sushi", "Coca-Cola", 1);
        });
        assertTrue(exception.getMessage().contains("El producto no existe en el menú."));
    }

    @Test
    public void testPedidoInvalidoCantidadCero() {
        Menu.agregarProducto(new Producto("Cena","Pizza", 200));
        Menu.agregarProducto(new Producto("Bebida","Coca-Cola", 50));

        mesa = new Mesa(1, new FakeRegistrador());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mesa.realizarPedido("Pizza", "Coca-Cola", 0);
        });
        assertTrue(exception.getMessage().contains("La cantidad debe ser mayor que 0"));
    }

    @Test
    public void testTXT() {
        FakeRegistrador registrador= new FakeRegistrador();
        // Configurar menú con productos
        Menu.agregarProducto(new Producto("Comida","Pizza", 400));
        Menu.agregarProducto(new Producto("Comida","Hamburguesa", 250));
        Menu.agregarProducto(new Producto("Bebida","Coca-Cola", 50));
        Menu.agregarProducto(new Producto("Bebida","Agua", 30));

        // Crear mesa
        Mesa mesa = new Mesa(1, registrador);

        // Realizar pedidos
        mesa.realizarPedido("Pizza", "Coca-Cola", 2);
        mesa.realizarPedido("Hamburguesa", "Agua", 1);


        int totalVisa = mesa.pagar("Visa", 3);
        String fechaMonto = LocalDate.now() + " || " + totalVisa;

        assertTrue(registrador.startWithTXT(fechaMonto));
    }

    @Test
    public void testJDBC() {
        FakeRegistrador registrador= new FakeRegistrador();
        // Configurar menú con productos
        Menu.agregarProducto(new Producto("Comida","Pizza", 400));
        Menu.agregarProducto(new Producto("Comida","Hamburguesa", 250));
        Menu.agregarProducto(new Producto("Bebida","Coca-Cola", 50));
        Menu.agregarProducto(new Producto("Bebida","Agua", 30));

        // Crear mesa
        Mesa mesa = new Mesa(1, registrador);

        // Realizar pedidos
        mesa.realizarPedido("Pizza", "Coca-Cola", 2);
        mesa.realizarPedido("Hamburguesa", "Agua", 1);


        int totalVisa = mesa.pagar("Visa", 3);
        String fechaMonto = LocalDate.now() + " || " + totalVisa;
        assertTrue(registrador.startWithJDBC(fechaMonto));

    }

}

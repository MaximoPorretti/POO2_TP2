package Restaurante.algoritmo;



import TP2.Registrador;

public class Main {
    public static void main(String[] args) {
        // Configurar menú con productos
        Menu.agregarProducto(new Producto("Comida","Pizza", 400));
        Menu.agregarProducto(new Producto("Comida","Hamburguesa", 250));
        Menu.agregarProducto(new Producto("Bebida","Coca-Cola", 50));
        Menu.agregarProducto(new Producto("Bebida","Agua", 30));
    
        // Crear mesa
        Mesa mesa = new Mesa(1, new Registrador());
    
        // Realizar pedidos
        mesa.realizarPedido("Pizza", "Coca-Cola", 2);
        mesa.realizarPedido("Hamburguesa", "Agua", 1);
    
        // Pagar con diferentes tarjetas
        System.out.println("Los Descuentos de tus tarjetas son: ");
        Registrador registrador= new Registrador();
        float totalVisa = mesa.pagar("Visa", 3);
        System.out.println("Total a pagar con Visa (3% propina): $" + totalVisa);
        float totalMastercard = mesa.pagar("Mastercard", 3);
        System.out.println("Total a pagar con Mastercard (3% propina): $" + totalMastercard);
        float totalComarcaPlus = mesa.pagar("Comarca Plus", 2);
        System.out.println("Total a pagar con Comarca Plus (2% propina): $" + totalComarcaPlus);



    }
}

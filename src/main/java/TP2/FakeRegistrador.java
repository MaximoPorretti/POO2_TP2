package TP2;

public class FakeRegistrador implements Registrar {

    private String contenidoTXT;
    private String contenidoJDBC;

    @Override
    public void registrarTXT(String datos) {
        this.contenidoTXT = datos;
    }

    @Override
    public void registrarJDBC(String datos) {
        this.contenidoJDBC = datos;
    }


    //si empieza con "datos" devuelve true sino false
public boolean startWithTXT(String datos) {return this.contenidoTXT.startsWith(datos);}
public boolean startWithJDBC(String datos) {
    return this.contenidoJDBC.startsWith(datos);
}

}
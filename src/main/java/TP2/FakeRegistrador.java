package TP2;

public class FakeRegistrador implements Registrar {

    private String contenidoTXT;
    private String contenidoJDBC;
    private String contenidoEmail;

    @Override
    public void registrarTXT(String datos) {
        this.contenidoTXT = datos;
    }

    @Override
    public void registrarJDBC(String datos) {
        this.contenidoJDBC = datos;
    }

    @Override
    public void enviarEmail(String datos) {
        this.contenidoEmail = datos;
    }

    //si empieza con "datos" devuelve true sino false
public boolean startWithTXT(String datos) {return this.contenidoTXT.startsWith(datos);}
public boolean startWithJDBC(String datos) {
    return this.contenidoJDBC.startsWith(datos);
}
public boolean startWithEmail(String datos) {
    return this.contenidoEmail.startsWith(datos);
}
}
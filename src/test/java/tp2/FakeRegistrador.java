package tp2;

import TP2.Registrar;

public class FakeRegistrador implements Registrar {

    private String contenidoTXT;
    private String contenidoJDBC;

    @Override
    public short registrarTXT(String datos) {
        this.contenidoTXT = datos;
        return 0;
    }

    @Override
    public short registrarJDBC(String datos) {this.contenidoJDBC = datos;
        return 0;
    }


    //si empieza con "datos" devuelve true sino false
public boolean startWithTXT(String datos) {return this.contenidoTXT.startsWith(datos);}
public boolean startWithJDBC(String datos) {
    return this.contenidoJDBC.startsWith(datos);
}

}
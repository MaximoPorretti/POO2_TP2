package TP2;

public interface Registrar {
        public void registrarTXT(String datos);
       public void registrarJDBC(String datos);
        public boolean startWithTXT(String datos);
        public boolean startWithJDBC(String datos);

}

package Clases;

public class Criptomoneda extends Activo{

    // Atributos


    public Criptomoneda(String codigo, String nombre) {
        super(codigo, nombre);
    }

    @Override
    public double calcularVolatilidad() {
        return 0;
    }
}

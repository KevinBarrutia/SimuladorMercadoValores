package Clases;

public class Criptomoneda extends Activo{

    // Atributos


    public Criptomoneda(String codigo, String nombre, double precioActual) {
        super(codigo, nombre, precioActual);
    }

    @Override
    public double calcularVolatilidad() {
        return 0;
    }
}

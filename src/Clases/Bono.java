package Clases;

public class Bono extends Activo{
    public Bono(String codigo, String nombre, double precioActual) {
        super(codigo, nombre, precioActual);
    }

    @Override
    public double calcularVolatilidad() {
        return 0;
    }
}

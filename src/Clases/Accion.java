package Clases;

public class Accion extends Activo{
    public String empresa;
    public double dividendoPorAccion;

    public Accion(String codigo, String nombre, double precioActual) {
        super(codigo, nombre, precioActual);
    }

    @Override
    public double calcularVolatilidad() {
        return 0;
    }



}

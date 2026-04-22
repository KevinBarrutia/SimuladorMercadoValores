package Clases;

public class Accion extends Activo{
    public Accion(String codigo, String nombre) {
        super(codigo, nombre);
    }


    @Override
    public double calcularVolatilidad() {
        return 0;
    }
}

package Clases;

public class Bono extends Activo{
    public Bono(String codigo, String nombre) {
        super(codigo, nombre);
    }

    @Override
    public double calcularVolatilidad() {
        return 0;
    }
}

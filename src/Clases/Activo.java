package Clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Activo {

    // Atributos
    protected String codigo;
    protected String nombre;
    protected double precioActual;
    protected ArrayList<Double> historialPrecios;

    // Constantes
    private static final int cantidad = 30;
    private static final double precioMinimo = 10.0;
    private static final double precioMaximo = 500.0;

    // Constructor
    public Activo(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        historialPrecios = new ArrayList<>();
        generarHistorialPrecios();
    }

    // Getters Setters
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getPrecioActual() {
        return precioActual;
    }
    public void setPrecioActual(double precioActual) {
        this.precioActual = precioActual;
    }

    // Metodos
    // Metodo Abstracto
    public abstract double calcularVolatilidad();

    // Generar tendencia
    public List<Double> getUltimos7Precios() {
        int n = historialPrecios.size();

        return new ArrayList<>(
                historialPrecios.subList(Math.max(0, n - 7), n)
        );
    }

    // Creación de Precios Aleatorios
    public void generarHistorialPrecios(){
        Random r = new Random();

        for (int i = 0; i < cantidad; i++) {
            double precio = precioMinimo + (precioMaximo - precioMinimo) * r.nextDouble();
            historialPrecios.add(precio);
        }

        // Actualizar precio actual al último generado
        if (!historialPrecios.isEmpty()) {
            precioActual = historialPrecios.getLast();
        }
    }
}

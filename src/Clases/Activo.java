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
    public abstract double calcularVolatilidad();

    public List<Double> getUltimos7Precios() {
        int n = historialPrecios.size();

        return new ArrayList<>(
                historialPrecios.subList(Math.max(0, n - 7), n)
        );
    }

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

    public String tendencia() {
        // Obtener el promedio de 30 días
        double suma30 = 0;
        for (double p : historialPrecios) {
            suma30 += p;
        }
        double media30 = suma30 / historialPrecios.size();

        // Obtener el promedio de los últimos 7 días
        List<Double> ultimos7 = getUltimos7Precios();
        double suma7 = 0;
        for (double p : ultimos7) {
            suma7 += p;
        }
        double media7 = suma7 / ultimos7.size();

        if (media7 > media30) {
            return "alcista";
        } else if (precioActual < (media30 * 0.8)) {
            return "bajista";
        } else {
            return "estable";
        }
    }
}

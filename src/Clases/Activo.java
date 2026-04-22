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

    /**
     * Constructor para inicializar un activo con su identificación básica.
     * Al invocarse, dispara automáticamente la generación del historial de precios.
     * @param codigo Identificador alfanumérico.
     * @param nombre Nombre oficial del activo.
     */
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
    /**
     * Calcula el índice de riesgo o inestabilidad del activo.
     * @return Valor numérico (double) que representa la volatilidad.
     */
    public abstract double calcularVolatilidad();

    /**
     * Devuelve una lista con los precios de los últimos 7 periodos registrados.
     * Si hay menos de 7, devuelve todos los disponibles.
     * @return List de Double con los precios más recientes.
     */
    public List<Double> getUltimos7Precios() {
        int n = historialPrecios.size();

        return new ArrayList<>(
                historialPrecios.subList(Math.max(0, n - 7), n)
        );
    }

    /**
     * Llena el historial con 30 precios aleatorios entre los límites configurados.
     * Al finalizar, establece el precio actual basado en el último valor generado.
     */
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

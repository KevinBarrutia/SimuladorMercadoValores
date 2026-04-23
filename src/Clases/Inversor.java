package Clases;

import java.util.ArrayList;
import java.util.List;

public class Inversor {

    // Atributos según el requerimiento de listas paralelas
    private String dni;
    private double capital;
    private String perfil;

    // Listas paralelas: el índice 0 de 'portfolio' corresponde al índice 0 de 'cantidades'
    private ArrayList<Activo> portfolio;
    private ArrayList<Integer> cantidades;

    public Inversor(String dni, double capital, String perfil) {
        this.dni = dni;
        this.capital = capital;
        this.perfil = perfil;
        this.portfolio = new ArrayList<>();
        this.cantidades = new ArrayList<>();
    }

    // Getters and Setters
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public void setCapital(double capital) {
        this.capital = capital;
    }
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    public double getCapital() { return capital; }
    public String getPerfil() { return perfil; }

    /**
     * Valida si el inversor puede comprar según su perfil de riesgo.
     */
    public boolean puedeComprar(Activo activo, int cantidad) {
        double costoTotal = activo.getPrecioActual() * cantidad;
        double vol = activo.calcularVolatilidad();

        if (costoTotal > this.capital) return false;

        switch (this.perfil.toLowerCase()) {
            case "conservador":
                return vol < 1.0;
            case "moderado":
                return vol < 2.0 || activo.tendencia().equals("alcista");
            case "agresivo":
                return true;
            default:
                return false;
        }
    }

    /**
     * Compra el activo y gestiona las dos listas mediante el mismo índice.
     */
    public void comprar(Activo a, int cantidad) {
        if (puedeComprar(a, cantidad)) {
            this.capital -= (a.getPrecioActual() * cantidad);

            // Buscamos si ya poseemos el activo para actualizar su cantidad
            int indiceEncontrado = -1;
            for (int i = 0; i < portfolio.size(); i++) {
                if (portfolio.get(i).getCodigo().equals(a.getCodigo())) {
                    indiceEncontrado = i;
                    break;
                }
            }

            if (indiceEncontrado != -1) {
                // Si existe, actualizamos la lista de cantidades en el mismo índice
                int cantidadNueva = cantidades.get(indiceEncontrado) + cantidad;
                cantidades.set(indiceEncontrado, cantidadNueva);
            } else {
                // Si es nuevo, añadimos a ambas listas para mantener la paridad
                portfolio.add(a);
                cantidades.add(cantidad);
            }

            System.out.println("Compra exitosa: " + cantidad + " de " + a.getNombre());
        } else {
            System.out.println("No se cumple el perfil de riesgo o capital insuficiente.");
        }
    }

    /**
     * Simulación mensual recorriendo las listas coordinadas.
     */
    public double simularMes() {
        double balanceMes = 0;

        for (int i = 0; i < portfolio.size(); i++) {
            Activo activo = portfolio.get(i);
            int cantidad = cantidades.get(i);

            double precioAnterior = activo.getPrecioActual();

            // Simulación de fluctuación de precio
            double factorCambio = (Math.random() * 2 - 1) * (activo.calcularVolatilidad() / 100);
            double nuevoPrecio = precioAnterior * (1 + factorCambio);

            activo.setPrecioActual(nuevoPrecio);

            // Plusvalía/Minusvalía acumulada
            balanceMes += (nuevoPrecio - precioAnterior) * cantidad;
        }
        return balanceMes;
    }

    public void rebalancear() {
        System.out.println("Rebalanceando portafolio de listas paralelas...");
    }


}
package Clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inversor {

    // Atributos
    private String dni;
    private double capital;
    private String perfil; // "Conservador", "Moderado", "Agresivo"
    // Usamos un Map para guardar Activo y la cantidad (Integer) asociada
    private Map<Activo, Integer> portfolio;

    public Inversor(String dni, double capital, String perfil) {
        this.dni = dni;
        this.capital = capital;
        this.perfil = perfil;
        this.portfolio = new HashMap<>();
    }

    /**
     * Determina si el inversor puede comprar un activo según su perfil
     * y el riesgo del activo.
     */
    public boolean puedeComprar(Activo activo, int cantidad) {
        double costoTotal = activo.getPrecioActual() * cantidad;
        double vol = activo.calcularVolatilidad();

        // Verificar primero si tiene dinero suficiente
        if (costoTotal > this.capital) return false;

        // Lógica de perfil de riesgo
        switch (this.perfil.toLowerCase()) {
            case "conservador":
                return vol < 1.0;
            case "moderado":
                return vol < 2.0 || activo.tendencia().equals("alcista");
            case "agresivo":
                return true; // Compra cualquiera
            default:
                return false;
        }
    }

    /**
     * Realiza la compra si la validación de puedeComprar es exitosa.
     */
    public void comprar(Activo a, int cantidad) {
        if (puedeComprar(a, cantidad)) {
            this.capital -= (a.getPrecioActual() * cantidad);
            // Actualizar portfolio: si ya existe suma la cantidad, si no, la crea
            portfolio.put(a, portfolio.getOrDefault(a, 0) + cantidad);
            System.out.println("Compra exitosa: " + cantidad + " de " + a.getNombre());
        } else {
            System.out.println("No se cumple el perfil de riesgo o capital insuficiente.");
        }
    }

    /**
     * Recorre el portfolio y calcula la plusvalía o minusvalía total.
     * (Simula el cambio de precio basado en la volatilidad)
     */
    public double simularMes() {
        double balanceMes = 0;

        for (Map.Entry<Activo, Integer> entry : portfolio.entrySet()) {
            Activo activo = entry.getKey();
            int cantidad = entry.getValue();

            double precioAnterior = activo.getPrecioActual();

            // Simulación simple: el precio cambia un % aleatorio basado en su volatilidad
            double factorCambio = (Math.random() * 2 - 1) * (activo.calcularVolatilidad() / 100);
            double nuevoPrecio = precioAnterior * (1 + factorCambio);

            activo.setPrecioActual(nuevoPrecio); // Actualizamos el precio del activo

            // Plusvalía = (Precio Nuevo - Precio Anterior) * cantidad
            balanceMes += (nuevoPrecio - precioAnterior) * cantidad;
        }

        return balanceMes;
    }

    /**
     * Lógica de rebalanceo según pérdidas/ganancias.
     */
    public void rebalancear() {
        // En un caso real, aquí compararías el historial de 3 meses.
        // Vender activos con pérdida > 10% y comprar ganadores > 15%.
        System.out.println("Ejecutando algoritmo de rebalanceo...");
    }

    // Getters básicos
    public double getCapital() { return capital; }
    public String getPerfil() { return perfil; }
}
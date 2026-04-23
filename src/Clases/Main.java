package Clases;

import Clases.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializar el Mercado
        Mercado mercado = new Mercado();

        // 2. Crear Acciones (con tu lógica de sector y dividendos)
        Accion apple = new Accion("AAPL", "Apple Inc.", "Apple Corp", 0.25, "Tecnología", 15000);
        Accion santander = new Accion("SAN", "Banco Santander", "Santander Group", 0.15, "Financiero", 50000);

        // 3. Crear Criptomonedas (para comparar riesgos)
        Criptomoneda eth = new Criptomoneda("ETH", "Ethereum", "Ethereum Network", 120000000, 1000000);
        eth.addWalletGrande(35.0); // Alta concentración (Afecta volatilidad)

        // Registrar todo en el mercado
        mercado.agregarActivo(apple);
        mercado.agregarActivo(santander);
        mercado.agregarActivo(eth);

        // 4. Crear Inversores con distintos perfiles
        Inversor ana = new Inversor("22334455B", 10000.0, "Moderado");
        Inversor pedro = new Inversor("88776655C", 5000.0, "Agresivo");

        mercado.agregarInversor(ana);
        mercado.agregarInversor(pedro);

        System.out.println("=== ANÁLISIS DE ACTIVOS ===");
        // Probar tu lógica de dividendos
        System.out.println("Dividendo Anual Apple (Tecnología -30%): $" + apple.dividendoAnual());
        System.out.println("Dividendo Anual Santander (Normal): $" + santander.dividendoAnual());

        // Probar volatilidad comparada
        System.out.println("Volatilidad Acción (Estable): " + apple.calcularVolatilidad());
        System.out.println("Volatilidad Cripto (Riesgosa): " + eth.calcularVolatilidad());

        System.out.println("\n=== SIMULACION DE COMPRAS ===");

        // Ana (Moderada) intenta comprar algo muy volátil
        System.out.print("Ana intenta comprar ETH: ");
        ana.comprar(eth, 1); // fallo por riesgo > 2.0 o tendencia

        // Pedro (Agresivo) compra sin miedo
        System.out.print("Pedro intenta comprar ETH: ");
        pedro.comprar(eth, 2);

        System.out.println("\n=== EVENTO DE MERCADO: CRISIS ===");
        System.out.println("Precio Apple antes de crisis: $" + apple.getPrecioActual());

        mercado.crisis(); // Llama a tu método que reduce 20-40% y quiebra agresivos

        System.out.println("Precio Apple después de crisis: $" + apple.getPrecioActual());
        System.out.println("Capital de Pedro tras crisis (Agresivo): $" + pedro.getCapital());
        System.out.println("Capital de Ana tras crisis (Moderada): $" + ana.getCapital());

        System.out.println("\n=== RESULTADO FINAL DE CARTERAS ===");
        double plusvaliaAna = ana.simularMes();
        System.out.println("Plusvalía/Minusvalía de Ana este mes: $" + plusvaliaAna);
    }
}
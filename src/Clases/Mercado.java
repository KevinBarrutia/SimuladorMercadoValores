package Clases;

import java.util.ArrayList;
import java.util.Random;

public class Mercado {
    // Atributos
    private ArrayList<Activo> activos;
    private ArrayList<Inversor> inversores;

    // Constructor
    public Mercado() {
        this.activos = new ArrayList<>();
        this.inversores = new ArrayList<>();
    }

    // Getters and Setters
    public void agregarActivo(Activo a) { activos.add(a); }
    public void agregarInversor(Inversor i) { inversores.add(i); }

    public void crisis() {
        Random random = new Random();

        // Impacto en los Activos
        for (Activo activo : activos) {
            // Genera un porcentaje entre 0.20 y 0.40
            double porcentajePerdida = 0.20 + (0.40 - 0.20) * random.nextDouble();
            double nuevoPrecio = activo.getPrecioActual() * (1 - porcentajePerdida);
            activo.setPrecioActual(nuevoPrecio);
        }

        // Impacto en los Inversores (Quiebra)
        for (Inversor inversor : inversores) {
            if (inversor.getPerfil().equalsIgnoreCase("agresivo")) {
                inversor.setCapital(0);
                System.out.println("Inversor DNI " + inversor.getDni() + " ha quebrado.");
            }
        }
    }


}
package Clases;

import java.util.ArrayList;
import java.util.Random;

public class Mercado {
    // Atributos definidos en la imagen
    private ArrayList<Activo> activos;
    private ArrayList<Inversor> inversores;

    public Mercado() {
        this.activos = new ArrayList<>();
        this.inversores = new ArrayList<>();
    }

    /**
     * Crisis(): Todos los activos pierden entre 20-40% de su valor.
     * Los inversores agresivos con palanca (leverage) > 2x quiebran.
     */
    public void crisis() {
        Random random = new Random();

        // 1. Impacto en los Activos
        for (Activo activo : activos) {
            // Genera un porcentaje entre 0.20 y 0.40
            double porcentajePerdida = 0.20 + (0.40 - 0.20) * random.nextDouble();
            double nuevoPrecio = activo.getPrecioActual() * (1 - porcentajePerdida);
            activo.setPrecioActual(nuevoPrecio);
        }

        // 2. Impacto en los Inversores (Quiebra)
        for (Inversor inversor : inversores) {
            // Asumiendo que el inversor tiene un atributo o método para consultar su perfil
            if (inversor.getPerfil().equalsIgnoreCase("agresivo")) {
                /* * Nota: La consigna menciona "palanca > 2x".
                 * Si no tienes ese atributo, se asume que los agresivos
                 * operan así y su capital pasa a 0.
                 */
                inversor.setCapital(0);
                System.out.println("Inversor DNI " + inversor.getDni() + " ha quebrado.");
            }
        }
    }

    // Métodos para agregar datos al mercado
    public void agregarActivo(Activo a) { activos.add(a); }
    public void agregarInversor(Inversor i) { inversores.add(i); }
}
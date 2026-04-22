package Clases;

import java.util.ArrayList;
import java.util.List;

public class Criptomoneda extends Activo {

    // Atributos
    private String blockchain;
    private double maxSupply;
    private double minadoRestante;
    private ArrayList<Double> walletsGrandes; // porcentajes (ej: 25.0 = 25%)

    /**
     * Constructor para crear una criptomoneda con sus parámetros de red y suministro.
     * @param codigo         Símbolo de la moneda.
     * @param nombre         Nombre de la moneda.
     * @param blockchain     Nombre de la red principal.
     * @param maxSupply      Suministro total máximo.
     * @param minadoRestante Unidades pendientes de emisión.
     */
    public Criptomoneda(String codigo, String nombre,
                        String blockchain, double maxSupply, double minadoRestante) {
        super(codigo, nombre);
        this.blockchain = blockchain;
        this.maxSupply = maxSupply;
        this.minadoRestante = minadoRestante;
        this.walletsGrandes = new ArrayList<>();
    }

    // Getters Setters
    public String getBlockchain() {
        return blockchain;
    }
    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }
    public double getMaxSupply() {
        return maxSupply;
    }
    public void setMaxSupply(double maxSupply) {
        this.maxSupply = maxSupply;
    }
    public double getMinadoRestante() {
        return minadoRestante;
    }
    public void setMinadoRestante(double minadoRestante) {
        this.minadoRestante = minadoRestante;
    }
    public List<Double> getWalletsGrandes() {
        return walletsGrandes;
    }

    // Metodos
    /**
     * Registra el porcentaje de posesión de una billetera de gran tamaño (ballena).
     * @param porcentaje Valor entre 0 y 100 que representa la cuota de mercado de la wallet.
     */
    public void addWalletGrande(double porcentaje) {
        walletsGrandes.add(porcentaje);
    }


    /**
     * Calcula la volatilidad específica para criptomonedas usando la desviación estándar
     * de los últimos 7 días, un factor de ajuste de mercado (2.5) y
     * penalizaciones por concentración de ballenas.
     * @return Índice de volatilidad ajustado.
     */
    @Override
    public double calcularVolatilidad() {
        List<Double> ultimos7Precios = getUltimos7Precios();

        // Media
        double media = 0;
        for (double precio : ultimos7Precios) {
            media += precio;
        }
        media /= ultimos7Precios.size();

        // Varianza
        double varianza = 0;
        for (double precio : ultimos7Precios) {
            varianza += Math.pow(precio - media, 2);
        }
        varianza /= ultimos7Precios.size();
        double volatilidad = Math.sqrt(varianza);

        // Factor criptomoneda
        volatilidad *= 2.5;

        // Riesgo por concentración
        for (double w : walletsGrandes) {
            if (w > 20) {
                volatilidad += 0.5;
                break;
            }
        }
        return volatilidad;
    }

    /**
     * Analiza el nivel de riesgo por centralización de la moneda.
     * Evalúa si el suministro está concentrado en pocas manos (Top 1 y Top 3 wallets).
     * @return String con el nivel de centralización ("alta", "media", "baja" o "desconocida").
     */
    public String centralizacion() {
        if (walletsGrandes.isEmpty()) return "desconocida";

        double top1 = walletsGrandes.getFirst();

        if (top1 > 30) return "alta";
        double sumaTop3 = 0;
        for (int i = 0; i < Math.min(3, walletsGrandes.size()); i++) {
            sumaTop3 += walletsGrandes.get(i);
        }
        if (sumaTop3 > 50) return "media";
        return "baja";
    }
}
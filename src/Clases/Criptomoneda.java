package Clases;

import java.util.ArrayList;
import java.util.List;

public class Criptomoneda extends Activo {

    // Atributos
    private String blockchain;
    private double maxSupply;
    private double minadoRestante;
    private ArrayList<Double> walletsGrandes; // porcentajes (ej: 25.0 = 25%)

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


    public void addWalletGrande(double porcentaje) {
        walletsGrandes.add(porcentaje);
    }

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
        double volatilidad = Math.sqrt(varianza); // Volatilidad Base

        // Factor criptomoneda
        volatilidad *= 2.5;

        // Riesgo por concentración
        for (double wallet : walletsGrandes) {
            if (wallet > 20) {
                volatilidad += 0.5;
                break;
            }
        }
        return volatilidad;
    }

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
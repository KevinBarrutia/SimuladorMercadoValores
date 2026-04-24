package Clases;

import java.util.ArrayList;
import java.util.Arrays;

public class Bono extends Activo{
    public String paisEmisor;
    public String rating;
    public int vencimiento;
    public double cuponAnual;


    private static final ArrayList<String> paisesEmergentes =
            new ArrayList<>(Arrays.asList("Argentina","Turquía",
                    "Venezuela","Pakistan","Nigeria"));

    public Bono(String codigo, String nombre, String paisEmisor,
                String rating, int vencimiento, double cuponAnual) {
        super(codigo, nombre);
        this.paisEmisor = paisEmisor;
        this.rating = rating;
        this.vencimiento = vencimiento;
        this.cuponAnual = cuponAnual;
    }

    @Override
    public double calcularVolatilidad() {
        double base;

        switch (rating.toUpperCase()) {
            case "AAA": base = 0.0; break;
            case "AA":  base = 0.125; break;
            case "A":   base = 0.25; break;
            case "BBB": base = 0.375; break;
            case "BASURA": base = 0.5; break;
            default: base = 0.25; break;
        }

        if (vencimiento < 1) {
            base *= 0.5;
        }

        return base;
    }

    public String riesgoPais(){
        if(paisesEmergentes.contains(paisEmisor)){
            switch (rating.toUpperCase()){
                case "AAA": return "AA";
                case "AA":  return "A";
                case "A":   return "BBB";
                default:    return "basura";
            }
        }
        return rating;
    }
}

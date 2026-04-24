package Clases;

public class Accion extends Activo{
    public String empresa;
    public double dividendoPorAccion;
    public String sector;
    public int numAccionesEnCirculacion;

    public Accion(String codigo, String nombre,String empresa,double dividendoPorAccion,
                  String sector,int numAccionesEnCirculacion) {
        super(codigo, nombre);

        this.empresa = empresa;
        this.dividendoPorAccion = dividendoPorAccion;
        this.sector = sector;
        this.numAccionesEnCirculacion = numAccionesEnCirculacion;
    }

    // Getters and Setters
    public int getNumAccionesEnCirculacion() {
        return numAccionesEnCirculacion;
    }
    public void setNumAccionesEnCirculacion(int numAccionesEnCirculacion) {
        this.numAccionesEnCirculacion = numAccionesEnCirculacion;
    }

    public String getSector() {
        return sector;
    }
    public void setSector(String sector) {
        this.sector = sector;
    }
    public double getDividendoPorAccion() {
        return dividendoPorAccion;
    }
    public void setDividendoPorAccion(double dividendoPorAccion) {
        this.dividendoPorAccion = dividendoPorAccion;
    }
    public String getEmpresa() {
        return empresa;
    }
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }


    @Override
    public double calcularVolatilidad() {
        if (historialPrecios == null || historialPrecios.isEmpty()) return 0.0;

        double media = 0.0;
        for (double p : historialPrecios) {
            media += p;
        }
        media /= historialPrecios.size();

        double sumaCuadrados = 0.0;
        for (double p : historialPrecios) {
            sumaCuadrados += Math.pow(p - media, 2);
        }

        return Math.sqrt(sumaCuadrados / historialPrecios.size());
    }

    public double dividendoAnual(){
        if (sector.equalsIgnoreCase("Tecnología")){
            return getDividendoPorAccion() * 4 * 0.70; // reduce en 30%
        }else{
            return getDividendoPorAccion() * 4;
        }
    }
}

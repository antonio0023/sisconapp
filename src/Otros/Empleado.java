package Otros;

public class Empleado {

   private int codigo;
    private String nombre;
    private Double salario;
    private Double eficiencia;
    private int anio;
    private Double salarioDev;
    /*private Double isss;
    private Double afp;
    private Double insafor;*/
    private Double cotizaciones;
    private Double aguinaldo;
    private Double vacaciones;
    private Double salarioCal;
    private Double fr;
    private Double fre;
    private int horas;
    private boolean deptoProduccion;

    public boolean isDeptoProduccion() {
        return deptoProduccion;
    }

    public void setDeptoProduccion(boolean deptoProduccion) {
        this.deptoProduccion = deptoProduccion;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    @Override
    public String toString() {
        return codigo + " " + nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Double getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(Double eficiencia) {
        this.eficiencia = eficiencia;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Double getSalarioDev() {
        return salarioDev;
    }

    public void setSalarioDev(Double salarioDev) {
        this.salarioDev = salarioDev;
    }

    public Double getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(Double cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public Double getAguinaldo() {
        return aguinaldo;
    }

    public void setAguinaldo(Double aguinaldo) {
        this.aguinaldo = aguinaldo;
    }

    public Double getVacaciones() {
        return vacaciones;
    }

    public void setVacaciones(Double vacaciones) {
        this.vacaciones = vacaciones;
    }

    public Double getSalarioCal() {
        return salarioCal;
    }

    public void setSalarioCal(Double salarioCal) {
        this.salarioCal = salarioCal;
    }

    public Double getFr() {
        return fr;
    }

    public void setFr(Double fr) {
        this.fr = fr;
    }
    
    public Double getFre() {
        return fre;
    }

    public void setFre(Double fre) {
        this.fre = fre;
    }
}

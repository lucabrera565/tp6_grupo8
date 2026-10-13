package ar.edu.unju.escmi.tp6.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Credito {

	public static final int CANTIDAD_CUOTAS=20;

	private TarjetaCredito tarjetaCredito;
	private Factura factura;
	private List<Cuota> cuotas = new ArrayList<Cuota>();

	public Credito() {
	}

	public Credito(TarjetaCredito tarjetaCredito, Factura factura, List<Cuota> cuotas) {
		this.tarjetaCredito = tarjetaCredito;
		this.factura = factura;
		this.cuotas = cuotas;
		generarCuotas();
	}

	public Credito(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	public TarjetaCredito getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	public void generarCuotas() {
		if(cuotas==null) {
			cuotas=new ArrayList<Cuota>();
		}
		cuotas.clear();
		double montoCuota=this.factura.calcularTotal()/CANTIDAD_CUOTAS;
		LocalDate fechaGeneracion=LocalDate.now();

		for(int nroCuota=1;nroCuota<=CANTIDAD_CUOTAS;nroCuota++) {
			Cuota cuota=new Cuota();
			cuota.setMonto(montoCuota);
			cuota.setNroCuota(nroCuota);
			cuota.setFechaGeneracion(fechaGeneracion);
			cuota.setFechaVencimiento(fechaGeneracion.plusMonths(nroCuota));
			cuotas.add(cuota);
		}
	}

	public void mostrarCredito() {
		System.out.println("Tarjeta De Credito: " + tarjetaCredito + "\n" + factura
				+ "TOTAL DE LA COMPRA: $" + factura.calcularTotal() + "\nCant. Cuotas: " + cuotas.size() + "\n");
		for(Cuota cuota: cuotas) {
			System.out.println(cuota);
		}
	}
}
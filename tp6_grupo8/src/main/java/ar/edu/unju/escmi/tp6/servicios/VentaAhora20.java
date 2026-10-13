package ar.edu.unju.escmi.tp6.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ar.edu.unju.escmi.tp6.collections.CollectionCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionFactura;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cuota;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

public class VentaAhora20 {

	public static final LocalDate FIN_PROGRAMA=LocalDate.of(2026,12,22);
	public static final double MONTO_MAXIMO=2500000;
	public static final double MONTO_MAXIMO_CELULARES=1000000;

	public void validarVigencia(LocalDate fecha) {
		if(fecha.isAfter(FIN_PROGRAMA)) {
			throw new IllegalStateException("El programa Ahora 20 finalizo el 22/12/2026.");
		}
	}

	public Credito registrarVenta(Cliente cliente,TarjetaCredito tarjeta,List<Detalle> detalles) {
		LocalDate hoy=LocalDate.now();
		validarVigencia(hoy);

		if(cliente==null) {
			throw new IllegalArgumentException("El cliente no existe.");
		}
		if(tarjeta==null || tarjeta.getCliente()==null || tarjeta.getCliente().getDni()!=cliente.getDni()) {
			throw new IllegalArgumentException("La tarjeta no existe o no pertenece al cliente.");
		}
		if(tarjeta.getFechaCaducacion()==null || tarjeta.getFechaCaducacion().isBefore(hoy)) {
			throw new IllegalStateException("La tarjeta esta vencida.");
		}
		if(detalles==null || detalles.isEmpty()) {
			throw new IllegalArgumentException("La compra debe tener al menos un producto.");
		}

		double total=0;
		double totalCelulares=0;
		Map<Producto,Integer> pedidos=new LinkedHashMap<Producto,Integer>();

		for(Detalle det:detalles) {
			Producto producto=det.getProducto();
			if(det.getCantidad()<=0) {
				throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
			}
			if(!CollectionProducto.esProductoAhora20(producto)) {
				throw new IllegalArgumentException("El producto no es de fabricacion nacional, no entra en Ahora 20.");
			}
			int pedido=pedidos.getOrDefault(producto,0)+det.getCantidad();
			Stock stock=CollectionStock.buscarStock(producto);
			if(stock==null || stock.getCantidad()<pedido) {
				throw new IllegalStateException("Stock insuficiente para: "+producto.getDescripcion());
			}
			pedidos.put(producto,pedido);
			total+=det.getImporte();
			if(producto.esCelular()) {
				totalCelulares+=det.getImporte();
			}
		}

		if(total>MONTO_MAXIMO) {
			throw new IllegalStateException("La compra supera el maximo de $2.500.000.");
		}
		if(totalCelulares>MONTO_MAXIMO_CELULARES) {
			throw new IllegalStateException("La compra de celulares supera el maximo de $1.000.000.");
		}
		if(!tarjeta.tieneSaldoSuficiente(total)) {
			throw new IllegalStateException("La tarjeta no tiene limite suficiente. Disponible: $"+tarjeta.getLimiteCompra());
		}

		// recien ahora que esta todo validado se modifica el sistema
		long nroFactura=CollectionFactura.facturas.size()+1;
		Factura factura=new Factura(hoy,nroFactura,cliente,new ArrayList<Detalle>(detalles));
		Credito credito=new Credito(tarjeta,factura,new ArrayList<Cuota>());

		tarjeta.descontarMonto(total);
		for(Map.Entry<Producto,Integer> entrada:pedidos.entrySet()) {
			CollectionStock.reducirStock(CollectionStock.buscarStock(entrada.getKey()),entrada.getValue());
		}
		CollectionFactura.agregarFactura(factura);
		CollectionCredito.agregarCredito(credito);
		return credito;
	}
}
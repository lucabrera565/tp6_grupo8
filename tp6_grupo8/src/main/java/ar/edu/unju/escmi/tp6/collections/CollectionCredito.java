package ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.tp6.dominio.Credito;

public class CollectionCredito {

	 public static List<Credito> creditos = new ArrayList<Credito>();

	 public static void agregarCredito(Credito credito) {

	    	try {
	    		creditos.add(credito);
			} catch (Exception e) {
				System.out.println("\nNO SE PUEDE GUARDAR EL CREDITO");
			}

	    }

	 public static List<Credito> buscarCreditosPorDni(long dni) {
		 List<Credito> encontrados=new ArrayList<Credito>();
		 try {
			 for(Credito cre:creditos) {
				 if(cre.getFactura()!=null && cre.getFactura().getCliente()!=null
						 && cre.getFactura().getCliente().getDni()==dni) {
					 encontrados.add(cre);
				 }
			 }
		 } catch (Exception e) {
			 System.out.println("\nNO SE PUDIERON BUSCAR LOS CREDITOS");
		 }
		 return encontrados;
	 }
}
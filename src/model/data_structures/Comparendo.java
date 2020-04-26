package model.data_structures;

import java.util.Date;

public class Comparendo implements Comparable<Comparendo> {
	private int OBJECT_ID;
	private Date FECHA;
	private String HORA;
	private String Infraccion;
	private String CLASE_VEHICULO;
	private String TIPO_SERVICIO;
	private String LOCALIDAD;
	private String DESC_INFRACCION;
	private String MEDIO;
	private String MUNICIPIO;
	private double latitud;
	private double longitud;
	private double distancia;
	private boolean inmovilizado;
	private boolean suspendido;

//m

	public Comparendo (int pOBJECT_ID, Date pFECHA, String pHORA, String pMedioDeteccion, String pClasevehiculo,String pTIPO_SERVICIO, String pInfraccion, String pDescInfraccion,  String pLOCALIDAD, String pMUNICIPIO, double pLatitud, double pLongitud, double pDistancia)
	{
		OBJECT_ID = pOBJECT_ID;
		FECHA= pFECHA;
		HORA = pHORA;
		MEDIO = pMedioDeteccion;
		Infraccion= pInfraccion;
		CLASE_VEHICULO = pClasevehiculo;
		TIPO_SERVICIO=pTIPO_SERVICIO;
		LOCALIDAD= pLOCALIDAD;
		DESC_INFRACCION = pDescInfraccion;
		MUNICIPIO = pMUNICIPIO;
		latitud =pLatitud;
		longitud  = pLongitud;
		distancia = pDistancia;
		if(DESC_INFRACCION.contains("SERÁ INMOVILIZADO")||DESC_INFRACCION.contains("SERA INMOVILIZADO"))
		inmovilizado=true;
		else
			inmovilizado=false;
		if(DESC_INFRACCION.contains("LICENCIA DE CONDUCCIÓN")||DESC_INFRACCION.contains("LICENCIA DE CONDUCCION"))
			suspendido=true;
			else
				suspendido=false;
	}
	public int darID(){
		return OBJECT_ID;
	}
	public Date darFecha(){
		return FECHA;
	}
	public String darHora(){
		return HORA;
	}
	public String darMedio(){
		return MEDIO; 
	}
	public String darInfraccion(){
		return Infraccion;
	}
	public String darClaseVehiculo(){
		return CLASE_VEHICULO;
	}
	public String darTipoServicio(){
		return TIPO_SERVICIO;
	}
	public String darLocalidad(){
		return LOCALIDAD;
	}
	public String darDescInfo(){
		return DESC_INFRACCION;
	}
	public String darMunicipio(){
		return MUNICIPIO;
	}
	public double darlatitud(){
		return latitud;
	}
	public double darLongitud(){
		return longitud;
	}
	public double darDistancia(){
		return distancia;
	}
	public void cambiarDistancia(double newDistancia){
		distancia = newDistancia;
	}
	public boolean esInmovilizado(){return inmovilizado;}
	public boolean esSuspendido(){return suspendido;}
	@Override
	public int compareTo(Comparendo pComparendo) {
		// TODO Auto-generated method stu
		int respuesta = this.darFecha().compareTo(pComparendo.darFecha());
		if(respuesta==0){
			if(this.OBJECT_ID< pComparendo.darID())return -1;
			else if (this.OBJECT_ID> pComparendo.darID())return 1;
		}

		return respuesta;
	}

}




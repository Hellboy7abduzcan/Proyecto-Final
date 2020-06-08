package gt.edu.umg.ProyectoFinal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Alumno {
	private final DateFormat formatoFecha = new SimpleDateFormat("DIA MES AÑO"); 
	private int carne;
	private String nombre;
	private Date fechaNacimiento;
	private byte[] bytesNombre;
	private byte[] bytesFechaNacimiento;
	public int getCarne() {
		return carne;
	}

	public void setCarne(int carne) {
		this.carne = carne;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
		bytesNombre = new byte[50]; 
		for (int i = 0; i < nombre.length(); i++) {
			bytesNombre[i] = (byte)nombre.charAt(i);
		}
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		String strFecha = formatoFecha.format(fechaNacimiento);
		bytesFechaNacimiento = strFecha.getBytes();
	}

	public byte[] getBytesNombre() {
		return bytesNombre;
	}

	public void setBytesNombre(byte[] bytesNombre) {
		this.bytesNombre = bytesNombre;
		nombre = new String(bytesNombre);
	}

	public void setBytesFechaNacimiento(byte[] bytesFechaNacimiento) throws ParseException {
		this.bytesFechaNacimiento = bytesFechaNacimiento;
		String strFecha = new String(bytesFechaNacimiento); 
		this.fechaNacimiento = formatoFecha.parse(strFecha);
	}

	public byte[] getBytesFechaNacimiento() {
		return bytesFechaNacimiento;
	}
}

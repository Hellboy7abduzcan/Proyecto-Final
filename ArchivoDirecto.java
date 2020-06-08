package gt.edu.umg.ProyectoFinal;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
public class ArchivoDirecto {
	Scanner sc = new Scanner(System.in);
	RandomAccessFile fichero = null;
	private final String ruta = "C:/Users/panfi/OneDrive/Documents/Tarea Convers";
	private final int totalBytes = 83;
	private final static String formatoFecha = "dd/MM/yyyy";
	static DateFormat format = new SimpleDateFormat(formatoFecha);

	public static void main(String[] args) {
		ArchivoDirecto ad = new ArchivoDirecto();
		ad.iniciar();
		System.exit(0); 
	}

	private void iniciar() {
		int opcion = 0;
		try {
			fichero = new RandomAccessFile(ruta, "rw");
			System.out.println("Bienvenid@ Señ@r");
			int carne;
			do {
				try {
					System.out.println("ELIJA SU OPCION, PORFAVOR");
					System.out.println("1.Agregar");
					System.out.println("2.Listar");
					System.out.println("3.Buscar");
					System.out.println("4.Modificar");
					System.out.println("0.Salir");
					opcion = sc.nextInt();
					switch (opcion) {
					case 0:
						System.out.println("GRACIAS POR SU PREFERENCIA");
						break;
					case 1:
						grabarRegistro();
						break;
					case 2:
						listarRegistros();
						break;
					case 3:
						System.out.println("INGRESE EL CARNÉ QUE DESEA BUSCAR: ");
						carne = sc.nextInt();
						sc.nextLine();
						encontrarRegistro(carne);
						break;
					case 4:
						System.out.println("INGRESE EL CARNÉ QUE DESEA MODIFICAR: ");
						carne = sc.nextInt();
						sc.nextLine();
						modificarRegistro(carne);
						break;
					default:
						System.out.println("OPCION INCORRECTA, INTENTE DE NUEVO");
						break;
					}
				} catch (Exception e) { 
					System.out.println("FAIL: " + e.getMessage());
				}
			} while (opcion != 0);
			fichero.close();
		} catch (Exception e) { 
			System.out.println("FAIL: " + e.getMessage());
		}
	}

	private boolean grabarRegistro() {
		boolean resultado = false;
		try {
			Alumno a = new Alumno();
			System.out.println("INGRESE EL CARNÉ, PORFAVOR");
			a.setCarne(sc.nextInt());
			sc.nextLine();
			System.out.println("INGRESE EL NOMBRE, PORFAVOR");
			String strNombre = "";
			int longitud = 0;
			do {
				strNombre = sc.nextLine();
				longitud = strNombre.length();
				if (longitud <= 0 || longitud > 44) {
					System.out.println("LA LONGITUD DEL NOMBRE QUE DESEA INGRESAR NO ES VÁLIDA [1 - 50]");
				}
			} while (longitud <= 0 || longitud > 44);
			a.setNombre(strNombre);
			System.out.println("INGRESE LA FECHA, PORFAVOR");
			Date date = null;
			while (date == null) {
				date = strintToDate(sc.nextLine());
			}
			a.setFechaNacimiento(date);
			fichero.seek(fichero.length());
			fichero.writeInt(a.getCarne());
			fichero.write(a.getBytesNombre());
			fichero.write(a.getBytesFechaNacimiento());
			fichero.write("\n".getBytes()); 
			resultado = true;
		} catch (Exception e) {
			resultado = false;
			System.out.println("ERROR AL INTENTAR AGREGAR SU REGISTRO" + e.getMessage());
		}
		return resultado;
	}

	public void listarRegistros() {
		try {
			long longitud = fichero.length();
			if (longitud <= 0) {
				System.out.println("NO EXISTE REGISTRO");
				return;
			}
			fichero.seek(0);
			System.out.println(longitud);
			Alumno a;
			while (longitud >= totalBytes) {
				a = new Alumno();
				a.setCarne(fichero.readInt());
				byte[] bNombre = new byte[44]; 
				fichero.read(bNombre);
				a.setBytesNombre(bNombre);
				byte[] bFecha = new byte[22]; 
				fichero.read(bFecha);
				fichero.readByte();
				a.setBytesFechaNacimiento(bFecha);
				System.out.println("CARNÉ: " + a.getCarne());
				System.out.println("NOMBRE: " + a.getNombre());
				System.out.println("FECHA DE NACIMIENTO: " + dateToString(a.getFechaNacimiento()));
				longitud -= totalBytes;
			}
		} catch (Exception e) {
			System.out.println("FAIL: " + e.getMessage());
		}
	}

	public void encontrarRegistro(int carne) {
		try {
			long longitud = fichero.length();
			if (longitud <= 0) {
				System.out.println("NO EXISTE REGISTROS");
				return;
			}
			boolean bndEncontrado = false;
			fichero.seek(0);
			Alumno a = new Alumno();
			while (longitud >= totalBytes) {
				a.setCarne(fichero.readInt());
				byte[] bNombre = new byte[44];
				fichero.read(bNombre);
				a.setBytesNombre(bNombre);
				byte[] bFecha = new byte[22];
				fichero.read(bFecha);
				fichero.readByte();
				a.setBytesFechaNacimiento(bFecha);
				if (a.getCarne() == carne) {
					System.out.println("CARNÉ: " + a.getCarne());
					System.out.println("NOMBRE: " + a.getNombre());
					System.out.println("FECHA DE NACIMIENTO: " + dateToString(a.getFechaNacimiento()));
					bndEncontrado = true;
					break;
				}
				longitud -= totalBytes;
			}
			if (!bndEncontrado) { 
				System.out.println("NO SE ENCONTRÓ EL CARNÉ INGRESADO, PORFAVOR VERIFIQUE");
			}
		} catch (Exception e) {
			System.out.println("FAIL: " + e.getMessage());
		}
	}

	private void modificarRegistro(int carne) {
		try {
			boolean bndEncontrado = false, bndModificado = false;
			fichero.seek(0);
			long longitud = fichero.length();
			int registros = 0;
			Alumno a = new Alumno();
			while (longitud > totalBytes) {
				a.setCarne(fichero.readInt());
				byte[] bNombre = new byte[44];
				fichero.read(bNombre);
				a.setBytesNombre(bNombre);
				byte[] bFecha = new byte[22];
				fichero.read(bFecha);
				fichero.readByte();
				a.setBytesFechaNacimiento(bFecha);
				if (a.getCarne() == carne) {
					System.out.println("SI NO DESEA MODIFICAR, PRESIONE ENTER PORFAVOR");
					System.out.println("INGRESE EL NOMBRE");
					String tmpStr = "";
					int len = 0;
					long posicion;
					do {
						tmpStr = sc.nextLine();
						len = tmpStr.length();
						if (len > 44) {
							System.out.println("LA LONGITUD DEL NOMBRE ES INVALIDA [1 - 50]");
						}
					} while (len > 44);
					if (len > 0) {
						a.setNombre(tmpStr);
						posicion = registros * totalBytes;
						fichero.seek(posicion);
						fichero.skipBytes(5);
						fichero.write(a.getBytesNombre());
						bndModificado = true;
					}
					System.out.println("INGRESE LA FECHA, PORFAVOR");
					tmpStr = sc.nextLine();
					if (tmpStr.length() > 0) {
						Date date = null;
						while (date == null) {
							date = strintToDate(tmpStr);
						}
						a.setFechaNacimiento(date);
						posicion = registros * totalBytes;
						fichero.seek(posicion);
						fichero.skipBytes(5 + 44);
						fichero.write(a.getBytesFechaNacimiento());
						bndModificado = true;
					}
					if (bndModificado) { 
						System.out.println("EL REGISTRO HA SIDO MODIFICADO CORRECTAMENTE, LOS NUEVOS DATOS SON:");
					}
					System.out.println("CARNÉ: " + a.getCarne());
					System.out.println("NOMBRE: " + a.getNombre());
					System.out.println("FECHA DE NACIMIENTO: " + dateToString(a.getFechaNacimiento()));
					bndEncontrado = true;
					break;
				}
				registros++;
				longitud -= totalBytes;
			}
			if (!bndEncontrado) { 
				System.out.println("NO SE HALLÓ EL CARNÉ INGRESADO, PORFAVOR VERIFIQUE");
			}
		} catch (Exception e) {
			System.out.println("FAIL: " + e.getMessage());
		}
	}

	public Date strintToDate(String strFecha) {
		Date date = null;
		try {
			date = format.parse(strFecha);
		} catch (Exception e) {
			date = null;
			System.out.println("FAIL EN FECHA: " + e.getMessage());
		}
		return date;
	}

	public String dateToString(Date date) {
		String strFecha;
		strFecha = format.format(date);
		return strFecha;
	}
}

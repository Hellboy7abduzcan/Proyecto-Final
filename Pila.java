package gt.edu.umg.ProyectoFinal;

import java.util.Scanner;
public class Pila {
	private Nodo cima;
	private static Scanner sc;
	
	public void push(int elemento) {
		Nodo nuevo = new Nodo(elemento);
		if (cima == null) {
			cima = nuevo;
		} else {
			nuevo.siguiente = cima;
			cima = nuevo;
		}
	}

	public int pop() {
		if (cima == null) {
			return -1;
		} else {
			int aux = cima.elemento;
			cima = cima.siguiente;
			return aux;
		}
	}

	public void listar() {
		if (cima == null) {
			System.out.println("La pila esta vacia");
		} else {
			Nodo tmp;
			tmp = cima;
			while (tmp != null) {
				System.out.println(tmp.elemento);
				tmp = tmp.siguiente;
			}
		}
	}

	public void vaciarPila() {
		if (cima == null) {
			System.out.println("La pila está vacia");
		} else {
			Nodo tmp;
			while (cima != null) {
				tmp = cima;
				cima = cima.siguiente;
				tmp.siguiente = null;
			}
		}		
	}

	public static void main(String[] args) {
		Pila p = new Pila();
		sc = new Scanner(System.in);
		int salir = 0, elemento, opcion;
		while (salir == 0) {
			System.out.println("1 .... PUSH");
			System.out.println("2 .... POP");
			System.out.println("3 .... Listar");
			System.out.println("4 .... Vaciar");
			System.out.println("0 .... Salir");
			opcion = sc.nextInt();
			switch (opcion) {
			case 1:
				System.out.println("Ingrese la pila: ");
				elemento = sc.nextInt();
				p.push(elemento);
				break;
			case 2:
				elemento = p.pop();
				if (elemento == -1) {
					System.out.println("La pila esta vacia");
				} else {
					System.out.println("Valor eliminado de la pila: " + elemento);
				}
				break;
			case 3: 
				p.listar();
				break;
			case 4:
				p.vaciarPila();
				break;
			default:
				if (opcion == 0) {
					salir = 1;
					System.out.println("Que tenga buen dia señor, el androide Christian se despide de usted coordialmente");
				}
				break;
			}
		}
	}
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Buffer {
	private static int capacidad;
	private static int NClientes;
	private ArrayList<Mensaje> mensajes = new ArrayList<>();
	private Object servidores = new Object();
	private Object clientes = new Object();

	public void almacenar(Mensaje men) throws InterruptedException {
		synchronized (clientes) {
			System.out.println("Va a entrar un mensaje, mi capacidad es: " + capacidad);
			while(capacidad<1) {clientes.wait();}
		}
		synchronized (this) {
			mensajes.add(men);
			capacidad--;
			System.out.println("entró un mensaje, mi capacidad es: " + capacidad + " hay " + mensajes.size());
		}
	}

	public Mensaje retirar() throws InterruptedException {
		synchronized (servidores) {
			System.out.println("va a salir un mensaje, mi capacidad es: " + capacidad);
			while(mensajes.isEmpty() && NClientes > 0)
			{
				Thread.yield();
				Thread.sleep(400);
			}
			Mensaje m = null;
			
			// no entiendo cómo, pero al final llegan acá aún cuando no hay clientes y por eso
			// hago esta verificación.
			if (NClientes > 0)
			{
				m = mensajes.remove(0);
				capacidad++;
				synchronized (clientes) {
					clientes.notify();
				}
			}
			System.out.println("salió un mensaje, mi capacidad es: " + capacidad);
			return m;
		}
	}

	public synchronized int getNClientes() {
		return NClientes;
	}

	public synchronized void salir() {
		NClientes--;
	}

	public synchronized int mensajes()
	{
		return mensajes.size();
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		Cliente[] clientes;
		Servidor[] servidores;
		Buffer buff = new Buffer();


		BufferedReader br = new BufferedReader(new FileReader(new File("parametros.txt")));
		br.readLine();
		br.readLine();
		capacidad = Integer.parseInt(br.readLine());
		br.readLine();
		NClientes = Integer.parseInt(br.readLine()); 
		clientes = new Cliente[NClientes];
		br.readLine();
		int j = 0;
		for (String s : br.readLine().split(",")) {
			clientes[j]=new Cliente(Integer.parseInt(s),buff,j);
			clientes[j].start();
			j++;
		}
		br.readLine();
		int i = Integer.parseInt(br.readLine());
		servidores = new Servidor[i];
		for (int k = 0; k < i; k++) {
			servidores[k]=new Servidor(buff,k);
			servidores[k].start();
		}
		for (Cliente cliente : clientes) {
			cliente.join();
		}
		for (Servidor servidor : servidores) {
			servidor.join();
		}
		br.close();
	}
}

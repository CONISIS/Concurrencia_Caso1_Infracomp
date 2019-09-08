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
			while(capacidad<1) {clientes.wait();}
		}
		synchronized (this) {
			mensajes.add(men);
			capacidad--;
		}
	}
	
	public Mensaje retirar() throws InterruptedException {
		synchronized (servidores) {
			while(mensajes.isEmpty()){Thread.yield();Thread.sleep(400);}			
		}
		Mensaje m;
		synchronized (servidores) {
			m = mensajes.remove(0);
			capacidad++;
		}
		synchronized (clientes) {
			clientes.notify();
		}
		return m;
	}
	
	public synchronized int getNClientes() {
		return NClientes;
	}
	
	public synchronized void salir() {
		NClientes--;
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
		int i = Integer.parseInt(br.readLine());
		servidores = new Servidor[i];
		for (int j = 0; j < i; j++) {
			servidores[j]=new Servidor(buff,j);
			servidores[j].start();
		}
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
		for (Servidor servidor : servidores) {
			servidor.join();
		}
		for (Cliente cliente : clientes) {
			cliente.join();
		}
		br.close();
	}
}

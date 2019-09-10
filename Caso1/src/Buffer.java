import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Buffer {
	private int capacidad;
	private int NClientes;
	private LinkedList<Mensaje> mensajes;

	public Buffer (int capacidad, int clientes)
	{
		this.capacidad = capacidad;
		NClientes = clientes;
		mensajes = new LinkedList<>();
	}

	public void almacenar(Mensaje men) throws InterruptedException
	{
		System.out.println("Va a entrar un mensaje, mi capacidad es: " + capacidad);
		synchronized(this)
		{
			while(capacidad<1)
			{
				System.out.println("Alguién se durmió almacenando");
				wait();
				System.out.println("Alguién se despertó");
			}
		}
		synchronized(this)
		{
			mensajes.add(men);
			capacidad--;
		}
		System.out.println("entró un mensaje, mi capacidad es: " + capacidad);
	}

	public Mensaje retirar() throws InterruptedException
	{
		synchronized (this)
		{
			if (NClientes < 0)
				return null;
		}

		System.out.println("va a salir un mensaje, mi capacidad es: " + capacidad);
		synchronized (this)
		{
			while(mensajes.isEmpty())
			{
				System.out.println("Un servidor cedió su puesto");
				Thread.yield();
				Thread.sleep(400);
			}
		}

		synchronized (this)
		{
			Mensaje m = null;
			if (NClientes > 0)
			{
				System.out.println("Un servidor sacará un mensaje");
				m = mensajes.remove();
				capacidad++;
				notify();
				System.out.println("Un servidor despertó a alguien");
				System.out.println("salió un mensaje, mi capacidad es: " + capacidad);
			}
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
		BufferedReader br = new BufferedReader(new FileReader(new File("parametros.txt")));
		br.readLine();
		br.readLine();
		int c = Integer.parseInt(br.readLine());
		br.readLine();
		int n = Integer.parseInt(br.readLine());
		Buffer buff = new Buffer(c,n);
		clientes = new Cliente[n];
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

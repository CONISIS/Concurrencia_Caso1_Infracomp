import java.util.Random;

public class Cliente extends Thread{
	
	int id;
	int mensajes;
	Buffer buff;
	Random rand = new Random();
	
	public Cliente(int mensajes,Buffer buff,int id) {
		this.mensajes = mensajes;
		this.buff = buff;
		this.id = id;
	}
	
	public void run() {
		for (int i = 0; i < mensajes; i++) {
			System.out.println("Cliente "+id+" iniciando m "+i);
			Mensaje m = new Mensaje(rand.nextInt(50), buff);
			m.mandar();
		}
		System.out.println("Cliente "+id+" saliendo");
		buff.salir();
	}
}

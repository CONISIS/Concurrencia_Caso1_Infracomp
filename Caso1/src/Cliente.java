import java.util.Random;

public class Cliente extends Thread{

	int id;
	int mensajes;
	Buffer buff;
	Random rand = new Random();
	Mensaje m;

	public Cliente(int mensajes,Buffer buff,int id) {
		this.mensajes = mensajes;
		this.buff = buff;
		this.id = id;
	}

	public void run() {
		for (int i = 0; i < mensajes; i++) {
			m = new Mensaje(rand.nextInt(50), buff);
			System.out.println("Cliente "+id+" iniciando m "+i + " " + m);
			m.mandar();
		}
		System.out.println("Cliente "+id+" saliendo");
		buff.salir();
	}
}

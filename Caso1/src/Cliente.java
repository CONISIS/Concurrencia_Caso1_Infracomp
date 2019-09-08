import java.util.Random;

public class Cliente extends Thread{
	
	int mensajes;
	Buffer buff;
	Random rand = new Random();
	
	public Cliente(int mensajes,Buffer buff) {
		this.mensajes = mensajes;
		this.buff = buff;
	}
	
	public void run() {
		for (int i = 0; i < mensajes; i++) {
			Mensaje m = new Mensaje(rand.nextInt(50), buff);
		}
		buff.salir();
	}
}

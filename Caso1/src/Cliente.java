
public class Cliente extends Thread{
	
	int mensajes;
	Buffer buff;
	
	public Cliente(int mensajes,Buffer buff) {
		this.mensajes = mensajes;
		this.buff = buff;
	}
	
	public void run() {

	}
}

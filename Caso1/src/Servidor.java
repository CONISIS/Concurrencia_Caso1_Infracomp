
public class Servidor extends Thread{
	
	Buffer buff;

	public void run() {
		while(buff.getNClientes()>0) {
			try {
				Mensaje m = buff.retirar();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public Servidor(Buffer buff) {
		this.buff = buff;
	}
}

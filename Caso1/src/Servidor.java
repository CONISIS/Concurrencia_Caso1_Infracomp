
public class Servidor extends Thread{
	
	Buffer buff;
	@Override
	public void run() {
		while(buff.getNClientes()>0) {
			Mensaje m = buff.retirar();
			
		}
	}
	
	
	public Servidor(Buffer buff) {
		this.buff = buff;
	}
}


public class Servidor extends Thread{
	
	Buffer buff;
	int id;

	public void run() {
		while(buff.getNClientes()>0) {
			try {
				Mensaje m = buff.retirar();
				m.setMensaje(m.getMensaje()+1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public Servidor(Buffer buff, int id) {
		this.buff = buff;
		this.id = id;
	}
}


public class Servidor extends Thread{

	Buffer buff;
	int id;
	int cont = 0;

	public void run() {
		while(buff.getNClientes()>0) {
			try {
				Mensaje m = buff.retirar();
				if (m != null)
				{
					System.out.println("El servidor " + id + " retiró un mensaje");
					m.setMensaje(m.getMensaje()+1);
					cont ++;
					synchronized (m)
					{
						m.notify();
					}	
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("El servidor " + id + " respondió " + cont  + " solicitudes");
	}

	public Servidor(Buffer buff, int id) {
		this.buff = buff;
		this.id = id;
	}
}

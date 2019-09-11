
public class Mensaje{
		int mensaje;
		Buffer buff;
		
		public Mensaje(int mensaje, Buffer buff) {
			this.mensaje = mensaje;
			this.buff = buff;
		}

		public int getMensaje() {
			return mensaje;
		}

		public void setMensaje(int mensaje) {
			this.mensaje = mensaje;
		}
		public void mandar() {
			try {
				buff.almacenar(this);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public synchronized void esperar()
		{
			try
			{
				System.out.println("Un cliente se dormirá en el mensaje " + this);
				wait();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public synchronized void despertar()
		{
			System.out.println("Un cliente se despertará del mensaje " + this);
			notify();
		}
}

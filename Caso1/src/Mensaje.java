
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
		public void irse() {
			try {
				buff.almacenar(this);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
}

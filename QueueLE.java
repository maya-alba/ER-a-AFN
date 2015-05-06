package final_MC;
public class QueueLE<E> {
	
	private ListaEnlazada<E> datos;
	
	public QueueLE(){
		this.setDatos(new ListaEnlazada<E>());
	}
	
	public void enqueue(E dato){
		this.getDatos().insertarFin(dato);
	}
	public E dequeue(){
		try{
			return this.getDatos().borrarInicio();
		}
		catch(ListaVaciaException e){
			throw new QueueVaciaException("No se puede hacer un dequeue en una queue vacía");
		}
	}
	public E next(){
		try{
			return this.getDatos().inicio();
		}
		catch(ListaVaciaException e){
			throw new QueueVaciaException("No se puede regresar el siguiente elemento de una queue vacía");
		}
	}
	public int size(){
		return this.getDatos().size();
	
	}
	public void flush(){
		this.setDatos(new ListaEnlazada<E>());
		System.gc();
	}
	public boolean isEmpty(){
		return this.getDatos().isEmpty();
	}
	public String toString(){
		return this.getDatos().toString();

	}
	public static void main(String[] args) {
		QueueLE<Integer> fila = new QueueLE<Integer>();
		
		for(int i=0; i<10;i++){
			fila.enqueue(i+1);
		}
		System.out.println(fila);
		
		
		System.out.println(fila.next());
		

	}

	public ListaEnlazada<E> getDatos() {
		return datos;
	}

	public void setDatos(ListaEnlazada<E> datos) {
		this.datos = datos;
	}

}

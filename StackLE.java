package final_MC;
public class StackLE<E> {

	private ListaEnlazada<E> datos;
	
	public StackLE(){
		this.datos= new ListaEnlazada<E>();
	}
	public void push(E dato){
		this.datos.insertarInicio(dato);
	}
	public E pop(){
		try{
			return this.datos.borrarInicio();
		}
		catch(ListaVaciaException e){
			throw new StackVaciaException("No se puede hacer un pop en una pila vacía");
		}
	}
	public E top(){
		try{
			return this.datos.inicio();
		}
		catch(ListaVaciaException e){
			throw new StackVaciaException("No se puede hacer un pop en una pila vacía");
		}
	}
	public boolean isEmpty(){
		return this.datos.isEmpty();
	}
	public int size(){
		return this.datos.size();
	}
	public void flush(){
		this.datos = new ListaEnlazada<E>();
		System.gc();
	}
	public String toString(){
		String tmp ="[";
		for(int i=0;i<this.size();i++){
			tmp+=this.datos.elementoEn(this.size()-1-i);
		}
		return tmp+"]";
	}
	
	public static void main(String[] args) {
		
		StackLE<Integer> pila = new StackLE<Integer>();
		
		for(int i=0;i<10;i++){
			pila.push(i+1);
		}
		System.out.println(pila);
		pila.pop();
		System.out.println(pila);
		System.out.println(pila.size());
		pila.flush();
		System.out.println(pila.size());
	
		
		
		

	}

}

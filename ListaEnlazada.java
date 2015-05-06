package final_MC;
public class ListaEnlazada<E> {

	Nodo<E> inicio,
			fin;
	
	int size;
	
	public ListaEnlazada(){
		this.inicio=null;
		this.fin=null;
		this.size=0;
	}
	
	public ListaEnlazada(E[] datos){
		this();
		for(int i=0; i<datos.length;i++){
			insertarFin(datos[i]);
		}
	}
	
	public void insertarInicio(E dato){
		Nodo<E> nuevo=new Nodo<E>(dato, this.inicio);
		this.inicio=nuevo;
		if(this.size==0){
			this.fin=nuevo;
		}
		this.size++;
	}
	
	public boolean isEmpty(){
		return this.size==0;
	}
	
	public void insertarFin(E dato){
		if(this.size==0){
			this.insertarInicio(dato);
		}
		else{
			Nodo<E> nuevo = new Nodo<E>(dato);
			this.fin.setNext(nuevo);
			this.fin = nuevo;
			this.size++;
		}
	}
	
	public int size(){
		return this.size;
	}
	
	public E inicio(){
		try{
			return this.inicio.getDato();
		}
		catch(NullPointerException e){
			throw new ListaVaciaException("No se puede regresar el 1er elemento de una lista vacía");
		}
		
	}
	
	public E fin(){
		try{
			return this.fin.getDato();
		}
		catch(NullPointerException e){
			throw new ListaVaciaException("No se puede regresar el último elemento de una lista vacía");
		}
		
	}
	
	public E elementoEn(int pos){	
		if(pos<0 || pos>= this.size){
			throw new IndexOutOfBoundsException("No se puede obtener el elemento "+ pos+" de una lista de size "+this.size);
		}
		else if(pos==this.size-1){
			return this.fin();			
		}
		else{
			Nodo<E> tmp = this.inicio; 
			for(int i=0;i<pos;i++){
				tmp = tmp.getNext();	
			}
			return tmp.getDato();
		}
	}
	
	public void insertarEn(int pos, E dato){
		if(pos<0 || pos>this.size){
			throw new IndexOutOfBoundsException("No se puede insertar un elemento en la posición "+pos+" en una lista de size "+this.size);
		}
		else if(pos==0){
			this.insertarInicio(dato);
		}
		else if(pos==this.size){
			this.insertarFin(dato);
		}
		else{
			Nodo<E> nodo = this.inicio;
			for(int i=0;i<pos-1;i++){
				nodo=nodo.getNext();
			}
			Nodo<E> nuevo = new Nodo<E>(dato,nodo.getNext());
			//nuevo.setNext(nodo.getNext());
			nodo.setNext(nuevo);
			this.size++;
		}
		
	}
	
	public E borrarEn(int pos){
		
		if(pos<0 || pos>=this.size){
			throw new IndexOutOfBoundsException("No se puede borrar un elemento en la posición "+pos+" en una lista de size "+this.size);
		}
		if(pos==0){
			return this.borrarInicio();
		}
		else if(pos==this.size-1){
			return this.borrarFin();
		}
		else{
			Nodo<E> nodo = this.inicio;
			E dato;
			for(int i=0;i<pos-1;i++){
				nodo=nodo.getNext();
			}
			dato = nodo.getNext().getDato();
			nodo.setNext(nodo.getNext().getNext());
			this.size--;
			return dato;
		}
		
	}
	
	public E borrarInicio(){
		try{
			E dato = this.inicio.getDato();
			this.inicio=this.inicio.getNext();
			this.size--;
			return dato;
		}
		catch(NullPointerException e){
			throw new ListaVaciaException("No se puede borrar el inicio de una lista vacía");
		}
	}
	
	public E borrarFin(){
		try{
			E dato = this.fin.getDato();
			if(size==1){
				this.inicio=this.fin=null;
				this.size--;
			}
			else{
				Nodo<E> nodo = this.inicio;	
				for(int i=0; i<this.size-2;i++){
					nodo=nodo.getNext();
				}
				nodo.setNext(null);
				this.fin=nodo;
				this.size--;
			}
			return dato;
		}
		catch(NullPointerException e){
			throw new ListaVaciaException("No se puede borrar el final de una lista vacía");
		}
	}
	
	
	public String toString(){
		
		Nodo nodo = this.inicio;
		String res ="";
		
		for(int i=0; i<this.size;i++){
			res+=nodo+" ";
			nodo = nodo.getNext();
		}

		return res;	
	}
	
	
	public static void main(String[] args) {
		
		
		/*Integer[] list = {4,5,6,46,123,14};
		
		ListaEnlazada lista = new ListaEnlazada(list);
		System.out.println(lista);
		System.out.println(lista.borrarEn(5));
		System.out.println(lista);
		lista.insertarFin(10);
		System.out.println(lista);
		*/
		
		

		/*
		Nodo nodo = lista.inicio;

		while(nodo != null){
			System.out.println(nodo.getDato());
			nodo = nodo.next;
		}
		*/
		

	}

}

class Nodo<E>{
	private E dato;
	private Nodo<E> next;
	
	
	public Nodo(E dato, Nodo<E> next) {
		this.dato = dato;
		this.next = next;
	}
	public Nodo(E dato){
		this(dato,null);
	}
	public E getDato() {
		return dato;
	}
	public void setDato(E dato) {
		this.dato = dato;
	}
	public Nodo<E> getNext() {
		return next;
	}
	public void setNext(Nodo<E> next) {
		this.next = next;
	}
	public String toString(){
		return this.dato.toString();
	}
}

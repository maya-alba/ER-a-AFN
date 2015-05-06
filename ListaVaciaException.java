package final_MC;
public class ListaVaciaException extends RuntimeException {
	
	public ListaVaciaException(){
		super("La lista está vacía");
	}
	public ListaVaciaException(String msg){
		super(msg);
	}

}

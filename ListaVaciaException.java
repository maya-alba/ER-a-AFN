package final_MC;
public class ListaVaciaException extends RuntimeException {
	
	public ListaVaciaException(){
		super("La lista est� vac�a");
	}
	public ListaVaciaException(String msg){
		super(msg);
	}

}

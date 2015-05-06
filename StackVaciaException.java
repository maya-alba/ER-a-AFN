package final_MC;
public class StackVaciaException extends RuntimeException {
	
	public StackVaciaException(String msj){
		super(msj);
	}
	public StackVaciaException(){
		super("La pila está vacía");
	}
	

}

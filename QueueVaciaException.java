package final_MC;
public class QueueVaciaException extends RuntimeException {
	
	public QueueVaciaException(String msj){
		super(msj);
	}
	public QueueVaciaException(){
		super("La queue está vacía");
	}

}

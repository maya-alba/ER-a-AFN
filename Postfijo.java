package final_MC;
import javax.swing.JOptionPane;


public class Postfijo{
	
	String aEvaluar;
	private StackLE<Double> evaluando;
	private QueueLE<Character> postfijo;
	private StackLE<Character> pila;
	private String expRegular = "";
	
	public Postfijo(String expresion){
		//imprime en pantalla solución
		this.aEvaluar=expresion;
		this.postfijo = new QueueLE<Character>();
		this.pila = new StackLE<Character>();
		this.evaluando = new StackLE<Double>();
		parsing(this.aEvaluar);		
	}
	
	public void parsing(String expresion){
		
		int size= this.aEvaluar.length();
		char chSiguiente;
		
		for(int i=0; i<size;i++){
			//this.postfijo.enqueue(this.aEvaluar.charAt(i));
			char ch=this.aEvaluar.charAt(i);
			
			//revisa si es  \n
			if(ch == '\\'){
				this.postfijo.enqueue(ch);
				this.postfijo.enqueue(this.aEvaluar.charAt(i+1));
				i++;
			}		
			
			//si es un * + o caracter de alfabeto meterlo directamente a cola
			if(ch != '(' && ch != ')' && ch !=',' && ch != '\\'){
				this.postfijo.enqueue(ch);
				
				//revisa que esté dentro del rango el siguiente char y que el actual sea algo del alfabeto
				if((i < size-1) && (ch != '*' || ch != '+')){
					chSiguiente=this.aEvaluar.charAt(i+1);
					
					//que el siguiente sea algo del alfabeto
					if(chSiguiente != ')' && chSiguiente != '*' && chSiguiente != '+' && chSiguiente != ','){
						//Checar si ya hay u & en la pila, tienen misma prioridad, sacar uno
						if(!this.pila.isEmpty() && this.pila.top() == '&'){
							this.postfijo.enqueue(this.pila.pop());
						}
						this.pila.push('&');
					}
				}
			}
			//si es un '(' a la pila
			if(ch=='('){
				this.pila.push(ch);
			}
			
			if(ch==','){
				//Si está vacía la pila o hay un '(' poner operador
				if(this.pila.isEmpty() || this.pila.top()=='('){
					this.pila.push(ch);
				}
				else{
					//si no está vacía, prioridades
					if(ch==',' && this.pila.top()==','){
						//System.out.println("lo que entra es de la misma prioridad");
						this.postfijo.enqueue(this.pila.pop());
						this.pila.push(ch);
					}
					else if(ch==',' && this.pila.top()=='&'){
						//System.out.println("lo que está en la pila tiene mayor prioridad, sacarlos");
						//se saca todo
						while(!this.pila.isEmpty() && this.pila.top()!='('){
							this.postfijo.enqueue(this.pila.pop());
						}
						this.pila.push(ch);
					}
				}
				
			}
			if(ch==')'){
				try{
					while(this.pila.top()!='('){
						this.postfijo.enqueue(this.pila.pop());
					}
					//se deshace del '(' que queda en la pila
					this.pila.pop();
					
					//revisa que esté dentro del rango el siguiente char
					if(i < size-1 ){
						chSiguiente=this.aEvaluar.charAt(i+1);
						if(chSiguiente != ')' && chSiguiente != '*' && chSiguiente != '+' && chSiguiente != ','){
							//Checar si ya hay u & en la pila, tienen misma prioridad, sacar uno
							if(!this.pila.isEmpty() && this.pila.top() == '&'){
								this.postfijo.enqueue(this.pila.pop());
							}
							this.pila.push('&');
						}
					}
				}
				catch(StackVaciaException e){
					throw new StackVaciaException("No se encontró un '(' de apertura");
				}		
			}
			
		}
		while(!this.pila.isEmpty() && this.pila.top()!='('){
			this.postfijo.enqueue(this.pila.pop());
		}
		if(!this.pila.isEmpty() && this.pila.top()=='('){
			throw new StackVaciaException("No se encontró un ')' de cierre");
		}
		
		System.out.println("Expresión en postfijo: "+this.postfijo);
		pasarPostfijo(this.postfijo);
	}
	
	public String pasarPostfijo( QueueLE<Character> cola){
		for(int i=0;i<cola.size();i++){
			expRegular+=cola.getDatos().elementoEn(i);
		}
		//System.out.println(expRegular);
		return expRegular;
		
	}
	
	public String getER(){
		return this.expRegular;
	}
	
	/*public static void main(String[] args) {
		
		String expresion = "";
		
		try{
			expresion = JOptionPane.showInputDialog("Escriba expresión a evaluar: ");
			System.out.println("Expresión a evaluar: "+expresion);
			Postfijo exp = new Postfijo(expresion);
		}
		catch(NullPointerException e){
			System.out.println("Se cerró el programa");
		}	
	}*/

}

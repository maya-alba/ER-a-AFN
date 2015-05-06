package final_MC;

import javax.swing.JOptionPane;

public class ERaAFN {
	
	public String expPostfijo;
	char[] alfabeto={'a','b','c','d','e'};
	String[][] matriz = new String[10][alfabeto.length];
	boolean[] aceptacion = new boolean[10];
	boolean[] borrado = new boolean[10];
	int[][] automatas = new int[10][10];
	int cuentaEstados;
	
	public ERaAFN(String exp){
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				matriz[i][j] = "%";
			}
		}
		for (int i = 0; i < automatas.length; i++) {
			for (int j = 0; j < automatas[i].length; j++) {
				automatas[i][j] = -1;
			}
		}
		this.expPostfijo = exp;
		this.armarInicial(this.expPostfijo);
		//this.imprimirMatriz();
	}
	
	public void armarInicial(String expresion){
		String exTmp = expresion;
		char caracter1 = ' ';
		char caracter2 = ' ';
		char operacion;
		String opAnterior = "";
		int i = 0;
		while((i < expresion.length()-1) && (expresion.charAt(i) != '*' && expresion.charAt(i) != '+' && expresion.charAt(i) != '&' && expresion.charAt(i) != ',')){
			i++;
		}
		if(expresion.charAt(i) == '*' || expresion.charAt(i) == '+'){
			caracter1 = expresion.charAt(i-1);
			operacion = expresion.charAt(i);
			opAnterior = opAstSum(caracter1, operacion);
			exTmp = expresion.substring(0, i-1) + "#" + expresion.substring(i+1, expresion.length());
			
		}
		else if(expresion.charAt(i) == '&' || expresion.charAt(i) == ','){
			caracter1 = expresion.charAt(i-2);
			caracter2 = expresion.charAt(i-1);
			operacion = expresion.charAt(i);
			opAnterior = opAndOr(caracter1, caracter2, operacion);
			exTmp = expresion.substring(0, i-2) + "#" + expresion.substring(i+1, expresion.length());
		}
		imprimirMatriz();
		armarAFN(exTmp);
	}
	
	
	public String opAstSum(char caracter1, char operacion){
		int posicion = 0;
		if(caracter1 == '.'){
			
			if(operacion == '*'){
				for (int i = 0; i < alfabeto.length; i++) {
					matriz[0][i] = "0";
				}
				
				aceptacion[0] = true;
				cuentaEstados++;
				automatas[0][0]=0;
			}
			else{ //operacion +
				for (int i = 0; i < alfabeto.length; i++) {
					matriz[0][i] = "1";
					matriz[1][i] = "1";
				}
				aceptacion[1] = true;
				cuentaEstados+=2;
				automatas[0][0]=0;
				automatas[0][1]=1;
			}
			
		}
		else{
			for (int i = 0; i < alfabeto.length; i++) {
				if(caracter1 == alfabeto[i]){
					posicion = i;
				}
			}
			if(operacion == '*'){
				matriz[0][posicion] = "0";
				aceptacion[0] = true;
				cuentaEstados++;
				automatas[0][0]=0;
			}
			else{ //operacion +
				matriz[0][posicion] = "1";
				matriz[1][posicion] = "1";
				aceptacion[1] = true;
				cuentaEstados+=2;
				automatas[0][0]=0;
				automatas[0][1]=1;
			}
		}
		
		
		return "#";
	}
	
	public String opAndOr(char caracter1, char caracter2, char operacion){
		int posicion1 = 0;
		int posicion2 = 0;
		
		if(caracter1 == '.' && caracter2 != '.'){
			for (int i = 0; i < alfabeto.length; i++) {
				if(caracter2 == alfabeto[i]){
					posicion2 = i;
				}
			}
			if(operacion == '&'){
				for (int i = 0; i < alfabeto.length; i++) {
					matriz[0][i]="1";
				}
				matriz[1][posicion2] = "2";
				aceptacion[2] = true;
				cuentaEstados+=3;
				automatas[0][0]=0;
				automatas[0][1]=1;
				automatas[0][2]=2;
			}
			else{ // operacion or ,
				for (int i = 0; i < alfabeto.length; i++) {
					matriz[0][i]="1";
				}
				matriz[0][posicion2]= matriz[0][posicion2].concat(",2");
				aceptacion[1] = true;
				aceptacion[2] = true;
				cuentaEstados+=3;
				automatas[0][0]=0;
				automatas[0][1]=1;
				automatas[0][2]=2;
			}
			
		}
		else if(caracter1 != '.' && caracter2 == '.'){
			for (int i = 0; i < alfabeto.length; i++) {
				if(caracter1 == alfabeto[i]){
					posicion1 = i;
				}
			}
			if(operacion == '&'){
				for (int i = 0; i < alfabeto.length; i++) {
					matriz[1][i] = "2";
				}
				matriz[0][posicion1]="1";
				aceptacion[2] = true;
				cuentaEstados+=3;
				automatas[0][0]=0;
				automatas[0][1]=1;
				automatas[0][2]=2;
			}
			else{ // operacion or ,
				for (int i = 0; i < alfabeto.length; i++) {
					matriz[0][i]="2";
				}
				matriz[0][posicion1]= matriz[0][posicion1].concat(",1");
				aceptacion[1] = true;
				aceptacion[2] = true;
				cuentaEstados+=3;
				automatas[0][0]=0;
				automatas[0][1]=1;
				automatas[0][2]=2;
			}
			
		}
		else if(caracter1 == '.' && caracter2 == '.'){
			
			if(operacion == '&'){
				for (int i = 0; i < alfabeto.length; i++) {
					matriz[0][i] = "1";
					matriz[1][i] = "2";
				}			
				aceptacion[2] = true;
				cuentaEstados+=3;
				automatas[0][0]=0;
				automatas[0][1]=1;
				automatas[0][2]=2;
			}
			else{ // operacion or ,
				for (int i = 0; i < alfabeto.length; i++) {
					matriz[0][i] = "1";
				}
				for (int i = 0; i < alfabeto.length; i++) {
					matriz[0][i] = matriz[0][i].concat(",2");
				}
				
				aceptacion[1] = true;
				aceptacion[2] = true;
				cuentaEstados+=3;
				automatas[0][0]=0;
				automatas[0][1]=1;
				automatas[0][2]=2;
			}
			
		}
		else{
			
			for (int i = 0; i < alfabeto.length; i++) {
				if(caracter1 == alfabeto[i]){
					posicion1 = i;
				}
				if(caracter2 == alfabeto[i]){
					posicion2 = i;
				}
			}
			if(operacion == '&'){
				matriz[0][posicion1] = "1";
				matriz[1][posicion2] = "2";
				aceptacion[2] = true;
				cuentaEstados+=3;
				automatas[0][0]=0;
				automatas[0][1]=1;
				automatas[0][2]=2;
			}
			else{ // operacion or ,
				matriz[0][posicion1] = "1";
				matriz[0][posicion2] = "2";
				aceptacion[1] = true;
				aceptacion[2] = true;
				cuentaEstados+=3;
				automatas[0][0]=0;
				automatas[0][1]=1;
				automatas[0][2]=2;
			}
		}
		return "#";
	}
	
	public void asteriscoDif(int gatos){
		for (int j = 1; j < aceptacion.length; j++) { //recorre cada estado para ver si es de aceptacion, y en su caso, borrarlo
			if(aceptacion[j]){
				aceptacion[j] = false;
				borrado[j] = true;
				int i = 0;
				boolean encontrado = false;
				while(automatas[gatos-1][i] != -1){
					if(j == automatas[gatos-1][i]){
						automatas[gatos-1][i] = automatas[gatos-1][i+1];
						encontrado = true;
					}
					if(encontrado){
						automatas[gatos-1][i] = automatas[gatos-1][i+1];
					}
					i++;
				}
				for (int j2 = 0; j2 < alfabeto.length; j2++) { //checa transicion del estado borrado para redireccionarlas al inicial
					if(matriz[j][j2] != "%"){
						if(matriz[0][j2] != "%"){
							matriz[0][j2] = matriz[0][j2].concat(",0");
						}
						else{
							matriz[0][j2] = "0";
						}
					}
				}
				for (int k = 0; k < matriz.length; k++) { //for k y for k2 recorren cada elemento de la matriz para checar sus transiciones
					for (int k2 = 0; k2 < matriz[k].length; k2++) {
						matriz[k][k2] = matriz[k][k2].replace(""+j, "0");
					}
				}
				for (int k = 0; k < alfabeto.length; k++) {
					matriz[j][k] = "%";
				}
			}
		}
		aceptacion[automatas[gatos-1][0]]=true;
	}
	
	public void asteriscoFac(char caracter1, int gatos){
		if(caracter1 == '.'){
			for (int i = 0; i < alfabeto.length; i++) {
				matriz[cuentaEstados][i] = cuentaEstados+"";
			}	
		}
		else{
			int posicion = 0;
			for (int i = 0; i < alfabeto.length; i++) {
				if(caracter1 == alfabeto[i]){
					posicion = i;
				}
			}			
			matriz[cuentaEstados][posicion] = cuentaEstados+"";
		}
		automatas[gatos][0] = cuentaEstados;
		aceptacion[cuentaEstados] = true;
		cuentaEstados++;
	}
	
	public void sumaDif(int gatos){
		int i = 0;
		for (int j = 1; j < aceptacion.length; j++) { //recorre cada estado para ver si es de aceptacion, y en su caso, borrarlo
			if(aceptacion[j]){
				aceptacion[j] = false;
				borrado[j] = true;
				boolean encontrado = false;
				while(automatas[gatos-1][i] != -1){
					if(j == automatas[gatos-1][i]){
						automatas[gatos-1][i] = automatas[gatos-1][i+1];
						encontrado = true;
					}
					if(encontrado){
						automatas[gatos-1][i] = automatas[gatos-1][i+1];
					}
					i++;
				}
				for (int j2 = 0; j2 < alfabeto.length; j2++) { //checa transicion del estado borrado para redireccionarlas al inicial
					if(matriz[j][j2] != "%"){
						if(matriz[cuentaEstados][j2] != "%"){
							matriz[cuentaEstados][j2] = matriz[cuentaEstados][j2].concat(","+cuentaEstados);
						}
						else{
							matriz[cuentaEstados][j2] = cuentaEstados+"";
						}
					}
				}
				for (int k = 0; k < matriz.length; k++) { //for k y for k2 recorren cada elemento de la matriz para checar sus transiciones
					for (int k2 = 0; k2 < matriz[k].length; k2++) {
						matriz[k][k2] = matriz[k][k2].replace(""+j, cuentaEstados+"");
					}
				}
			}
		}
		aceptacion[cuentaEstados]=true;
		for (int j = 0; j < alfabeto.length; j++) {
			//if(matriz[cuentaEstados][i] == "%"){
			matriz[cuentaEstados][j] = matriz[0][j];
			//}
		}
		automatas[gatos-1][i-1] = cuentaEstados;
		cuentaEstados++;
	}
	
	public void sumaFac(char caracter1, int gatos){
		if(caracter1 == '.'){
			for (int i = 0; i < alfabeto.length; i++) {
				matriz[cuentaEstados][i] = (cuentaEstados+1)+"";
				matriz[cuentaEstados+1][i] = (cuentaEstados+1)+"";
			}
		}
		else{
			int posicion = 0;
			for (int i = 0; i < alfabeto.length; i++) {
				if(caracter1 == alfabeto[i]){
					posicion = i;
				}
			}
			matriz[cuentaEstados][posicion] = (cuentaEstados+1)+"";
			matriz[cuentaEstados+1][posicion] = (cuentaEstados+1)+"";		
		}
		
		automatas[gatos][0]= cuentaEstados;
		automatas[gatos][1]= cuentaEstados+1;
		aceptacion[cuentaEstados+1] = true;
		cuentaEstados+=2;
	}
	
	public void andSuperDif(int gatos){
		int i=0;
		while(automatas[gatos-2][i] != -1){
			i++;
		}
		int j=0;
		while(automatas[gatos-1][j] != -1){
			j++;
		}
	
		for (int k = 0; k < i; k++) {
			if(aceptacion[automatas[gatos-2][k]]){
				aceptacion[automatas[gatos-2][k]] = false;
				for (int k2 = 0; k2 < alfabeto.length; k2++) {
					if(matriz[automatas[gatos-2][k]][k2] == "%"){
						matriz[automatas[gatos-2][k]][k2] = matriz[automatas[gatos-1][0]][k2];
					}
					else{
						if(matriz[automatas[gatos-1][k]][k2] != "%"){
							matriz[automatas[gatos-2][k]][k2] = matriz[automatas[gatos-2][k]][k2].concat(","+matriz[automatas[gatos-1][0]][k2]);
						}
					}
					matriz[automatas[gatos-1][0]][k2] = "%";
				}
				for (int j2 = 0; j2 < matriz.length; j2++) {
					for (int l = 0; l < matriz[j2].length; l++) {
						matriz[j2][l] =  matriz[j2][l].replace(automatas[gatos-1][0]+"", automatas[gatos-2][k]+"");
					}
				}
				if(j == 1){
					aceptacion[automatas[gatos-2][k]]=true;
				}
			}
		}
		
		aceptacion[automatas[gatos-1][0]] = false;
		for (int j2 = 0; j2 < j-1; j2++) {
			automatas[gatos-2][i+j2] = automatas[gatos-1][j2+1];
			automatas[gatos-1][j2+1] = -1;
		}
		automatas[gatos-1][0] = -1;
	}
	
	public void andDifDes(char caracter, int gatos){
		System.out.println(caracter);
		if(caracter == '.'){
			System.out.println("holis");
			for (int i = 0; i < aceptacion.length; i++) {
				if(aceptacion[i]){
					aceptacion[i] = false;
					for (int j = 0; j < alfabeto.length; j++) {
						if(matriz[i][j] == "%"){
							matriz[i][j] = cuentaEstados+"";
						}
						else{
							matriz[i][j] = matriz[i][j].concat(","+cuentaEstados);
						}
					}
				}
			}
		}
		else{
			int posicion = 0;
			for (int i = 0; i < alfabeto.length; i++) {
				if(caracter == alfabeto[i]){
					posicion = i;
				}
			}
			for (int i = 0; i < aceptacion.length; i++) {
				if(aceptacion[i]){
					aceptacion[i] = false;
					if(matriz[i][posicion] == "%"){
						matriz[i][posicion] = cuentaEstados+"";
					}
					else{
						matriz[i][posicion] = matriz[i][posicion].concat(","+cuentaEstados);
					}
				}
			}
		}
		int i=0;
		while(automatas[gatos-1][i] != -1){
			i++;
		}
		automatas[gatos-1][i] = cuentaEstados;
		aceptacion[cuentaEstados] = true;
		cuentaEstados++;
	}
	
	public void andDifAnt(char caracter, int gatos){
		if(caracter == '.'){
			for (int i = 0; i < alfabeto.length; i++) {
				matriz[cuentaEstados][i] = matriz[automatas[gatos-1][0]][i];
				matriz[automatas[gatos-1][0]][i] = "%";
			}
			for (int i = 0; i < matriz.length; i++) {
				for (int j = 0; j < matriz[i].length; j++) {
					matriz[i][j] = matriz[i][j].replace(automatas[gatos-1][0]+"", cuentaEstados+"");
				}
			}
			if(aceptacion[automatas[gatos-1][0]]){
				aceptacion[automatas[gatos-1][0]] = false;
				aceptacion[cuentaEstados] = true;
			}
			for (int i = 0; i < alfabeto.length; i++) {
				matriz[automatas[gatos-1][0]][i] = cuentaEstados+"";
			}
		}
		else{
			int posicion = 0;
			for (int i = 0; i < alfabeto.length; i++) {
				if(caracter == alfabeto[i]){
					posicion = i;
				}
			}
			for (int i = 0; i < alfabeto.length; i++) {
				matriz[cuentaEstados][i] = matriz[automatas[gatos-1][0]][i];
				matriz[automatas[gatos-1][0]][i] = "%";
			}
			for (int i = 0; i < matriz.length; i++) {
				for (int j = 0; j < matriz[i].length; j++) {
					matriz[i][j] = matriz[i][j].replace(automatas[gatos-1][0]+"", cuentaEstados+"");
				}
			}
			if(aceptacion[automatas[gatos-1][0]]){
				aceptacion[automatas[gatos-1][0]] = false;
				aceptacion[cuentaEstados] = true;
			}
			
			matriz[automatas[gatos-1][0]][posicion] = cuentaEstados+"";
		}
		
		int i = 0;
		while(automatas[gatos-1][i] != -1){
			i++;
		}
		for(int j = i-1; j > 0; j--){
			automatas[gatos-1][j+1] = automatas[gatos-1][j];
		}
		automatas[gatos-1][1] = cuentaEstados;
		cuentaEstados++;
	}
	
	public void andFac(char caracter1, char caracter2, int gatos){
		int posicion1 = 0;
		int posicion2 = 0;
		if(caracter1 == '.' && caracter2 != '.'){
			for (int i = 0; i < alfabeto.length; i++) {
				if(caracter2 == alfabeto[i]){
					posicion2 = i;
				}
			}
			for (int i = 0; i < alfabeto.length; i++) {
				matriz[cuentaEstados][i] = (cuentaEstados+1)+"";
			}
			matriz[cuentaEstados+1][posicion2] = (cuentaEstados+2)+"";
		}
		else if(caracter1 != '.' && caracter2 == '.'){
			for (int i = 0; i < alfabeto.length; i++) {
				if(caracter1 == alfabeto[i]){
					posicion1 = i;
				}
			}
			for (int i = 0; i < alfabeto.length; i++) {
				matriz[cuentaEstados+1][i] = (cuentaEstados+2)+"";
			}
			matriz[cuentaEstados][posicion1] = (cuentaEstados+1)+"";
			
		}
		else if(caracter1 == '.' && caracter2 == '.'){
			for (int i = 0; i < alfabeto.length; i++) {
				matriz[cuentaEstados][i] = (cuentaEstados+1)+"";
				matriz[cuentaEstados+1][i] = (cuentaEstados+2)+"";
			}
		}
		else{
			for (int i = 0; i < alfabeto.length; i++) {
				if(caracter1 == alfabeto[i]){
					posicion1 = i;
				}
				if(caracter2 == alfabeto[i]){
					posicion2 = i;
				}
			}
			matriz[cuentaEstados][posicion1] = (cuentaEstados+1)+"";
			matriz[cuentaEstados+1][posicion2] = (cuentaEstados+2)+"";
		}
		automatas[gatos][0]=cuentaEstados;
		automatas[gatos][1]=cuentaEstados+1;
		automatas[gatos][2]=cuentaEstados+2;
		aceptacion[cuentaEstados+2] = true;
		cuentaEstados+=3;
		
	}
	
	public void comaSuperDif(int gatos){
		int m = 0;
		while(automatas[gatos-2][m] != -1){
			m++;
		}
		int n=0;
		while(automatas[gatos-1][n] != -1){
			n++;
		}
		boolean bucle1 = false;
		boolean bucle2 = false;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if(matriz[i][j].contains(automatas[gatos-2][0]+"")){
					bucle1 = true;
				}
				if(matriz[i][j].contains(automatas[gatos-1][0]+"")){
					bucle2 = true;
				}
			}
		}
		if(bucle1 || bucle2){
			for (int i = 0; i < matriz.length; i++) {
				for (int j = 0; j < matriz[i].length; j++) {
					if(bucle1){
						for (int j2 = 0; j2 < alfabeto.length; j2++) {
							matriz[cuentaEstados][j2] = matriz[automatas[gatos-2][0]][j2];
						}
						matriz[i][j] = matriz[i][j].replace(automatas[gatos-2][0]+"", cuentaEstados+"");
						if(aceptacion[automatas[gatos-2][0]]){
							aceptacion[cuentaEstados] = true;
						}
					}
					if(bucle2){
						for (int j2 = 0; j2 < alfabeto.length; j2++) {
							matriz[cuentaEstados+1][j2] = matriz[automatas[gatos-1][0]][j2];
						}
						matriz[i][j] = matriz[i][j].replace(automatas[gatos-1][0]+"", (cuentaEstados+1)+"");
						if(aceptacion[automatas[gatos-1][0]]){
							aceptacion[cuentaEstados+1] = true;
						}
						
					}
				}
			}
		}
		if(aceptacion[automatas[gatos-1][0]]){
			aceptacion[automatas[gatos-1][0]] = false;
			aceptacion[automatas[gatos-2][0]] = true;
		}
		for (int i = 0; i < alfabeto.length; i++) {
			if(matriz[automatas[gatos-2][0]][i] == "%"){
				matriz[automatas[gatos-2][0]][i] = matriz[automatas[gatos-1][0]][i];
			}
			else{
				if(matriz[automatas[gatos-1][0]][i] != "%"){
					matriz[automatas[gatos-2][0]][i] = matriz[automatas[gatos-2][0]][i].concat(","+matriz[automatas[gatos-1][0]][i]);
				}
			}
			matriz[automatas[gatos-1][0]][i] = "%";
		}
		for (int j2 = 0; j2 < n-1; j2++) {
			automatas[gatos-2][m+j2] = automatas[gatos-1][j2+1];
			automatas[gatos-1][j2+1] = -1;
		}
		automatas[gatos-1][0] = -1;
		cuentaEstados+=2;
	}
	
	public void comaDif(char caracter, int gatos){
		int posicion = 0;
		for (int i = 0; i < alfabeto.length; i++) {
			if(caracter == alfabeto[i]){
				posicion = i;
			}
		}
		int i = 0;
		while(automatas[gatos-1][i] != -1){
			i++;
		}
		if(matriz[automatas[gatos-1][0]][posicion] == "%"){
			matriz[automatas[gatos-1][0]][posicion] = cuentaEstados+"";
		}
		else{
			matriz[automatas[gatos-1][0]][posicion] = matriz[automatas[gatos-1][0]][posicion].concat(","+cuentaEstados);
		}
		aceptacion[cuentaEstados] = true;
		automatas[gatos-1][i] = cuentaEstados;
		cuentaEstados++;
	}
	
	public void comaFac(char caracter1, char caracter2, int gatos){
		int posicion1 = 0;
		int posicion2 = 0;
		for (int i = 0; i < alfabeto.length; i++) {
			if(caracter1 == alfabeto[i]){
				posicion1 = i;
			}
			if(caracter2 == alfabeto[i]){
				posicion2 = i;
			}
		}
		automatas[gatos][0]=cuentaEstados;
		automatas[gatos][1]=cuentaEstados+1;
		automatas[gatos][2]=cuentaEstados+2;
		matriz[cuentaEstados][posicion1] = (cuentaEstados+1)+"";
		matriz[cuentaEstados][posicion2] = (cuentaEstados+2)+"";
		aceptacion[cuentaEstados+1] = true;
		aceptacion[cuentaEstados+2] = true;
		cuentaEstados+=3;
	}
	
	public void armarAFN(String expresion){
		int i = 0;
		while((i < expresion.length()-1) && (expresion.charAt(i) != '*' && expresion.charAt(i) != '+' && expresion.charAt(i) != '&' && expresion.charAt(i) != ',')){
			i++;
		}
		int gatos = 0;
		for (int j = 0; j < i; j++) {
			if(expresion.charAt(j) == '#'){
				gatos++;
			}
		}
		System.out.println(expresion);
		if(expresion.charAt(i) == '*'){
			if(expresion.charAt(i-1) == '#'){
				asteriscoDif(gatos);
				expresion = expresion.substring(0, i) + expresion.substring(i+1, expresion.length());
			}
			else{
				asteriscoFac(expresion.charAt(i-1), gatos);
				expresion = expresion.substring(0, i-1) + "#" + expresion.substring(i+1, expresion.length());
			}
		}
		else if(expresion.charAt(i) == '+'){
			if(expresion.charAt(i-1) == '#'){
				sumaDif(gatos);
				expresion = expresion.substring(0, i) + expresion.substring(i+1, expresion.length());
			}
			else{
				sumaFac(expresion.charAt(i-1), gatos);
				expresion = expresion.substring(0, i-1) + "#" + expresion.substring(i+1, expresion.length());
			}
		}
		else if(expresion.charAt(i) == '&'){
			if(expresion.charAt(i-2) == '#' && expresion.charAt(i-1) == '#'){
				andSuperDif(gatos);
				expresion = expresion.substring(0, i-2) + "#" + expresion.substring(i+1, expresion.length());
			}
			else if(expresion.charAt(i-2) != '#' && expresion.charAt(i-1) == '#'){
				andDifAnt(expresion.charAt(i-2), gatos);
				expresion = expresion.substring(0, i-2) + "#" + expresion.substring(i+1, expresion.length());
			}
			else if(expresion.charAt(i-2) == '#' && expresion.charAt(i-1) != '#'){
				andDifDes(expresion.charAt(i-1), gatos);
				expresion = expresion.substring(0, i-2) + "#" + expresion.substring(i+1, expresion.length());
			}
			else{
				andFac(expresion.charAt(i-2), expresion.charAt(i-1), gatos);
				expresion = expresion.substring(0, i-2) + "#" + expresion.substring(i+1, expresion.length());
			}
		}
		else if(expresion.charAt(i) == ','){
			if(expresion.charAt(i-2) == '#' && expresion.charAt(i-1) == '#'){
				comaSuperDif(gatos);
				expresion = expresion.substring(0, i-2) + "#" + expresion.substring(i+1, expresion.length());
			}
			else if((expresion.charAt(i-2) != '#' && expresion.charAt(i-1) == '#') || (expresion.charAt(i-2) == '#' && expresion.charAt(i-1) != '#')){
				if(expresion.charAt(i-2) != '#'){
					comaDif(expresion.charAt(i-2), gatos);
				}
				if(expresion.charAt(i-1) != '#'){
					comaDif(expresion.charAt(i-1), gatos);
				}
				expresion = expresion.substring(0, i-2) + "#" + expresion.substring(i+1, expresion.length());
			}
			else{
				comaFac(expresion.charAt(i-2), expresion.charAt(i-1), gatos);
				expresion = expresion.substring(0, i-2) + "#" + expresion.substring(i+1, expresion.length());
			}
		}
		imprimirMatriz();
		if(!expresion.equals("#")){
			armarAFN(expresion);
		}
	}
	
	public void imprimirMatriz(){
		System.out.print("   ");
		for (int i = 0; i < alfabeto.length; i++) {
			System.out.print(alfabeto[i]+" ");
		}
		System.out.println();
		for (int i = 0; i < matriz.length; i++) {
			if(aceptacion[i]){
				System.out.print("*");
			}
			else{
				System.out.print(" ");
			}
			System.out.print(i+" ");
			for (int j = 0; j < matriz[i].length; j++) {
				System.out.print(matriz[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("Automata 0:");
		for (int i = 0; i < automatas.length; i++) {
			System.out.print(automatas[0][i]+" ");
		}
		System.out.println("Automata 1");
		for (int i = 0; i < automatas.length; i++) {
			System.out.print(automatas[1][i]+" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		String expresion;
		Postfijo exp = null;
		try{
			String ER = JOptionPane.showInputDialog("Escriba expresión a evaluar: ");
			exp = new Postfijo(ER);
		}
		catch(NullPointerException e){
			System.out.println("Se cerró el programa");
		}	
		expresion = exp.getER();
		System.out.println("Expresión en post: "+expresion);
		
		ERaAFN abc = new ERaAFN(expresion);
		
	}

}


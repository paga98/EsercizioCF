public class MainCL {

	public static void main(String[] args) {
		ReaderAndWriter rAndw = new ReaderAndWriter();
		//Generazione dei codici fiscali di tutte le persone
		rAndw.genFiscalCodes();
		//Controllo dei codici fiscali nel file codiciFiscali.xml e confronto con quelli delle persone
		rAndw.checkFiscalCodes();
		//Scrittura del documento finale
		rAndw.writeOutput();
	}

}

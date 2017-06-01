
/**@author Guilherme Wenger
 * @author Flavio Prado
 * @author Guilherme Maeda
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ep02 {

	/**
	 * @param args the command line arguments
	 */
	static String[][] matriz;

	public static void main(String[] args) {

		String linhaArquivo;
		String caminhoArquivoEntrada = args[0];
		File arquivoEntrada = new File(caminhoArquivoEntrada);

		try {

			BufferedReader br = new BufferedReader(new FileReader(arquivoEntrada));
			linhaArquivo = br.readLine();
			linhaArquivo = linhaArquivo.trim();
			linhaArquivo = linhaArquivo.replace("-", ":");
			linhaArquivo = linhaArquivo.replace(";", ":");

			String[] l = linhaArquivo.split(":");
			//criar uma lista encadeada com o dados do arquivo entrada.txt
			Aresta ar = new Aresta();
			ar.inicio = l[0];
			ar.fim = l[1];
			ar.peso = Integer.parseInt(l[2]);

			Aresta aux = new Aresta();

			int linhas = 1;

			for (int i = 3; i < l.length;) {
				if (ar.prox == null) {
					Aresta a = new Aresta();
					a.inicio = l[0];
					a.fim = l[i];
					a.peso = Integer.parseInt(l[i + 1]);
					ar.prox = a;
					aux = ar.prox;
					i += 2;
				} else {
					Aresta a = new Aresta();
					a.inicio = l[0];
					a.fim = l[i];
					a.peso = Integer.parseInt(l[i + 1]);
					aux.prox = a;
					aux = aux.prox;
					i += 2;
				}
			}

			while (br.ready()) {

				linhaArquivo = br.readLine();
				linhaArquivo = linhaArquivo.trim();
				linhaArquivo = linhaArquivo.replace("-", ":");
				linhaArquivo = linhaArquivo.replace(";", ":");

				l = linhaArquivo.split(":");

				for (int i = 1; i < l.length;) {
					Aresta a = new Aresta();
					a.inicio = l[0];
					a.fim = l[i];
					a.peso = Integer.parseInt(l[i + 1]);
					aux.prox = a;
					aux = aux.prox;
					i += 2;

				}
				linhas++;//contador de linhas do arquivo entrada.txt
			}
			//fim criar lista encadeada
			//inicio criar uma matriz com os salvos na lista aresta
			int cont = 0;
			Aresta a = ar;//variavel auxiliar usada para percorrer a lista
			//contar elementos da lista encadeada
			while (a != null) {
				a = a.prox;
				cont++;
			}
			int colunas = Math.round(cont / linhas); //quantidade de nós da lista encadeada / pela quantidade de linhas lidas no arquivo entrada.txt
			Aresta[][] matriz = new Aresta[linhas][colunas];

			aux = ar;
			//popular a matriz		
			for (int i = 0; i < linhas; i++) {
				for (int j = 0; j < colunas; j++) {
					matriz[i][j] = aux;
					aux = aux.prox;
				}
			}
			//fim criar matriz
			String resultado = kruskal(matriz, linhas, colunas);
			gravar(resultado);
			
			br.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Ep02.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Ep02.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
	//algoritmo que busca a AEM
	public static String kruskal(Aresta[][] m, int l, int c) {
		String aem = "";
		for (int i = 0; i < l; i++) {
			for (int j = 0; j < c - 1; j++) {

				if ((m[i][j].peso > m[i][j + 1].peso)) {//verifica quem tem o peso menor
					if (!aem.contains(m[i][j + 1].fim)) {//verifica se o nó destino da aresta já está na aem, evita fechar ciclos
						aem += m[i][j + 1].inicio + m[i][j + 1].fim + ", ";
					} else if (!aem.contains(m[i][j].fim)) {
						aem += m[i][j].inicio + m[i][j].fim + ", ";
					}
				} else {
					if (!aem.contains(m[i][j].fim)) {
						aem += m[i][j].inicio + m[i][j].fim + ", ";
					} else if (!aem.contains(m[i][j + 1].fim)) {
						aem += m[i][j + 1].inicio + m[i][j + 1].fim + ", ";
					}
				}
			}
		}

		return aem;
	}
	// metodo que grava o resultado no arquivo saida txt
	public static void gravar(String resultado) {

		try {
			File caminhoArquivoSaida = new File("Saida.txt");
			FileWriter arquivoSaida = new FileWriter(caminhoArquivoSaida);
			PrintWriter printer = new PrintWriter(arquivoSaida);
			
			String aem = resultado.substring (0, resultado.length() - 2);
			printer.println(aem);
			
			printer.close();
			arquivoSaida.close();

		} catch (FileNotFoundException ex) {
			Logger.getLogger(Ep02.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Ep02.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Walsh {
	static class Task {
		public final String input_file = "walsh.in";
		public final String output_file = "walsh.out";
		int dimensiune_matrice, nr_perechi;
		int[] indice_linie, indice_coloana;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(input_file));
				dimensiune_matrice = sc.nextInt();
				nr_perechi = sc.nextInt();
				indice_linie = new int[nr_perechi];
				indice_coloana = new int[nr_perechi];
				for (int i = 0; i < nr_perechi; i++) {
					indice_linie[i] = sc.nextInt();
					indice_coloana[i] = sc.nextInt();
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int parse_matrix(int dimensiune, int linie, int coloana, int neg_count) {
			if (linie <= dimensiune / 2 && coloana <= dimensiune / 2) {
				/* coltul stanga sus */
				if (dimensiune > 2) {
					return parse_matrix(dimensiune / 2, linie, coloana, neg_count);
				} else if (neg_count % 2 != 0) {
					return 1;
				} else {
					return 0;
				}
			} else if (linie > dimensiune / 2 && coloana <= dimensiune / 2) {
				/* coltul stanga jos */
				if (dimensiune > 2) {
					int line = (linie - dimensiune / 2);
					return parse_matrix(dimensiune / 2, line, coloana, neg_count);
				} else if (neg_count % 2 != 0) {
					return 1;
				} else {
					return 0;
				}
			} else if (linie <= dimensiune / 2 && coloana > dimensiune / 2) {
				/* coltul dreapta sus */
				if (dimensiune > 2) {
					int column = (coloana - dimensiune / 2);
					return parse_matrix(dimensiune / 2, linie, column, neg_count);
				} else if (neg_count % 2 != 0) {
					return 1;
				} else {
					return 0;
				}
			} else {
				/* coltul dreapta jos */
				if (dimensiune > 2) {
					neg_count++;
					int line = (linie - dimensiune / 2), column = (coloana - dimensiune / 2);
					return parse_matrix(dimensiune / 2, line, column, neg_count);
				} else if (neg_count % 2 != 0) {
					return 0;
				}
				return 1;
			}
		}

		private void writeOutput(int[] count) {
			try {
				PrintWriter pw = new PrintWriter(new File(output_file));
				for (int i = 0; i < count.length; i++) {
					pw.println(count[i]);
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}
	public static void main(String[] args) {
		Task task = new Task();
		task.readInput();
		int[] v = new int[task.nr_perechi];

		for (int i = 0; i < task.nr_perechi; i++) {
			int dim = task.dimensiune_matrice;
			v[i] = task.parse_matrix(dim, task.indice_linie[i], task.indice_coloana[i], 0);
		}
		task.writeOutput(v);
	}
}

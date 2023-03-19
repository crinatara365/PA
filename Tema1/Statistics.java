import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Statistics {
	static class Task {
		public final String input_file = "statistics.in";
		public final String output_file = "statistics.out";
		int nr_cuvinte;
		String[] cuvinte;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(input_file));
				nr_cuvinte = sc.nextInt();
				sc.nextLine();
				cuvinte = new String[nr_cuvinte];
				for (int i = 0; i < nr_cuvinte; i++) {
					cuvinte[i] = sc.nextLine();
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int solve() {
			ArrayList<Integer> letters = new ArrayList<Integer>();
			ArrayList<Integer> total = new ArrayList<Integer>();

			/* 	parcurgem fiecare litera din alfabet */
			for (char i = 'a'; i <= 'z'; i++) {
				ArrayList<Integer> score_each_letter = new ArrayList<Integer>();

				/* 	pentru fiecare litera, parcurgem fiecare cuvant din lista */
				for (int j = 0; j < nr_cuvinte; j++) {
					/* se calculeaza "scorul" literei pentru fiecare cuvant */
					score_each_letter.add(times(i, cuvinte[j])
							- (cuvinte[j].length() - times(i, cuvinte[j])));
				}

				/* 	avem vectorul de scoruri sortat descrescator */
				Collections.sort(score_each_letter, Collections.reverseOrder());
				int aux = score_each_letter.get(0), contor = 1, sum = 0;
				while (aux > 0 && contor < score_each_letter.size()) {
					aux = aux + score_each_letter.get(contor);
					contor++;
					sum++;
				}

				/* 	daca se termina vectorul si aux este inca pozitiv,
					sum nu a apucat sa se incrementeze ultima data */
				if (aux > 0) {
					sum++;
				}

				/* 	in vectorul total o sa avem cate cuvinte
					putem concatena ca suma sa ramana pozitiva */
				total.add(sum);
			}

			int dominant = Collections.max(total);
			return dominant;
		}

		private int times(char a, String cuvant) {
			int contor = 0;
			for (int i = 0; i < cuvant.length(); i++) {
				if (cuvant.toCharArray()[i] == a) {
					contor++;
				}
			}
			return contor;
		}

		private void writeOutput(int count) {
			try {
				PrintWriter pw = new PrintWriter(new File(output_file));
				pw.println(count);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void main(String[] args) {
		Task task = new Task();
		task.readInput();
		task.writeOutput(task.solve());
	}
}
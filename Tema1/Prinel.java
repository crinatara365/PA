import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Prinel {
	static class Task {
		public final String input_file = "prinel.in";
		public final String output_file = "prinel.out";
		int n, k;
		ArrayList<Integer> target = new ArrayList<Integer>();
		int[] p;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(input_file));
				n = sc.nextInt();
				k = sc.nextInt();
				for (int i = 0; i < n; i++) {
					int aux = sc.nextInt();
					target.add(aux);
				}
				p = new int[n];
				for (int i = 0; i < n; i++) {
					int aux = sc.nextInt();
					p[i] = aux;
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int solve() {
			/* 	obtinem maximul din target pentru a sti exact
				pana la ce valoare calculam operatiile */
			int max = Collections.max(target);
			int[] all_values = new int[max + 1];

			/* 	initializam valorile din vector cu numarul maxim
				care poate aparea in input + 1 */
			for (int i = 1; i <= max; i++) {
				all_values[i] = 100001;
			}
			all_values[1] = 0;
			for (int i = 1; i <= max; ++i) {
				/*	calculam de la 1 pana la max numarul de operatii */
				ArrayList<Integer> divisors = get_divisors(i);
				for (int j = 0; j < divisors.size(); ++j) {
					if (i + divisors.get(j) < max + 1) {
						all_values[i + divisors.get(j)] = Math.min(all_values[i
								+ divisors.get(j)], all_values[i] + 1);
					}
				}
			}

			/*	alegem din all_values doar elementele care apar in target */
			int[] weight = new int[target.size()];
			for (int i = 0; i < weight.length;i++) {
				weight[i] = all_values[target.get(i)];
			}

			/*	apelam problema rucsacului */
			int result = rucsac(k, weight, p, n);
			return result;
		}

		static int rucsac(int W, int[] wt, int[] val, int n) {
			int []dp = new int[W + 1];

			for (int i = 1; i < n + 1; i++) {
				for (int w = W; w >= 0; w--) {

					if (wt[i - 1] <= w) {
						dp[w] = Math.max(dp[w],
								dp[w - wt[i - 1]] + val[i - 1]);
					}
				}
			}
			return dp[W];
		}

		private ArrayList<Integer> get_divisors(int n) {
			ArrayList<Integer> v = new ArrayList<Integer>();
			int i = 1;
			while (i * i <= n) {
				if (n % i == 0) {
					v.add(i);
					if (n / i != i) {
						v.add(n / i);
					}
				}
				i++;
			}
			return v;
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
		int result = task.solve();
		task.writeOutput(result);
	}
}
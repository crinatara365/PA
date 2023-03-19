import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Curse {
	static class Task {
		public final String input_file = "curse.in";
		public final String output_file = "curse.out";
		int N, M, A;
		int[][] matrix;

		private void readInput() {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(input_file));
				String contentLine = br.readLine();
				int contor = 0, contor2 = 0;
				while (contentLine != null) {
					if (contor == 0) {
						String[] s = contentLine.split("\\s");
						N = Integer.parseInt(s[0]);
						M = Integer.parseInt(s[1]);
						A = Integer.parseInt(s[2]);
						matrix = new int[A][N];
						contor++;
					} else if (contor == 1) {
						String[] str = contentLine.split("\\s");
						for (int i = 0; i < N; i++) {
							matrix[contor2][i] = Integer.parseInt(str[i]);
						}
						contor2++;
					}
					contentLine = br.readLine();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				try {
					if (br != null) {
						br.close();
					}
				} catch (IOException ioe) {
					System.out.println("Error in closing the BufferedReader");
				}
			}
		}

		private ArrayList<Integer> solve() {
			/* se creeaza o lista de adiacenta pentru fiecare nod */
			ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(M);
			for (int i = 0; i < M; i++) {
				ArrayList<Integer> aux = new ArrayList<Integer>();
				adj.add(aux);
			}

			/* se stabilesc legaturile intre noduri */
			for (int i = 0; i < A; i++) {
				if (i + 1 < A) {
					int j = 0;
					while (j < N) {
						while (matrix[i][j] == matrix[i + 1][j]) {
							j++;
						}
						if (adj.get(matrix[i][j] - 1).contains(matrix[i + 1][j]) == false) {
							adj.get(matrix[i][j] - 1).add(matrix[i + 1][j]);
						}
						break;
					}
				}
			}

			/* sortare topologica */
			ArrayList<Integer> topsort = new ArrayList<>();
			boolean[] used = new boolean[M];

			// pentru fiecare nod
			for (int node = 0; node < M; node++) {
				if (!used[node]) {
					dfs(node, used, topsort, adj);
				}
			}

			Collections.reverse(topsort);
			return topsort;
		}

		void dfs(int node, boolean[] used, ArrayList<Integer> topsort,
					ArrayList<ArrayList<Integer>> adj) {
			used[node] = true;

			for (Integer neigh : adj.get(node)) {
				if (!used[neigh - 1]) {
					dfs(neigh - 1, used, topsort, adj);
				}
			}
			topsort.add(node);
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				PrintWriter pw = new PrintWriter(new File(output_file));
				for (int i = 0; i < result.size(); i++) {
					int node = result.get(i) + 1;
					pw.print(node + " ");
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
		ArrayList<Integer> result = task.solve();
		task.writeOutput(result);
	}
}




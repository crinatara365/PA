import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Beamdrone {
	static class Task {
		public final String input_file = "beamdrone.in";
		public final String output_file = "beamdrone.out";
		int N, M;
		int xi, yi, xf, yf;
		char[][] graph;

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
						graph = new char[N][M];
						contor++;
					} else if (contor == 1) {
						String[] str = contentLine.split("\\s");

						xi = Integer.parseInt(str[0]);
						yi = Integer.parseInt(str[1]);
						xf = Integer.parseInt(str[2]);
						yf = Integer.parseInt(str[3]);
						contor++;
					} else {
						char[] aux = contentLine.toCharArray();
						for (int i = 0 ; i < M; i++) {
							graph[contor2][i] = aux[i];
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

		private int solve() {
			MyNode source = new MyNode(xi, yi, "start", 0);
			/* initializam coada de prioritati */
			PriorityQueue<MyNode> pQueue = new PriorityQueue<MyNode>(N * M, new NodeComparator());

			int[][] d = new int[N][M];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					d[i][j] = 100000;
				}
			}

			/* adaugam nodul sursa in coada */
			pQueue.add(source);

			/* incepem relaxarea muchiilor */
			while (!pQueue.isEmpty()) {
				/* scoatem nodul cu costul cel mai mic */
				MyNode node = pQueue.poll();

				if (node.x == xf && node.y == yf) {
					return node.d;
				}

				/* ne deplasam la dreapta */
				if (node.y + 1 < M) {
					if (graph[node.x][node.y + 1] != 'W') {
						MyNode right_neigh = new MyNode(node.x, node.y + 1, "right",
											d[node.x][node.y + 1]);

						if (node.direction == "start" || node.direction == right_neigh.direction) {
							if (right_neigh.d == 100000) {
								d[right_neigh.x][right_neigh.y] = node.d + 0;
								right_neigh.d = node.d + 0;
								pQueue.add(right_neigh);
							} else if (node.d + 0 <= right_neigh.d) {
								d[right_neigh.x][right_neigh.y] = node.d + 0;
								right_neigh.d = node.d + 0;
								pQueue.add(right_neigh);
							}
						} else {
							if (right_neigh.d == 100000) {
								d[right_neigh.x][right_neigh.y] = node.d + 1;
								right_neigh.d = node.d + 1;
								pQueue.add(right_neigh);
							} else if (node.d + 1 <= right_neigh.d) {
								d[right_neigh.x][right_neigh.y] = node.d + 1;
								right_neigh.d = node.d + 1;
								pQueue.add(right_neigh);
							}
						}
					}
				}

				/* ne deplasam la stanga */
				if (node.y - 1 >= 0) {
					if (graph[node.x][node.y - 1] != 'W') {
						MyNode left_neigh = new MyNode(node.x, node.y - 1, "left",
											d[node.x][node.y - 1]);
						if (node.direction == "start" || node.direction == left_neigh.direction) {
							if (left_neigh.d == 100000) {
								d[node.x][node.y - 1] = node.d + 0;
								left_neigh.d = node.d + 0;
								pQueue.add(left_neigh);
							} else if (node.d + 0 <= left_neigh.d) {
								d[node.x][node.y - 1] = node.d + 0;
								left_neigh.d = node.d + 0;
								pQueue.add(left_neigh);
							}
						} else {
							if (left_neigh.d == 100000) {
								d[node.x][node.y - 1] = node.d + 1;
								left_neigh.d = node.d + 1;
								pQueue.add(left_neigh);
							} else if (node.d + 1 <= left_neigh.d) {
								d[node.x][node.y - 1] = node.d + 1;
								left_neigh.d = node.d + 1;
								pQueue.add(left_neigh);
							}
						}
					}
				}

				/* ne deplasam in sus */
				if (node.x - 1 >= 0) {
					if (graph[node.x - 1][node.y] != 'W') {
						MyNode up_neigh = new MyNode(node.x - 1, node.y, "up",
										d[node.x - 1][node.y]);
						if (node.direction == "start" || node.direction == up_neigh.direction) {
							if (up_neigh.d == 100000) {
								d[node.x - 1][node.y] = node.d + 0;
								up_neigh.d = node.d + 0;
								pQueue.add(up_neigh);
							} else if (node.d + 0 <= up_neigh.d) {
								d[node.x - 1][node.y] = node.d + 0;
								up_neigh.d = node.d + 0;
								pQueue.add(up_neigh);
							}
						} else {
							if (up_neigh.d == 100000) {
								d[node.x - 1][node.y] = node.d + 1;
								up_neigh.d = node.d + 1;
								pQueue.add(up_neigh);
							} else if (node.d + 1 <= up_neigh.d) {
								d[node.x - 1][node.y] = node.d + 1;
								up_neigh.d = node.d + 1;
								pQueue.add(up_neigh);
							}
						}
					}
				}

				/* ne deplasam in jos */
				if (node.x + 1 < N) {
					if (graph[node.x + 1][node.y] != 'W') {
						MyNode down_neigh = new MyNode(node.x + 1, node.y, "down",
											d[node.x + 1][node.y]);
						if (node.direction == "start" || node.direction == down_neigh.direction) {
							if (down_neigh.d == 100000) {
								d[node.x + 1][node.y] = node.d + 0;
								down_neigh.d = node.d + 0;
								pQueue.add(down_neigh);
							} else if (node.d + 0 <= down_neigh.d) {
								d[node.x + 1][node.y] = node.d + 0;
								down_neigh.d = node.d + 0;
								pQueue.add(down_neigh);
							}
						} else {
							if (down_neigh.d == 100000) {
								d[node.x + 1][node.y] = node.d + 1;
								down_neigh.d = node.d + 1;
								pQueue.add(down_neigh);
							} else if (node.d + 1 <= down_neigh.d) {
								d[node.x + 1][node.y] = node.d + 1;
								down_neigh.d = node.d + 1;
								pQueue.add(down_neigh);
							}
						}
					}
				}

			}
			return -1;
		}


		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new File(output_file));
				pw.println(result);
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

class MyNode {
	int x, y, d;
	String direction;
	public MyNode(int x, int y, String direction, int d) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.d = d;
	}
}

class NodeComparator implements Comparator<MyNode> {
	public int compare(MyNode n1, MyNode n2) {
		if (n1.d < n2.d) {
			return -1;
		} else if (n1.d > n2.d) {
			return 1;
		}
		return 0;
	}
}

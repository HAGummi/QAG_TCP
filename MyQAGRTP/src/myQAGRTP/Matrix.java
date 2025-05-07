/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myQAGRTP;

import java.util.Random;

/**
 *
 * @author MAKMAL 15
 */
public class Matrix {
	static Random rn = new Random();

	public static void main(String[] args) {
		int n = 5;
		int[][] m = createMatrix(n);
		displayMatric(m);

	}

	private static int[][] createMatrix(int n) {
		int[][] matrix = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int num = rn.nextInt(7) % 2;
				matrix[i][j] = num;
			}
		}
		return matrix;
	}

	private static void displayMatric(int[][] m) {
		int[] rowSum = new int[m.length];
		int[] colSum = new int[m.length];
//        int i, j;
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				rowSum[i] += m[i][j];
				colSum[j] += m[i][j];
				System.out.print(m[i][j] + " ");
			}
			System.out.println(": " + rowSum[i]);
//            System.out.println();
		}
		System.out.println("---------");
		for (int i = 0; i < m.length; i++) {
			System.out.print(colSum[i] + " ");
		}
		System.out.println();

	}

}

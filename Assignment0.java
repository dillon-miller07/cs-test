import java.util.Scanner;

public class Assignment0 {

	// MAIN METHOD
	/**
	 * The main driver method of the program.
	 * Performs input, processing, and output for:
	 * - Finding local maxima in a 1D integer array
	 * - Compressing and decompressing runs of repeated values
	 * - Reading two matrices and multiplying them
	 * 
	 * @author Dillon Miller
	 * @version 1.0
	 * @param args Command line arguments (unused)
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		// Get user input for single dimensional array
		int[] arr = getSingleDimensionalArray(scanner); 
		 
		// Find local maxima and print results
		int[] localMaximums = localMaxima(arr); 
		System.out.println("Local Maximums:");
		printArray(localMaximums);	
		if (localMaximums.length == 0) {
			System.out.println("There is no local maxima because there are not enough elements.");
		}
		System.out.println();
			 
		// Compress runs in the array and print
		int[][] compressedArr = compressRuns(arr);
		System.out.println("Compressed array (value, count):");
		for (int i = 0; i < compressedArr.length; i++) {
		    System.out.println("{" + compressedArr[i][0] + ", " + compressedArr[i][1] + "}");
		}
		System.out.println();
		    
		// Decompress the compressed array and print
		int[] decompressedArr = decompressRuns(compressedArr);
		System.out.println("Decompressed array:");
		printArray(decompressedArr);
		 
		System.out.println();
		
		// Read two matrices from user input
		double[][] matrixA = readMatrix(scanner, "Matrix A");
		double[][] matrixB = readMatrix(scanner, "Matrix B");
		
		// Multiply matrices and print results
		double[][] matrixC = matrixMultiply(matrixA, matrixB);
		System.out.println();
		System.out.println("Matrix A:");
		printMatrix(matrixA);
		System.out.println();
		System.out.println("Matrix B:");
		printMatrix(matrixB);
		System.out.println();
		System.out.println("Multiplying Matrices...");
		System.out.println("Matrix C:");
		printMatrix(matrixC);
	}
	
	// TASKS
	
	/**
	 * Finds all local maxima in the input array.
	 * A local maximum is an element strictly greater than its immediate neighbors.
	 * 
	 * @param arr Input array of integers
	 * @return Array containing all local maxima found
	 */
	public static int[] localMaxima(int[] arr) {
		if (arr.length < 2) {
			return new int[0];  // No local maxima possible if less than 2 elements
		}
		int count = 0;
	  
		// Check first element
		if (arr[0] > arr[1]) {
			count++;
		}

		// Check middle elements
		for (int i = 1; i < arr.length - 1; i++) {
			if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
				count++;
			}
		}

		// Check last element
		if (arr[arr.length - 1] > arr[arr.length - 2]) {
			count++;
		}

		int[] localMaximums = new int[count];
		int index = 0;

		// Store first local maximum if exists
		if (arr[0] > arr[1]) {
			localMaximums[index++] = arr[0];
		}

		// Store middle local maxima
		for (int i = 1; i < arr.length - 1; i++) {
			if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
				localMaximums[index++] = arr[i];
			}
		}

		// Store last local maximum if exists
		if (arr[arr.length - 1] > arr[arr.length - 2]) {
			localMaximums[index++] = arr[arr.length - 1];
		}

		return localMaximums;
	}
		 
	/**
	 * Compresses runs of identical values in a sorted array using run-length encoding.
	 * 
	 * @param arr Input array of integers (will be sorted within method)
	 * @return 2D array where each row is {value, count_of_consecutive_occurrences}
	 */
	public static int[][] compressRuns(int[] arr) {
		arr = sortArray(arr); // Sort array before compression
		int uniqueNumbers = 1;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] != arr[i - 1]) {
				uniqueNumbers++;
			}
		}
		    	
		int[][] compressedArr = new int[uniqueNumbers][2];
		int runIndex = 0;
		int currentValue = arr[0];
		int count = 1;
		    	
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] == currentValue) {
				count++;
			} else {
				compressedArr[runIndex][0] = currentValue;
				compressedArr[runIndex][1] = count;
				runIndex++;
				currentValue = arr[i];
				count = 1;
			}
		}
		// Store last run
		compressedArr[runIndex][0] = currentValue;
		compressedArr[runIndex][1] = count;
		return compressedArr;
	}
	    
	/**
	 * Decompresses a run-length encoded array back into the original array.
	 * 
	 * @param compressedArr 2D array containing {value, count} pairs
	 * @return Decompressed array
	 */
	public static int[] decompressRuns(int[][] compressedArr) {
		int length = 0;
		// Calculate total length of decompressed array
		for (int i = 0; i < compressedArr.length; i++) {
			length += compressedArr[i][1];
		}
		int[] decompressedArr = new int[length];
		
		int index = 0; 

		for (int i = 0; i < compressedArr.length; i++) {
			int value = compressedArr[i][0];   
			int count = compressedArr[i][1];   

			for (int j = 0; j < count; j++) {
				decompressedArr[index++] = value;                         
			}
		}
		return decompressedArr;
	}
    
	// ARRAY HELPERS
	
	/**
	 * Prompts user for the size and values of a 1D integer array.
	 * 
	 * @param scanner Scanner object to read user input
	 * @return Array of integers input by user
	 */
	public static int[] getSingleDimensionalArray(Scanner scanner) {
		System.out.print("Enter the number of elements for your 1D array ");
		int size = scanner.nextInt();
		
		int[] arr = new int[size];

		// Read array elements from user
		for (int i = 0; i < size; i++) {
			System.out.print("Enter element " + (i + 1) + ": ");
			arr[i] = scanner.nextInt();
		}
		return arr;
	}
	    
	/**
	 * Sorts an integer array using bubble sort.
	 * If the array is empty, prints a message and terminates the program.
	 * 
	 * @param arr Input array to sort
	 * @return Sorted array
	 */
	public static int[] sortArray(int[] arr) {
		if (arr.length == 0) {
			System.out.println("Empty Array. Please try again for better results.");
			System.exit(0);
		}
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
		return arr;
	}
	    
	/**
	 * Prints all elements of an integer array separated by spaces.
	 * 
	 * @param array The array to print
	 */
	public static void printArray(int[] array) {
		for (int num : array) {
			System.out.print(num + " ");
		}
		System.out.println();
	}
	    
	/**
	 * Multiplies two matrices and returns the result matrix.
	 * 
	 * @param matrixA First input matrix
	 * @param matrixB Second input matrix
	 * @return Resulting matrix after multiplication
	 */
	public static double[][] matrixMultiply(double[][] matrixA, double[][] matrixB) {
		int rowsA = matrixA.length;
		int colsA = matrixA[0].length;
		int rowsB = matrixB.length;
		int colsB = matrixB[0].length;
		
		double[][] matrixC = new double[rowsA][colsB];
		
		// Perform matrix multiplication using triple nested loops
		for (int i = 0; i < rowsA; i++) {
			for (int j = 0; j < colsB; j++) {
				matrixC[i][j] = 0;
				for (int k = 0; k < colsA; k++) {
					matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
				}
			}
		}

		return matrixC;
	}
  
	// MATRIX HELPERS
	
	/**
	 * Prints a 2D double array as a formatted matrix.
	 * 
	 * @param matrix Matrix to print
	 */
	public static void printMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}
	    
	/**
	 * Reads a matrix from user input.
	 * 
	 * @param scanner Scanner object for user input
	 * @param name Name of the matrix (for prompts)
	 * @return The matrix as a 2D double array
	 */
	public static double[][] readMatrix(Scanner scanner, String name) {
		System.out.print("Enter number of rows for " + name + ": ");
		int rows = scanner.nextInt();

		System.out.print("Enter number of columns for " + name + ": ");
		int cols = scanner.nextInt();

		double[][] matrix = new double[rows][cols];

		System.out.println("Enter values for " + name + ":");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print("[" + i + "][" + j + "]: ");
				matrix[i][j] = scanner.nextDouble();
			}
		}

		return matrix;
	}

}

public class ConvertMatrixToTable {
    public static void printMatrixAsTable(double[][] matrix, int decimalPlaces, long[] w, double[] x) {
        int maxWidth = 0;
        for (double[] row : matrix) {
            for (double value : row) {
                maxWidth = Math.max(maxWidth, formatValue(value, decimalPlaces).length());
            }
        }
        
        int headerWidth = Math.max(maxWidth, 6);
        
        System.out.printf("%" + headerWidth + "s", "");
        for (double value : x) System.out.printf("%" + (maxWidth + 1) + ".1f", value);
        System.out.println();
                System.out.printf("%" + headerWidth + "s", "");
        for (int j = 0; j < x.length; j++) System.out.printf("%" + (maxWidth + 1) + "s", "-".repeat(maxWidth));
        System.out.println();
        
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("%" + (headerWidth - 1) + "d|", w[i]);
            for (double value : matrix[i]) System.out.printf("%" + (maxWidth + 1) + "s", formatValue(value, decimalPlaces));
            System.out.println();
        }
    }
    
    public static String formatValue(double value, int decimalPlaces) {
        return Double.isNaN(value) ? "NaN" : 
               Double.isInfinite(value) ? (value > 0 ? "+∞" : "-∞") : 
               String.format("%." + decimalPlaces + "f", value);
    }
}

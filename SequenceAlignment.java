package sequencealignment;



public class SequenceAlignment {

    static void SequenceAlignment(String x, String y,
            int penalty, int gap) {
        
        int i, j; // intialising variables 

        int m = x.length(); // length of gene1 
        int n = y.length(); // length of gene2 

        

        // table for storing optimal 
        // substructure answers 
        int dp[][] = new int[m + 1][n + 1];

        int ncount = n;
        //traverse array from bottom right to top left
        //first traversal goes along the bottom diaginal as much as it can untill it runs out of columns
        for (int diagonal = 1; diagonal <= (n + 1); diagonal++) //calcualte how many diagonals
        {

            for (int r = m - diagonal + 1; r <= m; r++) { //look at what rows are doing, columns can be calculated from rows

                if (r < 0) {
                    n--; //need to still decrement n, but we cannot use this negative number
                } else {  //the usual, 

                    if (r == m) {
                        dp[r][n] = 2 * (ncount - n);
                    } else if (n == ncount) {
                        dp[r][n] = 2 * (m - r);
                    } else { //dynamic programming
                        //need to update penalty if string match
                        if (x.charAt(r) == y.charAt(n)) {

                            penalty = 0;
                        }
                        dp[r][n] = Math.min(dp[r + 1][n + 1] + penalty, Math.min(dp[r + 1][n] + gap, dp[r][n + 1] + gap));
                    }

                    n--;
                    penalty = 1;
                }
            }
            n = ncount;
        }

        //middle and top of the array traversal
        ncount = n;
        for (int diaginal = m - 1; diaginal >= 0; diaginal--) {

            if (diaginal >= n) { //first case middle traversal
                for (int r = diaginal - n; r <= diaginal; r++) {

                    if (r == m) {
                        dp[r][n] = 2 * (ncount - n); //base case
                    } else if (n == ncount) {
                        dp[r][n] = 2 * (m - r);   //base case
                    } else { //dynamic programming
                        //need to update penalty if string match
                        if (x.charAt(r) == y.charAt(n)) {
                            penalty = 0;
                        }
                        dp[r][n] = Math.min(dp[r + 1][n + 1] + penalty, Math.min(dp[r + 1][n] + gap, dp[r][n + 1] + gap));
                    }

                    n--;
                    penalty = 1;
                }
                n = ncount;

            } else {    //top traversal

                n = n - 1;
                for (int r = 0; r <= diaginal; r++) {

                    if (r == m) {
                        dp[r][n] = 2 * (ncount - n);
                    } else if (n == ncount) {
                        dp[r][n] = 2 * (m - r);
                    } else { //dynamic programming
                        //need to update penalty if string match
                        if (x.charAt(r) == y.charAt(n)) {
                            penalty = 0;
                        }
                        dp[r][n] = Math.min(dp[r + 1][n + 1] + penalty, Math.min(dp[r + 1][n] + gap, dp[r][n + 1] + gap));
                    }

                    n--;
                    penalty = 1;

                }
                ncount--;
                n = ncount;

            }

        }
        
        
        n = y.length();

        for (int r = 0; r < dp.length; r++) {
            for (int c = 0; c < dp[r].length; c++) {
                System.out.printf("%4d", dp[r][c]);
            }
            System.out.println("");
        }
        System.out.println();
        //done with array now, just find the aligned strings now

        char[] mArray = new char[m];
        char[] nArray = new char[m];

        i = 0;
        j = 0;
       
        //index of the two arrays 
        int arrayIndex = 0;
        //stops the while loop if we reach a border
        while (i != m && j != n) {
        
        //calculate penalty
            if (x.charAt(i) == y.charAt(j)) {
                penalty = 0;
            }
            //case 1, add a gap to  x array and leave y array alone
            if (dp[i][j] == dp[i][j + 1] + gap) { 
               
                mArray[arrayIndex] = '_';
                nArray[arrayIndex] = y.charAt(j);

                arrayIndex++;
                j++;
                
              //case 2
            } else if (dp[i][j] == dp[i + 1][j + 1] + penalty) {
                //line those two up
                mArray[arrayIndex] = x.charAt(i);
                nArray[arrayIndex] = y.charAt(j);

                arrayIndex++;
                i++;
                j++;

                //case 3, gap goes in y with x copying
            } else if (dp[i][j] == dp[i + 1][j] + gap) {

                mArray[arrayIndex] = x.charAt(i);
                nArray[arrayIndex] = '_';

                arrayIndex++;
                i++;
            }
            //reset penalty, and avoid arrayOutofBounds
            penalty = 1;
            if (arrayIndex == m) {
                break;
            }
        }
        
        //print strings nicely
        for (int q = 0; q < m; q++) {
            System.out.print(mArray[q] + " ");
        }
        System.out.println();
        for (int q = 0; q < m; q++) {
            System.out.print(nArray[q] + " ");
        }
        System.out.println();

    }


    public static void main(String[] args) {
        // Gene Strings
        String gene1 = "TCCCAGTTATGTCAGGGGACACGAGCATGCAGAGAC";
        String gene2 = "AATTGCCGCCGTCGTTTTCAGCAGTTATGTCAGATC";

        if (gene2.length() > gene1.length()) { //bigger string will always be whats stored in m
            String temp = gene1;
            gene1 = gene2;
            gene2 = temp;
        }

        //setting our mismatch and gap 
        int gap = 2;
        int misMatch = 1;
        

        // calling the function to 
        // calculate the result 
        SequenceAlignment(gene1,gene2,misMatch, gap);
                
    }
}

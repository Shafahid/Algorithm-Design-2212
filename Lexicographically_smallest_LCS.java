import java.util.*;

public class Solution {
    String[][] dp;
    String s, t;

    public String lcs(int i, int j) {

        if (i == 0 || j == 0) return "";

    
        if (dp[i][j] != null) return dp[i][j];

        if (s.charAt(i - 1) == t.charAt(j - 1)) {
            dp[i][j] = lcs(i - 1, j - 1) + s.charAt(i - 1);
        } else {
            String option1 = lcs(i - 1, j);
            String option2 = lcs(i, j - 1);

            if (option1.length() > option2.length()) {
                dp[i][j] = option1;
            } else if (option2.length() > option1.length()) {
                dp[i][j] = option2;
            } else {
                dp[i][j] = option1.compareTo(option2) < 0 ? option1 : option2;
            }
        }

        return dp[i][j];
    }

    public String lexSmallestLCS(String s1, String t1) {
        this.s = s1;
        this.t = t1;
        int n = s.length();
        int m = t.length();

        dp = new String[n + 1][m + 1]; 
        return lcs(n, m);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        String s = "abac";
        String t = "cab";
        String result = sol.lexSmallestLCS(s, t);
    }
}

import java.util.ArrayList;
import java.util.List;

/*
Кроссворд
*/
public class Solution {
    public static void main(String[] args) {
        int[][] crossword = new int[][]{
                {'f', 'd', 'e', 'r', 'l', 'k'},
                {'u', 's', 'a', 'm', 'e', 'o'},
                {'l', 'n', 'g', 'r', 'o', 'v'},
                {'m', 'l', 'p', 'r', 'r', 'h'},
                {'p', 'o', 'e', 'e', 'j', 'j'}
        };
        List<Word> words = detectAllWords(crossword, "hvok", "dsnlo", "vorgnl", "same", "home", "lse", "ful", "ranm", "darr");
        words.forEach(System.out::println);
        System.out.println("========= dups ==========");
        words = detectAllWords(crossword, "jr", "rr", "oe", "mm", "vo", "jrgs", "vorg", "aaa");
        words.forEach(System.out::println);
/*
    expected
    hvok - (5, 3) - (5, 0)
    dsnlo - (1, 0) - (1, 4)
    vognl - (5, 2) - (0, 2)
    same - (1, 1) - (4, 1)
    home - (5, 3) - (2, 0)
    lse - (0, 2) - (2, 0)
    ful - (0, 0) - (0, 2)
    ranm - (3, 0) - (0, 3)
    darr - (1, 0) - (4, 3)
    ========= dups ==========
    jr - (4, 4) - (4, 3)
    rr - (3, 2) - (3, 3)
    oe - (5, 1) - (4, 1)
    vo - (5, 2) - (5, 1)
    jrgs - (4, 4) - (1, 1)
    vorg - (5, 2) - (2, 2)]
*/

    }

    public static List<Word> detectAllWords(int[][] crossword, String... words) {
        List<Word> res = new ArrayList<>();
        ArrayList<int[]> direction = new ArrayList<>();
        direction.add(new int[]{-1, 0});    // north
        direction.add(new int[]{-1, -1});   // north-west
        direction.add(new int[]{-1, 1});    // north-east
        direction.add(new int[]{1, 0});     // south
        direction.add(new int[]{1, -1});    // south-west
        direction.add(new int[]{1, 1});     // south-east
        direction.add(new int[]{0, -1});    // west
        direction.add(new int[]{0, 1});     // east

        for (String word : words) {
            char firstLetter = word.charAt(0);
            boolean found = false;

            for (int y = 0; y < crossword.length; y++) {
                for (int x = 0; x < crossword[y].length; x++) {
                    if (crossword[y][x] == firstLetter) {
                        Point point = findEnd(crossword, y, x, word, direction);
                        if (point != null) {
                            Word wordRes = new Word(word);
                            wordRes.setStartPoint(x, y);
                            wordRes.setEndPoint(point.x, point.y);
                            res.add(wordRes);
                            found = true;
                            break;
                        }
                    }
                }
                if (found) break;
            }
        }

        return res;
    }

    private static Point findEnd(int[][] crossword, int y, int x, String word, ArrayList<int[]> direction) {
        Point point = null;

        for (int[] dir : direction) {
            point = checkDirection(crossword, y, x, word, dir);
            if (point != null) break;
        }

        return point;
    }

    private static Point checkDirection(int[][] crossword, int y, int x, String word, int[] direction) {
        boolean found = false;
        int i = 1;

        try {

            for (; i < word.length(); i++) {
                if (crossword[y + i * direction[0]][x + i * direction[1]] != word.charAt(i)) {
                    found = false;
                    break;
                }
                found = true;
            }

            if (found)
                return new Point(x + i * direction[1] - direction[1], y + i * direction[0] - direction[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        return null;
    }

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Word {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public Word(String text) {
            this.text = text;
        }

        public void setStartPoint(int i, int j) {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j) {
            endX = i;
            endY = j;
        }

        @Override
        public String toString() {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }
    }
}
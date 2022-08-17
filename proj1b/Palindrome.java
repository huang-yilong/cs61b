public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        return isPalindrome(word, 0, word.length() - 1);
    }

    private boolean isPalindrome(String word, int l, int r) {
        if (l >= r) {
            return true;
        }
        if (word.charAt(l) != word.charAt(r)) {
            return false;
        }
        return isPalindrome(word, l + 1, r - 1);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        }
        return isPalindrome(word, 0, word.length() - 1, cc);
    }

    private boolean isPalindrome(String word, int l, int r, CharacterComparator cc) {
        if (l >= r) {
            return true;
        }
        if (!cc.equalChars(word.charAt(l), word.charAt(r))) {
            return false;
        }
        return isPalindrome(word, l + 1, r - 1, cc);
    }
}

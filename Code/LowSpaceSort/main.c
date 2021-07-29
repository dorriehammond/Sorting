// THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
// A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Dorrie Hammond

// If you submit, please add your name above.
// Note: this C program is tested with clang-7, it may need
// some modification to work with other versions of C.

// Goal: re-implement function low_space_sort using
// low space methods, see the "Constraints" listed below.
// Your program should still produce the same output!
// It will probably be slower, but that is OK.
//
// Essentially, you are showing that a log space transducer
// is able to read as input a list of strings (the words),
// and print out those strings in sorted order.

#include <stdio.h>
#include <stdlib.h> // qsort (which you should not use)

void low_space_sort(const char *);

int main(int argc, char **argv) {
    // Here we just run few small fixed examples.
    // In principle, your code should allow much longer test inputs:
    low_space_sort("each input s is a list of words separated by spaces");
    low_space_sort("the quick brown fox jumped over the lazy dog");
    low_space_sort("a b ab ba b aba bab abab baba aaaa bbbb aabb bbaa");
    low_space_sort("this sorting algorithm could be slower than bubblesort");
    //low_space_sort("each input s is a list of words separated by spaces");
    return 0;
}

// Does char c end a word? (either space or NUL)
int word_end(char c) { return c==' ' || c=='\0'; }

// Compare two words, where each word is terminated by ' ' or '\0'.
// Return negative if a before b, positive if b before a, zero if equal.
// Both a and b point into the read-only input string s of length n.
// This function is already low space, you may use it as is.
int compare_words(const char *a, const char *b)
{
    // We want to compare strings a and b (pointers inside s).
    int i=0; // index into both strings a and b
    while (a[i]==b[i] && !word_end(a[i])) ++i;
    if (a[i]==b[i]) return 0; // the strings are equal
    if (word_end(a[i])) return -1; // a is prefix of b
    if (word_end(b[i])) return +1; // b is prefix of a
    if (a[i] < b[i]) return -1; // a before b
    return +1; // a[i] > b[i], b before a
}

// compare_words_qsort is similar, but converting the arguments
// as given by qsort. You should NOT use qsort in a low space solution,
// so you should not need this function in your solution.
int compare_words_qsort(const void *a, const void *b) {
    return compare_words(*(const char **) a, *(const char **) b);
}

void print_word(const char *s){
  while(!word_end(*s)) { putchar(*s++); }
}

const char * next_word(const char *s){
  while(!word_end(*s)) { s=s+1; }
  return(s=s+1);
}

const char * first_word(const char *word, int word_count) {
  const char *curr = word;
  int compare;
  for (int i = 0; i < word_count-1; i++) {
    compare = compare_words(curr,word);
    if (compare == +1) { curr = word; }
    word = next_word(word);
  }
  return(curr);
}

const char * pick_word(const char *word, const char *prev, int word_count) {
  const char *curr = prev;
  int compare;
  for (int i = 0; i < word_count-1; i++) {
    compare = compare_words(prev,word);
    if (compare == -1 || (compare == 0 && prev < word)) {
      curr = word;
      break;
    }
    word = next_word(word);
  }

  for (int i = 0; i < word_count-1; i++) {
    compare = compare_words(prev,word);
    if (compare == -1 || (compare == 0 && prev < word)) {  
      compare = compare_words(curr,word);
      if (compare == +1) {
        curr = word;
      }
    }
    word = next_word(word);
  }
  return(curr);
}

void low_space_sort(const char *s)
{
    // The input string s is a sequence of words separated by spaces.
    //
    // Goal: print the words of s in sorted order, separated by spaces,
    // and finally terminated with a newline.
    // As given, this code already works!
    // However, it uses too much space when it allocates an array.
    //
    // TODO: rewrite this using "low space" methods, essentially
    // you are solving the problem using O(lg n) space, where n is
    // the length of the input string s.
    //
    // Constraints:
    //
    //   * do not allocate anything! (no arrays, no new strings)
    //   * do not modify the string s (treat it as read-only text)
    //   * all char* variables must point into s
    //   * produce output one char at a time, using putchar (like below)
    //   * all int variables should have absolute value at most O(n)
    //   * you may define and call functions, but no recursion is allowed.
    //   * you may also have char variables
    //   * no other variable types allowed (no arrays, etc.)
    //
    // Note: the "compare_words" function above is alreay low space,
    // you are free to use that as given.

    int n = 0;
    while (s[n] != '\0') ++n;
    // Now n equals strlen(s)
    int word_count = 1;
    for (int i=0; i<n; ++i)
        if (s[i]==' ') ++word_count;
    // Now word_count is the number of words in s.

    // my code
    const char *word = &s[0];
    const char *curr;
    int compare;
    const char *prev;
    if (word_count == 1) {
      print_word(word);
      putchar('\n'); }
    else {
      curr = first_word(&s[0],word_count);
      print_word(curr);
      putchar(' ');
      prev = curr;
      for (int j = 0; j < word_count-1; j++) {
        curr = pick_word(&s[0],prev,word_count);
        print_word(curr);
        if (j==word_count-2) { putchar('\n'); }
        else { putchar(' '); }
        prev = curr;
      }
    }

}
void main() throws IOException {
    var start = LocalDateTime.now();

    var words = getWordsFromFile("src/txt/harry.txt");

    var distincts = getDistinctWords(words);
    var frequencies = getWordOrderedFrequencies(distincts, words);
    var wordsWithFreq = getSortedWordsWithFrequency(distincts, frequencies);

    printWordsWithTopFrequencies(wordsWithFreq, 30);

    var finish = LocalDateTime.now();

    System.out.println("------");
    System.out.println(ChronoUnit.MILLIS.between(start, finish));
}

String[] getWordsFromFile(String fileName) throws IOException {
    return Files
        .readString(Path.of(fileName), StandardCharsets.UTF_8)
        .replaceAll("[^A-Za-z ]"," ")
        .toLowerCase(Locale.ROOT)
        .split(" +");
}

String[] getDistinctWords(String[] words) {
    Arrays.sort(words);

    var distinctString = " ";

    for (var word : words) {
        if (!distinctString.contains(word)){
            distinctString+= word + " ";
        }
    }

    return distinctString.split(" ");
}

int[] getWordOrderedFrequencies(String[] distincts, String[] words) {
    var freq = new int[distincts.length];

    for (int i = 0; i < distincts.length ; i++) {
        int count = 0;

        for (var word : words) {
            if (distincts[i].equals(word)) {
                count++;
            }
        }
        freq[i] = count;
    }

    return freq;
}

String[] getSortedWordsWithFrequency(String[] distincts, int[] freq) {
    var wordsWithFreq = new String[distincts.length];

    for (int i = 0; i < distincts.length ; i++) {
        wordsWithFreq[i] = distincts[i] + " " + freq[i];
    }

    Arrays.sort(wordsWithFreq, Comparator.comparing(str -> Integer.valueOf(str.replaceAll("[^0-9]", ""))));

    return wordsWithFreq;
}

void printWordsWithTopFrequencies(String[] wordsWithFreq, int limit) {
    for (int i = 0; i < limit; i++) {
        System.out.println(wordsWithFreq[wordsWithFreq.length - i - 1]);
    }
}

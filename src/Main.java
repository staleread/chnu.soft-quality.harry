void main() throws IOException {
    var start = LocalDateTime.now();

    var frequencies = getWordFrequencies("src/txt/harry.txt");
    var wordsWithFreq = getSortedWordsWithFrequency(frequencies);

    printWordsWithTopFrequencies(wordsWithFreq, 30);

    var finish = LocalDateTime.now();

    System.out.println("------");
    System.out.println(ChronoUnit.MILLIS.between(start, finish));
}

Map<String, Integer> getWordFrequencies(String fileName) throws IOException {
    var freq = new HashMap<String, Integer>();
    var buffer = new byte[64 * 1024];
    var word = new byte[64];
    var wordLength = 0;

    try (var in = new FileInputStream(fileName)) {
        int read;
        while ((read = in.read(buffer)) != -1) {
            for (int i = 0; i < read; i++) {
                var b = buffer[i];
                if (b >= 'A' && b <= 'Z') {
                    b += 'a' - 'A';
                }
                if (b >= 'a' && b <= 'z') {
                    if (wordLength == word.length) {
                        word = Arrays.copyOf(word, wordLength * 2);
                    }
                    word[wordLength++] = b;
                } else if (wordLength > 0) {
                    freq.merge(new String(word, 0, wordLength, StandardCharsets.ISO_8859_1), 1, Integer::sum);
                    wordLength = 0;
                }
            }
        }
    }

    if (wordLength > 0) {
        freq.merge(new String(word, 0, wordLength, StandardCharsets.ISO_8859_1), 1, Integer::sum);
    }

    return freq;
}

List<Map.Entry<String, Integer>> getSortedWordsWithFrequency(Map<String, Integer> freq) {
    var wordsWithFreq = new ArrayList<>(freq.entrySet());

    wordsWithFreq.sort(
        Map.Entry.<String, Integer>comparingByValue().reversed()
            .thenComparing(Map.Entry.<String, Integer>comparingByKey().reversed()));

    return wordsWithFreq;
}

void printWordsWithTopFrequencies(List<Map.Entry<String, Integer>> wordsWithFreq, int limit) {
    for (var entry : wordsWithFreq.subList(0, limit)) {
        System.out.println(entry.getKey() + " " + entry.getValue());
    }
}

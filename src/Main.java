void main() throws IOException {
    var start = LocalDateTime.now();

    var words = getWordsFromFile("src/txt/harry.txt");

    var frequencies = getWordFrequencies(words);
    var wordsWithFreq = getSortedWordsWithFrequency(frequencies);

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

Map<String, Integer> getWordFrequencies(String[] words) {
    var freq = new HashMap<String, Integer>();

    for (var word : words) {
        freq.merge(word, 1, Integer::sum);
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

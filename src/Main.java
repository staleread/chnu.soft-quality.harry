void main() throws IOException {
    LocalDateTime start = LocalDateTime.now();

    var words = getWordsFromFile("src/txt/harry.txt");

    Arrays.sort(words);

    String distinctString = " ";

    for (int i = 0; i < words.length ; i++) {
        if (!distinctString.contains(words[i])){
            distinctString+= words[i] + " ";
        }
    }

    String[] distincts = distinctString.split(" ");
    int[] freq = new int[distincts.length];

    for (int i = 0; i < distincts.length ; i++) {
        int count = 0;
        for (int j = 0; j < words.length ; j++) {
            if (distincts[i].equals(words[j])) {
                count++;
            }
        }
        freq[i] = count;
    }

    for (int i = 0; i < distincts.length ; i++) { // 5 000
        distincts[i] += " " + freq[i];
    }

    Arrays.sort(distincts, Comparator.comparing(str
                -> Integer.valueOf(str.replaceAll("[^0-9]", ""))));

    for (int i = 0; i < 30; i++) {
        System.out.println(distincts[distincts.length - 1 - i]);
    }
    LocalDateTime finish = LocalDateTime.now();

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

# Refactoring suggestions

1. `Files.readString()` looks cleaner then `new String(Files.readAllBytes(...))`
2. Prefer `var` for variables with obvious types e.g. `LocalDateTime start = LocalDateTime.now()` and `int[] freq = new int[distincts.length]`
3. Use for-range loop instead of for loop with index for words looping
4. Abstract away the implementation logic from the `main()` by extracting functions like `getWordsFromFile()`, `getDistinctWords()`, `getWordOrderedFrequencies()`
5. Remove unused variables
6. Remove irrelevant comments
7. Do not mutate the elements of `distincts` (appending the frequency to each word of the array). This makes the variable lose meaning after then.

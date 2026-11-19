# Lab 2. Refactoring suggestions

1. `Files.readString()` looks cleaner then `new String(Files.readAllBytes(...))`
2. Prefer `var` for variables with obvious types e.g. `LocalDateTime start = LocalDateTime.now()` and `int[] freq = new int[distincts.length]`
3. Use for-range loop instead of for loop with index for words looping
4. Abstract away the implementation logic from the `main()` by extracting functions like `getWordsFromFile()`, `getDistinctWords()`, `getWordOrderedFrequencies()`
5. Remove unused variables
6. Remove irrelevant comments
7. Do not mutate the elements of `distincts` (appending the frequency to each word of the array). This makes the variable lose meaning after then.

# Labs 3-4. Memory/Time optimization

## Memory optimization
1. Using a growing String object for the disctinct words would make string pool create an instance for each iteration.
2. Word lookup (after word matching fix adding `" "` around the search term) construct a search string for each of the files words.
3. Regex for trimming non-digits when sorting the `word N` strings gets allocated for each comparison (typically `O(n log n)` times)
4. File parsing pipeline can be improved by reading from a fixed buffer into a hash map directly instead of reading the whole file as a string, then allocating a new string by applying the regex and allocating string for each word
5. Using fixed 64-byte buffer for the word being parsed is cheaper then string concatenation which leads to allocation of all variants to string pool e.g. "a", "at", "ato" and "atom".


| Metric | Baseline (`60dceea`) | HashMap | HashMap + buffered byte reading |
|---|---|---|---|
| Total allocated, MB (smallest no-GC heap) | 221 | 13 | 9 |
| GC count (2 MB young gen) | 137 | 7 | 3 |
| Peak heap after GC, MB | 9 | 8 | 2 |
| Smallest working `-Xmx`, MB | 7 | 7 | 3 |

# Time optimization
1. The word lookup in `distrinctString` takes longer than key lookup in `HashMap`
2. If we only print `limit` most frequent words, then sorting the whole word list alphabetically is extra work. Picking `limit` most frequent and sorting the entries with matching frequencies is faster.
3. The `getWordOrderedFrequencies` calculates word frequence scanning the whole list of words. Can be resolved in 2 ways
  - as the words are already sorted, then encountering the first word after 1+ searched words means the lookup can be stopped
  - using `HashMap` with `word-frequency` pairs resolves the lookup completely with `O(n)` call
4. File reading pipeline suggested in "Memory optimization" section also reuses time dramatically
5. Using ASCII code comparison for the chars while parsing words wins regex replace operation

| Metric | Baseline (`60dceea`) | HashMap | HashMap + buffered byte reading |
|---|---|---|---|
| Elapsed, ms (median of 15, excludes JVM startup) | 1768 | 67 | 24 |

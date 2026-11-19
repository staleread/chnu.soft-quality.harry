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

| Metric | Baseline (`e460cb2`) | HashMap |
|---|---|---|
| Total allocated, MB (smallest no-GC heap) | 221 | 13 |
| GC count (2 MB young gen) | 137 | 7 |
| Peak heap after GC, MB | 9 | 8 |

Drafts:
4. File parsing pipeline...
3. Constructing distinct words array, then an array of frequencis and ...

# Time optimization
1. The word lookup in `distrinctString` takes longer than key lookup in `HashMap`
2. If we only print `limit` most frequent words, then sorting the whole word list alphabetically is extra work. Picking `limit` most frequent and sorting the entries with matching frequencies is faster.
3. The `getWordOrderedFrequencies` calculates word frequence scanning the whole list of words. Can be resolved in 2 ways
  - as the words are already sorted, then encountering the first word after 1+ searched words means the lookup can be stopped
  - using `HashMap` with `word-frequency` pairs resolves the lookup completely with `O(n)` call

| Metric | Baseline (`e460cb2`) | HashMap |
|---|---|---|
| Elapsed, ms (median of 15, excludes JVM startup) | 1768 | 67 |

package com.shagalalab.sozlik.shared.data.spellchecker

class SpellChecker {

    companion object {
        private const val ALPHABET_LATIN = "aábcdefgǵhiıjklmnńoópqrstuúvwxyz"
        private const val ALPHABET_CYRILLIC = "аәбвгғдеёжзийкқлмнңоөпрстуүўфхҳцчшщъыьэюя"
    }

    fun edits1(word: String): Set<String> {
        val alphabet = if (ALPHABET_LATIN.contains(word.first())) ALPHABET_LATIN else ALPHABET_CYRILLIC
        val edits: MutableSet<String> = mutableSetOf()
        val splits: List<SplitWord> = SplitWord.allSplits(word)
        for (split in splits) {
            val a: String = split.prefix
            val b: String = split.suffix
            val lb = b.length
            if (lb > 0) {
                edits.add(a + b.substring(1)) // delete 1 character
                for (element in alphabet) {
                    edits.add(a + element + b.substring(1)) // replace 1 characters
                }
            }
            if (lb > 1) {
                edits.add(a + b.substring(2)) //delete 2 characters
                edits.add(a + b[1] + b[0] + b.substring(2)) // transpose
                for (element1 in alphabet) {
                    for (element2 in alphabet) {
                        edits.add(a + element1 + element2 + b.substring(2)) // replace 2 characters
                    }
                }
            }
            for (i in alphabet.indices) {
                edits.add(a + alphabet[i] + b) // insert
                for (element in alphabet) {
                    edits.add(a + alphabet[i] + element + b)
                }
            }
        }
        return edits
    }

    private class SplitWord private constructor(word: String, splitPos: Int) {
        val prefix: String
        val suffix: String

        init {
            prefix = word.substring(0, splitPos)
            suffix = word.substring(splitPos, word.length)
        }

        override fun toString(): String {
            return "$prefix-$suffix"
        }

        companion object {
            fun allSplits(word: String): List<SplitWord> {
                val splits: MutableList<SplitWord> = mutableListOf()
                for (i in 0..word.length) {
                    splits.add(SplitWord(word, i))
                }
                return splits
            }
        }
    }
}

package io.kanro.idea.plugin.protobuf.string.case

object TrainCaseFormatter : BaseCaseFormatter() {
    override fun formatWord(index: Int, word: CharSequence): CharSequence {
        return word.toString().toUpperCase()
    }

    override fun appendDelimiter(builder: StringBuilder) {
        builder.append('-')
    }
}

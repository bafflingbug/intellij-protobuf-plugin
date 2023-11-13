package io.kanro.idea.plugin.protobuf.string.case

object UpperDotCaseFormatter : BaseCaseFormatter() {
    override fun formatWord(
        index: Int,
        word: CharSequence,
    ): CharSequence {
        return word.toString().uppercase()
    }

    override fun appendDelimiter(builder: StringBuilder) {
        builder.append('.')
    }
}

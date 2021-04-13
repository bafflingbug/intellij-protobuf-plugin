package io.kanro.idea.plugin.protobuf.aip.annotator

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufStringValue
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufVisitor

class AipAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        element.accept(object : ProtobufVisitor() {
            override fun visitStringValue(o: ProtobufStringValue) {
                o.reference?.let {
                    if (it.resolve() == null) {

                        holder.newAnnotation(
                            HighlightSeverity.ERROR,
                            "Resource name ${o.text} not found."
                        )
                            .range(o.textRange)
                            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                            .create()
                    }
                    return
                }
            }
        })
    }
}

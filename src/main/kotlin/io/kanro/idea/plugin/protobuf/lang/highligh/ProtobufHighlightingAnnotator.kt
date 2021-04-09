package io.kanro.idea.plugin.protobuf.lang.highligh

import com.intellij.codeInsight.daemon.impl.HighlightInfoType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufEnumDefinition
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufEnumValueDefinition
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufFieldAssign
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufFieldDefinition
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufFieldName
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufGroupField
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufIdentifier
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufMapField
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufMessageDefinition
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufNumberValue
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufOneOfField
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufRpcMethod
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufServiceDefinition
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufTypeName
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufVisitor
import io.kanro.idea.plugin.protobuf.lang.psi.primitive.ProtobufElement
import io.kanro.idea.plugin.protobuf.lang.psi.token.ProtobufKeywordToken
import io.kanro.idea.plugin.protobuf.lang.psi.token.ProtobufToken
import io.kanro.idea.plugin.protobuf.lang.support.BuiltInType

class ProtobufHighlightingAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        element.accept(ProtobufHighlightingVisitor(holder))
    }

    private class ProtobufHighlightingVisitor(val holder: AnnotationHolder) : ProtobufVisitor() {
        override fun visitEnumValueDefinition(o: ProtobufEnumValueDefinition) {
            createHighlight(o.identifier() ?: return, ProtobufHighlighter.ENUM_VALUE)
        }

        override fun visitNumberValue(o: ProtobufNumberValue) {
            if (o.floatLiteral == null && o.integerLiteral == null) {
                createHighlight(o, ProtobufHighlighter.KEYWORD)
            }
        }

        override fun visitTypeName(o: ProtobufTypeName) {
            if (BuiltInType.isBuiltInType(o.text)) {
                createHighlight(o, ProtobufHighlighter.KEYWORD)
            }
        }

        override fun visitIdentifier(o: ProtobufIdentifier) {
            when (o.owner()) {
                is ProtobufMessageDefinition,
                is ProtobufFieldDefinition,
                is ProtobufMapField,
                is ProtobufOneOfField,
                is ProtobufGroupField,
                is ProtobufFieldAssign,
                is ProtobufEnumDefinition,
                is ProtobufServiceDefinition,
                is ProtobufRpcMethod -> {
                    createHighlight(o, ProtobufHighlighter.IDENTIFIER)
                }
            }
        }

        override fun visitFieldName(o: ProtobufFieldName) {
            createHighlight(o, ProtobufHighlighter.IDENTIFIER)
        }

        override fun visitElement(o: ProtobufElement) {
            when (o.node.elementType) {
                is ProtobufToken, is ProtobufKeywordToken -> {
                    o
                }
            }
        }

        private fun createHighlight(element: PsiElement, textAttributesKey: TextAttributesKey) {
            holder.newSilentAnnotation(HighlightInfoType.SYMBOL_TYPE_SEVERITY)
                .range(element.textRange)
                .textAttributes(textAttributesKey)
                .create()
        }
    }
}
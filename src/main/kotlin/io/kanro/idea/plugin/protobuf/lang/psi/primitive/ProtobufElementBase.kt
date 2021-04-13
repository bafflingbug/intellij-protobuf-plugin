package io.kanro.idea.plugin.protobuf.lang.psi.primitive

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiNameIdentifierOwner
import io.kanro.idea.plugin.protobuf.lang.psi.primitive.feature.ProtobufNamedElement

abstract class ProtobufElementBase(node: ASTNode) : ASTWrapperPsiElement(node) {
    override fun getPresentation(): ItemPresentation? {
        if (this is ItemPresentation) return this
        return null
    }

    override fun getTextOffset(): Int {
        if (this is PsiNameIdentifierOwner) {
            if (this.nameIdentifier == this) {
                return super.getTextOffset()
            }
            return this.nameIdentifier?.textOffset ?: super.getTextOffset()
        }
        return super.getTextOffset()
    }

    override fun getName(): String? {
        if (this is ProtobufNamedElement) return name()
        return null
    }
}

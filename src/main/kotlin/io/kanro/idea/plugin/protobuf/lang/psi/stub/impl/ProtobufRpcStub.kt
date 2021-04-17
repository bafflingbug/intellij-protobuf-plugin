package io.kanro.idea.plugin.protobuf.lang.psi.stub.impl

import com.intellij.psi.stubs.StubElement
import io.kanro.idea.plugin.protobuf.lang.psi.ProtobufRpcDefinition
import io.kanro.idea.plugin.protobuf.lang.psi.stub.ProtobufStub
import io.kanro.idea.plugin.protobuf.lang.psi.stub.primitive.ProtobufNamedStub
import io.kanro.idea.plugin.protobuf.lang.psi.stub.type.ProtobufRpcStubType

class ProtobufRpcStub(
    data: Array<String>,
    parent: StubElement<*>?
) : ProtobufStubBase<ProtobufRpcDefinition>(data, parent, ProtobufRpcStubType),
    ProtobufStub<ProtobufRpcDefinition>,
    ProtobufNamedStub {
    override fun name(): String? {
        return data(0).takeIf { it.isNotEmpty() }
    }
}

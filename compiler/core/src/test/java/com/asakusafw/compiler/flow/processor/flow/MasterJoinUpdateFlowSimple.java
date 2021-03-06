/**
 * Copyright 2011-2017 Asakusa Framework Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.asakusafw.compiler.flow.processor.flow;

import com.asakusafw.compiler.flow.processor.MasterJoinUpdateFlowProcessor;
import com.asakusafw.compiler.flow.processor.operator.MasterJoinUpdateFlowFactory;
import com.asakusafw.compiler.flow.processor.operator.MasterJoinUpdateFlowFactory.Simple;
import com.asakusafw.compiler.flow.testing.external.Ex1MockExporterDescription;
import com.asakusafw.compiler.flow.testing.external.Ex1MockImporterDescription;
import com.asakusafw.compiler.flow.testing.external.Ex2MockImporterDescription;
import com.asakusafw.compiler.flow.testing.model.Ex1;
import com.asakusafw.compiler.flow.testing.model.Ex2;
import com.asakusafw.vocabulary.flow.Export;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.Import;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.JobFlow;
import com.asakusafw.vocabulary.flow.Out;


/**
 * test for {@link MasterJoinUpdateFlowProcessor}.
 */
@JobFlow(name = "testing")
public class MasterJoinUpdateFlowSimple extends FlowDescription {

    private In<Ex1> in1;

    private In<Ex2> in2;

    private Out<Ex1> out1;

    private Out<Ex1> out2;

    /**
     * Creates a new instance.
     * @param in1 input1
     * @param in2 input2
     * @param out1 output1
     * @param out2 output2
     */
    public MasterJoinUpdateFlowSimple(
            @Import(name = "e1", description = Ex1MockImporterDescription.class)
            In<Ex1> in1,
            @Import(name = "e2", description = Ex2MockImporterDescription.class)
            In<Ex2> in2,
            @Export(name = "e1", description = Ex1MockExporterDescription.class)
            Out<Ex1> out1,
            @Export(name = "e2", description = Ex1MockExporterDescription.class)
            Out<Ex1> out2) {
        this.in1 = in1;
        this.in2 = in2;
        this.out1 = out1;
        this.out2 = out2;
    }

    @Override
    protected void describe() {
        MasterJoinUpdateFlowFactory f = new MasterJoinUpdateFlowFactory();
        Simple op = f.simple(in2, in1);
        out1.add(op.updated);
        out2.add(op.missed);
    }
}

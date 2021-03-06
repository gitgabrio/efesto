/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.foo.engine.runtime.service;

import org.kie.efesto.common.api.model.FRI;
import org.kie.efesto.runtimemanager.api.model.EfestoInput;
import org.kie.efesto.runtimemanager.api.service.KieRuntimeService;
import org.kie.foo.engine.api.model.FooResources;
import org.kie.foo.engine.runtime.model.EfestoInputFoo;
import org.kie.foo.engine.runtime.model.EfestoOutputFoo;
import org.kie.memorycompiler.KieMemoryCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.kie.foo.engine.runtime.utils.FooRuntimeHelper.*;

public class KieRuntimeServiceFoo implements KieRuntimeService<String, String, EfestoInput<String>, EfestoOutputFoo> {

    private static final Logger logger = LoggerFactory.getLogger(KieRuntimeServiceFoo.class.getName());


    @Override
    public boolean canManageInput(EfestoInput toEvaluate, KieMemoryCompiler.MemoryCompilerClassLoader memoryCompilerClassLoader) {
        return canManage(toEvaluate.getFRI());
    }

    @Override
    public Optional<EfestoOutputFoo> evaluateInput(EfestoInput<String> toEvaluate, KieMemoryCompiler.MemoryCompilerClassLoader memoryCompilerClassLoader) {
        try {
            FooResources fooResources = loadFooResources(toEvaluate.getFRI(), memoryCompilerClassLoader);
            EfestoInputFoo darInputFoo = new EfestoInputFoo(toEvaluate.getFRI(), toEvaluate.getInputData());
            return Optional.of(getEfestoOutput(fooResources, darInputFoo));
        } catch (Exception e) {
            logger.warn("{} can not evaluate {}",
                    this.getClass().getName(),
                    toEvaluate.getFRI());
            return Optional.empty();
        }
    }

}

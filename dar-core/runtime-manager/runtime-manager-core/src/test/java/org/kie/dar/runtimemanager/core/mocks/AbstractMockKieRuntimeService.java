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
package org.kie.dar.runtimemanager.core.mocks;

import org.kie.dar.runtimemanager.api.exceptions.KieRuntimeServiceException;
import org.kie.dar.runtimemanager.api.model.DARInput;
import org.kie.dar.runtimemanager.api.model.DAROutput;
import org.kie.dar.runtimemanager.api.service.KieRuntimeService;
import org.kie.memorycompiler.KieMemoryCompiler;

public abstract class AbstractMockKieRuntimeService implements KieRuntimeService {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends DARInput, E extends DAROutput> E evaluateInput(T toEvaluate, KieMemoryCompiler.MemoryCompilerClassLoader memoryCompilerClassLoader) {
        if (!canManageInput(toEvaluate.getFullResourceName(), memoryCompilerClassLoader)) {
            throw new KieRuntimeServiceException(String.format("Unmanaged input %s", toEvaluate.getFullResourceName()));
        }
        return (E) new MockDAROutput();
    }

}
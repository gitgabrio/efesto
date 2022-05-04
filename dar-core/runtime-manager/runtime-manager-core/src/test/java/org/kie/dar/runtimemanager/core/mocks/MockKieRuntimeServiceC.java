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

import org.kie.memorycompiler.KieMemoryCompiler;

public class MockKieRuntimeServiceC extends AbstractMockKieRuntimeService {

    @Override
    public boolean canManageInput(String fullResourceName, KieMemoryCompiler.MemoryCompilerClassLoader memoryCompilerClassLoader) {
        return fullResourceName.equals(MockDARInputC.class.getSimpleName());
    }

}
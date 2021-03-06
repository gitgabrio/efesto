package org.kie.efesto.runtimemanager.api.utils;/*
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

import org.junit.jupiter.api.BeforeAll;
import org.kie.efesto.runtimemanager.api.mocks.MockKieRuntimeServiceAB;
import org.kie.efesto.runtimemanager.api.mocks.MockKieRuntimeServiceC;
import org.kie.efesto.runtimemanager.api.service.KieRuntimeService;
import org.kie.memorycompiler.KieMemoryCompiler;

import java.util.Arrays;
import java.util.List;

class TestSPIUtils {

    private static final List<Class<? extends KieRuntimeService>> KIE_RUNTIME_SERVICES = Arrays.asList(MockKieRuntimeServiceAB.class, MockKieRuntimeServiceC.class);

    private static KieMemoryCompiler.MemoryCompilerClassLoader memoryCompilerClassLoader;

    @BeforeAll
    static void setUp() {
        memoryCompilerClassLoader = new KieMemoryCompiler.MemoryCompilerClassLoader(Thread.currentThread().getContextClassLoader());
    }


//    @Test
//    void getKieRuntimeService() {
//        Optional<KieRuntimeService> retrieved = SPIUtils.getKieRuntimeService(new FRI(MockEfestoInputA.class.getPackageName(), MockEfestoInputA.class.getSimpleName()), true, memoryCompilerClassLoader);
//        assertTrue(retrieved.isPresent());
//        assertTrue(retrieved.get() instanceof MockKieRuntimeServiceAB);
//        retrieved = SPIUtils.getKieRuntimeService(new FRI(MockEfestoInputA.class.getPackageName(), MockEfestoInputA.class.getSimpleName()), true, memoryCompilerClassLoader);
//        assertTrue(retrieved.isPresent());
//        assertTrue(retrieved.get() instanceof MockKieRuntimeServiceAB);
//        retrieved = SPIUtils.getKieRuntimeService(new FRI(MockEfestoInputC.class.getPackageName(), MockEfestoInputC.class.getSimpleName()), true, memoryCompilerClassLoader);
//        assertTrue(retrieved.isPresent());
//        assertTrue(retrieved.get() instanceof MockKieRuntimeServiceC);
//        retrieved = SPIUtils.getKieRuntimeService(new FRI(MockEfestoInputD.class.getPackageName(), MockEfestoInputD.class.getSimpleName()), true, memoryCompilerClassLoader);
//        assertTrue(retrieved.isEmpty());
//    }

//    @Test
//    void getKieRuntimeServices() {
//        List<KieRuntimeService> retrieved = SPIUtils.getKieRuntimeServices(true);
//        assertNotNull(retrieved);
//        assertEquals(KIE_RUNTIME_SERVICES.size(), retrieved.size());
//    }
}
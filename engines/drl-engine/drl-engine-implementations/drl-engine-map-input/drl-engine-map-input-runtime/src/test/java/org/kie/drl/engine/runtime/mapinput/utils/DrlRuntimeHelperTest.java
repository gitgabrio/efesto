package org.kie.drl.engine.runtime.mapinput.utils;/*
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
import org.junit.jupiter.api.Test;
import org.kie.dar.common.api.model.FRI;
import org.kie.dar.runtimemanager.api.model.AbstractDARInput;
import org.kie.dar.runtimemanager.api.model.DARMapInputDTO;
import org.kie.drl.engine.mapinput.compilation.model.test.Applicant;
import org.kie.drl.engine.mapinput.compilation.model.test.LoanApplication;
import org.kie.drl.engine.runtime.mapinput.model.DARInputDrlMap;
import org.kie.drl.engine.runtime.mapinput.model.DAROutputDrlMap;
import org.kie.memorycompiler.KieMemoryCompiler;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


class DrlRuntimeHelperTest {

    private static final String basePath = "LoanApplication";
    private static KieMemoryCompiler.MemoryCompilerClassLoader memoryCompilerClassLoader;

    @BeforeAll
    static void setUp() {
        memoryCompilerClassLoader = new KieMemoryCompiler.MemoryCompilerClassLoader(Thread.currentThread().getContextClassLoader());
    }

    @Test
    void canManage() {
        FRI fri = new FRI(basePath, "drl");
        AbstractDARInput darInputDrlMap = new DARInputDrlMap(fri, null);
        assertThat(DrlRuntimeHelper.canManage(darInputDrlMap)).isTrue();
        darInputDrlMap = new AbstractDARInput(fri, "") {
        };
        assertThat(DrlRuntimeHelper.canManage(darInputDrlMap)).isFalse();
        fri = new FRI("notexisting", "drl");
        darInputDrlMap = new DARInputDrlMap(fri, null);
        assertThat(DrlRuntimeHelper.canManage(darInputDrlMap)).isFalse();
    }

    @Test
    void execute() {
        List<Object> inserts = new ArrayList<>();

        inserts.add(new LoanApplication("ABC10001", new Applicant("John", 45), 2000, 1000));
        inserts.add(new LoanApplication("ABC10002", new Applicant("Paul", 25), 5000, 100));
        inserts.add(new LoanApplication("ABC10015", new Applicant("George", 12), 1000, 100));

        List<LoanApplication> approvedApplications = new ArrayList<>();
        final Map<String, Object> globals = new HashMap<>();
        globals.put("approvedApplications", approvedApplications);
        globals.put("maxAmount", 5000);

        DARMapInputDTO darMapInputDTO = new DARMapInputDTO(inserts, globals, Collections.emptyMap(), Collections.emptyMap());

        DARInputDrlMap darInputDrlMap = new DARInputDrlMap(new FRI(basePath, "drl"), darMapInputDTO);
        Optional<DAROutputDrlMap> retrieved = DrlRuntimeHelper.execute(darInputDrlMap, memoryCompilerClassLoader);
        assertThat(retrieved).isNotNull().isPresent();
        assertThat(approvedApplications).hasSize(1);
        LoanApplication approvedApplication = approvedApplications.get(0);
        assertThat(approvedApplication).isEqualTo(inserts.get(0));
    }
}
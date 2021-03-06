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
package org.kie.efesto.compilationmanager.api.service;

import org.kie.efesto.common.api.io.IndexFile;
import org.kie.efesto.compilationmanager.api.model.EfestoResource;
import org.kie.memorycompiler.KieMemoryCompiler;

import java.util.List;

public interface CompilationManager {

    /**
     * Produce one <code>EfestoCallableOutput</code> from the given <code>EfestoRedirectOutput</code>.
     * The return is <code>Optional</code> because the engine required to process given <code>EfestoRedirectOutput</code>
     * may not be found
     *
     * @param toProcess
     * @return
     */
    List<IndexFile> processResource(EfestoResource toProcess, KieMemoryCompiler.MemoryCompilerClassLoader memoryCompilerClassLoader);

//    /**
//     * Produce a <code>List&lt;EfestoCallableOutput&gt;</code> from the given <code>List&lt;EfestoRedirectOutput&gt;</code>
//     *
//     * @param toProcess
//     * @return
//     */
//    List<IndexFile> processResources(List<EfestoRedirectOutput> toProcess, KieMemoryCompiler.MemoryCompilerClassLoader memoryCompilerClassLoader);


}

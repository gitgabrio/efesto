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
package org.kie.efesto.compilationmanager.core.utils;

import org.kie.efesto.common.api.exceptions.KieEfestoCommonException;
import org.kie.efesto.common.api.io.IndexFile;
import org.kie.efesto.common.api.model.*;
import org.kie.efesto.compilationmanager.api.exceptions.KieCompilerServiceException;
import org.kie.efesto.compilationmanager.api.model.*;
import org.kie.efesto.compilationmanager.api.service.KieCompilerService;
import org.kie.memorycompiler.KieMemoryCompiler;
import org.kie.memorycompiler.KieMemoryCompilerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.kie.efesto.common.api.constants.Constants.INDEXFILE_DIRECTORY_PROPERTY;
import static org.kie.efesto.common.api.utils.FileUtils.getFileFromFileName;
import static org.kie.efesto.common.api.utils.JSONUtils.getGeneratedResourcesObject;
import static org.kie.efesto.common.api.utils.JSONUtils.writeGeneratedResourcesObject;
import static org.kie.efesto.compilationmanager.api.utils.SPIUtils.getKieCompilerService;

public class CompilationManagerUtils {

    private static final Logger logger = LoggerFactory.getLogger(CompilationManagerUtils.class.getName());
    private static final String DEFAULT_INDEXFILE_DIRECTORY = "./target/classes";

    private CompilationManagerUtils() {
    }

    public static void populateIndexFilesWithProcessedResource(final List<IndexFile> toPopulate, EfestoResource toProcess, KieMemoryCompiler.MemoryCompilerClassLoader memoryCompilerClassLoader) {
        Optional<KieCompilerService> retrieved = getKieCompilerService(toProcess, true);
        if (retrieved.isEmpty()) {
            logger.warn("Cannot find KieCompilerService for {}", toProcess.getClass());
            return;
        }
        Optional<List<EfestoCompilationOutput>> darCompilationOutputOptional = retrieved.map(service -> service.processResource(toProcess, memoryCompilerClassLoader));
        darCompilationOutputOptional.ifPresent(darCompilationOutputs -> {
            Optional<IndexFile> indexFileOptional = getIndexFileFromCompilationOutputs(darCompilationOutputs);
            indexFileOptional.ifPresent(indexFile -> {
                toPopulate.add(indexFile);
                darCompilationOutputs.forEach(darCompilationOutput -> {
                    populateIndexFile(indexFile, darCompilationOutput);
                    if (darCompilationOutput instanceof EfestoCallableOutputClassesContainer) {
                        loadClasses(((EfestoCallableOutputClassesContainer) darCompilationOutput).getCompiledClassesMap(), memoryCompilerClassLoader);
                    }
                    if (darCompilationOutput instanceof EfestoRedirectOutput) {
                        populateIndexFilesWithProcessedResource(toPopulate, (EfestoRedirectOutput) darCompilationOutput, memoryCompilerClassLoader);
                    }
                });
            });
        });
    }

    static Optional<IndexFile> getIndexFileFromCompilationOutputs(List<EfestoCompilationOutput> compilationOutputs) {
        return compilationOutputs.stream()
                .filter(EfestoCallableOutput.class::isInstance)
                .map(EfestoCallableOutput.class::cast)
                .map(CompilationManagerUtils::getIndexFile)
                .findFirst();
    }

    static IndexFile getIndexFile(EfestoCallableOutput compilationOutput) {
        String parentPath = System.getProperty(INDEXFILE_DIRECTORY_PROPERTY, DEFAULT_INDEXFILE_DIRECTORY);
        IndexFile toReturn = new IndexFile(parentPath, compilationOutput.getFri().getModel());
        File existingFile;
        try {
            existingFile = getFileFromFileName(toReturn.getName());
            toReturn = new IndexFile(existingFile);
            logger.debug("IndexFile {} already exists", toReturn.getName());
        } catch (KieEfestoCommonException e) {
            logger.debug("IndexFile {} does not exists, creating it...", toReturn.getName());
            createIndexFile(toReturn);
        }
        return toReturn;
    }

    static void createIndexFile(IndexFile toCreate) {
        try {
            logger.debug("Writing file {}", toCreate.getPath());
            if (!toCreate.createNewFile()) {
                throw new KieCompilerServiceException("Failed to create " + toCreate.getName());
            }
        } catch (IOException e) {
            logger.error("Failed to create {} due to {}", toCreate.getName(), e);
            throw new KieCompilerServiceException("Failed to create " + toCreate.getName(), e);
        }
    }

    static void populateIndexFile(IndexFile toPopulate, EfestoCompilationOutput compilationOutput) {
        try {
            GeneratedResources generatedResources = getGeneratedResourcesObject(toPopulate);
            populateGeneratedResources(generatedResources, compilationOutput);
            writeGeneratedResourcesObject(generatedResources, toPopulate);
        } catch (IOException e) {
            throw new KieCompilerServiceException(e);
        }
    }

    static void populateGeneratedResources(GeneratedResources toPopulate, EfestoCompilationOutput compilationOutput) {
        toPopulate.add(getGeneratedResource(compilationOutput));
        if (compilationOutput instanceof EfestoClassesContainer) {
            toPopulate.addAll(getGeneratedResources((EfestoClassesContainer) compilationOutput));
        }
    }

    static GeneratedResource getGeneratedResource(EfestoCompilationOutput compilationOutput) {
        if (compilationOutput instanceof EfestoRedirectOutput) {
            return new GeneratedRedirectResource(((EfestoRedirectOutput) compilationOutput).getFri(), ((EfestoRedirectOutput) compilationOutput).getTargetEngine());
        } else if (compilationOutput instanceof EfestoCallableOutput) {
            return new GeneratedExecutableResource(((EfestoCallableOutput) compilationOutput).getFri(), ((EfestoCallableOutput) compilationOutput).getFullClassNames());
        } else {
            throw new KieCompilerServiceException("Unmanaged type " + compilationOutput.getClass().getName());
        }

    }

    static List<GeneratedResource> getGeneratedResources(EfestoClassesContainer finalOutput) {
        return finalOutput.getCompiledClassesMap().keySet().stream()
                .map(CompilationManagerUtils::getGeneratedClassResource)
                .collect(Collectors.toList());
    }

    static GeneratedClassResource getGeneratedClassResource(String fullClassName) {
        return new GeneratedClassResource(fullClassName);
    }

    static void loadClasses(Map<String, byte[]> compiledClassesMap, KieMemoryCompiler.MemoryCompilerClassLoader memoryCompilerClassLoader) {
        for (Map.Entry<String, byte[]> entry : compiledClassesMap.entrySet()) {
            memoryCompilerClassLoader.addCode(entry.getKey(), entry.getValue());
            try {
                memoryCompilerClassLoader.loadClass(entry.getKey());
            } catch (ClassNotFoundException e) {
                throw new KieMemoryCompilerException(e.getMessage(), e);
            }
        }
    }
}



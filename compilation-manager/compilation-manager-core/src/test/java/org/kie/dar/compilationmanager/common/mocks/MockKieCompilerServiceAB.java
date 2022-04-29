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
package org.kie.dar.compilationmanager.common.mocks;

import org.kie.dar.compilationmanager.api.model.DARResource;

import java.util.Arrays;
import java.util.List;

public class MockKieCompilerServiceAB extends MockKieCompilerService {

    private static List<Class<? extends DARResource>> managedResources = Arrays.asList(MockDARResourceA.class, MockDARResourceB.class);

    @Override
    public boolean manageResource(DARResource toProcess) {
        return managedResources.contains(toProcess.getClass());
    }

}

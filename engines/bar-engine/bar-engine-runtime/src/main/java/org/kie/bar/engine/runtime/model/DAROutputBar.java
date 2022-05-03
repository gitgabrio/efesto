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
package org.kie.bar.engine.runtime.model;

import org.kie.dar.runtimemanager.api.model.DAROutput;

public class DAROutputBar implements DAROutput {

    private final String fullResourceName;
    private final Object outputData;

    public DAROutputBar(String fullResourceName, Object outputData) {
        this.fullResourceName = fullResourceName;
        this.outputData = outputData;
    }

    @Override
    public String getFullResourceName() {
        return fullResourceName;
    }

    @Override
    public Object getOutputData() {
        return outputData;
    }
}

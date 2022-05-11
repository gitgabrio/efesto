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
package org.kie.dar.common.api.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "step-type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GeneratedIntermediateResource.class, name = "intermediate"),
        @JsonSubTypes.Type(value = GeneratedFinalResource.class, name = "final")
})
public abstract class GeneratedResource implements Serializable {

    private final String fullPath;
    private final String type;

    protected GeneratedResource(String fullPath, String type) {
        this.fullPath = fullPath;
        this.type = type;
    }

    public String getFullPath() {
        return fullPath;
    }

    public String getType() {
        return type;
    }

    /**
     * Two <code>GeneratedResource</code>s are equals if they have the same full path
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        GeneratedResource that = (GeneratedResource) o;
        return fullPath.equals(that.fullPath);
    }

    @Override
    public int hashCode() {
        // TODO verify
        return 1;
    }
}

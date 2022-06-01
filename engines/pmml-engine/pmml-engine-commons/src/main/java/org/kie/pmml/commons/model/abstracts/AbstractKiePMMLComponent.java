/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.pmml.commons.model.abstracts;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.kie.pmml.commons.model.KiePMMLExtension;

/**
 * Abstract class base of all other <b>KiePMML</b> components.
 *
 * All of them will have a <b>name</b>, a <code>List&lt;KiePMMLExtension&gt;</code> and an autogenerated <b>id</b>.
 *
 * Contained/nested components will also have the <b>parentId</b>.
 *
 * For complex instantiation, concrete classes should extend the <b>Builder</b> defined in the current one
 *
 */
public abstract class AbstractKiePMMLComponent implements Serializable {

    private static final long serialVersionUID = -130007459165854426L;

    protected final String name;
    protected final List<KiePMMLExtension> extensions;
    protected String id;
    protected String parentId;

    protected AbstractKiePMMLComponent(String name, List<KiePMMLExtension> extensions) {
        this.name = name;
        this.extensions = extensions;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    /**
     * @return <b>unmodifiable</b> <code>List&lt;KiePMMLExtension&gt;</code>
     */
    public List<KiePMMLExtension> getExtensions() {
        return Collections.unmodifiableList(extensions);
    }

    public static class Builder<T extends AbstractKiePMMLComponent> {

        private static final AtomicInteger counter = new AtomicInteger(1);
        protected T toBuild;

        protected Builder(String prefix, Supplier<T> supplier) {
            this.toBuild = supplier.get();
            this.toBuild.id = prefix + counter.getAndAdd(1);
        }

        protected Builder withParentId(String parentId) {
            this.toBuild.parentId = parentId;
            return this;
        }

        public T build() {
            return toBuild;
        }
    }
}

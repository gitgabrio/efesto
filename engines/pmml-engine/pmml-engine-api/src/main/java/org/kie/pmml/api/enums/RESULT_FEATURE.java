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
package org.kie.pmml.api.enums;

import org.kie.pmml.api.exceptions.KieEnumException;

import java.util.Arrays;
import java.util.Objects;

/**
 * @see <a href=http://dmg.org/pmml/v4-4/Output.html#xsdType_RESULT-FEATURE>RESULT_FEATURE</a>
 */
public enum RESULT_FEATURE implements Named {
    PREDICTED_VALUE("predictedValue"),
    PREDICTED_DISPLAY_VALUE("predictedDisplayValue"),
    TRANSFORMED_VALUE("transformedValue"),
    DECISION("decision"),
    PROBABILITY("probability"),
    AFFINITY("affinity"),
    RESIDUAL("residual"),
    STANEfestoD_ERROR("standardError"),
    STANEfestoD_DEVIATION("standardDeviation"),
    CLUSTER_ID("clusterId"),
    CLUSTER_AFFINITY("clusterAffinity"),
    ENTITY_ID("entityId"),
    ENTITY_AFFINITY("entityAffinity"),
    WARNING("warning"),
    RULE_VALUE("ruleValue"),
    REASON_CODE("reasonCode"),
    ANTECEDENT("antecedent"),
    CONSEQUENT("consequent"),
    RULE("rule"),
    RULE_ID("ruleId"),
    CONFIDENCE("confidence"),
    SUPPORT("support"),
    LIFT("lift"),
    LEVERAGE("leverage");

    public static final RESULT_FEATURE DEFAULT = PREDICTED_VALUE;

    private final String name;

    RESULT_FEATURE(String name) {
        this.name = name;
    }

    public static RESULT_FEATURE byName(String name) {
        return Arrays.stream(RESULT_FEATURE.values()).filter(value -> Objects.equals(name, value.name)).findFirst().orElseThrow(() -> new KieEnumException("Failed to find RESULT_FEATURE with name: " + name));
    }

    public static RESULT_FEATURE getOrDefault(RESULT_FEATURE input) {
        return input == null ? DEFAULT : input;
    }

    public String getName() {
        return name;
    }
}

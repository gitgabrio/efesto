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
package org.kie.pmml.compilation.commons.factories;

import org.dmg.pmml.Discretize;
import org.junit.jupiter.api.Test;
import org.kie.pmml.commons.model.expressions.KiePMMLDiscretize;

import static org.kie.pmml.compilation.api.testutils.PMMLModelTestUtils.getRandomDiscretize;
import static org.kie.pmml.compilation.commons.factories.InstanceFactoriesTestCommon.commonVerifyKiePMMLDiscretize;

public class KiePMMLDiscretizeInstanceFactoryTest {

    @Test
    void getKiePMMLDiscretize() {
        Discretize toConvert = getRandomDiscretize();
        KiePMMLDiscretize retrieved = KiePMMLDiscretizeInstanceFactory.getKiePMMLDiscretize(toConvert);
        commonVerifyKiePMMLDiscretize(retrieved, toConvert);
    }
}
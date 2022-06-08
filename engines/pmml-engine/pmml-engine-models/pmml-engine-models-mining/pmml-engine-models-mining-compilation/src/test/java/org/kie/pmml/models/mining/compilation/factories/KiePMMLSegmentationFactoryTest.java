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

package org.kie.pmml.models.mining.compilation.factories;

import org.dmg.pmml.mining.MiningModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kie.pmml.commons.model.KiePMMLModel;
import org.kie.pmml.compilation.api.dto.CommonCompilationDTO;
import org.kie.pmml.compilation.commons.mocks.HasClassLoaderMock;
import org.kie.pmml.models.mining.compilation.dto.MiningModelCompilationDTO;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kie.pmml.commons.Constants.PACKAGE_NAME;

public class KiePMMLSegmentationFactoryTest extends AbstractKiePMMLFactoryTest {

    @BeforeAll
    public static void setup() throws IOException, JAXBException, SAXException {
        innerSetup();
    }

    @Test
    void getSegmentationSourcesMap() {
        final List<KiePMMLModel> nestedModels = new ArrayList<>();
        final CommonCompilationDTO<MiningModel> source =
                CommonCompilationDTO.fromGeneratedPackageNameAndFields(PACKAGE_NAME,
                        pmml,
                        MINING_MODEL,
                        new HasClassLoaderMock());
        final MiningModelCompilationDTO compilationDTO =
                MiningModelCompilationDTO.fromCompilationDTO(source);
        final Map<String, String> retrieved = KiePMMLSegmentationFactory.getSegmentationSourcesMap(compilationDTO,
                nestedModels);
        assertThat(retrieved).isNotNull();
        int expectedNestedModels = MINING_MODEL.getSegmentation().getSegments().size();
        assertThat(nestedModels).hasSize(expectedNestedModels);
    }

}
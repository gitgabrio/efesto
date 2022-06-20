/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
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

package org.kie.pmml.compilation.commons.codegenfactories;

import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.NormDiscrete;
import org.junit.jupiter.api.Test;
import org.kie.pmml.commons.model.expressions.KiePMMLNormDiscrete;
import org.kie.pmml.compilation.commons.utils.JavaParserUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kie.efesto.common.api.utils.FileUtils.getFileContent;
import static org.kie.pmml.compilation.commons.testutils.CodegenTestUtils.commonValidateCompilationWithImports;

public class KiePMMLNormDiscreteFactoryTest {

    private static final String TEST_01_SOURCE = "KiePMMLNormDiscreteFactoryTest_01.txt";

    @Test
    void getNormDiscreteVariableDeclaration() throws IOException {
        String variableName = "variableName";
        String fieldName = "fieldName";
        String fieldValue = "fieldValue";
        double mapMissingTo = 45.32;

        NormDiscrete normDiscrete = new NormDiscrete();
        normDiscrete.setField(FieldName.create(fieldName));
        normDiscrete.setValue(fieldValue);
        normDiscrete.setMapMissingTo(mapMissingTo);

        BlockStmt retrieved = KiePMMLNormDiscreteFactory.getNormDiscreteVariableDeclaration(variableName,
                normDiscrete);
        String text = getFileContent(TEST_01_SOURCE);
        Statement expected = JavaParserUtils.parseBlock(String.format(text, variableName, fieldName, fieldValue, mapMissingTo));
        assertThat(JavaParserUtils.equalsNode(expected, retrieved)).isTrue();
        List<Class<?>> imports = Arrays.asList(Collections.class, KiePMMLNormDiscrete.class);
        commonValidateCompilationWithImports(retrieved, imports);
    }

}
package org.kie.dar.common.api.utils;/*
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

import org.junit.jupiter.api.Test;
import org.kie.dar.common.api.exceptions.KieDARCommonException;
import org.kie.dar.common.api.utils.FileUtils;

import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    private final static String TEST_FILE = "TestingEmptyFile.txt";
    private final static String NOT_EXISTING_FILE = "NotExistingFile.txt";

    @Test
    void getInputStreamFromFileNameExisting() {
        InputStream retrieved = FileUtils.getInputStreamFromFileName(TEST_FILE);
        assertNotNull(retrieved);
    }

    @Test
    void getInputStreamFromFileNameNotExisting() {
        try {
            FileUtils.getInputStreamFromFileName(NOT_EXISTING_FILE);
            fail("Expecting KieDARCommonException thrown");
        } catch (Exception e) {
         assertTrue(e instanceof KieDARCommonException);
        }
    }

    @Test
    void getFileFromFileNameExisting() {
        File retrieved = FileUtils.getFileFromFileName(TEST_FILE);
        assertNotNull(retrieved);
    }
    @Test
    void getFileFromFileNameNotExisting() {
        try {
            FileUtils.getFileFromFileName(NOT_EXISTING_FILE);
            fail("Expecting KieDARCommonException thrown");
        } catch (Exception e) {
            assertTrue(e instanceof KieDARCommonException);
        }
    }

}
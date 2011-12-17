/*
 * Copyright (C) 2011 JFrog Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jfrog.build.client;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.LinkedList;
import java.util.Map;

/**
 * A map of artifact specs per configuration
 *
 * @author Yoav Landman
 */
public class ArtifactSpecs extends LinkedList<ArtifactSpec> {

    public ArtifactSpecs() {
    }

    /**
     * Build a new ArtifactSpecs from a newline separated string
     *
     * @param specsNotation A sting containing artifact spec notations separated by the newline (\n) or (\r\n)
     *                      character(s)
     */
    public ArtifactSpecs(String specsNotation) {
        if (specsNotation != null) {
            String[] notations = specsNotation.split("\r{0,1}\n");
            for (String notation : notations) {
                ArtifactSpec spec = ArtifactSpec.newSpec(notation);
                add(spec);
            }
        }
    }

    /**
     * Iterate over all the specs and if matches add the properties
     *
     * @param spec
     * @return
     */
    public Multimap<String, CharSequence> getProperties(ArtifactSpec spec) {
        Multimap<String, CharSequence> props = ArrayListMultimap.create();
        for (ArtifactSpec matcherSpec : this) {
            if (matcherSpec.matches(spec)) {
                Map<String, CharSequence> matcherSpecProperties = matcherSpec.getProperties();
                for (Map.Entry<String, CharSequence> entry : matcherSpecProperties.entrySet()) {
                    props.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return props;
    }
}

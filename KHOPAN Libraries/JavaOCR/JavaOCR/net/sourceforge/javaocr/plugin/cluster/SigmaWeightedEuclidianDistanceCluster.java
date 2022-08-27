/*
 * Copyright (c) 2003-2012, Ronald B. Cemer , Konstantin Pribluda, William Whitney, Andrea De Pasquale
 *
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.sourceforge.javaocr.plugin.cluster;

/**
 * normalise  dimenstion by sigma
 */
public class SigmaWeightedEuclidianDistanceCluster extends NormalDistributionCluster {
    /**
     * create cluster calculating euclidian distance between center and feature
     * vector
     *
     * @param c    assotiated character
     * @param size size of feature cluster
     */
    public SigmaWeightedEuclidianDistanceCluster(int dimensions) {
        super(dimensions);
    }

    /**
     * compute dimension normalised by sigma
     * @param dimension
     * @param i
     * @return
     */
    @Override
    double computeDimension(double dimension, int i) {
        return super.computeDimension(dimension, i) / Math.sqrt(getQuads()[i]);
    }
}

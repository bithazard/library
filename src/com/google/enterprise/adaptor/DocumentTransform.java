// Copyright 2011 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.enterprise.adaptor;

import java.util.Map;

/**
 * Represents an individual transform in the transform pipeline.
 *
 * <p>Implementations should also typically have a static factory method with a
 * single {@code Map<String, String>} argument for creating instances based on
 * configuration.
 */
public interface DocumentTransform {
  /**
   * Any changes to {@code metadata} and {@code params} will be
   * passed on to subsequent transforms. This method must be thread-safe.
   * @param metadata of document
   * @param params are extra contextual information
   */
  public void transform(Metadata metadata, Map<String, String> params);
}

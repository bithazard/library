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

package adaptorlib;

import java.util.*;

/**
 * Abstract Adaptor that provides reasonable default implementations of many
 * {@link Adaptor} methods.
 */
public abstract class AbstractAdaptor implements Adaptor {
  /**
   * {@inheritDoc}
   *
   * <p>This implementation does nothing.
   */
  @Override
  public void setDocIdPusher(DocIdPusher pusher) {}

  /**
   * {@inheritDoc}
   *
   * <p>This implementation provides {@link AuthzStatus#PERMIT} for all {@code
   * DocId}s in an unmodifiable map.
   */
  @Override
  public Map<DocId, AuthzStatus> isUserAuthorized(String userIdentifier,
                                                  Set<String> groups,
                                                  Collection<DocId> ids) {
    Map<DocId, AuthzStatus> result
        = new HashMap<DocId, AuthzStatus>(ids.size() * 2);
    for (DocId id : ids) {
      result.put(id, AuthzStatus.PERMIT);
    }
    return Collections.unmodifiableMap(result);
  }
}
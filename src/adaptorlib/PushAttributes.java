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

import java.net.URL;
import java.util.Date;

/** Controls for pushed {@link DocId} that dictate GSA's treatment. */
public final class PushAttributes {

  /** Specifies default values for all attributes of pushed {@link DocId}. */
  public static final PushAttributes DEFAULT = new Builder().build();

  private final boolean delete;
  private final Date lastModified;
  private final URL displayUrl;
  private final boolean crawlImmediately;
  private final boolean crawlOnce;
  private final boolean lock;

  private PushAttributes(boolean delete, Date lastModified, URL displayUrl,
      boolean crawlImmediately, boolean crawlOnce, boolean lock) {
    this.delete = delete;
    this.lastModified = lastModified;
    this.displayUrl = displayUrl;
    this.crawlImmediately = crawlImmediately;
    this.crawlOnce = crawlOnce;
    this.lock = lock;
  }

  public boolean isToBeDeleted() {
    return delete;
  }

  public Date lastModified() {
    return lastModified;
  }

  public URL displayUrl() {
    return displayUrl;
  }

  public boolean crawlImmediately() {
    return crawlImmediately;
  }

  public boolean crawlOnce() {
    return crawlOnce;
  }

  public boolean lock() {
    return lock;
  }

  @Override
  public boolean equals(Object o) {
    boolean same = false;
    if (null != o && this.getClass().equals(o.getClass())) {
      PushAttributes other = (PushAttributes) o;
      same = (this.delete == other.delete)
          && (this.crawlImmediately == other.crawlImmediately)
          && (this.crawlOnce == other.crawlOnce)
          && (this.lock == other.lock)
          && equalsNullSafe(lastModified, other.lastModified)
          && equalsNullSafe(displayUrl, other.displayUrl);
    } 
    return same;
  }

  @Override
  public int hashCode() {
    int code = (null == lastModified) ? 0 : lastModified.hashCode() * 31;
    code += (null == displayUrl) ? 0 : displayUrl.hashCode();
    return code;
  }

  @Override
  public String toString() {
    return "PushAttributes(delete=" + delete
        + ",lastModified=" + lastModified
        + ",displayUrl=" + displayUrl
        + ",crawlImmediately=" + crawlImmediately
        + ",crawlOnce=" + crawlOnce
        + ",lock=" + lock + ")";
  }

  /**
   * Used to create instances of PushAttributes, which are immutable.
   * All fields have default values.
   */
  public static class Builder {
    private boolean delete = false;
    private Date lastModified;
    private URL displayUrl;
    private boolean crawlImmediately = false;
    private boolean crawlOnce = false;
    private boolean lock = false;

    public Builder() {}
  
    public Builder setDeleteFromIndex(boolean b) {
      this.delete = b;
      return this;
    }
  
    public Builder setLastModified(Date lastModified) {
      this.lastModified = lastModified;
      return this;
    }
  
    public Builder setDisplayUrl(URL displayUrl) {
      this.displayUrl = displayUrl;
      return this;
    }
  
    public Builder setCrawlImmediately(boolean b) {
      this.crawlImmediately = crawlImmediately;
      return this;
    }
  
    public Builder setCrawlOnce(boolean b) {
      this.crawlOnce = crawlOnce;
      return this;
    }
  
    public Builder setLock(boolean b) {
      this.lock = lock;
      return this;
    }

    /** Creates single instance of PushAttributes.  Does not reset builder. */
    public PushAttributes build() {
      return new PushAttributes(delete, lastModified, displayUrl,
          crawlImmediately, crawlOnce, lock);
    }
  }

  private static boolean equalsNullSafe(Object a, Object b) {
    boolean same;
    if (null == a && null == b) {
      same = true;
    } else if (null != a && null != b) {
      same = a.equals(b);
    } else {
      same = false;
    }
    return same;
  }
}

package adaptorlib;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
/** DocId refers to a unique document in repository.
  You give the GSA a DocId to have it insert your
  document for crawl and index.
  The GSA provides the DocId when it asks your code
  for some information about a particular document in
  repository.  For example when the GSA wants the bytes
  of a particular document or when it wants to find
  out if a particular user has read permissions for it.
  For deleting document from GSA see DeletedDocId
  subclass. */
public class DocId {
  private final String uniqId;  // Not null.
  private final DocReadPermissions access;  // Who has access?

  /** Constructs DocId that is marked public (visible by all). */
  public DocId(String id) {
    this(id, DocReadPermissions.IS_PUBLIC);
  }

  /** Creates with id and specific permissions. */
  public DocId(String id, DocReadPermissions acl) {
    if (null == id) {
      throw new IllegalArgumentException("id cannot be null");
    }
    if (null == acl) {
      throw new IllegalArgumentException("permissions must be provided");
    }
    this.uniqId = id;
    this.access = acl;
  }

  public String getUniqueId() {
    return uniqId;
  }
  DocReadPermissions getDocReadPermissions() {
    return access;
  }    

  /** "DocId(" + uniqId + "|" + access + ")" */
  public String toString() {
    return "DocId(" + uniqId + "|" + access + ")";
  }

  private URI encode() {
    if (Config.passDocIdToGsaWithoutModification()) {
      return URI.create(uniqId);
    } else {
      URI base = Config.getBaseUri(this);
      URI resource;
      try {
        resource = new URI(null, null, base.getPath() + Config.getDocIdPath()
                           + uniqId, null);
      } catch (URISyntaxException ex) {
        throw new IllegalStateException(ex);
      }
      return base.resolve(resource);
    }
  }

  /** Provides URL used in feed file sent to GSA. */
  URL getFeedFileUrl() {
    try {
      return encode().toURL();
    } catch (MalformedURLException e) {
      throw new IllegalStateException("unable to safely encode " + this, e);
    }
  }

  /** This default action is "add". */
  String getFeedFileAction() {
    return "add";
  } 

  /** Given a URI that was used in feed file, convert back to doc id. */
  static DocId decode(URI uri) {
    if (Config.passDocIdToGsaWithoutModification()) {
      return new DocId(uri.toString());
    } else {
      String basePath = Config.getBaseUri().getPath();
      String id = uri.getPath().substring(basePath.length()
                                          + Config.getDocIdPath().length());
      return new DocId(id);
    }
  }

  public boolean equals(Object o) {
    boolean same = false;
    if (null != o && getClass().equals(o.getClass())) {
      DocId d = (DocId) o;
      same = this.uniqId.equals(d.uniqId)
          && this.access.equals(d.access);
    }
    return same;
  }

  public int hashCode() {
    return this.uniqId.hashCode();
  }
}

// Implement in terms of generic DocId builder. 
/** A DeletedDocId is a DocId that when sent to GSA
  results in quickly removing referenced document
  from crawling and index.
  <p> Please note that GSA will figure out a document
  is deleted on its own and sending a DeletedDocId is
  optional.  Sending the GSA DeletedDocId
  instances will be faster than waiting for GSA to
  realize a document has been deleted.
  <p> Look at DocId for more details. */
//class DeletedDocId extends DocId {
//  DeletedDocId(String id) {
//    super(id, DocReadPermissions.USE_HEAD_REQUEST);
//  }
//
//  /** Provides delete for action attribute value. */
//  String getFeedFileAction() {
//    return "delete";
//  } 
//
//  /** "DeletedDocId(" + getUniqueId() + ")" */
//  public String toString() {
//    return "DeletedDocId(" + getUniqueId() + ")";
//  }
//}

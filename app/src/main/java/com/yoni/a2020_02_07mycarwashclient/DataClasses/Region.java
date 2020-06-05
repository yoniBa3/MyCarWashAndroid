
package com.yoni.a2020_02_07mycarwashclient.DataClasses;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.util.List;
import java.util.Date;

public class Region
{
  private Date created;
  private String ownerId;
  private String objectId;
  private Date updated;
  private String name;
  public Date getCreated()
  {
    return created;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

                                                    
  public Region save()
  {
    return Backendless.Data.of( Region.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Region> callback )
  {
    Backendless.Data.of( Region.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Region.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Region.class ).remove( this, callback );
  }

  public static Region findById( String id )
  {
    return Backendless.Data.of( Region.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Region> callback )
  {
    Backendless.Data.of( Region.class ).findById( id, callback );
  }

  public static Region findFirst()
  {
    return Backendless.Data.of( Region.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Region> callback )
  {
    Backendless.Data.of( Region.class ).findFirst( callback );
  }

  public static Region findLast()
  {
    return Backendless.Data.of( Region.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Region> callback )
  {
    Backendless.Data.of( Region.class ).findLast( callback );
  }

  public static List<Region> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Region.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Region>> callback )
  {
    Backendless.Data.of( Region.class ).find( queryBuilder, callback );
  }
}
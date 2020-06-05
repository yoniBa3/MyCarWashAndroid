
package com.yoni.a2020_02_07mycarwashclient.DataClasses;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.util.List;
import java.util.Date;

public class Wash
{
  private Date updated;
  private String objectId;
  private Date created;
  private String ownerId;
  private Date time;
  private String carNumber;
  public Date getUpdated()
  {
    return updated;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public Date getTime()
  {
    return time;
  }

  public void setTime( Date time )
  {
    this.time = time;
  }

  public String getCarNumber()
  {
    return carNumber;
  }

  public void setCarNumber( String carNumber )
  {
    this.carNumber = carNumber;
  }

                                                    
  public Wash save()
  {
    return Backendless.Data.of( Wash.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Wash> callback )
  {
    Backendless.Data.of( Wash.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Wash.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Wash.class ).remove( this, callback );
  }

  public static Wash findById( String id )
  {
    return Backendless.Data.of( Wash.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Wash> callback )
  {
    Backendless.Data.of( Wash.class ).findById( id, callback );
  }

  public static Wash findFirst()
  {
    return Backendless.Data.of( Wash.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Wash> callback )
  {
    Backendless.Data.of( Wash.class ).findFirst( callback );
  }

  public static Wash findLast()
  {
    return Backendless.Data.of( Wash.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Wash> callback )
  {
    Backendless.Data.of( Wash.class ).findLast( callback );
  }

  public static List<Wash> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Wash.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Wash>> callback )
  {
    Backendless.Data.of( Wash.class ).find( queryBuilder, callback );
  }
}
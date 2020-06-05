
package com.yoni.a2020_02_07mycarwashclient.DataClasses;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.util.List;
import java.util.Date;

public class Services
{
  private String objectId;
  private String ownerId;
  private Date created;
  private Date updated;
  private Double cost;
  private Integer time;
  private String name;
  public String getObjectId()
  {
    return objectId;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public Date getCreated()
  {
    return created;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public Double getCost()
  {
    return cost;
  }

  public void setCost( Double cost )
  {
    this.cost = cost;
  }

  public Integer getTime()
  {
    return time;
  }

  public void setTime( Integer time )
  {
    this.time = time;
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

                                                    
  public Services save()
  {
    return Backendless.Data.of( Services.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Services> callback )
  {
    Backendless.Data.of( Services.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Services.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Services.class ).remove( this, callback );
  }

  public static Services findById( String id )
  {
    return Backendless.Data.of( Services.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Services> callback )
  {
    Backendless.Data.of( Services.class ).findById( id, callback );
  }

  public static Services findFirst()
  {
    return Backendless.Data.of( Services.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Services> callback )
  {
    Backendless.Data.of( Services.class ).findFirst( callback );
  }

  public static Services findLast()
  {
    return Backendless.Data.of( Services.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Services> callback )
  {
    Backendless.Data.of( Services.class ).findLast( callback );
  }

  public static List<Services> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Services.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Services>> callback )
  {
    Backendless.Data.of( Services.class ).find( queryBuilder, callback );
  }
}
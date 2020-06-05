
package com.yoni.a2020_02_07mycarwashclient.DataClasses;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.util.List;
import java.util.Date;

public class Machon
{
  private String oHour;
  private String ownerId;
  private String city;
  private String cHour;
  private Date created;
  private String tel;
  private String name;
  private Date updated;
  private String objectId;
  private String address;
  public String getOHour()
  {
    return oHour;
  }

  public void setOHour( String oHour )
  {
    this.oHour = oHour;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getCity()
  {
    return city;
  }

  public void setCity( String city )
  {
    this.city = city;
  }

  public String getCHour()
  {
    return cHour;
  }

  public void setCHour( String cHour )
  {
    this.cHour = cHour;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getTel()
  {
    return tel;
  }

  public void setTel( String tel )
  {
    this.tel = tel;
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress( String address )
  {
    this.address = address;
  }

                                                    
  public Machon save()
  {
    return Backendless.Data.of( Machon.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Machon> callback )
  {
    Backendless.Data.of( Machon.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Machon.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Machon.class ).remove( this, callback );
  }

  public static Machon findById( String id )
  {
    return Backendless.Data.of( Machon.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Machon> callback )
  {
    Backendless.Data.of( Machon.class ).findById( id, callback );
  }

  public static Machon findFirst()
  {
    return Backendless.Data.of( Machon.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Machon> callback )
  {
    Backendless.Data.of( Machon.class ).findFirst( callback );
  }

  public static Machon findLast()
  {
    return Backendless.Data.of( Machon.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Machon> callback )
  {
    Backendless.Data.of( Machon.class ).findLast( callback );
  }

  public static List<Machon> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Machon.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Machon>> callback )
  {
    Backendless.Data.of( Machon.class ).find( queryBuilder, callback );
  }
}

package com.yoni.a2020_02_07mycarwashclient.users;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.util.List;
import java.util.Date;

public class UserInfo
{
  private String email;
  private String objectId;
  private String name;
  private String CarNumber;
  private String ownerId;
  private Date created;
  private Date updated;
  private String phoneNumber;
  public String getEmail()
  {
    return email;
  }

  public void setEmail( String email )
  {
    this.email = email;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

  public String getCarNumber()
  {
    return CarNumber;
  }

  public void setCarNumber( String CarNumber )
  {
    this.CarNumber = CarNumber;
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

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public void setPhoneNumber( String phoneNumber )
  {
    this.phoneNumber = phoneNumber;
  }

                                                    
  public UserInfo save()
  {
    return Backendless.Data.of( UserInfo.class ).save( this );
  }

  public void saveAsync( AsyncCallback<UserInfo> callback )
  {
    Backendless.Data.of( UserInfo.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( UserInfo.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( UserInfo.class ).remove( this, callback );
  }

  public static UserInfo findById( String id )
  {
    return Backendless.Data.of( UserInfo.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<UserInfo> callback )
  {
    Backendless.Data.of( UserInfo.class ).findById( id, callback );
  }

  public static UserInfo findFirst()
  {
    return Backendless.Data.of( UserInfo.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<UserInfo> callback )
  {
    Backendless.Data.of( UserInfo.class ).findFirst( callback );
  }

  public static UserInfo findLast()
  {
    return Backendless.Data.of( UserInfo.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<UserInfo> callback )
  {
    Backendless.Data.of( UserInfo.class ).findLast( callback );
  }

  public static List<UserInfo> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( UserInfo.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<UserInfo>> callback )
  {
    Backendless.Data.of( UserInfo.class ).find( queryBuilder, callback );
  }
}
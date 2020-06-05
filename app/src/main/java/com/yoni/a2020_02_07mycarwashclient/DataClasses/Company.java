
package com.yoni.a2020_02_07mycarwashclient.DataClasses;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.util.List;
import java.util.Date;

public class Company
{
  private String ownerId;
  private String address;
  private String objectId;
  private String email;
  private String fax;
  private Date updated;
  private String person;
  private Date created;
  private String phoneNumber;
  private String name;
  public String getOwnerId()
  {
    return ownerId;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress( String address )
  {
    this.address = address;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail( String email )
  {
    this.email = email;
  }

  public String getFax()
  {
    return fax;
  }

  public void setFax( String fax )
  {
    this.fax = fax;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public String getPerson()
  {
    return person;
  }

  public void setPerson( String person )
  {
    this.person = person;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public void setPhoneNumber( String phoneNumber )
  {
    this.phoneNumber = phoneNumber;
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

                                                    
  public Company save()
  {
    return Backendless.Data.of( Company.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Company> callback )
  {
    Backendless.Data.of( Company.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Company.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Company.class ).remove( this, callback );
  }

  public static Company findById( String id )
  {
    return Backendless.Data.of( Company.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Company> callback )
  {
    Backendless.Data.of( Company.class ).findById( id, callback );
  }

  public static Company findFirst()
  {
    return Backendless.Data.of( Company.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Company> callback )
  {
    Backendless.Data.of( Company.class ).findFirst( callback );
  }

  public static Company findLast()
  {
    return Backendless.Data.of( Company.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Company> callback )
  {
    Backendless.Data.of( Company.class ).findLast( callback );
  }

  public static List<Company> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Company.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Company>> callback )
  {
    Backendless.Data.of( Company.class ).find( queryBuilder, callback );
  }
}
package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Submitted type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Submitteds", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Submitted implements Model {
  public static final QueryField ID = field("Submitted", "id");
  public static final QueryField HEARTRATE = field("Submitted", "heartrate");
  public static final QueryField BLOODPRESSURE = field("Submitted", "bloodpressure");
  public static final QueryField STEPS = field("Submitted", "steps");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Float") Double heartrate;
  private final @ModelField(targetType="String") String bloodpressure;
  private final @ModelField(targetType="Int") Integer steps;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public Double getHeartrate() {
      return heartrate;
  }
  
  public String getBloodpressure() {
      return bloodpressure;
  }
  
  public Integer getSteps() {
      return steps;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Submitted(String id, Double heartrate, String bloodpressure, Integer steps) {
    this.id = id;
    this.heartrate = heartrate;
    this.bloodpressure = bloodpressure;
    this.steps = steps;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Submitted submitted = (Submitted) obj;
      return ObjectsCompat.equals(getId(), submitted.getId()) &&
              ObjectsCompat.equals(getHeartrate(), submitted.getHeartrate()) &&
              ObjectsCompat.equals(getBloodpressure(), submitted.getBloodpressure()) &&
              ObjectsCompat.equals(getSteps(), submitted.getSteps()) &&
              ObjectsCompat.equals(getCreatedAt(), submitted.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), submitted.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getHeartrate())
      .append(getBloodpressure())
      .append(getSteps())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Submitted {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("heartrate=" + String.valueOf(getHeartrate()) + ", ")
      .append("bloodpressure=" + String.valueOf(getBloodpressure()) + ", ")
      .append("steps=" + String.valueOf(getSteps()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Submitted justId(String id) {
    return new Submitted(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      heartrate,
      bloodpressure,
      steps);
  }
  public interface BuildStep {
    Submitted build();
    BuildStep id(String id);
    BuildStep heartrate(Double heartrate);
    BuildStep bloodpressure(String bloodpressure);
    BuildStep steps(Integer steps);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private Double heartrate;
    private String bloodpressure;
    private Integer steps;
    @Override
     public Submitted build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Submitted(
          id,
          heartrate,
          bloodpressure,
          steps);
    }
    
    @Override
     public BuildStep heartrate(Double heartrate) {
        this.heartrate = heartrate;
        return this;
    }
    
    @Override
     public BuildStep bloodpressure(String bloodpressure) {
        this.bloodpressure = bloodpressure;
        return this;
    }
    
    @Override
     public BuildStep steps(Integer steps) {
        this.steps = steps;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Double heartrate, String bloodpressure, Integer steps) {
      super.id(id);
      super.heartrate(heartrate)
        .bloodpressure(bloodpressure)
        .steps(steps);
    }
    
    @Override
     public CopyOfBuilder heartrate(Double heartrate) {
      return (CopyOfBuilder) super.heartrate(heartrate);
    }
    
    @Override
     public CopyOfBuilder bloodpressure(String bloodpressure) {
      return (CopyOfBuilder) super.bloodpressure(bloodpressure);
    }
    
    @Override
     public CopyOfBuilder steps(Integer steps) {
      return (CopyOfBuilder) super.steps(steps);
    }
  }
  
}

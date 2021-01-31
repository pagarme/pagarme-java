
package me.pagar.model;

import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author ta-ma
 */
public class Operation extends PagarMeModel<String> {
   @Expose
   private String status;
   @Expose
   private String failReason;
   @Expose
   private String type;
   @Expose
   private boolean rollbacked;
   @Expose
   private String model;
   @Expose 
   private String modelId;
   @Expose
   private String groupId;
   @Expose
   private String nextGroupId;
   @Expose
   private String requestId;
   @Expose
   private long startedAt;
   @Expose
   private long endedAt;
   @Expose 
   private String processor;
   @Expose
   private String processorResponseCode;
   @Expose
   private Map<String,Object> metadata;
   @Expose
   private Date dateUpdated; 

    public String getStatus() {
        return status;
    }

    public String getFailReason() {
        return failReason;
    }

    public String getType() {
        return type;
    }

    public boolean isRollbacked() {
        return rollbacked;
    }

    public String getModel() {
        return model;
    }

    public String getModelId() {
        return modelId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getNextGroupId() {
        return nextGroupId;
    }

    public String getRequestId() {
        return requestId;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public long getEndedAt() {
        return endedAt;
    }

    public String getProcessor() {
        return processor;
    }

    public String getProcessorResponseCode() {
        return processorResponseCode;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }
   
}

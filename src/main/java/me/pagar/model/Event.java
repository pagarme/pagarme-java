
package me.pagar.model;

import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author ta-ma
 */
public class Event extends PagarMeModel<String> {
    @Expose
    private String name;
    @Expose
    private String model;
    @Expose
    private String model_id;
    @Expose
    private Map<String,Object> payload;
    @Expose
    private Date dateUpdated; 
    
    
    
}

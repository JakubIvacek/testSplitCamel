import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class PropertyPreservingAggregationStrategy implements AggregationStrategy {
    
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            return newExchange;
        }
        
        if (newExchange != null && newExchange.getProperties() != null) {
            newExchange.getProperties().forEach((key, value) -> {
                oldExchange.setProperty(key, value);
            });
        }
        
        if (newExchange != null && newExchange.getException() != null) {
            oldExchange.setException(newExchange.getException());
        }
        
        return oldExchange;
    }
}
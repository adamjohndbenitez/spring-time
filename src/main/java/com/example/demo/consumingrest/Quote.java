package com.example.demo.consumingrest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This simple Java class has a handful of properties and matching getter methods.
 * It is annotated with @JsonIgnoreProperties from the Jackson JSON processing library
 * to indicate that any properties not bound in this type should be ignored.
 * To directly bind your data to your custom types, you need to specify the variable name
 * to be exactly the same as the key in the JSON document returned from the API.
 * In case your variable name and key in JSON doc do not match, you can use @JsonProperty
 * annotation to specify the exact key of the JSON document.
 * (This example matches each variable name to a JSON key, so you do not need that annotation here.)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    private String type;
    private Value value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "type='" + type + "\'" +
                ", value=" + value + "}";
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Value {
        private Long id;
        private String quote;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }

        @Override
        public String toString() {
            return "Value{" +
                    "id=" + id +
                    ", quote=" + quote + "\'" +
                    "}";
        }
    }
}
